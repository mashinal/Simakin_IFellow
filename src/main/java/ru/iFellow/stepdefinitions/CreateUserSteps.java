package ru.iFellow.stepdefinitions;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import io.qameta.allure.Step;
import ru.iFellow.step.CreateUser;

public class CreateUserSteps {

    private CreateUser createUserService;
    private Response response;
    private JSONObject responseBody;
    private String name;
    private String job;

    public CreateUserSteps() {
        System.out.println("CreateUserSteps инициализирован");
    }

    @Step("Создаем пользователя с именем {name} и должностью {job}")
    @Дано("у меня есть пользователь с именем {string} и должностью {string}")
    public void i_have_a_user_with_name_and_job(String name, String job) {
        System.out.println("Шаг @Дано: Инициализация пользователя");
        this.name = name;
        this.job = job;
        createUserService = new CreateUser();
    }

    @Step("Отправляем POST запрос для создания пользователя с именем {name} и должностью {job}")
    @Когда("я отправляю POST запрос для создания пользователя с именем {string} и должностью {string}")
    public void i_send_a_post_request_to_create_the_user_with_name_and_job(String name, String job) {
        System.out.println("Шаг @Когда: Отправка POST запроса");
        response = createUserService.createUser(name, job);
    }

    @Step("Проверяем, что статус код равен {statusCode}")
    @Тогда("статус код ответа должен быть {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        System.out.println("Шаг @Тогда: Проверка статус кода");
        Assertions.assertEquals(statusCode, response.getStatusCode(), "Статус код не соответствует ожиданиям");
    }

    @Step("Проверяем, что ответ содержит имя {expectedName}")
    @И("ответ должен содержать имя {string}")
    public void the_response_should_contain_the_name(String expectedName) {
        System.out.println("Шаг @И: Проверка имени");
        responseBody = new JSONObject(response.getBody().asString());
        Assertions.assertEquals(expectedName, responseBody.getString("name"), "Имя в ответе не совпадает");
    }

    @Step("Проверяем, что ответ содержит должность {expectedJob}")
    @И("ответ должен содержать должность {string}")
    public void the_response_should_contain_the_job(String expectedJob) {
        System.out.println("Шаг @И: Проверка должности");
        Assertions.assertEquals(expectedJob, responseBody.getString("job"), "Должность в ответе не совпадает");
    }

    @Step("Проверяем, что ответ содержит поле {field}")
    @И("ответ должен содержать поле {string}")
    public void the_response_should_contain_an(String field) {
        System.out.println("Шаг @И: Проверка поля");
        Assertions.assertTrue(responseBody.has(field), "В ответе отсутствует поле " + field);
    }
}
