import org.junit.jupiter.api.Test;
import Interface.Interface;
import Interface.Launcher;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {

    @Test
    void test_main() {
        Interface temp = new Interface();
        Interface.testMode = 2;
        Launcher.main(null); // target function
        assertTrue(true);
    }
}