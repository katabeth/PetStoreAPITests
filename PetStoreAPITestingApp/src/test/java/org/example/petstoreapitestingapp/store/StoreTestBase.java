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

    protected static Response postOrder(int id, int petId, int quantity, String shipDate, String status, boolean complete) {
        return RestAssured
                .given(RequestUtils.postRequestSpec(
                        BASE_URI,
                        "/store/order",
                        Map.of(
                                "Accept", "application/json",
                                "Content-Type", "application/json"
                        ),
                        Map.of(),
                        Map.of(
                                "id", id,
                                "petId", petId,
                                "quantity", quantity,
                                "shipDate", shipDate,
                                "status", status,
                                "complete", complete
                        ))
                )
                .when()
                .post()
                .thenReturn();
    }

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