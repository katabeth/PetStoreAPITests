package org.example.petstoreapitestingapp.users;

import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetUserLogoutTests extends UserTestBase {
    private static Response response = null;

    @BeforeAll
    public static void setup() {
        response = getUserLogout();
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
    public void validateResponseTime() {
        long maxResponseTime = 2000L;
        MatcherAssert.assertThat(response.getTime(), Matchers.lessThan(maxResponseTime));
    }

    @Test
    public void validateLogoutSuccessMessage() {
        String responseBody = response.getBody().asString();
        MatcherAssert.assertThat(responseBody, Matchers.containsString("User logged out"));
    }

    @Test
    public void validateLogoutWithoutLogin() {
        Response logoutResponseWithoutLogin = getUserLogout();
        MatcherAssert.assertThat(logoutResponseWithoutLogin.getStatusCode(), Matchers.is(200));
        MatcherAssert.assertThat(logoutResponseWithoutLogin.getBody().asString(), Matchers.containsString("User logged out"));
    }

    @Test
    public void validateLogoutResponseBodyNotEmpty() {
        MatcherAssert.assertThat(response.getBody().asString().length(), Matchers.greaterThan(0));
    }
}