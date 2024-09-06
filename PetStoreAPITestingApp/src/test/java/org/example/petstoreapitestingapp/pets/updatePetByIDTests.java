package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.ApiResponse;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class updatePetByIDTests extends PetsTestBase {
    private static Response response;
    private static final String PATH = "/pet/{id}?";
    private static Pet returnedPet;
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
                .given(RequestUtils.petIDRequestSpec(BASE_URI, PATH, 55))
                .queryParams(Map.of(
                        "name","Charles",
                        "status","pending"))
                .log().all()
                .when()
                .post()
                .thenReturn();
        response.prettyPrint();
        returnedPet = response.as(Pet.class);
        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));
    }
    @Test
    @Order(2)
    @DisplayName("Check update gives correct name")
    public void checkUpdateGivesCorrectName() {
        MatcherAssert.assertThat(returnedPet.getName(), org.hamcrest.Matchers.is("Charles"));
    }
    @Test
    @Order(3)
    @DisplayName("Check update gives correct status")
    public void checkUpdateGivesCorrectStatus() {
        MatcherAssert.assertThat(returnedPet.getStatus(), org.hamcrest.Matchers.is("pending"));
    }
}
