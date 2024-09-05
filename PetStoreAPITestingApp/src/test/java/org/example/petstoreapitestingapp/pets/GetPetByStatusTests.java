package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GetPetByStatusTests extends PetsTestBase {

    private static final String PATH_UNDER_TEST = "pet/findByStatus";

    @BeforeAll
    public static void beforeAll() {
        response = RestAssured
                .given(RequestUtils.getRequestSpec(
                        BASE_URI,
                        PATH_UNDER_TEST
                ))
                .queryParam("status","available")
                .when()
                .get()
                .thenReturn();
        pets = response.as(Pet[].class);
    }

    @Test
    @DisplayName("Check status code is 200")
    public void GetPetByStatus_200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Check status is available")
    public void GetPetByStatus_StatusIsAvailable() {
        MatcherAssert.assertThat(response.jsonPath().getString("[0].status"), Matchers.is("available"));
    }
}
