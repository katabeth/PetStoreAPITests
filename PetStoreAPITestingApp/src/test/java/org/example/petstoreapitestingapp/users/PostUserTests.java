package org.example.petstoreapitestingapp.users;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class PostUserTests extends UserTestBase {
    private static Response response;
    private static final String PATH = "/user";
    private static final Map<String, String> headers = Map.of(
        "Accept", "application/json",
        "Content-Type", "application/json"
    );
    private static final Map<String, String> pathParams = Map.of();
    private static final Map<String, ?> body = Map.of(
        "id", 345,
        "username", "test",
        "firstName", "test",
        "lastName", "test",
        "email", "test",
        "password", "test",
        "phone", "test",
        "userStatus", 1
    );

    @BeforeAll
    public static void beforeAll() {
        response = RestAssured
                .given(RequestUtils.postRequestSpec(
                        BASE_URI,
                        PATH,
                        headers,
                        pathParams,
                        body
                )).auth().basic(USERNAME, PASSWORD)
                .when()
                .post()
                .thenReturn();

        response.prettyPrint();

        try {
            userResponse = response.as(User.class);
        } catch (Exception e) {
            System.out.println("Defect found on api route: " + BASE_URI + PATH);
        }

    }

    @Test
    @DisplayName("Check if valid user is created")
    public void checkIfValidUserIsCreated() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    public void validateResponseTime() {
        long maxResponseTime = 2000L;
        MatcherAssert.assertThat(response.getTime(), Matchers.lessThan(maxResponseTime));
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
    @DisplayName("Check if status code is 500 - found defect")
    public void checkStatusCode() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(500));
    }

    @Test
    @DisplayName("Check if id is 101")
    public void checkId() {
        MatcherAssert.assertThat(userResponse.getId(), Matchers.is(101));
    }

    @Test
    @DisplayName("Check if username is test")
    public void checkUsername() {
        MatcherAssert.assertThat(userResponse.getUsername(), Matchers.is("test"));
    }

    @Test
    @DisplayName("Check if firstName is test")
    public void checkFirstName() {
        MatcherAssert.assertThat(userResponse.getFirstName(), Matchers.is("test"));
    }

    @Test
    @DisplayName("Check if lastName is test")
    public void checkLastName() {
        MatcherAssert.assertThat(userResponse.getLastName(), Matchers.is("test"));
    }

    @Test
    @DisplayName("Check if email is test")
    public void checkEmail() {
        MatcherAssert.assertThat(userResponse.getEmail(), Matchers.is("test"));
    }

    @Test
    @DisplayName("Check if password is test")
    public void checkPassword() {
        MatcherAssert.assertThat(userResponse.getPassword(), Matchers.is("test"));
    }

    @Test
    @DisplayName("Check if phone is test")
    public void checkPhone() {
        MatcherAssert.assertThat(userResponse.getPhone(), Matchers.is("test"));
    }

    @Test
    @DisplayName("Check if userStatus is 1")
    public void checkUserStatus() {
        MatcherAssert.assertThat(userResponse.getUserStatus(), Matchers.is(1));
    }

}
