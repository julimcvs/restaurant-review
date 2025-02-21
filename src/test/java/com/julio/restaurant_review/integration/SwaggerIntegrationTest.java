package com.julio.restaurant_review.integration;

import com.julio.restaurant_review.config.TestConfigs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {
    @Test
    @DisplayName("Swagger UI Page should be shown correctly")
    public void TestShouldDisplaySwaggerUiPage() {
        var content = given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfigs.SERVER_PORT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().body().asString();
        assertTrue(content.contains("Swagger UI"));
    }
}
