package org.example.petstoreapitestingapp.store.order;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.store.StoreTestBase;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class DeletePetStoreOrderIdTests extends StoreTestBase {

    private static Response response;
    private static final String PATH = "/store/order/{orderId}";
    private static final Map<String, String> headers = Map.of(
                                                        "Accept", "application/json",
                                                        "Content-Type", "application/json"
                                                        );
    private static String orderId = "100";
    private static Map<String, String> pathParams = Map.of("orderId", orderId);

    @BeforeAll
    public static void beforeAll() {
        response =
                RestAssured
                        .given(RequestUtils.deleteRequestSpec(
                                BASE_URI,
                                PATH,
                                headers,
                                pathParams
                        ))
                        .when()
                        .delete()
                        .thenReturn();

    }

    @Test
    @DisplayName("Check status code is 200")
    public void checkStatusCode() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Check if server is Jetty")
    public void checkServerHeader() {
        MatcherAssert.assertThat(response.headers().get("Server").getValue(), Matchers.containsString("Jetty"));
    }

    @Test
    @DisplayName("Check if invalid orderId returns 400")
    public void checkInvalidOrderId() {
        orderId = "abc";
        pathParams = Map.of("orderId", orderId);

        Response invalidResponse = RestAssured
                .given(RequestUtils.deleteRequestSpec(
                        BASE_URI,
                        PATH,
                        headers,
                        pathParams
                ))
                .when()
                .delete()
                .thenReturn();

        MatcherAssert.assertThat(invalidResponse.statusCode(), Matchers.is(400));
    }

}
