package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetPetByTagsTests extends PetsTestBase {

    private static final String PATH_UNDER_TEST = "pet/findByTags";

    @BeforeAll
    public static void beforeAll() {
        response = RestAssured
                .given(RequestUtils.getRequestSpec(
                        BASE_URI,
                        PATH_UNDER_TEST
                ))
                .queryParams("tags", "0", "tags", "itc")
                .when()
                .get()
                .thenReturn();
        pets = response.as(Pet[].class);
        response.prettyPrint();
    }

    @Test
    void testResponseHasStatus200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    void testResponseContainsMultiplePets() {
        MatcherAssert.assertThat(pets.length, Matchers.greaterThan(0));
    }

    @Test
    void testAllPetsHaveCorrectTag() {
        for (Pet pet : pets) {
            boolean hasCorrectTag = pet.getTags().stream()
                    .anyMatch(tag -> "itc".equals(tag.getName()) && tag.getId() == 0);
            MatcherAssert.assertThat(hasCorrectTag, Matchers.is(true));
        }
    }

    @Test
    void testEmptyResponseForInvalidTag() {
        Response invalidTagResponse = RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, PATH_UNDER_TEST))
                .queryParam("tags", "nonexistentTag")
                .when()
                .get()
                .thenReturn();

        Pet[] petsWithInvalidTag = invalidTagResponse.as(Pet[].class);
        MatcherAssert.assertThat(petsWithInvalidTag.length, Matchers.is(0));
    }

    @Test
    void testResponseForTagNotFound() {
        Response tagNotFoundResponse = RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, PATH_UNDER_TEST))
                .queryParam("tags", "9999")
                .when()
                .get()
                .thenReturn();

        MatcherAssert.assertThat(tagNotFoundResponse.statusCode(), Matchers.is(200));
        Pet[] petsWithTagNotFound = tagNotFoundResponse.as(Pet[].class);
        MatcherAssert.assertThat(petsWithTagNotFound.length, Matchers.is(0));
    }


}
