import MazeMap.*;
import Interface.Interface;
import Interface.Container;
import MazeGame.*;
import java.io.File;
import javax.swing.*;

import java.awt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

    Container container;
    Interface screen;

    @BeforeEach
    void setup(){
        screen = new Interface();
        container = new Container(screen);
    }

    @Test
    void test_constructor(){
        Container actualContainer = new Container(screen); // target function
        assertNotNull(actualContainer);
    }

    @Test
    void test_setStartMenu() {
        container.setStartMenu(); // target function
        assertEquals(new Dimension(1032,217),container.getPreferredSize());
    }

    @Test
    void test_handleNavButton() {
        Interface.testMode = 2;
        container.handleNavButton(0); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        container.handleNavButton(1); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        container.handleNavButton(2); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());

        container.handleNavButton(5); // target function
        assertEquals(1,screen.getContentPane().getComponentCount());
    }

    @Test
    void test_initSideMenu() {
        JPanel sidePanel_Actual =  container.initSideMenu(); // target function
        assertEquals(new Dimension(380,780),sidePanel_Actual.getPreferredSize());
    }

    @Test
    void test_setEditMap() {
        container.setEditMap(); // target function
        assertEquals(new Dimension(1140,745),container.getPreferredSize());
    }

    @Test
    void test_handleSPLoad() {
        Interface.testMode = 1;
        assertFalse(container.handleSPLoad()); // target function

        Interface.testMode = 2;
        assertFalse(container.handleSPLoad()); // target function

        Interface.testMode = 3;
        assertTrue(container.handleSPLoad()); // target function

        Interface.testMode = 4;
        assertTrue(container.handleSPLoad()); // target function
    }

    @Test
    void test_setShortestPathExample() {
        container.setShortestPathExample(); // target function
        assertEquals(new Dimension(1140,745),container.getPreferredSize());
    }

    @Test
    void test_setGameScreen() {
        Interface.testMode = 1;
        assertFalse(container.setGameScreen()); // target function

        Interface.testMode = 2;
        assertTrue(container.setGameScreen()); // target function

        Interface.testMode = 3;
        assertTrue(container.setGameScreen()); // target function

        Interface.testMode = 4;
        assertTrue(container.setGameScreen()); // target function
    }
}