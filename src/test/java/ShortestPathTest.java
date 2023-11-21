import MazeGame.MazeGame;
import MazeMap.MazeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShortestPathTest {
    private MazeMap map;
    @BeforeEach
    void setUp() {
        map = new MazeMap();
        // Call a method to load the maze
        // Note: You might need to adjust this part to fit your actual maze loading method
        map.load_MazeMap("Assets/map/MazeMap_SAMPLE.csv");// need to change this
    }
    //test boolean isValid(int row, int col, int ROWS, int COLS)
    @Test
    void test_isVaild(){
        int ROWS = map.getROWS();
        int COLS = map.getCOLS();
        //Boundary:
        //Vailid: 0,0 to 29,29
        //Invalid

    }
    //test void addNeighbors(Vertex cell, List<Vertex> list,MazeMap map)
    @Test
    void test_addNeighbors(){

    }
    //test Vertex getNeighbor(Vertex cell, int distance, int[][] distances,MazeMap map)

    //test Vertex[] trueorderpath(Stack<Vertex> path)
    //test void writecsv(Vertex[] path)
    //test Vertex[] shortestPath(MazeMap map,Vertex start,Vertex end, int mode)

}
