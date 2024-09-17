package ru.iFellow.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CredentialsReader {

    private Properties properties = new Properties();

    public CredentialsReader() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/generaltest.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return properties.getProperty("login");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

}
