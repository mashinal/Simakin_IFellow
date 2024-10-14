import hooks.WebHooks;
import org.junit.jupiter.api.BeforeEach;
import ru.iFellow.pages.EduJiraLoginPage;
import ru.iFellow.pages.HomePage;
import ru.iFellow.utils.Props;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest extends WebHooks {

//    @BeforeEach
//    public void setUp() {
//        loginToEduJira();
//    }

    protected void loginToEduJira() {
        Props props = Props.props;
        String login = props.login();
        String password = props.password();

        EduJiraLoginPage loginPage = new EduJiraLoginPage();
        loginPage.login(login, password).checkLoggedIn();
    }

    protected void verifyOpenTasksVisible(HomePage homePage) {
        assertTrue(homePage.isOpenTasks(), "Текст 'Открытые задачи' не видно на странице");
    }
}
