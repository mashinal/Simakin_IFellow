package ru.iFellow.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.$x;


public class HomePage {

    private final SelenideElement projectsButton = $x("//a[@id='browse_link']").as("Кнопка Проекты");
    private final SelenideElement testButton = $x("//a[contains(@id, 'admin_main')]").as("Кнопка Test");
    private final SelenideElement openTasks = $x("//span[contains(text(), 'Открытые задачи')]").as("Текст Открытые Задачи");
    private final SelenideElement tasksButton = $x("//span[@title='Задачи']").as("Кнопка Задачи");

    @Step("Нажать проекты и Test")
    public HomePage clickProjectTest() {
        projectsButton.click();
        testButton.click();
        tasksButton.click();
        return this;
    }

    public boolean isOpenTasks() {
        return openTasks.is(Condition.visible);

    }
}
