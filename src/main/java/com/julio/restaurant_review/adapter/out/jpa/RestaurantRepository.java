package com.julio.restaurant_review.adapter.out.jpa;

import com.julio.restaurant_review.domain.model.Image;
import com.julio.restaurant_review.domain.model.Restaurant;
import com.julio.restaurant_review.application.dtos.PaginatedRestaurantQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("""
            SELECT new com.julio.restaurant_review.application.dtos.PaginatedRestaurantQueryDTO(
                restaurant.id,
                restaurant.name,
                AVG(reviews.rating),
                COUNT(reviews.id)
            )
            FROM Restaurant restaurant
            LEFT JOIN restaurant.reviews reviews
            INNER JOIN restaurant.address address
            WHERE (LOWER(restaurant.name) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL)
              AND (LOWER(address.city) = LOWER(:city) OR :city IS NULL)
              AND (LOWER(address.neighborhood) = LOWER(:neighborhood) OR :neighborhood IS NULL)
            GROUP BY restaurant.id
            """)
    Page<PaginatedRestaurantQueryDTO> findAllPaginated(
            @Param("name") String name,
            @Param("city") String city,
            @Param("neighborhood") String neighborhood,
            Pageable pageable
    );

    @Query("""
                SELECT i
                FROM Restaurant restaurant
                LEFT JOIN restaurant.images i
                WHERE restaurant.id = :restaurantId
            """)
    Set<Image> findImagesByRestaurantId(@Param("restaurantId") Long restaurantId);
}