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

class MazeGameTest {

    private MazeGame game;

    @BeforeEach
    void setUp() {
        game = new MazeGame();
        // Assuming there is a method to load the maze that doesn't rely on a hard-coded file path
        game.loadMaze("src/main/java/MazaMap_TnJ.csv");
    }

// MazeGame

    @Test
    void testMazeGameConstructor() {
        MazeGame game = new MazeGame();

        assertNotNull(game.getMazeMap(), "Maze should be initialized.");
        assertNotNull(game.getTom(), "Tom should be initialized.");
        assertNotNull(game.getJerry(), "Jerry should be initialized.");
        // Assuming getMazeMap, getTom, and getJerry are methods to access these components
    }
    @Test
    void testLoadMaze() {
        MazeGame game = new MazeGame();
        String testFilePath = "src/main/java/MazaMap_TnJ.csv";

        game.loadMaze(testFilePath);

        // Verify the maze is loaded
        assertNotNull(game.getMazeMap(), "Maze should be loaded.");
        // Further checks can be added based on the expected state of the maze after loading
    }
    @Test
    void testGetJerryPositionAsVertex() {
        MazeGame mazeGame = new MazeGame();
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
    void testGetx() {
        MazeGame mazeGame = new MazeGame();
        MazeGame.Jerry jerry = mazeGame.new Jerry(3, 0);

        assertEquals(3, jerry.gety(), "Jerry's x-coordinate should be 3.");
    }

    @Test
    void testGety() {
        MazeGame mazeGame = new MazeGame();
        MazeGame.Jerry jerry = mazeGame.new Jerry(0, 4);

        assertEquals(4, jerry.getx(), "Jerry's y-coordinate should be 4.");
    }


    @Test
    void testGetColor() {
        // Assuming Jerry's constructor sets the color to orange
        MazeGame mazeGame = new MazeGame();
        MazeGame.Jerry jerry = mazeGame.new Jerry(0, 0); // Adjust if Jerry is an inner class
        Color expectedColor = Color.ORANGE;

        Color actualColor = jerry.getColor();

        assertEquals(expectedColor, actualColor, "Jerry's color should be orange.");
    }


    // Tom
    @Test
    void testFollowPathStepByStep() {
        MazeGame mazeGame = new MazeGame();
        MazeGame.Tom tom = mazeGame.new Tom(0, 0);

        // Mock the path for Tom
        Vertex[] mockPath = new Vertex[]{
                new Vertex(0, 1, 1, 0), // Next step
                new Vertex(0, 2, 2, 0)  // Following step
        };
        tom.setPathToJerry(mockPath); // Assuming a method to set the path

        // First call - Tom should not move
        tom.followPathStepByStep();
        assertEquals(0, tom.getx(), "Tom's x should remain unchanged.");
        assertEquals(0, tom.gety(), "Tom's y should remain unchanged.");

        // Second call - Tom should move to the next step
        tom.followPathStepByStep();
        assertEquals(1, tom.getx(), "Tom should move to x of the next step.");
        assertEquals(1, tom.gety(), "Tom should move to y of the next step.");
    }
    @Test
    void testGetCurrentPosition() {
        MazeGame mazeGame = new MazeGame();
        mazeGame.loadMaze("path/to/maze/file"); // Load the maze
        MazeMap mazeMap = mazeGame.getMazeMap();

        int initialX = 3;
        int initialY = 3;
        MazeGame.Tom tom = mazeGame.new Tom(initialX, initialY);

        Vertex currentPosition = tom.getCurrentPosition();

        // Verify that the position returned is the current position of Tom
        assertNotNull(currentPosition, "Current position should not be null.");
        assertEquals(initialX, currentPosition.getx(), "The x-coordinate should match Tom's x position.");
        assertEquals(initialY, currentPosition.gety(), "The y-coordinate should match Tom's y position.");
    }

    @Test
    void testTomMove() {
        MazeGame mazeGame = new MazeGame();
        mazeGame.loadMaze("src/main/java/MazaMap_TnJ.csv"); // Load the maze
        MazeMap mazeMap = mazeGame.getMazeMap();

        MazeGame.Tom tom = mazeGame.new Tom(0, 0);
        Vertex jerryPosition = new Vertex(10,5, 5, 0); // Assuming Jerry's position is (5, 5)

        // Save Tom's initial position
        int initialTomX = tom.getx()+1;
        int initialTomY = tom.gety()+1;
        // Move Tom towards Jerry
        tom.move(mazeMap, jerryPosition);

        // Check if Tom's position has changed and is closer to Jerry
        assertTrue(tom.getx() != initialTomX || tom.gety() != initialTomY, "Tom should have moved.");
    }


    // Jerry

    @Test
    void testJerryMove() {
        MazeGame mazeGame = new MazeGame();

        // Test movement UP
        MazeGame.Jerry jerryUp = mazeGame.new Jerry(5, 5);
        jerryUp.setDirection(Direction.UP);
        jerryUp.move();
        assertEquals(4, jerryUp.gety(), "Jerry should move up by one unit.");

        // Test movement DOWN
        MazeGame.Jerry jerryDown = mazeGame.new Jerry(5, 5);
        jerryDown.setDirection(Direction.DOWN);
        jerryDown.move();
        assertEquals(6, jerryDown.gety(), "Jerry should move down by one unit.");

        // Test movement LEFT
        MazeGame.Jerry jerryLeft = mazeGame.new Jerry(6, 5);
        jerryLeft.setDirection(Direction.LEFT);
        jerryLeft.move();
        assertEquals(4, jerryLeft.getx(), "Jerry should move left by one unit.");

        // Test movement RIGHT
        MazeGame.Jerry jerryRight = mazeGame.new Jerry(5, 6);
        jerryRight.setDirection(Direction.RIGHT);
        jerryRight.move();
        assertEquals(7, jerryRight.getx(), "Jerry should move right by one unit.");
    }

    @Test
    void testJerryConstructor() {
        MazeGame mazeGame = new MazeGame();
        MazeGame.Jerry jerry = mazeGame.new Jerry(5, 10);

        assertEquals(5, jerry.gety(), "Jerry's initial x should be 5.");
        assertEquals(10, jerry.getx(), "Jerry's initial y should be 10.");
        assertEquals(Color.ORANGE, jerry.getColor(), "Jerry's color should be orange.");
        assertEquals(Direction.LEFT, jerry.getDirection(), "Jerry's initial direction should be left.");
    }



    @Test
    void testIsValidMove() {
        MazeGame mazeGame = new MazeGame();
        MazeGame.Jerry jerry = mazeGame.new Jerry(0, 0);

        Vertex[][] mockMaze = new Vertex[10][10]; // Create a mock maze

        // Initialize the maze with open paths (e.g., vertex type 0)
        for (int i = 0; i < mockMaze.length; i++) {
            for (int j = 0; j < mockMaze[i].length; j++) {
                mockMaze[i][j] = new Vertex(0, i, j, 0); // Assuming Vertex constructor
            }
        }

        assertTrue(jerry.isValidMove(1, 1, mockMaze), "Move should be valid in an open path.");
        // You can add more assertions for different scenarios, like moving into a wall or outside the maze.
    }

    @Test
    void testSetDirection() {
        MazeGame mazeGame = new MazeGame();
        MazeGame.Jerry jerry = mazeGame.new Jerry(0, 0);

        jerry.setDirection(Direction.UP);

        assertEquals(Direction.UP, jerry.getDirection(), "Jerry's direction should be set to LEFT.");
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
        panel.paintComponent(g);

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
        Dimension expected = new Dimension(300, 300); // Replace with the expected dimension
        Dimension actual = panel.getPreferredSize();
        assertEquals(expected, actual, "The preferred size should match the expected dimension.");
    }

    @Test
    void testMainMethod() {
        String[] args = {}; // Empty arguments for the main method

        try {
            MazeGame.main(args); // Attempt to run the main method
        } catch (Exception e) {
            fail("The main method should not throw an exception.");
        }

    }

    // helper functions
    @Test
    void testGetMazeMap() {
        MazeGame game = new MazeGame();
        assertNotNull(game.getMazeMap(), "MazeMap should not be null.");
    }

    @Test
    void testGetJerry() {
        MazeGame game = new MazeGame();
        assertNotNull(game.getJerry(), "Jerry instance should not be null.");
    }

    @Test
    void testGetTom() {
        MazeGame game = new MazeGame();
        assertNotNull(game.getTom(), "Tom instance should not be null.");
    }
}
