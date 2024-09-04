package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestGetPetById extends PetsTestBase {

    private static Response response;

    @BeforeAll
    public static void beforeAll() {

        Map<String, String> headers = new HashMap<>();

        Map<String, String> pathParams = new HashMap<>();
        headers.put("petId", "1");

        response = RestAssured
                .given(RequestUtils.getRequestSpec(
                        "https://petstore3.swagger.io/api/v3/",
                        "pet/{petId}",
                        pathParams,
                        headers
                ))
                .when()
                .get()
                .thenReturn();
        petResponse = response.as(Pet.class);
        response.prettyPrint();
    }

    @Test
    void testPetHasAnIdOf1() {
        MatcherAssert.assertThat(petResponse.getId(), Matchers.is(1));
    }
}
