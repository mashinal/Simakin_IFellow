package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;


public class WebHooks {

    @BeforeEach
    public void initBrowser() {
        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = PageLoadStrategy.NORMAL.toString();
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 1500000;
        Selenide.open("https://edujira.ifellow.ru");
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}