import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import MazeGame.*;

class MazeGameTest {

    private MazeGame game;

    @BeforeEach
    void setUp() {
        game = new MazeGame();
        // Call a method to load the maze
        // Note: You might need to adjust this part to fit your actual maze loading method
        game.loadMaze("Assets/map/MazeMap_SAMPLE.csv");// need to change this
    }

    @Test
    void testMazeLoading() {
        int[][] expectedMaze = new int[][] {

                //************ Fill in with expected maze data

                 };
        assertArrayEquals(expectedMaze, game.getMaze());
    }

    @Test
    void testJerryMovement() {
        // Assume starting position of Jerry is (0, 12)
        // Simulate a move to the right
        game.moveJerry(MazeGame.Direction.RIGHT);

        // Now, get Jerry's position to validate the move
        int[] jerryPosition = game.getJerryPosition();
        int[] expectedPosition = new int[] {1, 12}; // Expected position after moving right *** DO NOT HARD CODE.

        assertArrayEquals(expectedPosition, jerryPosition);
    }
}
