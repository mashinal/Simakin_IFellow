package ru.iFellow.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Путь к .feature файлам
        glue = {"src/main/java/ru/iFellow/step"}, // Пакет со Step Definitions
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
                // Добавление Allure плагина
        },
        monochrome = true
)
public class RunCucumberTest {
    // Этот класс используется для запуска Cucumber тестов
}