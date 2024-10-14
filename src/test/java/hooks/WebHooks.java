package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.iFellow.utils.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class WebHooks {
    private static final Logger logger = LoggerFactory.getLogger(WebHooks.class);
    Props props = Props.props;

    @BeforeEach
    public void initBrowser() {
        logger.info("Инициализация браузера");

        String chromeDriverPath = props.chromedriverPath();
        if (chromeDriverPath != null && !chromeDriverPath.isEmpty() && Files.exists(Paths.get(chromeDriverPath))) {
            System.out.println(String.format("Указан путь к ChromeDriver: %s", chromeDriverPath));
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            WebDriver webDriver = new ChromeDriver();
            WebDriverRunner.setWebDriver(webDriver);
        } else {
            logger.info("Путь к ChromeDriver не указан. Используем драйвер по умолчанию Selenide");
        }

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
