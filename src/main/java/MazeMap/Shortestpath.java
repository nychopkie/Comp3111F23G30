package MazeMap;
import javax.swing.*;
import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * ============ Shortestpath Class ============
 * it contains a function to find the shortest path and other helper functions used by that function.<br>
 * Details could be found above each function. <br>
 * @operations:
 * 1. shortestPath(MazeMap map,Vertex start,Vertex end, int mode)<br>
 * 2. addNeighbors(Vertex cell, List<Vertex> list, MazeMap map) <br>
 * 3. getNeighbor(Vertex cell, int distance, int[][] distances,MazeMap map)<br>
 * 4. trueorderpath(Stack<Vertex> path)<br>
 * 5. writecsv(Vertex[] path)<br>
 * */
public class Shortestpath extends JPanel {
    /**
     *
     *the function to find the shortest path from the starting vertex to the ending vertex
     *@param map        the Maze map that the user decided to play. Only Maze map with at least 1 possible path should be passed in this parameter.
     *@param start      the starting point of the shortest path
     *@param mode       Either 0 or 1. Mode 0 represents the Tom and Jerry mode, where the system calls the shortestpath function
     *                  and only want to receive the shortest path as return. Mode 1 represents the normal mode where the user directly calls
     *                  the shortestpath function and want to receive a csv file containing the shortest path and seeing graphic representation
     *                  of the shortest path.
     *@return           a Vertex array containing the shortest path between the strating point and the ending point.
     *                  Null if map do not have a possible path from the starting vertex to the ending vertex.
     */
    public static Vertex[] shortestPath(MazeMap map,Vertex start,Vertex end, int mode){
        int ROWS= map.getSIZE();
        int COLS= map.getSIZE();
        Vertex[][] MazeMapData =map.getMazedata();
        Stack<Vertex> path = new Stack<>();
        int[][] distances = new int[ROWS][COLS];
        for(int i = 0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                distances[i][j] = Integer.MAX_VALUE;
            }
        }
        int distance = 0;
        List<Vertex> currentCells = Arrays.asList(start);
        while (distances[end.getx()][end.gety()] == Integer.MAX_VALUE
                && !currentCells.isEmpty()){
            List<Vertex> nextCells = new ArrayList<>();

            // loop over all cells added in previous round
            // set their distance
            //    and add their neighbors to the list for next round
            for (Vertex cell : currentCells) {
                if (distances[cell.getx()][cell.gety()] == Integer.MAX_VALUE
                        && MazeMapData[cell.getx()][cell.gety()].getVertex_type() != 1 ) {
                    distances[cell.getx()][cell.gety()] = distance;
                    addNeighbors(cell, nextCells, map);
                }
            }
            // prepare for next round
            currentCells = nextCells;
            distance++;
        }
        // find the path
        if (distances[end.getx()][end.gety()] < Integer.MAX_VALUE) {
            Vertex cell = end;
            path.push(end);
            for (int d = distances[end.getx()][end.gety()]-1; d >= 0; d--) {
                cell = getNeighbor(cell, d, distances,map);
                path.push(cell);
            }
        }

        int capacity = path.size();
        Vertex[] truepath = new Vertex[capacity];
        Iterator<Vertex> ite = path.iterator();
        for(int i=capacity-1; i >= 0 ;i--){
            truepath[i] = ite.next();
        }

        if (distances[end.getx()][end.gety()] == Integer.MAX_VALUE)
            return null;
        else if (mode == 1){
            //Entry n Exit
            //print the path and highlight the path
            PrintWriter pw;
            try {
                pw = new PrintWriter(new File("shortestpath.csv"));

                StringBuffer csvHeader = new StringBuffer("");
                StringBuffer csvData = new StringBuffer("");
                csvHeader.append("PathType,PathNo,Index,Row_X,Col_Y\n");
                pw.write(csvHeader.toString());
                for (int i = 0; i < truepath.length; i++) {
                    csvData.append("SP");
                    csvData.append(',');
                    csvData.append("1");
                    csvData.append(',');
                    csvData.append(Integer.toString(i+1));
                    csvData.append(',');
                    csvData.append(Integer.toString(truepath[i].getx()));
                    csvData.append(',');
                    csvData.append(Integer.toString(truepath[i].gety()));
                    csvData.append(',');
                    csvData.append('\n');
                }
                pw.write(csvData.toString());
                pw.close();

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for(int i = 0 ; i < truepath.length; i++)
                truepath[i].set_Shortest_Path();
            return truepath;
        }
            //mode 0 : Tom n Jerry
            //only return the path
            return truepath;
    }

    /**
     * the function to add all valid neighbors of a target vertex into a list.
     * @param cell  the target vertex that we want to find its neighbors.
     * @param list  the list that storing the neighbors of target vertex after the function.
     * @param map   the Maze map that the user decided to play.
     */
    public static void addNeighbors(Vertex cell, List<Vertex> list, MazeMap map) {
        int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Vertex[][] MazeMapData = map.getMazedata();
        int ROWS = map.getSIZE();
        int COLS = map.getSIZE();
        for (int[] d : ds) {
            int row = cell.getx() + d[0];
            int col = cell.gety() + d[1];
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS)
                list.add(MazeMapData[row][col]);
        }
    }

    /**
     * the function to get the neighbor of the target cell, which is with a particular distance value.
     * @param cell          the target vertex that we want to find its neighbor.
     * @param distance      distance from the starting point to that neighbor cell.
     * @param distances     a Vertex 2D array containing the distances from the starting point to all vertex.
     * @param map           the Maze map that the user decided to play.
     * @return              A vertex that is the neighbor of cell and has the specified distance.
     *                      Null if such vertex does not exist.
     */
    public static Vertex getNeighbor(Vertex cell, int distance, int[][] distances,MazeMap map) {
        Vertex[][] MazeMapData = map.getMazedata();
        int ROWS = map.getSIZE();
        int COLS = map.getSIZE();
        int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : ds) {
            int row = cell.getx() + d[0];
            int col = cell.gety() + d[1];
            if ((row >= 0 && row < ROWS && col >= 0 && col < COLS)&&
                    distances[row][col] == distance)
                return MazeMapData[row][col];
        }
        return null;
    }
}

