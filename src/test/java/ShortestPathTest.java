import MazeMap.Shortestpath;
import MazeMap.MazeMap;
import MazeMap.Vertex;
import java.io.BufferedReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class ShortestPathTest {
    private MazeMap map;
    @BeforeEach
    void setUp() {
        map = new MazeMap();
        // Call a method to load the maze
        // Note: You might need to adjust this part to fit your actual maze loading method
        map.load_MazeMap("Assets/map/MazeMap_SAMPLE.csv");
    }



    //test Vertex getNeighbor(Vertex cell, int distance, int[][] distances,MazeMap map)
    @Test
    void test_getNeighbor(){
    // Test can it return the neighbour of the target vertex having same distance as the parameter
        int ROWS = map.getROWS();
        int COLS = map.getCOLS();
        int[][] distances = new int[ROWS][COLS];
        for(int i = 0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                distances[i][j] = Integer.MAX_VALUE;
            }
        }
        //Case: have that distance nearby, return that vertex with that distance
        // target cell: (9,10)
        Vertex cell = map.getMazedata()[9][10];
        int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : ds) {
            int row = cell.getx() + d[0];
            int col = cell.gety() + d[1];
            if (d == ds[0]){
                distances[row][col] =3 ;
            } else if (d == ds[1]) {
                distances[row][col] = 4;
            } else if (d == ds[2]) {
                distances[row][col] = 5;
            } else if (d == ds[3]) {
                distances[row][col] = 1;
            }

        }
        // We set up the distance of the neighbors of target cell for testing
        Vertex output = Shortestpath.getNeighbor(cell,5,distances,map); //target function!
        // The assumed answer should be Vertex at(9,9)
        assertEquals(output,map.getMazedata()[9][9]);

        //Case: don't have that distance nearby, return null
        Vertex nulloutput = Shortestpath.getNeighbor(cell,6,distances,map);//target function!
        assertEquals(nulloutput,null);

    }

    //test Vertex[] shortestPath(MazeMap map,Vertex start,Vertex end, int mode)
    @Test
    void test_shortestPath(){
        //mode 1, entry n exit
        Vertex[] output1 = Shortestpath.shortestPath(map,map.getEntry(),map.getExit(),1);//target function!
        String fileName = "Assets/Test_path/test_path.csv";
        String line;
        String delimiter=",";
        boolean flag = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int i = 0;
            br.readLine();
            while((line = br.readLine()) != null){
                List<String> values = Arrays.asList(line.split(delimiter));
                if(Integer.valueOf(values.get(3)) != output1[i].getx()){
                    flag = false;
                }
                if(Integer.valueOf(values.get(4)) !=output1[i].gety()){
                    flag = false;
                }
                i++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //mode 2, clear vertex start and clear vertex end
        Vertex[] output2 = Shortestpath.shortestPath(map,map.getMazedata()[18][12],map.getMazedata()[16][6],0);//target function
        fileName = "Assets/Test_path/Test_path2.csv";
        boolean flag2 = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int i = 0;
            br.readLine();
            while((line = br.readLine()) != null){
                List<String> values = Arrays.asList(line.split(delimiter));
                if(Integer.valueOf(values.get(3)) != output2[i].getx()){
                    flag2 = false;
                }
                if(Integer.valueOf(values.get(4)) !=output2[i].gety()){
                    flag2 = false;
                }
                i++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //case 3 , invalid , no possible path
        Vertex[] output3 = Shortestpath.shortestPath(map,map.getMazedata()[0][0],map.getExit(),0);//target function
        boolean flag3 = output3 == null;
        assertTrue(flag&&flag2&&flag3);
    }
}
