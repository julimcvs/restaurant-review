package com.julio.restaurant_review.repositories;

import com.julio.restaurant_review.model.dto.PaginatedRestaurantQueryDTO;
import com.julio.restaurant_review.model.entity.Image;
import com.julio.restaurant_review.model.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("""
            SELECT new com.julio.restaurant_review.model.dto.PaginatedRestaurantQueryDTO(
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