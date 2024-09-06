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

public class GetPetStoreOrderIdTests extends StoreTestBase {
    private static Response response;
    private static final int TEST_ORDER_ID = 2;

    @BeforeAll
    public static void beforeAll() {
        response = getOrderById(TEST_ORDER_ID);
        orderResponse = response.as(Order.class);
    }

    @Test
    @DisplayName("Check if status code is 200")
    public void checkStatusCode() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Check content type header is application/json")
    public void checkContentTypeHeader() {
        MatcherAssert.assertThat(response.headers().get("Content-Type").getValue(), Matchers.is("application/json"));
    }

    @Test
    public void validateResponseTime() {
        long maxResponseTime = 2000L;
        MatcherAssert.assertThat(response.getTime(), Matchers.lessThan(maxResponseTime));
    }

    @Test
    public void validateResponseBodyNotEmpty() {
        MatcherAssert.assertThat(response.getBody().asString().length(), Matchers.greaterThan(0));
    }

    @Test
    @DisplayName("Check if id is 1")
    public void checkId() {
        MatcherAssert.assertThat(orderResponse.getId(), Matchers.is(TEST_ORDER_ID));
    }

    @Test
    @DisplayName("Check if petId is 1")
    public void checkPetId() {
        MatcherAssert.assertThat(orderResponse.getPetId(), Matchers.is(1));
    }

    @Test
    @DisplayName("Check if quantity is 0")
    public void checkQuantity() {
        MatcherAssert.assertThat(orderResponse.getQuantity(), Matchers.is(0));
    }

    @Test
    @DisplayName("Check if status is placed")
    public void checkStatus() {
        MatcherAssert.assertThat(orderResponse.getStatus(), Matchers.is("placed"));
    }

    @Test
    @DisplayName("Check if shipDate is 2023-10-01T00:00:00.000+00:00")
    public void checkShipDate() {
        MatcherAssert.assertThat(orderResponse.getShipDate(), Matchers.containsString("2023-10-01T00:00:00.000"));
    }

    @Test
    @DisplayName("Check if complete is false")
    public void checkComplete() {
        MatcherAssert.assertThat(orderResponse.isComplete(), Matchers.is(false));
    }

    @Test
    public void validateNonExistentOrderIdReturns404() {
        int nonExistentOrderId = 999999;
        Response nonExistentResponse = getOrderById(nonExistentOrderId);

        MatcherAssert.assertThat(nonExistentResponse.getStatusCode(), Matchers.is(404));
        MatcherAssert.assertThat(nonExistentResponse.getBody().asString(), Matchers.containsString("Order not found"));
    }

    @Test
    public void validateInvalidOrderIdReturns400() {
        String invalidOrderId = "abc";
        Response invalidResponse = RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, GET_ORDER_PATH, Map.of(), Map.of("orderId", invalidOrderId)))
                .get()
                .thenReturn();

        MatcherAssert.assertThat(invalidResponse.getStatusCode(), Matchers.is(400));
        MatcherAssert.assertThat(invalidResponse.jsonPath().getString("message"), Matchers.containsString("Input error: couldn't convert `" + invalidOrderId + "` to type `class java.lang.Long`"));
    }
}
