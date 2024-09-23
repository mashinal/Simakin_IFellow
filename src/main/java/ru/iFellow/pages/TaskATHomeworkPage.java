package ru.iFellow.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class TaskATHomeworkPage {

    private final SelenideElement searchField = $x("//input[@id='quickSearchInput']").as("Поле поиска");
    private final ElementsCollection searchElements = $$x("//li[contains(@class,'quick-search-result-item')]").as("Первый элемент из выпадающего списка поиска");
    private final SelenideElement taskStatus = $x("//span[contains(text(),'Сделать')]").as("Поле Статус задачи");
    private final SelenideElement fixVersion = $x("//a[contains(text(), 'Version 2.0')]").as("Поле Исправить в версиях");
    private final SelenideElement createdAlert = $x("//a[contains(@class,'issue-created')]").as("Всплывающее окно созданной заявки");
    private final SelenideElement statusLabel = $x("//span[@id='status-val']").as("Статус заявки Готово");

    private SelenideElement statusButton(String status) {
        return $x("//a[./span[text()='" + status + "']]").as("Кнопка Статуса");
    }

    @Step("Найти задачу {taskName}")
    public void searchTask(String taskName) {
        searchField.setValue(taskName);
        searchElements.shouldHave(sizeGreaterThan(2)).findBy(text(taskName)).click();
    }

    @Step("Проверить статус задачи")
    public boolean isTaskStatusCorrect() {
        return taskStatus.is(Condition.visible);
    }

    @Step("Проверить 'Исправить в версиях'")
    public boolean isFixVersionCorrect() {
        return fixVersion.is(Condition.visible);
    }

    @Step("Перейти на созданный тикет из всплывашки")
    public TaskATHomeworkPage clickAlertLink() {
        createdAlert.shouldBe(visible).click();
        return this;
    }

    @Step("Продвинуть тикет до статуса закрытого")
    public TaskATHomeworkPage moveTicketToClose() {
        statusButton("В работе").shouldBe(visible).click();
        statusButton("Бизнес-процесс").shouldBe(visible).click();
        statusButton("Выполнено").shouldBe(visible).click();
        return this;
    }

    @Step("Проверяем, что статус 'Выполнено'")
    public void checkStatusIsDone() {
        statusLabel.shouldBe(visible).shouldHave(text("Готово"));
    }
}