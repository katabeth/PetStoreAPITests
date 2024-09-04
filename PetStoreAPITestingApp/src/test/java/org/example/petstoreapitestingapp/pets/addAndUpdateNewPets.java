package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Pet pet = new Pet(
                10,,

        );

        RestAssured
                .given()
                .baseUri(PetsTestBase.BASE_URI)
                .basePath("/v2/pet")
                .contentType("application/json")
                .body(pet)
                .when()
                .post()
    }

}
