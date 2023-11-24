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


    //test void addNeighbors(Vertex cell, List<Vertex> list,MazeMap map)
    @Test
    void test_addNeighbors_boundary(){
    //Noted that addNeighbors don't have the responsibility of detecting barrier
    // It only is used in shortestpath function and shortestpath function won't use it to add the neighbors of a barrier!
    // We only need to test all the neighbours of target Vertex
        int ROWS = map.getROWS();
        int COLS = map.getCOLS();
        //Case: Vertex in first col, have only up,down,right
        Vertex target1 = map.getMazedata()[5][0];
        List<Vertex> neighbors1 = new ArrayList<>();
        Vertex[] temp1 = {map.getMazedata()[4][0],map.getMazedata()[6][0],map.getMazedata()[5][1]};
        List<Vertex> ans1 = Arrays.asList(temp1);
        Shortestpath.addNeighbors(target1,neighbors1,map);//Target Function!
        assertEquals(ans1,neighbors1);
        //Case: Vertex in first row, only down,left,right
        Vertex target2 = map.getMazedata()[0][5];
        List<Vertex> neighbors2 = new ArrayList<>();
        Vertex[] temp2 = {map.getMazedata()[1][5],map.getMazedata()[0][4],map.getMazedata()[0][6]};
        List<Vertex> ans2 = Arrays.asList(temp2);
        Shortestpath.addNeighbors(target2,neighbors2,map);//target function
        assertEquals(ans2,neighbors2);
        //Case: Vertex in last row, only up,left,right
        Vertex target3 = map.getMazedata()[ROWS-1][5];
        List<Vertex> neighbors3 = new ArrayList<>();
        Vertex[] temp3 = {map.getMazedata()[ROWS-2][5],map.getMazedata()[ROWS-1][4],map.getMazedata()[ROWS-1][6]};
        List<Vertex> ans3 = Arrays.asList(temp3);
        Shortestpath.addNeighbors(target3,neighbors3,map);//target function
        assertEquals(ans3,neighbors3);
        //Case: Vertex in last col,only up,down,left
        Vertex target4 = map.getMazedata()[5][COLS-1];
        List<Vertex> neighbors4 = new ArrayList<>();
        Vertex[] temp4 = {map.getMazedata()[4][COLS-1],map.getMazedata()[6][COLS-1],map.getMazedata()[5][COLS-2]};
        List<Vertex> ans4 = Arrays.asList(temp4);
        Shortestpath.addNeighbors(target4,neighbors4,map);//target function
        assertEquals(ans4,neighbors4);
    }
    @Test
    void test_addNeighbors_corner(){

        int ROWS = map.getROWS();
        int COLS = map.getCOLS();
        //Case:[0,0]    down right neighbor
        Vertex target1 = map.getMazedata()[0][0];
        List<Vertex> neighbors1 = new ArrayList<>();
        Vertex[] temp1 = {map.getMazedata()[1][0],map.getMazedata()[0][1]};
        List<Vertex> ans1 = Arrays.asList(temp1);
        Shortestpath.addNeighbors(target1,neighbors1,map);//Target function!
        assertEquals(ans1,neighbors1);
        //Case:[29,0]   up right neighbor
        Vertex target2 = map.getMazedata()[ROWS-1][0];
        List<Vertex> neighbors2 = new ArrayList<>();
        Vertex[] temp2 = {map.getMazedata()[ROWS-2][0],map.getMazedata()[ROWS-1][1]};
        List<Vertex> ans2 = Arrays.asList(temp2);
        Shortestpath.addNeighbors(target2,neighbors2,map);//Target function!
        assertEquals(ans2,neighbors2);
        //Case:[0,29]  down left neighbor
        Vertex target3 = map.getMazedata()[0][COLS-1];
        List<Vertex> neighbors3 = new ArrayList<>();
        Vertex[] temp3 = {map.getMazedata()[1][COLS-1],map.getMazedata()[0][COLS-2]};
        List<Vertex> ans3 = Arrays.asList(temp3);
        Shortestpath.addNeighbors(target3,neighbors3,map);//Target function!
        assertEquals(ans3,neighbors3);
        //Case:[29,29] up left neighbor
        Vertex target4 = map.getMazedata()[ROWS-1][COLS-1];
        List<Vertex> neighbors4 = new ArrayList<>();
        Vertex[] temp4 = {map.getMazedata()[ROWS-2][COLS-1],map.getMazedata()[ROWS-1][COLS-2]};
        List<Vertex> ans4 = Arrays.asList(temp4);
        Shortestpath.addNeighbors(target4,neighbors4,map);//Target function!
        assertEquals(ans4,neighbors4);
    }
    @Test
    void test_addNeighbors_normal(){
        //Case:Vertex in the middle, having up,down,left,right 4 neighbours
        int rows = map.getROWS()-4;
        int cols = map.getCOLS()-4;
        Vertex target = map.getMazedata()[rows][cols];
        List<Vertex> neighbors = new ArrayList<>();
        Vertex[] temp = {map.getMazedata()[rows-1][cols],map.getMazedata()[rows+1][cols],map.getMazedata()[rows][cols-1],map.getMazedata()[rows][cols+1]};
        List<Vertex> ans = Arrays.asList(temp);
        Shortestpath.addNeighbors(target,neighbors,map);//Target function!
        assertEquals(ans,neighbors);
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
