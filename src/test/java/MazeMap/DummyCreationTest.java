package MazeMap;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

class DummyCreationTest {

    @Test
    void createMap() {
        int input_x = 3;
        int input_y = 4;
        int[][] expected = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        int[][] actual = MazeMap.DummyCreation.createMap(input_x, input_y);
        assertArrayEquals(expected, actual);
    }

    @Test
    void changeValue() {
        int[][] expected = new int[][] {{0,0},{1,0}};
        int[][] input = new int[][] {{0,0},{0,0}};
        int[][] actual = MazeMap.DummyCreation.changeValue(input,1,0);
        assertArrayEquals(expected, actual);
    }
}