package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class GetPetByIdTests extends PetsTestBase {

    private static final String PATH_UNDER_TEST = "pet/{petId}";

    @BeforeAll
    public static void beforeAll() {
        response = RestAssured
                .given(RequestUtils.getRequestSpec(
                        BASE_URI,
                        PATH_UNDER_TEST,
                        Map.of("petId", "55")
                ))
                .when()
                .get()
                .thenReturn();
        petResponse = response.as(Pet.class);
        response.prettyPrint();
    }

    @Test
    void testPetHasAnIdOf1() {
        MatcherAssert.assertThat(petResponse.getId(), Matchers.is(55L));
    }

    @Test
    void testResponseHasStatus200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    void testPetCategoryIsDogs() {
        MatcherAssert.assertThat(petResponse.getCategory().getName(), Matchers.equalTo("Dogs"));
    }

    @Test
    void testPetStatusIsAvailable() {
        MatcherAssert.assertThat(petResponse.getStatus(), Matchers.equalTo("available"));
    }

    @Test
    void testResponseHasCorrectHeaders() {
        MatcherAssert.assertThat(response.getHeader("Content-Type"), Matchers.equalTo("application/json"));
        MatcherAssert.assertThat(response.getHeader("Access-Control-Allow-Origin"), Matchers.equalTo("*"));
    }

    @Test
    void testResponseBodyIsNotEmpty() {
        MatcherAssert.assertThat(response.getBody().asString().length(), Matchers.greaterThan(0));
    }

    @Test
    void testPetNotFound() {
        Response notFoundResponse = RestAssured
                .given(RequestUtils.getRequestSpec(
                        BASE_URI,
                        PATH_UNDER_TEST,
                        Map.of("petId", "999999")
                ))
                .when()
                .get()
                .thenReturn();
        MatcherAssert.assertThat(notFoundResponse.statusCode(), Matchers.is(404));
    }

    @Test
    void testInvalidPetIdReturns400() {
        Response badRequestResponse = RestAssured
                .given(RequestUtils.getRequestSpec(
                        BASE_URI,
                        PATH_UNDER_TEST,
                        Map.of("petId", "invalidFormat")
                ))
                .when()
                .get()
                .thenReturn();
        MatcherAssert.assertThat(badRequestResponse.statusCode(), Matchers.is(400));
    }
}
