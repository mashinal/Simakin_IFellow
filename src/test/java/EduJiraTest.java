import hooks.WebHooks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iFellow.pages.EduJiraLoginPage;
import ru.iFellow.pages.HomePage;
import ru.iFellow.pages.TaskATHomeworkPage;
import ru.iFellow.pages.TestPage;
import ru.iFellow.pages.models.CreateTaskModal;
import ru.iFellow.utils.Props;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EduJiraTest extends WebHooks {

    @Test
    @DisplayName("Авторизоваться в edujira")
    public void loginToEduJiraTest() {

        Props props = Props.props;

        String login = props.login();
        String password = props.password();

        EduJiraLoginPage loginPage = new EduJiraLoginPage();
        loginPage.login(login, password);
        loginPage.checkLoggedIn();
    }

    @Test
    @DisplayName("Перейти в проект 'Test'")
    public void clickProjectTest() {

        Props props = Props.props;

        String login = props.login();
        String password = props.password();

        EduJiraLoginPage loginPage = new EduJiraLoginPage();
        loginPage.login(login, password);
        loginPage.checkLoggedIn();

        HomePage homePage = new HomePage();
        homePage.clickProjectTest();
        assertTrue(homePage.isOpenTasks(), "Текст 'Открытые задачи' не видно на странице");
    }

    @Test
    @DisplayName("Проверить общее количество заведенных задач в проекте")
    public void checkNumberCreatedTasks() {

        Props props = Props.props;

        String login = props.login();
        String password = props.password();

        EduJiraLoginPage loginPage = new EduJiraLoginPage();
        loginPage.login(login, password);
        loginPage.checkLoggedIn();

        HomePage homePage = new HomePage();
        homePage.clickProjectTest();
        assertTrue(homePage.isOpenTasks(), "Текст 'Открытые задачи' не видно на странице");

        TestPage testPage = new TestPage();
        int initialTaskCount = testPage.getTaskCount();
        testPage.createNewTask();
        int updatedTaskCount = testPage.getTaskCount();
        assertEquals(initialTaskCount + 1, updatedTaskCount, "Количество задач не увеличилось на 1 после создания" +
                " новой задачи");
    }

    @Test
    @DisplayName("Перейти в задачу TestSeleniumATHomework и проверить 'статус задачи' и 'Исправить в версиях'")
    public void cheskStatusTask() {

        Props props = Props.props;

        String login = props.login();
        String password = props.password();

        EduJiraLoginPage loginPage = new EduJiraLoginPage();
        loginPage.login(login, password);
        loginPage.checkLoggedIn();

        HomePage homePage = new HomePage().clickProjectTest();
        assertTrue(homePage.isOpenTasks(), "Текст 'Открытые задачи' не видно на странице");

        TestPage testPage = new TestPage();
        int initialTaskCount = testPage.getTaskCount();
        testPage.createNewTask();
        int updatedTaskCount = testPage.getTaskCount();
        assertEquals(initialTaskCount + 1, updatedTaskCount, "Количество задач не увеличилось на 1 после создания" +
                " новой задачи.");

        TaskATHomeworkPage taskPage = new TaskATHomeworkPage();
        taskPage.searchTask("TestSeleniumATHomework");
        assertTrue(taskPage.isTaskStatusCorrect(), "Статус задачи не содержит 'Сделать'");
        assertTrue(taskPage.isFixVersionCorrect(), "'Исправить в версиях' не содержит 'Version 2.0'");
    }

    @Test
    @DisplayName("Создать новый баг с описанием")
    public void createBug() {

        Props props = Props.props;

        String login = props.login();
        String password = props.password();

        EduJiraLoginPage loginPage = new EduJiraLoginPage();
        loginPage.login(login, password);
        loginPage.checkLoggedIn();

        HomePage homePage = new HomePage().clickProjectTest();

        assertTrue(homePage.isOpenTasks(), "Текст 'Открытые задачи' не видно на странице.");

        TestPage testPage = new TestPage();
        int initialTaskCount = testPage.getTaskCount();
        testPage.createNewTask();
        int updatedTaskCount = testPage.getTaskCount();

        assertEquals(initialTaskCount + 1, updatedTaskCount,
                "Количество задач не увеличилось на 1 после создания новой задачи.");

        TaskATHomeworkPage taskPage = new TaskATHomeworkPage();
        taskPage.searchTask("TestSeleniumATHomework");

        assertTrue(taskPage.isTaskStatusCorrect(), "Статус задачи не содержит 'Сделать'.");
        assertTrue(taskPage.isFixVersionCorrect(), "'Исправить в версиях' не содержит 'Version 2.0'.");

        CreateTaskModal createTaskModal = new CreateTaskModal();
        createTaskModal.createTaskBug();

        taskPage.clickAlertLink()
                .moveTicketToClose()
                .checkStatusIsDone();
    }
}


