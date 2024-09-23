package lessonFour.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.PageLoadStrategy;
import ru.iFellow.utils.Props;

public class CucumberHooks {
    private final Props props = ConfigFactory.create(Props.class);

    @Before
    public void initBrowser() {

        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = String.valueOf(PageLoadStrategy.NORMAL);
        Configuration.timeout = props.timeout();
        Configuration.pageLoadTimeout = 60000;
        Configuration.baseUrl = props.baseUrl();

        String baseUrl = props.baseUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("base.url не задан в конфигурации");
        }

        Selenide.open("/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @After
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
