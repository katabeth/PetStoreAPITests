package org.example.petstoreapitestingapp.store;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class StoreGetInventoryTests extends StoreTestBase {
    private static final String INVENTORY_PATH = "store/inventory";
    private static Response response = null;

    @BeforeAll
    public static void setup() {
        response = RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, INVENTORY_PATH, Map.of(), Map.of()))
                .auth().basic(USERNAME, PASSWORD)
                .when()
                .get()
                .thenReturn();

        response.prettyPrint();
    }

    @Test
    public void validateStatusCode() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }

    @Test
    public void validateContentTypeHeader() {
        MatcherAssert.assertThat(response.getHeader("Content-Type"), Matchers.is("application/json"));
    }

    @Test
    public void validateResponseBodyNotEmpty() {
        MatcherAssert.assertThat(response.getBody().asString().length(), Matchers.greaterThan(0));
    }

    @Test
    public void validateInventoryContainsStatusKeys() {
        Map<String, Integer> inventory = response.as(Map.class);

        MatcherAssert.assertThat(inventory, Matchers.hasKey("ordered"));
        MatcherAssert.assertThat(inventory, Matchers.hasKey("approved"));
        MatcherAssert.assertThat(inventory, Matchers.hasKey("placed"));
        MatcherAssert.assertThat(inventory, Matchers.hasKey("test"));
        MatcherAssert.assertThat(inventory, Matchers.hasKey("pending"));
        MatcherAssert.assertThat(inventory, Matchers.hasKey("available"));
        MatcherAssert.assertThat(inventory, Matchers.hasKey("deliver"));
        MatcherAssert.assertThat(inventory, Matchers.hasKey("delivered"));
    }


    @Test
    public void validateInventoryStatusValuesAreNotNegative() {
        Map<String, Integer> inventory = response.as(Map.class);

        inventory.forEach((key, value) -> {
            MatcherAssert.assertThat(value, Matchers.greaterThanOrEqualTo(0));
        });
    }
}