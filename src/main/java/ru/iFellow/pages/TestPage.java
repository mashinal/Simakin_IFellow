package ru.iFellow.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.iFellow.pages.models.CreateTaskModal;

import static com.codeborne.selenide.Selenide.$x;


public class TestPage {

    private final SelenideElement taskCountElement = $x("//span[contains(text(), '1 из')]").as("Поле номер заявки");
    private final SelenideElement createTaskButton = $x("//a[@id='create_link']").as("Кнопка создать Задачу");

    @Step("Получить текущее количество задач")
    public int getTaskCount() {
        String taskCountText = taskCountElement.getText();
        String[] parts = taskCountText.split("1 из");
        return Integer.parseInt(parts[1].trim());
    }

    @Step("Создать новую задачу")
    public void createNewTask() {
        createTaskButton.click();
        CreateTaskModal createTaskModal = new CreateTaskModal();
        createTaskModal.createNewTask();
        createTaskButton.shouldHave(Condition.visible);
        Selenide.refresh();
    }
}
