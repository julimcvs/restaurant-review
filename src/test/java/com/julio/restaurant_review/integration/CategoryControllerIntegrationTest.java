package com.julio.restaurant_review.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julio.restaurant_review.config.TestConfigs;
import com.julio.restaurant_review.model.dto.CategoryListDTO;
import com.julio.restaurant_review.model.dto.CreateCategoryDTO;
import com.julio.restaurant_review.model.dto.ErrorResponseDTO;
import com.julio.restaurant_review.model.entity.Category;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerIntegrationTest extends AbstractIntegrationTest {
    private static RequestSpecification requestSpecification;
    private static ObjectMapper objectMapper;
    private Category category;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        requestSpecification = new RequestSpecBuilder()
                .setBasePath("categories")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Creates a category and returns 200")
    void integrationTestCreatesCategoryAndReturns200() throws IOException {
        // Given / Arrange
        // When / Act
        // Then / Assert
        var input = new CreateCategoryDTO("new category");
        var content = given().spec(requestSpecification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(input)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Category output = objectMapper.readValue(content, Category.class);
        assertTrue(output.getId() > 0);
        assertEquals(output.getName(), input.name());
        category = output;
    }

    @Test
    @DisplayName("Fails to create a category with blank name and returns 400")
    void integrationTestFailToCreateCategoryWithBlankNameAndReturns400() throws IOException {
        var input = new CreateCategoryDTO("    ");
        var content = given().spec(requestSpecification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(input)
                .when()
                .post()
                .then()
                .statusCode(400)
                .extract()
                .body()
                .asString();
        ErrorResponseDTO output = objectMapper.readValue(content, ErrorResponseDTO.class);
        assertTrue(output.getMessage().contains("não deve estar em branco"));
    }

    @Test
    @DisplayName("Fails to create a category with null name and returns 400")
    void integrationTestFailToCreateCategoryWithNullNameAndReturns400() throws IOException {
        var input = new CreateCategoryDTO(null);
        var content = given().spec(requestSpecification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(input)
                .when()
                .post()
                .then()
                .statusCode(400)
                .extract()
                .body()
                .asString();
        ErrorResponseDTO output = objectMapper.readValue(content, ErrorResponseDTO.class);
        assertTrue(output.getMessage().contains("não deve estar em branco"));
    }

    @Test
    @DisplayName("Gets all Categories and returns 200")
    void integrationTestGetsAllCategoriesAndReturns200() throws IOException {
        var content = given().spec(requestSpecification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        List<CategoryListDTO> output = objectMapper.readValue(content,  new TypeReference<List<CategoryListDTO>>(){});
        assertTrue(output.size() > 0);
    }
}
