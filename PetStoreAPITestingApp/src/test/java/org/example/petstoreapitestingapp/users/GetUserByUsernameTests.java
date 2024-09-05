package org.example.petstoreapitestingapp.users;

import io.restassured.response.Response;
import org.example.petstoreapitestingapp.pojo.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class GetUserByUsernameTests extends UserTestBase {
    private static Response response = null;
    private static final String TEST_USERNAME = "theUser";

    @BeforeAll
    public static void setup() {
        response = getUserByUsername(TEST_USERNAME);
        userResponse = response.as(User.class);
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
    public void validateResponseBodyNotEmpty() {
        MatcherAssert.assertThat(response.getBody().asString().length(), Matchers.greaterThan(0));
    }

    @Test
    public void validateUserContainsExpectedFields() {
        MatcherAssert.assertThat(userResponse.getUsername(), Matchers.is(TEST_USERNAME));
        MatcherAssert.assertThat(userResponse.getFirstName(), Matchers.notNullValue());
        MatcherAssert.assertThat(userResponse.getLastName(), Matchers.notNullValue());
        MatcherAssert.assertThat(userResponse.getEmail(), Matchers.notNullValue());
        MatcherAssert.assertThat(userResponse.getPhone(), Matchers.notNullValue());
    }

    @Test
    public void validateUserStatusNotNegative() {
        MatcherAssert.assertThat(userResponse.getUserStatus(), Matchers.greaterThanOrEqualTo(0));
    }

    @Test
    public void validateSpecificUserExists() {
        MatcherAssert.assertThat(userResponse.getId(), Matchers.is(10));
        MatcherAssert.assertThat(userResponse.getUsername(), Matchers.is("theUser"));
        MatcherAssert.assertThat(userResponse.getFirstName(), Matchers.is("John"));
        MatcherAssert.assertThat(userResponse.getLastName(), Matchers.is("James"));
        MatcherAssert.assertThat(userResponse.getEmail(), Matchers.is("john@email.com"));
        MatcherAssert.assertThat(userResponse.getPassword(), Matchers.is("12345"));
        MatcherAssert.assertThat(userResponse.getPhone(), Matchers.is("12345"));
        MatcherAssert.assertThat(userResponse.getUserStatus(), Matchers.is(1));
    }

    @Test
    public void validateNonExistentUsernameReturns404() {
        String nonExistentUsername = "non_existent_user";
        Response nonExistentResponse = getUserByUsername(nonExistentUsername);

        nonExistentResponse.prettyPrint();
        MatcherAssert.assertThat(nonExistentResponse.getStatusCode(), Matchers.is(404));
        MatcherAssert.assertThat(nonExistentResponse.getBody().asString(), Matchers.containsString("User not found"));
    }

    @Test
    public void validateInvalidUsernameReturns400() {
        String invalidUsername = "";
        Response invalidResponse = getUserByUsername(invalidUsername);

        MatcherAssert.assertThat(invalidResponse.getStatusCode(), Matchers.is(405));
        MatcherAssert.assertThat(invalidResponse.jsonPath().getString("message"), Matchers.containsString("HTTP 405 Method Not Allowed"));
    }
}
