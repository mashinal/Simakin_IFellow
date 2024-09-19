package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import ru.iFellow.utils.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebHooks {
    private static final Logger logger = LoggerFactory.getLogger(WebHooks.class);
    Props props = Props.props;

    @BeforeEach
    public void initBrowser() {
        logger.info("Инициализация браузера");

        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = String.valueOf(PageLoadStrategy.NORMAL);
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.baseUrl = props.baseUrl();


        String baseUrl = props.baseUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("base.url не задан в конфигурации");
        }

        logger.info("Открытие URL: {}", baseUrl);
        Selenide.open("/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        logger.info("Закрытие браузера");
        Selenide.closeWebDriver();
    }
}
