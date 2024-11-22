package ru.iFellow.pages.models;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class CreateTaskModal {

    private final SelenideElement fieldTaskType = $x("//input[@id='issuetype-field']");
    private final SelenideElement taskNameField = $x("//input[@id='summary']");
    private final SelenideElement createButton = $x("//input[@id='create-issue-submit']");
    private final SelenideElement descriptionTextArea = $x("//textarea[@id='description']");
    private final SelenideElement fieldLabels = $x("//textarea[@id='labels-textarea']");
    private final SelenideElement environmentField = $x("//textarea[@id='environment']");
    private final SelenideElement fieldTask = $x("//textarea[@id='issuelinks-issues-textarea']");
    private final SelenideElement createTaskButton = $x("//a[@id='create_link']");
    private final SelenideElement loadingCircle = $x("//div[contains(@class,'spinner')]");
    private final ElementsCollection typeTaskOptions = $$x("//li[contains(@role,'option')]");
    private final ElementsCollection firstElementDropdownTask = $$x("//a[@role='presentation']");


    public void setType(String type) {
        if (fieldTaskType.shouldBe(editable).has(not(value(type)))) {
            fieldTaskType.shouldBe(visible).sendKeys(Keys.CONTROL + "a");
            fieldTaskType.sendKeys(Keys.DELETE);
            fieldTaskType.setValue(type);
            typeTaskOptions.shouldBe(CollectionCondition.sizeGreaterThan(0)).first().click();
        }
        fieldTaskType.shouldHave(value(type));
        loadingCircle.shouldBe(disappear);
    }

    @Step("Создать новую задачу с типом 'Задачи'")
    public void createNewTask() {
        setType("Задача");
        taskNameField.setValue("Новая задача");
        createButton.click();
        Selenide.refresh();
    }

    @Step("Создать новую задачу с типом 'Ошибка'")
    public void createTaskBug() {
        createTaskButton.click();
        setType("Ошибка");
        taskNameField.setValue("Баг репорт");
        descriptionTextArea.setValue(RandomStringUtils.randomAlphanumeric(10));
        fieldLabels.setValue("Test");
        environmentField.setValue("Test");
        fieldTask.setValue("TEST");
        firstElementDropdownTask.findBy(visible).click();
        createButton.click();
    }
}