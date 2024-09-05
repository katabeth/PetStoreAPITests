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
    private static Order order;

    @BeforeAll
    public static void beforeAll() {
        response =
                RestAssured
                        .given(RequestUtils.getRequestSpec(
                                BASE_URI,
                                "/store/order/{orderId}",
                                Map.of(
                                        "Accept", "application/json",
                                        "Content-Type", "application/json"
                                ),
                                Map.of(
                                        "orderId", "10"
                                )
                        ))
                        .when()
                        .get()
                        .thenReturn();

        order = response.as(Order.class);
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
    @DisplayName("Check if id is 10")
    public void checkId() {
        MatcherAssert.assertThat(order.getId(), Matchers.is(10));
    }

    @Test
    @DisplayName("Check if petId is 198772")
    public void checkPetId() {
        MatcherAssert.assertThat(order.getPetId(), Matchers.is(555));
    }

    @Test
    @DisplayName("Check if quantity is 7")
    public void checkQuantity() {
        MatcherAssert.assertThat(order.getQuantity(), Matchers.is(7));
    }

    @Test
    @DisplayName("Check if status is approved")
    public void checkStatus() {
        MatcherAssert.assertThat(order.getStatus(), Matchers.is("approved"));
    }

    @Test
    @DisplayName("Check if shipDate is 2024-09-10T14:49:33.773+00:00")
    public void checkShipDate() {
        MatcherAssert.assertThat(order.getShipDate(), Matchers.containsString("2024-09-05T09:02:20.792"));
    }

    @Test
    @DisplayName("Check if complete is true")
    public void checkComplete() {
        MatcherAssert.assertThat(order.isComplete(), Matchers.is(true));
    }
}
