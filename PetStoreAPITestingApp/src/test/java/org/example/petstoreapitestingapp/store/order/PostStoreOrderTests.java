package org.example.petstoreapitestingapp.store.order;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.Order;
import org.example.petstoreapitestingapp.store.StoreTestBase;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class PostStoreOrderTests extends StoreTestBase {
    private static Response response;
    private static final String BASE_URL = BASE_URI;

    @BeforeAll
    public static void beforeAll() {
        response =
                RestAssured
                        .given(RequestUtils.postRequestSpec(
                                BASE_URI,
                                "/store/order",
                                Map.of(
                                        "Accept", "application/json",
                                        "Content-Type", "application/json"
                                ),
                                Map.of(),
                                Map.of(
                                        "id", 10,
                                        "petId", 198772,
                                        "quantity", 7,
                                        "shipDate", "2024-09-10T14:49:33.773Z",
                                        "status", "approved",
                                        "complete", true
                                ))
                        )
                        .when()
                        .post()
                        .thenReturn();

        orderResponse = response.as(Order.class);
    }

    @Test
    @DisplayName("Check if status code is 200")
    public void checkStatusCode() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Check server header is petstoreapi")
    public void checkServerHeader() {
        MatcherAssert.assertThat(response.headers().get("Server").getValue(), Matchers.containsString("Jetty"));
    }

    @Test
    @DisplayName("Check content type header is application/json")
    public void checkContentTypeHeader() {
        MatcherAssert.assertThat(response.headers().get("Content-Type").getValue(), Matchers.is("application/json"));
    }

    @Test
    @DisplayName("Check if id is 10")
    public void checkId() {
        MatcherAssert.assertThat(orderResponse.getId(), Matchers.is(10));
    }

    @Test
    @DisplayName("Check if petId is 198772")
    public void checkPetId() {
        MatcherAssert.assertThat(orderResponse.getPetId(), Matchers.is(198772));
    }

    @Test
    @DisplayName("Check if quantity is 7")
    public void checkQuantity() {
        MatcherAssert.assertThat(orderResponse.getQuantity(), Matchers.is(7));
    }

    @Test
    @DisplayName("Check if shipDate is 2024-09-10T14:49:33.773+00:00")
    public void checkShipDate() {
        MatcherAssert.assertThat(orderResponse.getShipDate(), Matchers.containsString("2024-09-10T14:49:33.773"));
    }

    @Test
    @DisplayName("Check if status is approved")
    public void checkStatus() {
        MatcherAssert.assertThat(orderResponse.getStatus(), Matchers.is("approved"));
    }

    @Test
    @DisplayName("Check if complete is true")
    public void checkComplete() {
        MatcherAssert.assertThat(orderResponse.isComplete(), Matchers.is(true));
    }

}