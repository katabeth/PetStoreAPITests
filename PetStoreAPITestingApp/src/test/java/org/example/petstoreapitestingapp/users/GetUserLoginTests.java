package org.example.petstoreapitestingapp.users;

import io.restassured.response.Response;
import org.example.petstoreapitestingapp.TestBase;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetUserLoginTests extends UserTestBase {
    private static Response response = null;
    private static final String TEST_USERNAME = TestBase.USERNAME;
    private static final String TEST_PASSWORD = TestBase.PASSWORD;
    private static String responseBody;

    @BeforeAll
    public static void setup() {
        response = getLoginUser(TEST_USERNAME, TEST_PASSWORD);
        response.prettyPrint();
        responseBody = response.getBody().asString();
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
    public void validateLoginSuccess() {
        MatcherAssert.assertThat(responseBody, Matchers.startsWith("Logged in user session:"));

        String sessionId = responseBody.replace("Logged in user session: ", "");
        MatcherAssert.assertThat(sessionId, Matchers.matchesPattern("\\d+"));
    }

    @Test
    public void validateNonExistentUserLogin() {
        Response nonExistentResponse = getLoginUser("invalidUser", "invalidPass");
        MatcherAssert.assertThat(nonExistentResponse.getStatusCode(), Matchers.is(400));
        MatcherAssert.assertThat(nonExistentResponse.getBody().asString(), Matchers.containsString("Invalid username/password"));
    }

    @Test
    public void validateInvalidUsernameLogin() {
        Response invalidResponse = getLoginUser("", TEST_PASSWORD);
        MatcherAssert.assertThat(invalidResponse.getStatusCode(), Matchers.is(400));
        MatcherAssert.assertThat(invalidResponse.getBody().asString(), Matchers.containsString("Invalid username/password"));
    }

    @Test
    public void validateInvalidPasswordLogin() {
        Response invalidResponse = getLoginUser(TEST_USERNAME, "");
        MatcherAssert.assertThat(invalidResponse.getStatusCode(), Matchers.is(400));
        MatcherAssert.assertThat(invalidResponse.getBody().asString(), Matchers.containsString("Invalid username/password"));
    }

    @Test
    public void validateNoContentLogin() {
        Response invalidResponse = getLoginUser("", "");
        MatcherAssert.assertThat(invalidResponse.getStatusCode(), Matchers.is(400));
        MatcherAssert.assertThat(invalidResponse.getBody().asString(), Matchers.containsString("Invalid username/password"));
    }
}