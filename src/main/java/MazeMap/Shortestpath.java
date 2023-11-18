package MazeMap;
import javax.swing.*;
import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Shortestpath extends JPanel {

    public static Vertex[] shortestPath(MazeMap map,Vertex start,Vertex end, int mode){
        int ROWS= map.getROWS(); int COLS= map.getCOLS();
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
        Vertex[] truepath = trueorderpath(path);
        if (distances[end.getx()][end.gety()] == Integer.MAX_VALUE) return null;
        else if (mode == 1){
            //Entry n Exit
            //print the path and highlight the path
            writecsv(truepath);
            for(int i = 0 ; i < truepath.length; i++) truepath[i].set_Shortest_Path();
            return truepath;
        }

            //Tom n Jerry
            //only return the path
            return truepath;



    }

    private static void addNeighbors(Vertex cell, List<Vertex> list,MazeMap map) {
        int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Vertex[][] MazeMapData = map.getMazedata();
        int ROWS = map.getROWS();
        int COLS = map.getCOLS();
        for (int[] d : ds) {
            int row = cell.getx() + d[0];
            int col = cell.gety() + d[1];
            if (isValid(row, col, ROWS,COLS))
                list.add(MazeMapData[row][col]);
        }
    }

    // find the neighbor of a cell having a certain distance from the start
    private static Vertex getNeighbor(Vertex cell, int distance, int[][] distances,MazeMap map) {
        Vertex[][] MazeMapData = map.getMazedata();
        int ROWS = map.getROWS();
        int COLS = map.getCOLS();
        int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : ds) {
            int row = cell.getx() + d[0];
            int col = cell.gety() + d[1];
            if (isValid(row, col, ROWS,COLS)&& distances[row][col] == distance)
                return MazeMapData[row][col];
        }
        return null;
    }

    // check if coordinates are inside the maze
    private static boolean isValid(int row, int col, int ROWS, int COLS) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }


    public static Vertex[] trueorderpath(Stack<Vertex> path){
        //reverse the order of stack
        int capacity = path.size();
        Vertex[] truepath = new Vertex[capacity];
        Iterator<Vertex> ite = path.iterator();
        for(int i=capacity-1; i >= 0 ;i--){
            truepath[i] = ite.next();
        }

        return truepath;
    }
    /*public static void printpath(Vertex[] path){
        System.out.print("The shortest path is { ");
        for(int i = 0; i< path.length;i++){
            System.out.print(path[i].getx()+","+path[i].gety()+"  ");
        }
        System.out.println(" } ");
        System.out.println("its length is "+ path.length);
    }*/
    public static void writecsv(Vertex[] path){
        PrintWriter pw;
        try {
            pw = new PrintWriter(new File("shortestpath.csv"));

            StringBuffer csvHeader = new StringBuffer("");
            StringBuffer csvData = new StringBuffer("");
            csvHeader.append("PathType,PathNo,Index,Row_X,Col_Y\n");
            pw.write(csvHeader.toString());
            for (int i = 0; i < path.length; i++) {
                csvData.append("SP");
                csvData.append(',');
                csvData.append("1");
                csvData.append(',');
                csvData.append(Integer.toString(i+1));
                csvData.append(',');
                csvData.append(Integer.toString(path[i].getx()));
                csvData.append(',');
                csvData.append(Integer.toString(path[i].gety()));
                csvData.append(',');
                csvData.append('\n');
            }
            pw.write(csvData.toString());
            pw.close();

        }catch (FileNotFoundException e) {
            return;
        }
    }
    //for testing function B
    public static void main(String args[]){
        int[][] b = {
        {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
        {1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
        {0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
        {0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1},
        {1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
        {1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1},
        {1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0},
        {1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0},
        {1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
        {1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
        {2, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0},
        {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1},
        {0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
        {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 3},
        {1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1},
        {1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
        {1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
        {1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1},
        {1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
        {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
        {0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0},
        {1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
        {1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0 }};
        MazeMap a = new MazeMap(b);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(a);
        frame.pack();
        frame.setVisible(true);
        if(shortestPath(a,a.getEntry(),a.getExit(),1)==null) System.out.print("No Path");
    }


}
