package org.example.petstoreapitestingapp.store.order;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.store.StoreTestBase;
import org.junit.jupiter.api.BeforeAll;

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
                     null,
                                Map.of(
                                        "id", 10,
                                        "petId", 198772,
                                        "quantity", 7,
                                        "shipDate", "2024-09-04T14:49:33.773Z",
                                        "status", "approved",
                                        "complete", true
                                ))
                        )
                        .when()
                        .post()
                        .thenReturn();
    }
}
