package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class updatePetByIDTests extends PetsTestBase {
    private static Response response;
    private static final String PATH = "/pet/{id}?";

    @BeforeAll
    public static void setUp() {
    }
    @AfterAll
    public static void tearDown() {
    }

    @Test
    @Order(1)
    @DisplayName("200 response code from update pet by ID")
    public void updatePetByIDResponseCode() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PATH)
                .pathParam("id", 55)
                .queryParams(Map.of(
                        "name","Charles",
                        "status","pending"))
                .log().all()
                .when()
                .post()
                .thenReturn();
        response.prettyPrint();
        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));
    }
    @Test
    @Order(2)
    @DisplayName("Check update gives correct name")
    public void checkUpdateGivesCorrectName() {
        MatcherAssert.assertThat(response.getBody().jsonPath().getString("name"), org.hamcrest.Matchers.is("Charles"));
    }
}
