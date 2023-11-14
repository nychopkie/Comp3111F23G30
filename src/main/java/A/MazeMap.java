package A;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;



public class MazeMap extends JPanel{
    // variables for the class
    int[][] MazeMapData;
    private static final int ROWS = 30, COLS = 30,  PIXEL_SIZE = 30, GAP = 1; //change gap to 1 for example if you want to set a gap between squares


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
    // create the maze map, jframe handling
    // returns nothing, but need to save the map
    void create_mazeMap(){
        // first initialize a plain grid for the map
        for (int i = 0; i < 30; i++){
            for (int j = 0; j < 30; j++){
                MazeMapData[i][j] = 0;
            }
        }
    }


    // load the map data csv
    //public static void load_mazeMap(//the csv )


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
