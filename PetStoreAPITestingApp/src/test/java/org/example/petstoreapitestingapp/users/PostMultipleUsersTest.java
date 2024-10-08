package org.example.petstoreapitestingapp.users;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PostMultipleUsersTest extends UserTestBase {
    private static Response response;
    private static final String PATH = "/user/createWithList";
    private static final Map<String, String> headers = Map.of(
            "Accept", "application/json",
            "Content-Type", "application/json"
    );
    private static final Map<String, String> pathParams = Map.of();
    private static final Map<String, ?>[] body = new Map[] {
            Map.of(
                    "id", 345,
                    "username", "test",
                    "firstName", "test",
                    "lastName", "test",
                    "email", "test",
                    "password", "test",
                    "phone", "test",
                    "userStatus", 1
            ), Map.of(
                    "id", 346,
                    "username", "test",
                    "firstName", "test",
                    "lastName", "test",
                    "email", "test",
                    "password", "test",
                    "phone", "test",
                    "userStatus", 1
            )
    };

    @BeforeAll
    public static void beforeAll() {
        response = RestAssured
                .given(RequestUtils.postRequestSpecList(
                        BASE_URI,
                        PATH,
                        headers,
                        pathParams,
                        body
                ))
                .when()
                .post()
                .thenReturn();

        response.prettyPrint();

        try {
            userResponseList = response.as(User[].class);
        } catch (Exception e) {
            System.out.println("Defect found on api route: " + BASE_URI + PATH);
        }
    }

    @Test
    @DisplayName("Check if status code is 200")
    public void checkStatusCode() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Check if content-type is application/json")
    public void checkContentTypeHeader() {
        MatcherAssert.assertThat(response.headers().get("Content-Type").getValue(), Matchers.is("application/json"));
    }

    @Test
    @DisplayName("Check if server header is Jetty")
    public void checkServerHeader() {
        MatcherAssert.assertThat(response.headers().get("Server").getValue(), Matchers.containsString("Jetty"));
    }

    @Test
    @DisplayName("Check if status code is 500 - defect found")
    public void checkStatusCodeDefect() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(500));
    }


}
