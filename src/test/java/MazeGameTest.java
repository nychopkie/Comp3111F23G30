import MazeGame.MazeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import MazeMap.Vertex;
import MazeGame.MazeGame.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import MazeMap.MazeMap;
import MazeGame.MazeGame.GameEntity;
import MazeGame.MazeGame.Direction;
import java.awt.*;
import Interface.*;

class MazeGameTest {

    private MazeGame game;
    private Interface screen;

    @BeforeEach
    void setUp() {
        screen = new Interface();
        game = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        // Assuming there is a method to load the maze that doesn't rely on a hard-coded file path
        //game.loadMaze("src/main/java/MazaMap_TnJ.csv");
    }

// MazeGame

    @Test
    void testMazeGameConstructor() {
        MazeGame game = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);

        assertNotNull(game.getMazeMap(), "Maze should be initialized.");
        assertNotNull(MazeGame.tom, "Tom should be initialized.");
        assertNotNull(MazeGame.jerry, "Jerry should be initialized.");
        // Assuming getMazeMap, getTom, and getJerry are methods to access these components
    }
    @Test
    void testLoadMaze() {
        MazeGame game = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        String testFilePath = "Assets/map/MazeMap_SAMPLE2.csv";

        game.loadMaze(testFilePath);

        // Verify the maze is loaded
        assertNotNull(game.getMazeMap(), "Maze should be loaded.");
        // Further checks can be added based on the expected state of the maze after loading
    }
    @Test
    void testGetJerryPositionAsVertex() {
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        MazeGame.Jerry jerry = mazeGame.new Jerry(0, 0);
        Vertex expectedPosition = new Vertex(0, 12, 5, 0); // Assuming initial position of Jerry

        Vertex actualPosition = mazeGame.getJerryPositionAsVertex();

        assertEquals(expectedPosition.getx(), actualPosition.getx(), "Jerry's position should match the expected position.");
    }

    // Game Entity
    @Test
    void testConstructor() {
        int x = 5;
        int y = 10;
        Color color = Color.RED;
        GameEntity entity = new GameEntity(x, y, color);

        assertEquals(x, entity.getx(), "X coordinate should be initialized correctly.");
        assertEquals(y, entity.gety(), "Y coordinate should be initialized correctly.");
        assertEquals(color, entity.getColor(), "Color should be initialized correctly.");
    }
    @Test
    void testDraw() {
        GameEntity entity = new GameEntity(5, 10, Color.BLACK);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        // Fill the image with a color
        g.setColor(Color.RED);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());

        // Draw the entity on the BufferedImage
        entity.draw(g);

        boolean isDrawn = false;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) != Color.WHITE.getRGB()) {
                    isDrawn = true;
                    break;
                }
            }
            if (isDrawn) break;
        }

        assertTrue(isDrawn, "Entity should draw something on the panel.");

        g.dispose();
    }


    @Test
    void testGety() {
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        MazeGame.Jerry jerry = mazeGame.new Jerry(3, 0);
        assertEquals(3, jerry.gety(), "Jerry's x-coordinate should be 3.");//target function
    }

    @Test
    void testGetx() {
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        MazeGame.Jerry jerry = mazeGame.new Jerry(0, 4);
        assertEquals(4, jerry.getx(), "Jerry's y-coordinate should be 4.");//target function
    }


    @Test
    void testGetColor() {
        // Assuming Jerry's constructor sets the color to orange
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        MazeGame.Jerry jerry = mazeGame.new Jerry(0, 0); // Adjust if Jerry is an inner class
        Color expectedColor = Color.ORANGE;

        Color actualColor = jerry.getColor();//target function

        assertEquals(expectedColor, actualColor, "Jerry's color should be orange.");
    }


    // Tom
    @Test
    void testFollowPathStepByStep() {
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        MazeGame.Tom tom = mazeGame.new Tom(0, 0);

        // Mock the path for Tom
        Vertex[] mockPath = new Vertex[]{
                new Vertex(0, 1, 1, 0), // Next step
                new Vertex(0, 2, 2, 0)  // Following step
        };
        tom.setPathToJerry(mockPath); // Assuming a method to set the path

        // First call - Tom should not move
        tom.followPathStepByStep();//target function
        assertEquals(0, tom.getx(), "Tom's x should remain unchanged.");
        assertEquals(0, tom.gety(), "Tom's y should remain unchanged.");

        // Second call - Tom should move to the next step
        tom.followPathStepByStep();//target function
        assertEquals(1, tom.getx(), "Tom should move to x of the next step.");
        assertEquals(1, tom.gety(), "Tom should move to y of the next step.");
    }
    @Test
    void testGetCurrentPosition() {
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        mazeGame.loadMaze("Assets/map/MazeMap_SAMPLE.csv"); // Load the maze
        MazeMap mazeMap = mazeGame.getMazeMap();

        int initialX = 3;
        int initialY = 3;
        MazeGame.Tom tom = mazeGame.new Tom(initialX, initialY);

        Vertex currentPosition = tom.getCurrentPosition();//target function

        // Verify that the position returned is the current position of Tom
        assertNotNull(currentPosition, "Current position should not be null.");
        assertEquals(initialX, currentPosition.getx(), "The x-coordinate should match Tom's x position.");
        assertEquals(initialY, currentPosition.gety(), "The y-coordinate should match Tom's y position.");
    }

    @Test
    void testTomMove() {
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        mazeGame.loadMaze("Assets/map/MazeMap_SAMPLE.csv"); // Load the maze
        MazeMap mazeMap = mazeGame.getMazeMap();

        MazeGame.Tom tom = mazeGame.new Tom(0, 0);
        Vertex jerryPosition = new Vertex(25,5, 5, 0); // Assuming Jerry's position is (5, 5)

        // Save Tom's initial position
        int initialTomX = tom.getx()+1;
        int initialTomY = tom.gety()+1;
        // Move Tom towards Jerry
        tom.move(mazeMap, jerryPosition);//target function

        // Check if Tom's position has changed and is closer to Jerry
        assertTrue(tom.getx() != initialTomX || tom.gety() != initialTomY, "Tom should have moved.");
    }

    // Jerry
    @Test
    void testJerryMove() {
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);

        // Test movement UP
        MazeGame.Jerry jerryUp = mazeGame.new Jerry(5, 5);
        MazeGame.Jerry.direction = Direction.UP;
        jerryUp.move();//target function
        assertEquals(4, jerryUp.gety(), "Jerry should move up by one unit.");

        // Test movement DOWN
        MazeGame.Jerry jerryDown = mazeGame.new Jerry(5, 5);
        MazeGame.Jerry.direction = Direction.DOWN;
        jerryDown.move();//target function
        assertEquals(6, jerryDown.gety(), "Jerry should move down by one unit.");

        // Test movement LEFT
        MazeGame.Jerry jerryLeft = mazeGame.new Jerry(6, 5);
        MazeGame.Jerry.direction = Direction.LEFT;
        jerryLeft.move();//target function
        assertEquals(4, jerryLeft.getx(), "Jerry should move left by one unit.");

        // Test movement RIGHT
        MazeGame.Jerry jerryRight = mazeGame.new Jerry(5, 6);
        MazeGame.Jerry.direction = Direction.RIGHT;
        jerryRight.move();//target function
        assertEquals(7, jerryRight.getx(), "Jerry should move right by one unit.");
    }

    @Test
    void testJerryConstructor() {
        MazeGame mazeGame = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        MazeGame.Jerry jerry = mazeGame.new Jerry(5, 10); //target function

        assertEquals(5, jerry.gety(), "Jerry's initial x should be 5.");
        assertEquals(10, jerry.getx(), "Jerry's initial y should be 10.");
        assertEquals(Color.ORANGE, jerry.getColor(), "Jerry's color should be orange.");
        assertEquals(Direction.LEFT, MazeGame.Jerry.direction, "Jerry's initial direction should be left.");
    }

