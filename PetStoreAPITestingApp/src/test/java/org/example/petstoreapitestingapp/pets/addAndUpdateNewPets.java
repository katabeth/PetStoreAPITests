package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.pojo.Category;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.example.petstoreapitestingapp.pojo.TagsItem;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class addAndUpdateNewPets extends PetsTestBase{

    private static Response response;

    @BeforeAll
    public static void setUp() {

    }
    @AfterAll
    public static void tearDown() {

    }

    @Test
    @DisplayName("Add new pet to the store")
    public void addNewPet() {
        TagsItem tagsItem = new TagsItem(0,"String");
        Pet newPet = new Pet(
                10,
                "Lion",
                new Category(2, "Cats"),
                List.of("string"),
                List.of(tagsItem),
                "available"
        );

        response = RestAssured
                .given()
                .baseUri(PetsTestBase.BASE_URI)
                .basePath("/pet")
                .contentType(ContentType.JSON)
                .body(newPet)
                .log().all()
                .when()
                .post()
                .thenReturn();
        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));
    }

}
