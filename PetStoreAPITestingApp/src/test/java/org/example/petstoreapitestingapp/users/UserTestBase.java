package org.example.petstoreapitestingapp.users;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.TestBase;
import org.example.petstoreapitestingapp.pojo.User;

import java.util.Map;

public class UserTestBase extends TestBase {
    protected static final String GET_USER_PATH = "/user/{username}";
    protected String username;
    protected static User userResponse;
    protected static User[] userResponseList;

    protected static Response getUserByUsername(String username) {
        return RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, GET_USER_PATH, Map.of("username", username)))
                .get()
                .thenReturn();
    }

    protected static Response getLoginUser(String username, String password) {
        return RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, "user/login"))
                .queryParams(Map.of(
                        "username", username,
                        "password", password
                ))
                .get()
                .thenReturn();
    }

    protected static Response getUserLogout() {
        return RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, "user/logout"))
                .get()
                .thenReturn();
    }

    protected static Response deleteUserByUsername(String username) {
        return RestAssured
                .given(RequestUtils.getRequestSpec(BASE_URI, GET_USER_PATH, Map.of("username", username)))
                .auth().basic(USERNAME, PASSWORD)
                .delete()
                .thenReturn();
    }
}
