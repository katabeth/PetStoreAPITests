package org.example.petstoreapitestingapp.users;

import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DeleteUserByIdTests extends UserTestBase {
    private static Response response;
    private static final String TEST_USERNAME = "theUser";
    private static final String NON_EXISTENT_USERNAME = "nonExistentUser";

    @BeforeAll
    public static void setup() {
        //createUser(TEST_USERNAME);
        response = deleteUserByUsername(TEST_USERNAME);
        response.prettyPrint();
    }

    @Test
    public void validateStatusCodeForValidUser() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }

    @Test
    public void validateStatusCodeForNonExistentUser() {
        Response nonExistentResponse = deleteUserByUsername(NON_EXISTENT_USERNAME);
        nonExistentResponse.prettyPrint();
        MatcherAssert.assertThat(nonExistentResponse.getStatusCode(), Matchers.is(404));
        MatcherAssert.assertThat(nonExistentResponse.getBody().asString(), Matchers.containsString("User not found"));
    }

    @Test
    public void validateStatusCodeForInvalidUsername() {
        Response invalidUsernameResponse = deleteUserByUsername("");
        invalidUsernameResponse.prettyPrint();
        MatcherAssert.assertThat(invalidUsernameResponse.getStatusCode(), Matchers.is(400));
        MatcherAssert.assertThat(invalidUsernameResponse.getBody().asString(), Matchers.containsString("Invalid username supplied"));
    }

    @Test
    public void validateResponseTimeForValidUser() {
        long maxResponseTime = 2000L;
        MatcherAssert.assertThat(response.getTime(), Matchers.lessThan(maxResponseTime));
    }

    @Test
    public void validateResponseBodyForValidUser() {
        MatcherAssert.assertThat(response.getBody().asString().isEmpty(), Matchers.is(true));
    }

    @Test
    public void validateUserNotFoundAfterDeletion() {
        Response getUserResponse = getUserByUsername(TEST_USERNAME);
        MatcherAssert.assertThat(getUserResponse.getStatusCode(), Matchers.is(404));
        MatcherAssert.assertThat(getUserResponse.getBody().asString(), Matchers.containsString("User not found"));
    }
}
