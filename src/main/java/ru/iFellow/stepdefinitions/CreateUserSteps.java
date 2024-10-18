package ru.iFellow.stepdefinitions;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import ru.iFellow.step.CreateUser;

import java.io.IOException;

public class CreateUserSteps {

    private CreateUser createUserService = new CreateUser();
    private Response response;
    private JSONObject responseBody;

    @Дано("у меня есть JSON файл {string}")
    public void i_have_a_json_file(String jsonFilePath) {
        System.out.println("Шаг @Дано: Инициализация JSON файла " + jsonFilePath);
    }

    @Когда("я отправляю POST запрос для создания пользователя из файла {string}")
    public void i_send_a_post_request_to_create_the_user_from_file(String jsonFilePath) throws IOException {
        System.out.println("Шаг @Когда: Отправка POST запроса из JSON файла");
        response = createUserService.createUserFromJson("src/test/resources/" + jsonFilePath);
    }

    @Тогда("статус код ответа должен быть {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        System.out.println("Шаг @Тогда: Проверка статус кода");
        Assertions.assertEquals(statusCode, response.getStatusCode(), "Статус код не соответствует ожиданиям");
    }

    @Тогда("ответ должен содержать имя {string}")
    public void the_response_should_contain_the_name(String expectedName) {
        System.out.println("Шаг @Тогда: Проверка имени");
        responseBody = new JSONObject(response.getBody().asString());
        Assertions.assertEquals(expectedName, responseBody.getString("name"), "Имя в ответе не совпадает");
    }

    @Тогда("ответ должен содержать должность {string}")
    public void the_response_should_contain_the_job(String expectedJob) {
        System.out.println("Шаг @Тогда: Проверка должности");
        Assertions.assertEquals(expectedJob, responseBody.getString("job"), "Должность в ответе не совпадает");
    }

    @Тогда("ответ должен содержать поле {string}")
    public void the_response_should_contain_the_field(String fieldName) {
        System.out.println("Шаг @Тогда: Проверка наличия поля " + fieldName);
        Assertions.assertTrue(responseBody.has(fieldName), "Ответ не содержит поле " + fieldName);
    }
}