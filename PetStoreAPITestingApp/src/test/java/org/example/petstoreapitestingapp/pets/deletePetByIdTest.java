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
public class deletePetByIdTest extends PetsTestBase {

    private static Response response;
    private static Pet newPet;
    private static TagsItem tagsItem1;
    private static TagsItem tagsItem2;
    private static Pet returnedPet;
    private static final String UPLOAD_PATH = "/pet";
    private static final String DELETE_PATH = "/pet/{petId}";
    @BeforeAll
    public static void setUp() {
        tagsItem1 = new TagsItem(0,"Cat");
        tagsItem2 = new TagsItem(1,"Dog");
        newPet = new Pet(
                56,
                "John",
                new Category(3, "CatAndDog"),
                List.of("string"),
                List.of(tagsItem1,tagsItem2),
                "available"
        );
        response = RestAssured
                .given(RequestUtils.petRequestSpec(
                        BASE_URI,
                        UPLOAD_PATH,
                        newPet
                ))
                .when()
                .post()
                .thenReturn();
    }
    @Test
    @Order(1)
    @DisplayName("Delete pet by ID gives 200 response")
    public void deletePetByIdGives200Response() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(DELETE_PATH)
                .pathParam("petId", 56)
                .header("api_key", "")
                .when()
                .delete()
                .thenReturn();

        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));
    }
}
