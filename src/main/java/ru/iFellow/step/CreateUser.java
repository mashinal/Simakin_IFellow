package ru.iFellow.step;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.iFellow.utils.Props;

public class CreateUser {

    private static final String BASE_URL = Props.props.createuserBaseUrl();

    public Response createUser(String name, String job) {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .basePath("/users")
                .header("Content-Type", "application/json")
                .body("{ \"name\": \"" + name + "\", \"job\": \"" + job + "\" }")
                .post();
    }
}
