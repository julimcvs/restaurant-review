package com.julio.restaurant_review.services;

import com.julio.restaurant_review.controllers.FilesController;
import com.julio.restaurant_review.exceptions.BadRequestException;
import com.julio.restaurant_review.exceptions.NotFoundException;
import com.julio.restaurant_review.model.dto.FilterRestaurantDTO;
import com.julio.restaurant_review.model.dto.FindVacanciesRequestDTO;
import com.julio.restaurant_review.model.dto.FindVacanciesResponseDTO;
import com.julio.restaurant_review.model.dto.ImageInfoDTO;
import com.julio.restaurant_review.model.dto.PaginatedRestaurantResponseDTO;
import com.julio.restaurant_review.model.dto.RestaurantConfigurationDTO;
import com.julio.restaurant_review.model.dto.RestaurantDTO;
import com.julio.restaurant_review.model.dto.RestaurantDetailsDTO;
import com.julio.restaurant_review.model.dto.RestaurantScheduleDTO;
import com.julio.restaurant_review.model.dto.TableSettingDTO;
import com.julio.restaurant_review.model.entity.Address;
import com.julio.restaurant_review.model.entity.Image;
import com.julio.restaurant_review.model.entity.Restaurant;
import com.julio.restaurant_review.model.entity.RestaurantConfiguration;
import com.julio.restaurant_review.model.entity.RestaurantSchedule;
import com.julio.restaurant_review.model.entity.TableSetting;
import com.julio.restaurant_review.repositories.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository repository;
    private final CategoryService categoryService;
    private final FileStorageService storageService;
    private final ReservationService reservationService;

    public RestaurantService(
            RestaurantRepository repository,
            CategoryService categoryService,
            FileStorageService storageService,
            @Lazy ReservationService reservationService
    ) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.reservationService = reservationService;
    }

    private static ImageInfoDTO getImageInfoDTO(Path path) {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
                .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
        return new ImageInfoDTO(filename, url);
    }

    private static void validateTableSetting(FindVacanciesRequestDTO input, Set<TableSetting> tableSettings, Integer reservationCount) {
        var tableSetting = tableSettings
                .stream()
                .filter(setting -> setting.getTableFor() == input.tableFor())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("A vacantion with the specified settings was not found."));
        if (tableSetting.getVacancyAmount() <= reservationCount) {
            throw new BadRequestException("Not enough vacancies for the specified settings.");
        }
    }

    public static Set<String> generateTimeSet(LocalDate inputDate, RestaurantSchedule todaySchedule, Integer intervalMinutes) {
        LinkedHashSet<String> timeSet = new LinkedHashSet<>();
        LocalTime now = LocalTime.now();
        LocalTime openingTime = todaySchedule.getOpeningTime();
        LocalTime closingTime = todaySchedule.getClosingTime();
        LocalTime startTime = openingTime;

        // If the input scheduledDate is today, set the start time to the next valid interval after now
        if (inputDate.isEqual(LocalDate.now())) {
            Integer minutesSinceOpening = (int) now.until(openingTime, java.time.temporal.ChronoUnit.MINUTES);
            if (minutesSinceOpening > 0) {
                // If current time is before opening, start at opening time
                startTime = openingTime;
            } else {
                // Find the next valid interval
                int minutesPastInterval = now.getMinute() % intervalMinutes;
                int minutesToNextInterval = intervalMinutes - minutesPastInterval;
                startTime = now.plusMinutes(minutesToNextInterval).withSecond(0).withNano(0);

                // Ensure startTime is not before openingTime
                if (startTime.isBefore(openingTime)) {
                    startTime = openingTime;
                }
            }
        }
        var startDate = LocalDate.now().atTime(startTime);
        var closingDate = LocalDate.now().atTime(closingTime);

        // Generate time slots in intervals of `intervalMinutes`
        for (LocalDateTime date = startDate; !date.isAfter(closingDate); date = date.plusMinutes(intervalMinutes)) {
            var time = date.toLocalTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Format without seconds
            timeSet.add(time.format(formatter));
        }

        return timeSet;
    }

    public RestaurantDetailsDTO findById(Long id) {
        var restaurant = findEntityById(id);
        var imagesInfo = getImagesInfoByFilenames(restaurant.getImages().stream().map(Image::getFilename).collect(Collectors.toSet()));
        return new RestaurantDetailsDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                Address.toDTO(restaurant.getAddress()),
                imagesInfo
        );
    }

    public RestaurantConfigurationDTO findConfigurationById(Long id) {
        var restaurant = findEntityById(id);
        var configuration = restaurant.getConfiguration();
        if (configuration == null) {
            return null;
        }
        return new RestaurantConfigurationDTO(
                configuration.getVacancyInterval(),
                configuration
                        .getSchedules()
                        .stream()
                        .map(schedule -> new RestaurantScheduleDTO(schedule.getDayOfWeek(), schedule.getOpeningTime(), schedule.getClosingTime()))
                        .collect(Collectors.toSet()),
                configuration
                        .getTableSettings()
                        .stream()
                        .map(tableSetting -> new TableSettingDTO(tableSetting.getTableFor(), tableSetting.getVacancyAmount()))
                        .collect(Collectors.toSet())
        );
    }

    public Restaurant findEntityById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
    }

    public Page<PaginatedRestaurantResponseDTO> findAllPaginated(FilterRestaurantDTO filter, Pageable pageable) {
        var output = repository.findAllPaginated(
                filter.name(),
                filter.city(),
                filter.neighborhood(),
                pageable
        );
        return output.map(restaurant -> {
            var image = repository.findImagesByRestaurantId(restaurant.getId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("No images were found for restaurant " + restaurant.getName()));
            var imageInfo = getImageInfoByFilename(image.getFilename());
            return new PaginatedRestaurantResponseDTO(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getAverageRating(),
                    restaurant.getTotalReviews(),
                    imageInfo
            );
        });
    }

    public FindVacanciesResponseDTO generateVacancies(Long id, FindVacanciesRequestDTO input) {
        var config = getConfigurationById(id);
        var reservationCount = this.reservationService.countReservationsByRestaurant(id, input.tableFor(), input.date());
        var tableSettings = config.getTableSettings();
        validateTableSetting(input, tableSettings, reservationCount);
        var dateSchedule = config.getSchedules()
                .stream()
                .filter(schedule -> schedule.getDayOfWeek() == input.date().getDayOfWeek())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Restaurant is not opened in the specified scheduledDate."));
        var timeSet = generateTimeSet(input.date(), dateSchedule, config.getVacancyInterval());
        return new FindVacanciesResponseDTO(timeSet);
    }

    public void validateVacancyAvailability(Integer tableFor, RestaurantConfiguration configuration, Integer reservationCount) {
        var tableSettings = configuration.getTableSettings();
        var tableSetting = tableSettings
                .stream()
                .filter(setting -> setting.getTableFor() == tableFor)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("A vacantion with the specified settings was not found."));
        if (tableSetting.getVacancyAmount() <= reservationCount) {
            throw new BadRequestException("Not enough vacancies for the specified settings.");
        }
    }

    private RestaurantConfiguration getConfigurationById(Long id) {
        var config = this.findEntityById(id).getConfiguration();
        if (config == null) {
            throw new BadRequestException("Restaurant not configured yet.");
        }
        return config;
    }

    public Restaurant save(RestaurantDTO input, MultipartFile[] images) {
        var restaurant = Restaurant.fromDTO(input);
        if (input.id() != null) {
            restaurant = repository
                    .findById(input.id())
                    .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        }
        saveImages(images, restaurant);
        var address = Address.fromDTO(input.address());
        restaurant.setAddress(address);
        var category = categoryService.findById(input.categoryId());
        restaurant.setCategory(category);
        return repository.save(restaurant);
    }

    public void updateConfiguration(Long restaurantId, RestaurantConfigurationDTO input) {
        var restaurant = findEntityById(restaurantId);
        var configuration = restaurant.getConfiguration();
        var newConfiguration = RestaurantConfiguration.fromDTO(input);
        if (configuration != null) {
            newConfiguration.setId(configuration.getId());
        }
        restaurant.setConfiguration(newConfiguration);
        repository.save(restaurant);
    }

    private Set<ImageInfoDTO> getImagesInfoByFilenames(Set<String> filenames) {
        return storageService
                .loadAll(filenames)
                .map(RestaurantService::getImageInfoDTO)
                .collect(Collectors.toSet());
    }

    private ImageInfoDTO getImageInfoByFilename(String filename) {
        return Optional.of(storageService
                        .loadFilePath(filename))
                .map(RestaurantService::getImageInfoDTO)
                .orElseThrow(() -> new NotFoundException("Image not found"));
    }

    private void saveImages(MultipartFile[] images, Restaurant restaurant) {
        if (images == null || images.length == 0) {
            throw new BadRequestException("You must provide at least one image");
        }
        if (images.length > 10) {
            throw new BadRequestException("You can only provide up to 10 images");
        }
        try {
            Arrays.asList(images).forEach(storageService::save);

        } catch (Exception e) {
            throw new BadRequestException("Could not store the files. Error: " + e.getMessage());
        }

        var imageEntities = Arrays.stream(images)
                .map(file -> new Image(file.getOriginalFilename()))
                .collect(Collectors.toSet());
        restaurant.setImages(imageEntities);
    }
}
