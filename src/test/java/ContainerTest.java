import MazeMap.*;
import Interface.*;
import MazeGame.*;
import java.io.File;

import java.awt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

    Robot robot;

    @BeforeEach
    void setup(){
        try{
            robot = new Robot();
        }
        catch(AWTException f){
            throw new RuntimeException(f);
        }
    }

    @Test
    void test_setStartMenu() {
    }

    @Test
    void test_setTestMenu() {
    }

    @Test
    void test_initSideMenu() {
    }

    @Test
    void test_setEditMap() {
    }

    @Test
    void test_setShortestPathExample() {
    }

    @Test
    void test_setGameScreen() {
    }

    @Test
    void test_setTestC() {
    }
}