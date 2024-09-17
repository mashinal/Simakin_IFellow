import config.WebHooks;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iFellow.pages.EduJiraLoginPage;
import ru.iFellow.pages.HomePage;
import ru.iFellow.pages.TaskATHomeworkPage;
import ru.iFellow.pages.TestPage;
import ru.iFellow.pages.models.CreateTaskModal;
import ru.iFellow.utils.CredentialsReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EduJiraTest extends WebHooks {

    @Test
    @DisplayName("Авторизоваться в edujira")
    public void loginToEduJiraTest() {

        CredentialsReader credentialsReader = new CredentialsReader();
        String login = credentialsReader.getLogin();
        String password = credentialsReader.getPassword();

        EduJiraLoginPage loginPage = new EduJiraLoginPage();
        loginPage.login(login, password);
        loginPage.checkLoggedIn();
    }

    @Test
    @DisplayName("Перейти в проект 'Test'")
    public void clickProjectTest() {

        CredentialsReader credentialsReader = new CredentialsReader();
        String login = credentialsReader.getLogin();
        String password = credentialsReader.getPassword();

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

        CredentialsReader credentialsReader = new CredentialsReader();
        String login = credentialsReader.getLogin();
        String password = credentialsReader.getPassword();

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

        CredentialsReader credentialsReader = new CredentialsReader();
        String login = credentialsReader.getLogin();
        String password = credentialsReader.getPassword();

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

        CredentialsReader credentialsReader = new CredentialsReader();
        String login = credentialsReader.getLogin();
        String password = credentialsReader.getPassword();

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
        createTaskModal.createTackBug();

        taskPage.clickAlertLink()
                .moveTicketToClose()
                .checkStatusIsDone();
    }
}


