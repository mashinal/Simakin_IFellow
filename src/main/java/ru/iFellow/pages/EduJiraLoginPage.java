package ru.iFellow.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class EduJiraLoginPage {

    private final SelenideElement loginField = $x("//input[@id='login-form-username']");
    private final SelenideElement passwordField = $x("//input[@id='login-form-password']");
    private final SelenideElement loginButton = $x("//input[contains(@value, 'Войти')]");
    private final SelenideElement createButton = $x("//a[contains(text(), 'Создать')]");

    @Step("Ввести данные логина и пароля и нажать на вход")
    public EduJiraLoginPage login(String username, String password) {
        loginField.setValue(username);
        passwordField.setValue(password);
        loginButton.click();

        return this;

    }

    @Step("Проверка видимости кнопки 'Создать'")
    public void checkLoggedIn() {
        createButton.shouldBe(visible);
    }
}


