package org.example.petstoreapitestingapp.pets;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class uploadingImageTest extends PetsTestBase{

    private static Response response;
    private static final String PATH = "/pet/{petId}/uploadImage";
    private static Pet returnedPet;
//    curl -X 'POST' \
//            'https://petstore3.swagger.io/api/v3/pet/55/uploadImage' \
//            -H 'accept: application/json' \
//            -H 'Content-Type: application/octet-stream' \
//            --data-binary '@380px-Disguise_Self_Masc_Human.webp.png'
    @BeforeAll
    public static void setUp() {
    }
    @AfterAll
    public static void tearDown() {
    }

    @Test
    @Order(1)
    @DisplayName("Add an image to an exisiting pet gives 200 repsosne")
    public void addAnImageToAnExisitingPetGives200Reposne() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PATH)
                .pathParam("petId", 55)
                .relaxedHTTPSValidation()
                .headers(Map.of(
                        "accept", "application/json",
                        "Content-Disposition: attachment; filename", "https://www.dndbeyond.com/avatars/thumbnails/43939/81/1000/1000/638607414147450285.png"
                ))
                .body("https://www.dndbeyond.com/avatars/thumbnails/43939/81/1000/1000/638607414147450285.png")
                .log().all()
                .when()
                .post()
                .thenReturn();
        response.prettyPrint();
        //returnedPet = response.as(Pet.class);
        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));

    }

}
