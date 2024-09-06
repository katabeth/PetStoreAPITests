package org.example.petstoreapitestingapp.users;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.petstoreapitestingapp.RequestUtils;
import org.example.petstoreapitestingapp.pojo.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Map;

public class UpdateUserByUserNameTests extends UserTestBase {

    private static Response response = null;
    protected static final String PATH_UNDER_TEST = "/user/{username}";
    private static final String TEST_USERNAME = "test";
    private static final String TEST_PASSWORD = "abc123";
    private static String sessionId;
    private static String initialEmail;

    @BeforeAll
    public static void setup() {
        User user = new User("John", "James", "12345", 1, "12345", "10", "john@email.com", "theUser");
        response = RestAssured
                .given()
                .baseUri("https://petstore3.swagger.io")
                .basePath("/api/v3/user/theUser")
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .put()
                .thenReturn();
        response.prettyPrint();
    }

    @Test
    public void testUpdateUserByUserName() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    public void testUpdateUserWithInvalidUsername() {
        User user = new User("Jane", "Doe", "67890", 1, "67890", "20", "jane.doe@email.com", "invalidUser");
        response = RestAssured
                .given()
                .baseUri("https://petstore3.swagger.io")
                .basePath("/api/v3/user/invalidUser")
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .put()
                .thenReturn();
        response.prettyPrint();
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(404));
    }

    @Test
    public void testUpdateUserWithInvalidData() {
        User user = new User("Invalid", "Data", null, 0, null, "0", "invalid.email", "theUser");
        response = RestAssured
                .given()
                .baseUri("https://petstore3.swagger.io")
                .basePath("/api/v3/user/theUser")
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .put()
                .thenReturn();
        response.prettyPrint();
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    public void testUpdateUserWithoutBody() {
        response = RestAssured
                .given()
                .baseUri("https://petstore3.swagger.io")
                .basePath("/api/v3/user/theUser")
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .log().all()
                .put()
                .thenReturn();
        response.prettyPrint();
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }
}
