package org.example.petstoreapitestingapp.users;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.TestBase;
import org.example.petstoreapitestingapp.pojo.User;

import java.util.Map;

public class UserTestBase extends TestBase {
    protected String username;
    protected static User userResponse;
    protected User[] users;

    protected static Response getUserByUsername(String username) {
        return RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, "user/{username}", Map.of("username", username)))
                .auth().basic(USERNAME, PASSWORD)
                .get()
                .thenReturn();
    }
}