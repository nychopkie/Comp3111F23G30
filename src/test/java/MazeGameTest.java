import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import MazeMap.Vertex;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

class MazeGameTest {

    private MazeGame game;

    @BeforeEach
    void setUp() {
        game = new MazeGame();
        // Assuming there is a method to load the maze that doesn't rely on a hard-coded file path
        game.loadMaze("/Users/meng/IdeaProjects/Tom and Jerry/src/main/java/MazaMap_TnJ.csv");
    }

    @Test
    void testMazeLoading() {
        assertNotNull(game.getMazeMap(), "Maze should be loaded and not null");
        assertNotNull(game.getMazeMap().getMazedata(), "Maze data should be loaded and not null");
    }

    @Test
    void testEntityInitialization() {
        assertNotNull(game.getJerry(), "Jerry should be initialized");
        assertNotNull(game.getTom(), "Tom should be initialized");
        // Assuming you have methods to get the positions of Tom and Jerry
        assertNotNull(game.getJerryPositionAsVertex(), "Jerry's position should be initialized");
        assertNotNull(game.getTom().getCurrentPosition(), "Tom's position should be initialized");
    }

    @Test
    void testJerryMovement() {
        Vertex initialPosition = game.getJerryPositionAsVertex();
        game.moveJerry(MazeGame.Direction.RIGHT);
        Vertex newPosition = game.getJerryPositionAsVertex();
        assertNotEquals(initialPosition, newPosition, "Jerry's position should change after moving");
    }


    @Test
    void testGameWinCondition() {

        // Run any game loop or update functions that check the win condition
        game.gameLoop();
        // Move Tom to a random clear vertex that is not the exit point
        Vertex randomClearVertex = findRandomClearVertexNotAtExit();
        game.getTom().setPositionForTesting(randomClearVertex);

        // Place Jerry at the exit point
        Vertex exitPoint = game.getMazeMap().getExit();
        game.getJerry().setPositionForTesting(exitPoint);

        // Check if the game recognizes the win condition
        assertTrue(game.isGameWon(), "Game should be won when Jerry reaches the exit");
    }

    private Vertex findRandomClearVertexNotAtExit() {
        Vertex[][] mazeData = game.getMazeMap().getMazedata();
        Vertex exitPoint = game.getMazeMap().getExit();
        List<Vertex> clearVertices = new ArrayList<>();
        for (int i = 0; i < mazeData.length; i++) {
            for (int j = 0; j < mazeData[i].length; j++) {
                if (mazeData[i][j].getVertex_type() != 1 && !mazeData[i][j].equals(exitPoint)) { // Exclude walls/obstacles and the exit point
                    clearVertices.add(mazeData[i][j]);
                }
            }
        }

        if (clearVertices.isEmpty()) {
            return null; // No clear vertex found
        }

        // Randomly select one clear vertex
        return clearVertices.get(new Random().nextInt(clearVertices.size()));
    }

    @Test
    void testGameLoseCondition() {

        game.gameLoop();

        // Place Jerry at the exit point
        Vertex exitPoint = game.getMazeMap().getExit();
        game.getJerry().setPositionForTesting(exitPoint);

        // Check if the game recognizes the lose condition
        assertTrue(game.isGameLost(), "Game should be lost when Tom catches Jerry");
    }

    @Test
    void testSimulatePlayerMovingJerry() {
        // Setup for Jerry and Tom

        // Simulate player moving Jerry
        simulatePlayerControlledMovement(game);

        // Trigger Tom's movement logic
        game.getTom().move(game.getMazeMap(), game.getJerryPositionAsVertex());

        // Debug: Print Tom's position after path calculation
        System.out.println("Tom's position after path calculation: " + game.getTom().getCurrentPosition());

        // Let Tom follow the path step by step
        while (!isTomCloseEnoughToJerry(game.getTom().getCurrentPosition(), game.getJerryPositionAsVertex())) {
            game.getTom().followPathStepByStep();

            // Debug: Print Tom's position after each step
            System.out.println("Tom's position: " + game.getTom().getCurrentPosition());
        }
    }

    private void simulatePlayerControlledMovement(MazeGame game) {
        for (int i = 0; i < 1000; i++) { // Loop for a certain number of steps
            // Try moving Jerry right, up, down, and left, in that order
            if (canMove(game, MazeGame.Direction.RIGHT)) {
                game.moveJerry(MazeGame.Direction.RIGHT);
            } else if (canMove(game, MazeGame.Direction.UP)) {
                game.moveJerry(MazeGame.Direction.UP);
            } else if (canMove(game, MazeGame.Direction.DOWN)) {
                game.moveJerry(MazeGame.Direction.DOWN);
            } else if (canMove(game, MazeGame.Direction.LEFT)) {
                game.moveJerry(MazeGame.Direction.LEFT);
            }
            game.jerryHasMoved = true;
        }
    }

    private boolean canMove(MazeGame game, MazeGame.Direction direction) {
        int newX = game.getJerry().getx();
        int newY = game.getJerry().gety();
        switch (direction) {
            case UP:    newY--; break;
            case DOWN:  newY++; break;
            case LEFT:  newX--; break;
            case RIGHT: newX++; break;
        }
        Vertex[][] mazeData = game.getMazeMap().getMazedata();
        return newX >= 0 && newX < mazeData[0].length &&
                newY >= 0 && newY < mazeData.length &&
                mazeData[newY][newX].getVertex_type() != 1; // Assuming '1' is an obstacle
    }

    private boolean isTomCloseEnoughToJerry(Vertex tomPosition, Vertex jerryPosition) {
        // Check if Tom is in the same position as Jerry
        if (tomPosition.equals(jerryPosition)) {
            return true;
        }

        // Check for adjacency (up, down, left, right)
        if (Math.abs(tomPosition.getx() - jerryPosition.getx()) <= 1 &&
                Math.abs(tomPosition.gety() - jerryPosition.gety()) <= 1) {
            return true;
        }

        return false;
    }
}
