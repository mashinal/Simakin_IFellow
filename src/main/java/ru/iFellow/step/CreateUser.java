package ru.iFellow.step;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import ru.iFellow.utils.Props;
import io.restassured.response.Response;
import io.restassured.RestAssured;

public class CreateUser {

    private static final String BASE_URL = Props.props.createuserBaseUrl();

    public Response createUserFromJson(String jsonFilePath) throws IOException {
        String requestBody = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .basePath("/users")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post();
    }
}