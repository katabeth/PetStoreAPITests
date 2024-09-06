package org.example.petstoreapitestingapp.pets;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class uploadingImageTest extends PetsTestBase{

    private static Response response;
    private static final String PATH = "/pet/{petId}/uploadImage";
    File file = new File("src/test/resources/cult_fanatic.png");
//    curl -X 'POST' \
//            'https://petstore3.swagger.io/api/v3/pet/55/uploadImage' \
//            -H 'accept: application/json' \
//            -H 'Content-Type: application/octet-stream' \
//            --data-binary '@380px-Disguise_Self_Masc_Human.webp.png'

    @Test
    @Order(1)
    @DisplayName("Add an image to an existing pet gives 200 response")
    public void addAnImageToAnExistingPetGives200Response() {
        response = RestAssured
                .given(RequestUtils.getRequestSpec(
                                BASE_URI,
                                PATH,
                                Map.of("petId", "55"))
                .contentType(ContentType.BINARY)// Set content type as BINARY
                .body(file)
                .when()
                .post()
                .thenReturn();
        response.prettyPrint();
        MatcherAssert.assertThat(response.getStatusCode(), org.hamcrest.Matchers.is(200));
    }

}
