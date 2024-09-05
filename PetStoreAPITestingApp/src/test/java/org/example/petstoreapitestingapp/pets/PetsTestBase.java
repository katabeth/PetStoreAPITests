package org.example.petstoreapitestingapp.pets;

import io.restassured.response.Response;
import org.example.petstoreapitestingapp.TestBase;
import org.example.petstoreapitestingapp.pojo.Pet;

public abstract class PetsTestBase extends TestBase {
    protected static Response response;
    protected Long petId;
    protected static Pet petResponse;
    protected static Pet[] pets;
}
