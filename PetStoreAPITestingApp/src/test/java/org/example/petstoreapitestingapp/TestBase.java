package org.example.petstoreapitestingapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class TestBase {
    protected static String BASE_URI;
    protected static String USERNAME;
    protected static String PASSWORD;

    static {
        loadConfig();
    }

    protected static void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = TestBase.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);

            BASE_URI = properties.getProperty("base.uri");
            USERNAME = properties.getProperty("auth.username");
            PASSWORD = properties.getProperty("auth.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
