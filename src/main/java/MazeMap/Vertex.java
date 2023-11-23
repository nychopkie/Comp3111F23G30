package MazeMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * =========== Vertex Class ===========
 *
 * @attributes: 1. x,y: the coordinates<br>
 * 2. vertex_type: the type of vertex<br>
 * 3. colours of the square
 * @operations: 1. mouseclick >> change colour if clicked
 */
public class Vertex extends JPanel implements MouseListener {

    /**
     * the coordinate of the vertex
     */
    int x, y;

    /**
     * vertex-type:<br>
     * 0 clear vertex path<br>
     * 1: barrier<br>
     * 2: entry<br>
     * 3: exit
     */
    int vertex_type;

    /** checks if the vertex can change value */
    private boolean canEdit;

    /**
     * the pixel size of each vertex
     */
    private final int sizeOfSquare;
    /**
     * colour of vertex-type 0 PATH
     */
    private static final Color CLEAR_VERTEX_COLOUR = Color.WHITE;
    /**
     * colour of vertex-type 1 BARRIER
     */
    private static final Color BARRIER_COLOUR = Color.DARK_GRAY;
    /**
     * colour of vertex-type 2 ENTRY
     */
    private static final Color ENTRY_VERTEX_COLOUR = Color.CYAN;
    /**
     * colour of vertex-type 3 EXIT
     */
    private static final Color EXIT_VERTEX_COLOUR = Color.RED;

    /**
     * colour of shortest path
     */
    private static final Color SP_VERTEX_COLOUR = Color.YELLOW;

    /**
     * the constructor for Vertex
     */
    public Vertex(int sizeOfSquare, int x, int y, int vertex_type) {
        this.sizeOfSquare = sizeOfSquare;
        this.x = x;
        this.y = y;
        this.vertex_type = vertex_type;
        setPreferredSize(new Dimension(sizeOfSquare, sizeOfSquare));
        colourByType();
        addMouseListener(this);
    }

    public void colourByType(){
        if (vertex_type == 0) {
            setBackground(CLEAR_VERTEX_COLOUR);
        } else if (vertex_type == 1) {
            setBackground(BARRIER_COLOUR);
        } else if (vertex_type == 2) {
            setBackground(ENTRY_VERTEX_COLOUR);
        } else {
            setBackground(EXIT_VERTEX_COLOUR);
        };
    }

    /** mutator to set the state on whether if the map is changable*/
    public void changeEditState(boolean flag){
        canEdit = flag;
    }

    //mutator
    public void set_Shortest_Path() {
        setBackground(SP_VERTEX_COLOUR);
    }

    //accessor
    public int getVertex_type() {
        return vertex_type;
    }

    // accessor of the edit status of the vertex
    public boolean getEditStatus() {
        return canEdit;
    }

    public int gety() {
        return this.y;
    }

    public int getx() {
        return this.x;
    }

    /**
     * action if click on vertex change colour based on type
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        handleMouseClick();
    }

    public void handleMouseClick(){
        if (canEdit){
            // if the point is the entry or exit or outermost barrier then no change
            if (this.vertex_type == 2 || this.vertex_type == 3 || this.x * this.y == 0 || this.x == 29 || this.y == 29) {
                return;
            }
            // if the vertex is a PATH >>> change to BARRIER
            if (this.vertex_type == 0) {
                setBackground(BARRIER_COLOUR);
                this.vertex_type = 1;
            }
            // if the vertex is a BARRIER >>> change to PATH
            else {
                setBackground(CLEAR_VERTEX_COLOUR);
                this.vertex_type = 0;
            }
        }
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
