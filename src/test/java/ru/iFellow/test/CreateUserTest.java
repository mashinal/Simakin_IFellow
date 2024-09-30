package ru.iFellow.test;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iFellow.step.CreateUser;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateUserTest {

    @Test
    public void testCreateUser() throws Exception {
        CreateUser service = new CreateUser();

        String originalJson = new String(Files.readAllBytes(Paths.get("src/test/resources/potato.json")));
        JSONObject jsonObject = new JSONObject(originalJson);

        jsonObject.put("name", "Tomato");
        jsonObject.put("job", "Eat maket");

        Response response = service.createUser(jsonObject.getString("name"), jsonObject.getString("job"));

        Assertions.assertEquals(201, response.getStatusCode(), "Статус код не равен 201");

        JSONObject responseBody = new JSONObject(response.getBody().asString());

        Assertions.assertEquals("Tomato", responseBody.getString("name"), "Имя в ответе не совпадает");
        Assertions.assertEquals("Eat maket", responseBody.getString("job"), "Должность в ответе не совпадает");

        Assertions.assertTrue(responseBody.has("id"), "В ответе отсутствует поле id");
    }
}
