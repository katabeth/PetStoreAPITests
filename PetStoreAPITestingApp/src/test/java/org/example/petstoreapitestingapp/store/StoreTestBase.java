package org.example.petstoreapitestingapp.store;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.TestBase;
import org.example.petstoreapitestingapp.pojo.Order;

import java.util.Map;

public class StoreTestBase extends TestBase {
    protected static final String POST_ORDER_PATH = "store/order";
    protected static final String GET_ORDER_PATH = "store/order/{orderId}";

    protected Integer orderId;
    protected static Order orderResponse;
    protected Order[] orders;

    protected static Response getOrderById(int orderId) {
        return RestAssured
                .given(RequestUtils.getRequestSpec(
                        BASE_URI,
                        GET_ORDER_PATH,
                        Map.of(
                                "orderId",
                                String.valueOf(orderId))
                ))
                .auth().basic(USERNAME, PASSWORD)
                .when()
                .get()
                .thenReturn();
    }
}