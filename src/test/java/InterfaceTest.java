import MazeMap.*;
import Interface.*;
import MazeGame.*;
import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InterfaceTest {

    private Interface screen;

    @BeforeEach
    void setup(){
        screen = new Interface();
        Interface.testMode = 2; // no need to input files
    }

    @Test
    protected void test_constructor() {
        Interface actualScreen = null;
        actualScreen = new Interface(); // target function
        assertNotNull(actualScreen);
    }

    @Test
    protected void test_setDisplayState() {
        boolean flag1 = screen.setDisplayState(-1); // target function
        assertFalse(flag1);

        boolean flag2 = screen.setDisplayState(9); // target function
        assertFalse(flag2);

        boolean flag3 = screen.setDisplayState(0); // target function
        assertTrue(flag3);

        boolean flag4 = screen.setDisplayState(3); // target function
        assertTrue(flag4);
    }

    @Test
    protected void test_display() {
        // 0
        screen.setDisplayState(0);
        screen.display(); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        // 1
        screen.setDisplayState(1);
        screen.display(); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        // 2
        screen.setDisplayState(2);
        screen.display(); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        // 3
        screen.setDisplayState(5);
        screen.display(); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());
    }

    @Test
    protected void test_showMainMenu(){
        screen.showMainMenu(); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());
    }

    @Test
    protected void test_showGameWindow(){
        Interface.testMode = 1;
        assertFalse(screen.showGameWindow()); //target function

        Interface.testMode = 2;
        assertTrue(screen.showGameWindow()); //target function

        Interface.testMode = 3;
        assertTrue(screen.showGameWindow()); //target function

        Interface.testMode = 4;
        assertTrue(screen.showGameWindow()); //target function
    }

    @Test
    protected void test_showMapEdit(){
        screen.showMapEdit(); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());
    }

    @Test
    protected void test_showTestB(){
        screen.showTestB(); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());
    }

    @Test
    void test_handleSPLoad() {
        Interface.testMode = 1;
        assertFalse(screen.handleSPLoad()); // target function

        Interface.testMode = 2;
        assertFalse(screen.handleSPLoad()); // target function

        Interface.testMode = 3;
        assertTrue(screen.handleSPLoad()); // target function

        Interface.testMode = 4;
        assertTrue(screen.handleSPLoad()); // target function
    }

    @Test
    void test_handleNavButton() {
        Interface.testMode = 2;
        screen.handleNavButton(0); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        screen.handleNavButton(1); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        screen.handleNavButton(2); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        screen.handleNavButton(5); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());
    }
}