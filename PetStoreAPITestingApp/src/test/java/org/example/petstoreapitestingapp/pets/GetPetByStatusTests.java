package org.example.petstoreapitestingapp.pets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.Pet;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetPetByStatusTests extends PetsTestBase {

    private static final String PATH_UNDER_TEST = "pet/findByStatus";
    private static Response responseAvailable;
    private static Response responsePending;
    private static Response responseSold;
    private static Response responseNotFound;
    private static Pet[] availablePets;
    private static Pet[] pendingPets;
    private static Pet[] soldPets;

    @BeforeAll
    public static void beforeAll() {
        responseAvailable = RestAssured.given(RequestUtils.getRequestSpec(BASE_URI, PATH_UNDER_TEST))
                .queryParam("status","available").when().get().thenReturn();
        availablePets = responseAvailable.as(Pet[].class);

        responsePending = RestAssured.given(RequestUtils.getRequestSpec(BASE_URI, PATH_UNDER_TEST))
                .queryParam("status","pending").when().get().thenReturn();
        pendingPets = responsePending.as(Pet[].class);

        responseSold = RestAssured.given(RequestUtils.getRequestSpec(BASE_URI, PATH_UNDER_TEST))
                .queryParam("status","sold").when().get().thenReturn();
        soldPets = responseSold.as(Pet[].class);

        responseNotFound = RestAssured.given(RequestUtils.getRequestSpec(BASE_URI, PATH_UNDER_TEST))
                .queryParam("status","adiowhdahdkj").when().get().thenReturn();
    }

    @Test
    @DisplayName("Check status code for available status is 200")
    public void GetPetByStatus_200() {MatcherAssert.assertThat(responseAvailable.statusCode(), Matchers.is(200));}

    @Test
    @DisplayName("Check first pet status is available")
    public void GetPetByStatus_StatusOfFirstPetIsAvailable() {MatcherAssert.assertThat(availablePets[0].getStatus(), Matchers.is("available"));}

    @Test
    @DisplayName("Check status code for pending status is 200")
    public void GetPetByStatus_StatusIsPending() {MatcherAssert.assertThat(responsePending.statusCode(), Matchers.is(200));}

    @Test
    @DisplayName("Check first pet status is pending")
    public void GetPetByStatus_StatusOfFirstPetIsPending() {MatcherAssert.assertThat(pendingPets[0].getStatus(), Matchers.is("pending"));}

    @Test
    @DisplayName("Check status code for sold status is 200")
    public void GetPetByStatus_StatusIsSold() {MatcherAssert.assertThat(responseSold.statusCode(), Matchers.is(200));}

    @Test
    @DisplayName("Check first pet status is sold")
    public void GetPetByStatus_StatusOfFirstPetIsSold() {MatcherAssert.assertThat(soldPets[0].getStatus(), Matchers.is("sold"));}

    @Test
    @DisplayName("Check status code for invalid status is 400")
    public void GetPetByStatus_InvalidStatus() {MatcherAssert.assertThat(responseNotFound.statusCode(), Matchers.is(400));}
}
