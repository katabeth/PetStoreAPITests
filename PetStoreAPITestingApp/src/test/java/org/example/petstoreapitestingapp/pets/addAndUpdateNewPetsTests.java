package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.Category;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.example.petstoreapitestingapp.pojo.TagsItem;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class addAndUpdateNewPetsTests extends PetsTestBase{

    private static Response response;
    private static Pet newPet;
    private static Pet updatePet;
    private static TagsItem tagsItem;
    private static final String PATH = "/pet";

    @BeforeAll
    public static void setUp() {
        tagsItem = new TagsItem(0,"String");
        newPet = new Pet(
                55,
                "Lion",
                new Category(2, "Cats"),
                List.of("string"),
                List.of(tagsItem),
                "available"
        );
        updatePet = new Pet(
                55,
                "Potatoes",
                new Category(2, "Cats"),
                List.of("string"),
                List.of(tagsItem),
                "available"
        );
    }
    @AfterAll
    public static void tearDown() {

    }

    @Test
    @Order(1)
    @DisplayName("Add new pet to the store")
    public void addNewPet() {
        response = RestAssured
                .given(RequestUtils.petRequestSpec(
                BASE_URI,
                PATH,
                newPet
                ))
                .when()
                .post()
                .thenReturn();

        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));
    }
    @Test
    @Order(2)
    @DisplayName("Check added pet gives correct name")
    public void checkAddedPetGivesCorrectName() {
        MatcherAssert.assertThat(response.getBody().jsonPath().getString("name"), org.hamcrest.Matchers.is("Lion"));
    }

    @Test
    @Order(3)
    @DisplayName("Update pet in the store")
    public void updatePet() {
        response = RestAssured
                .given(RequestUtils.petRequestSpec(
                        PetsTestBase.BASE_URI,
                        PATH,
                        newPet
                ))
                .when()
                .put()
                .thenReturn();
        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));
    }
    @Test
    @Order(4)
    @DisplayName("Check update gives correct name")
    public void checkUpdateGivesCorrectName() {
        MatcherAssert.assertThat(response.getBody().jsonPath().getString("name"), org.hamcrest.Matchers.is("Potatoes"));
        }
}
