package org.example.petstoreapitestingapp;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestUtils {
    public static RequestSpecification getRequestSpec(String baseUri, String path, Map<String, String> headers, Map<String, String> pathParams) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeaders(headers)
                .addPathParams(pathParams)
                .build();
    }

    // overload method to make get request without passing an empty hashmap
    public static RequestSpecification getRequestSpec(String baseUri, String path, Map<String, String> pathParams) {
        return getRequestSpec(baseUri, path, Map.of(), pathParams);
    }

    public static RequestSpecification postRequestSpec(String baseUri, String path, Map<String, String> headers, Map<String, String> pathParams, Map<String, ?> body) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeaders(headers)
                .addPathParams(pathParams)
                .setContentType(ContentType.JSON)
                .setBody(body)
                .build();
    }

    public static RequestSpecification deleteRequestSpec(String baseUri, String path, Map<String, String> headers, Map<String, String> pathParams) {
        return getRequestSpec(baseUri, path, headers, pathParams);
    }

    public static RequestSpecification putRequestSpec(String baseUri, String path, Map<String, String> headers, Map<String, String> pathParams, Map<String,?> body) {
        return postRequestSpec(baseUri, path, headers, pathParams, body);
    }
}