// test for GamePanel
@Test
void testPaintComponentColors() {
    GamePanel panel = new GamePanel();
    Vertex entryPoint = game.getMazeMap().getEntry();
    // Create a BufferedImage to get a real Graphics object
    BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();

    try {
        panel.paintComponent(g); // target function

        // Assuming you know the expected color and position
        // For example, checking if the pixel at Entry point
        Color expectedColor = new Color(64, 64, 64);
        Color actualColor = new Color(image.getRGB(0, 0), true); // Extracts color at pixel (0, 0)

        assertEquals(expectedColor, actualColor, "The pixel color is the same.");

    } finally {
        // Dispose of the Graphics object to release system resources
        g.dispose();
    }
}

    @Test
    void testGetPreferredSize() {
        MazeGame.GamePanel panel = new GamePanel();
        Dimension expected = new Dimension(750, 750); // Replace with the expected dimension
        Dimension actual = panel.getPreferredSize(); // target function
        assertEquals(expected, actual, "The preferred size should match the expected dimension.");
    }

    // helper functions
    @Test
    void testGetMazeMap() {
        MazeGame game = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        assertNotNull(game.getMazeMap(), "MazeMap should not be null."); // target function
    }

    @Test
    void testGetPanel() {
        MazeGame game = new MazeGame("Assets/map/MazeMap_SAMPLE.csv", screen);
        GamePanel panel = game.getPanel(); // target function
        Dimension expected = new Dimension(750, 750); // Replace with the expected dimension
        Dimension actual = panel.getPreferredSize(); // target function
        assertEquals(expected, actual, "The preferred size should match the expected dimension.");
    }
}
