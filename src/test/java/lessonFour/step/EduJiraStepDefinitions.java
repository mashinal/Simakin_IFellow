package lessonFour.step;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import ru.iFellow.pages.EduJiraLoginPage;
import ru.iFellow.pages.HomePage;
import ru.iFellow.pages.TestPage;
import ru.iFellow.pages.TaskATHomeworkPage;
import ru.iFellow.pages.models.CreateTaskModal;
import ru.iFellow.utils.Props;
import org.aeonbits.owner.ConfigFactory;

public class EduJiraStepDefinitions {

    private final EduJiraLoginPage loginPage = new EduJiraLoginPage();
    private final HomePage homePage = new HomePage();
    private final TestPage testPage = new TestPage();
    private final TaskATHomeworkPage taskPage = new TaskATHomeworkPage();
    private final Props props = ConfigFactory.create(Props.class);

    private int initialTaskCount;
    private int updatedTaskCount;

    @Given("Я открываю браузер и инициализирую его")
    public void openBrowserAndInitialize() {
    }

    @When("Я ввожу логин и пароль и нажимаю на вход")
    public void enterLoginCredentialsAndLogin() {
        String login = props.login();
        String password = props.password();
        loginPage.login(login, password);
    }

    @Then("Я вижу кнопку {string}")
    public void verifyButtonVisible(String buttonName) {
        if (buttonName.equals("Создать")) {
            loginPage.checkLoggedIn();
        }
    }

    @When("Я нажимаю на проекты и Test")
    public void clickProjectsAndTest() {
        homePage.clickProjectTest();
    }

    @Then("Я вижу текст {string}")
    public void verifyTextVisible(String text) {
        if (text.equals("Открытые задачи")) {
            Assertions.assertTrue(homePage.isOpenTasks(), "Текст 'Открытые задачи' не видно на странице");
        }
    }

    @When("Я проверяю количество задач")
    public void checkTaskCount() {
        initialTaskCount = testPage.getTaskCount();
    }

    @When("Я создаю новую задачу")
    public void createNewTask() {
        testPage.createNewTask();
    }

    @Then("Количество задач увеличилось на {int}")
    public void verifyTaskCountIncreased(int increment) {
        updatedTaskCount = testPage.getTaskCount();
        Assertions.assertEquals(initialTaskCount + increment, updatedTaskCount,
                "Количество задач не увеличилось на " + increment + " после создания новой задачи.");
    }

    @When("Я ищу задачу {string}")
    public void searchTask(String taskName) {
        taskPage.searchTask(taskName);
    }

    @Then("Я вижу статус задачи {string}")
    public void verifyTaskStatus(String expectedStatus) {
        boolean statusCorrect = false;
        switch (expectedStatus) {
            case "Сделать":
                statusCorrect = taskPage.isTaskStatusCorrect();
                break;
            case "Готово":
                taskPage.checkStatusIsDone();
                statusCorrect = true;
                break;
            default:
                Assertions.fail("Неизвестный статус задачи: " + expectedStatus);
        }
        Assertions.assertTrue(statusCorrect, "Статус задачи не соответствует ожидаемому: " + expectedStatus);
    }

    @Then("Я вижу {string} с текстом {string}")
    public void verifyFieldWithText(String fieldName, String expectedText) {
        switch (fieldName) {
            case "Исправить в версиях":
                Assertions.assertTrue(taskPage.isFixVersionCorrect(),
                        "'Исправить в версиях' не содержит '" + expectedText + "'");
                break;
            default:
                Assertions.fail("Неизвестное поле: " + fieldName);
        }
    }

    @When("Я создаю баг")
    public void createBug() {
        CreateTaskModal createTaskModal = new CreateTaskModal();
        createTaskModal.createTaskBug();
    }

    @When("Я открываю созданный тикет")
    public void openCreatedTicket() {
        taskPage.clickAlertLink();
    }

    @When("Я продвигаю тикет до закрытого")
    public void moveTicketToClose() {
        taskPage.moveTicketToClose();
    }

    @Then("Я проверяю, что статус {string}")
    public void verifyFinalStatus(String expectedStatus) {
        verifyTaskStatus(expectedStatus);
    }
}
