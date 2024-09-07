import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RunTest {
    //its RunTest origin


    @BeforeEach
    public void setup() {
        System.out.println("Setting upup before each test");
    }

    @AfterEach
    public void teardown() {
        System.out.println("Tearing down after each testing");
    }

    @Test
    public void test1() {
        System.out.println("Running test 1");
        Assertions.assertEquals("AW", "AW", "Test 1 failed");
    }
}
