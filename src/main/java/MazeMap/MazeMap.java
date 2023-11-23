package MazeMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;


/**
* ============ MazeMap Class ============
* @attributes:
 * 1. Vertex [30][30] MazeMapData: the map of the maze in a 2D array<br>
 *    &nbsp;&nbsp;&nbsp;&nbsp;- have 4 different values to
 *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;represent different things in the
 *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;map<br>
 * 2. ROWS: the number of rows in the map<br>
 * 3. COLS: the number of cols in the map<br>
 * 4. PIXEL_SIZE: the size of each vertex<br>
 * 5. GAP: the size of each gap
 *
 * @operations:
 * 1. load_MazeMap(csv path)<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;- the function to load a pre-existing
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;map to the screen
 *
 * */
public class MazeMap extends JPanel{
    // variables for the class
    /** the maze map containing all Vertices */
    Vertex[][] MazeMapData;

    /** the value for the map sizing*/
    //I change the ROWS,COLS to 3 for easier testing
    private static final int ROWS = 30, COLS = 30,  PIXEL_SIZE = 25, GAP = 1;
    //need exit and entry as instance variables to call shortestpath between entry n exit
    private int entry;
    private int exit ;
    private int seed = 0;
    public MazeMap() {
        super();
        // the gap colour
        setBackground(Color.GRAY);
        setLayout(new GridLayout(ROWS, COLS, GAP, GAP));

        // init the attribute in default mode
        this.MazeMapData = new Vertex[ROWS][COLS];
        // helper var for choosing vertex type
        int v_type = 0;
        // determine an entry and exit pos
        //Random random = new Random(0);
        this.entry = ThreadLocalRandom.current().nextInt(1, ROWS-1);
        this.exit = ThreadLocalRandom.current().nextInt(1, ROWS-1);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // determine the v_type first
                if (i == this.entry && j == 0) v_type = 2;                        // entry
                else if (i == this.exit && j == ROWS-1) v_type = 3;                    // exit
                else if (i == 0 || i == ROWS-1 || j == 0 || j == ROWS-1) v_type = 1; // barrier
                else v_type = 0;                                             // clear vertex path

                // each of the cell in the MazeMap
                this.MazeMapData[i][j] = new Vertex(PIXEL_SIZE, i, j, v_type);
                this.MazeMapData[i][j].changeEditState(false);
                add(this.MazeMapData[i][j]);
            }
        }
        // set size of the map
        setPreferredSize(new Dimension(ROWS*(PIXEL_SIZE+GAP),COLS*(PIXEL_SIZE+GAP)));
    }
    //accessor
    public Vertex getEntry(){
        return  MazeMapData[entry][0];
    }
    public Vertex getExit(){
        return MazeMapData[exit][COLS-1];
    }
    public int getROWS(){
        return ROWS;
    }
    public int getCOLS(){
        return COLS;
    }

    /** helper function to refresh map colour */
    public void refreshColour(){
        for (int i = 0; i < ROWS; ++i){
            for (int j = 0; j < COLS; ++j){
                MazeMapData[i][j].colourByType();
            }
        }
    }

    /** to change the map state of edit */
    public void changeState(boolean state){
        for (int i = 0; i < ROWS; ++i){
            for (int j = 0; j < COLS; ++j){
                MazeMapData[i][j].changeEditState(state);
            }
        }
    }

    /** load the map data csv */
    // change the mazedata data lmao
    public boolean load_MazeMap(String filePath){
        if (!filePath.endsWith(".csv")) {
            JOptionPane pane = new JOptionPane("Chosen file is not a .csv file, please load a valid map.",JOptionPane.WARNING_MESSAGE);
                    JDialog dialog = pane.createDialog(null, "warning");
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(5000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
            dialog.setModal(false);
            dialog.setVisible(true);
            //JOptionPane.showMessageDialog(this, "Chosen file is not a .csv file, please load a valid map.");
            return false;
        };
        if (!(new File(filePath).exists())){
            JOptionPane pane = new JOptionPane("Cannot find choosen file, please try again.",JOptionPane.WARNING_MESSAGE);
            JDialog dialog = pane.createDialog(null, "warning");
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }).start();
            dialog.setModal(false);
            dialog.setVisible(true);
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 0; col < COLS; col++) {
                    // Trim whitespace and then parse the integer
                    MazeMapData[row][col].vertex_type = Integer.parseInt(values[col].trim());
                    refreshColour();
                }
                row++;
            }
            // change the entry and exit too
            for (int i = 0; i < ROWS; ++i){
                if (MazeMapData[i][0].vertex_type == 2){
                    entry = i;
                }
                if (MazeMapData[i][ROWS-1].vertex_type == 3){
                    exit = i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // It's better to print the stack trace for debugging
        }
        return true;
    }

    /** save map data */
    public void save_MazeMap(){
        try {
            File file = new File("Assets/map/MazeMap_Custom.csv");
            // write into it according to the format
            PrintWriter pw = new PrintWriter(file);
            pw.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 30; i++){
                for (int j = 0; j < 30; j++){
                    if (j != 0){
                        writer.write(",");
                    }
                    writer.write(Integer.toString(MazeMapData[i][j].getVertex_type()));
                    if (j == 29){
                        writer.newLine();
                    }
                }
            }
            writer.flush();
            writer.close();

        } catch (IOException f) {
            System.out.println("An error occurred.");
            f.printStackTrace();
        }
    }

    public Vertex[][] getMazedata(){
        return MazeMapData;
    }
    //Mazemap for testing
    public MazeMap(int[][] array){
        super();
        // the gap colour
        setBackground(Color.GRAY);
        setLayout(new GridLayout(ROWS, COLS, GAP, GAP));
        MazeMapData = new Vertex[ROWS][COLS];
        for(int i=0;i<ROWS;i++){
            for(int j =0;j<COLS;j++){
                if (array[i][j]==2)  this.entry=i;
                if( array[i][j]==3 ) this.exit=i;
                MazeMapData[i][j] = new Vertex(PIXEL_SIZE,i,j,array[i][j]);
                add(this.MazeMapData[i][j]);
            }
        }
        setPreferredSize(new Dimension(ROWS*(PIXEL_SIZE+GAP),COLS*(PIXEL_SIZE+GAP)));
    }

}
