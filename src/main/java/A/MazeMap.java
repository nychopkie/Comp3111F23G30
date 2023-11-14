package A;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;


/**
* ============ MazeMap Class ============
* @attributes:
 * 1. int [30][30] MazeMapData: the map of the maze in an int 2D array<br>
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
 * &nbsp;&nbsp;&nbsp;&nbsp;- the function to load a pre-exisiting
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;map to the screen
 *
 * */
public class MazeMap extends JPanel{
    // variables for the class
    int[][] MazeMapData;
    private static final int ROWS = 30, COLS = 30,  PIXEL_SIZE = 30, GAP = 1;


    // default constructor of the map
    public MazeMap(){
        super();
        this.MazeMapData = new int[30][30];
        for (int i = 0; i < 30; i++){
            for (int j = 0; j < 30; j++){
                this.MazeMapData[i][j] = 0;
            }
        }
        setBackground(Color.pink);
        setLayout(new GridLayout(ROWS, COLS, GAP, GAP));

        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                Vertex newVertex = new Vertex(PIXEL_SIZE,i,j,MazeMapData[i][j]);
                newVertex.setTheColor(MazeMapData[i][j] == 1 ? Color.DARK_GRAY : Color.WHITE);
                add(newVertex);
            }
        }

        setPreferredSize(new Dimension(ROWS*(PIXEL_SIZE+GAP),COLS*(PIXEL_SIZE+GAP)));
    }
    // load the map data csv
    //void load_MazeMap(//the csv )

    // save the edited map to a csv
    // void save_MazeMap()

    // edit the maze map
    // void edit_MazeMap

    // need to figure out how to only edit if choose to edit
    // TESTING FOR NOW
    public static void main(String[] args0) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MazeMap());
        frame.pack();
        frame.setVisible(true);
    }
}

class Vertex extends JPanel implements MouseListener {

    private final int sizeOfSquare;
    private static final Color BARRIER_COLOUR = Color.DARK_GRAY;
    private static final Color CLEAR_VERTEX_COLOUR = Color.WHITE;

    int x;
    int y;

    /**
     * vertex-type:
     * 0: clear vertex path
     * 1: barrier
     * 2: entry
     * 3: exit
     */
    int vertex_type;

    Vertex (int sizeOfSquare,int x,int y, int vertex_type){
        this.sizeOfSquare = sizeOfSquare;
        this.x = x;
        this.y = y;
        this.vertex_type = vertex_type;
        setPreferredSize(new Dimension(sizeOfSquare , sizeOfSquare));
        setTheColor(CLEAR_VERTEX_COLOUR);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.vertex_type == 0){
            setTheColor(BARRIER_COLOUR);
            this.vertex_type = 1;

        }
        else{
            setTheColor(CLEAR_VERTEX_COLOUR);
            this.vertex_type = 0;
        }
    }

    void setTheColor(Color theColor) {
        setBackground(theColor);
    }

    int getSizeOfSquare(){
        return sizeOfSquare;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
