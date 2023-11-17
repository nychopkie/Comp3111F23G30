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
    Vertex(int sizeOfSquare, int x, int y, int vertex_type) {
        this.sizeOfSquare = sizeOfSquare;
        this.x = x;
        this.y = y;
        this.vertex_type = vertex_type;
        setPreferredSize(new Dimension(sizeOfSquare, sizeOfSquare));
        if (vertex_type == 0) {
            setTheColor(CLEAR_VERTEX_COLOUR);
        } else if (vertex_type == 1) {
            setTheColor(BARRIER_COLOUR);
        } else if (vertex_type == 2) {
            setTheColor(ENTRY_VERTEX_COLOUR);
        } else {
            setTheColor(EXIT_VERTEX_COLOUR);
        }
        addMouseListener(this);

    }

    //mutator
    public void set_Shortest_Path() {
        setTheColor(SP_VERTEX_COLOUR);
    }

    //accessor
    public int getVertex_type() {
        return vertex_type;
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
        // if the point is the entry or exit or outermost barrier then no change
        if (this.vertex_type == 2 || this.vertex_type == 3 || this.x * this.y == 0 || this.x == 29 || this.y == 29) {
            return;
        }
        // if the vertex is a PATH >>> change to BARRIER
        if (this.vertex_type == 0) {
            setTheColor(BARRIER_COLOUR);
            this.vertex_type = 1;

        }
        // if the vertex is a BARRIER >>> change to PATH
        else {
            setTheColor(CLEAR_VERTEX_COLOUR);
            this.vertex_type = 0;
        }
    }

    /**
     * modifier function to handle the colour of the vertex
     */
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
