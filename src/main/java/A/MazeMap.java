package A;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
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
    private static final int ROWS = 30, COLS = 30,  PIXEL_SIZE = 30, GAP = 1;


    /** default constructor of the map */
    public MazeMap(){
        super();
        // the gap colour
        setBackground(Color.pink);
        setLayout(new GridLayout(ROWS, COLS, GAP, GAP));

        // init the attribute in default mode
        this.MazeMapData = new Vertex[ROWS][COLS];
        // helper var for choosing vertex type
        int v_type = 0;
        // determine an entry and exit pos
        int entry = ThreadLocalRandom.current().nextInt(1, 29);
        int exit = ThreadLocalRandom.current().nextInt(1, 29);
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                // determine the v_type first
                if (i == entry && j == 0) v_type = 2;                        // entry
                else if (i == exit && j == 29) v_type = 3;                    // exit
                else if (i == 0 || i == 29 || j == 0 || j == 29) v_type = 1; // barrier
                else v_type = 0;                                             // clear vertex path

                // each of the cell in the MazeMap
                this.MazeMapData[i][j] = new Vertex(PIXEL_SIZE,i,j,v_type);
                add(this.MazeMapData[i][j]);
            }
        }

        // set size of the map
        setPreferredSize(new Dimension(ROWS*(PIXEL_SIZE+GAP),COLS*(PIXEL_SIZE+GAP)));
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

    // TESTING FOR NOW
    public static void main(String[] args0) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MazeMap());
        frame.pack();
        frame.setVisible(true);
    }
}


/**
 * =========== Vertex Class ===========
 * @attributes:
 * 1. x,y: the coordinates<br>
 * 2. vertex_type: the type of vertex<br>
 * 3. colours of the square
 *
 * @operations:
 * 1. mouseclick >> change colour if clicked
 *
 * */
class Vertex extends JPanel implements MouseListener {

    /** the coordinate of the vertex */
    int x, y;

    /**
     * vertex-type:<br>
     * 0 clear vertex path<br>
     * 1: barrier<br>
     * 2: entry<br>
     * 3: exit
     */
    int vertex_type;

    /** the pixel size of each vertex */
    private final int sizeOfSquare;

    /** colour of vertex-type 0 PATH*/
    private static final Color CLEAR_VERTEX_COLOUR = Color.WHITE;
    /** colour of vertex-type 1 BARRIER*/
    private static final Color BARRIER_COLOUR = Color.DARK_GRAY;
    /** colour of vertex-type 2 ENTRY */
    private static final Color ENTRY_VERTEX_COLOUR = Color.CYAN;
    /** colour of vertex-type 3 EXIT */
    private static final Color EXIT_VERTEX_COLOUR = Color.YELLOW;

    /** the constructor for Vertex */
    Vertex (int sizeOfSquare,int x,int y, int vertex_type){
        this.sizeOfSquare = sizeOfSquare;
        this.x = x;
        this.y = y;
        this.vertex_type = vertex_type;
        setPreferredSize(new Dimension(sizeOfSquare , sizeOfSquare));
        if (vertex_type == 0){
            setTheColor(CLEAR_VERTEX_COLOUR);
        }
        else if (vertex_type == 1){
            setTheColor(BARRIER_COLOUR);
        }
        else if (vertex_type == 2){
            setTheColor(ENTRY_VERTEX_COLOUR);
        }
        else{
            setTheColor(EXIT_VERTEX_COLOUR);
        }
        addMouseListener(this);
    }

    /** action if click on vertex change colour based on type*/
    @Override
    public void mouseClicked(MouseEvent e) {
        // if the point is the entry or exit or outermost barrier then no change
        if (this.vertex_type == 2 || this.vertex_type == 3 || this.x*this.y == 0 || this.x == 29 || this.y == 29){
            return;
        }
        // if the vertex is a PATH >>> change to BARRIER
        if (this.vertex_type == 0){
            setTheColor(BARRIER_COLOUR);
            this.vertex_type = 1;

        }
        // if the vertex is a BARRIER >>> change to PATH
        else{
            setTheColor(CLEAR_VERTEX_COLOUR);
            this.vertex_type = 0;
        }
    }

    /** modifier function to handle the colour of the vertex */
    void setTheColor(Color theColor) {
        setBackground(theColor);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // pass
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // pass
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // pass
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // pass
    }
}
