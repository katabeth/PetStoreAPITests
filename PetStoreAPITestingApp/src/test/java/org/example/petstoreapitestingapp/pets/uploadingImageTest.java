package org.example.petstoreapitestingapp.pets;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class uploadingImageTest extends PetsTestBase{

    private static Response response;
    private static final String PATH = "/pet/{petId}/uploadImage";
    private static Pet returnedPet;
    File imageFile = new File("path/to/your/image.jpg");
    File file = new File("src/test/resources/cult_fanatic.png");
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


    // Perform the POST request with the image as multipart form-data
     // Log the response
    @Test
    @Order(1)
    @DisplayName("Add an image to an existing pet gives 200 response")
    public void addAnImageToAnExistingPetGives200Response() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PATH)
                .pathParam("petId", 55)
                 // Attach the image as a multiPart field
                 // Add metadata as multipart field
                .contentType(ContentType.BINARY)// Set content type as multipart/form-data
                .body(file)
                .when()
                .post() // API endpoint for uploading the image
                .thenReturn();
        response.prettyPrint();
        //returnedPet = response.as(Pet.class);
        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));

    }

}
