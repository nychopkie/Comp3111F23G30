package MazeMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
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

    // TODO: loadmapdata
    // load the map data csv
    // change the mazedatya data lmao
    //void load_MazeMap(//the csv )

    // TODO: savemapdata
    // save the edited map to a csv
    // loop thru mazemapdata and like write type only
    // void save_MazeMap()

    // TODO: editmapdata
    // edit the maze map
    // void edit_MazeMap

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
