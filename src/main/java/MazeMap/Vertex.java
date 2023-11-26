package MazeMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** class that controls each grid cell of the map<br><hr>
 *ATTRIBUTES<br>
 * 1. x: the x-coordinate of the vertex<br>
 * 2. y: the y-coordinate of the vertex<br>
 * 3. draw: control to invoke drag drawing<br>
 * 4. vertex_type: the type of vertex it is<br>
 * 5. static canEdit: control whether the vertex can be edited<br>
 * 6. sizeOfSquare: the size of each square<br>
 * 7. CLEAR_VERTEX_COLOUR: white<br><hr>
 * OPERATIONS<br>
 * 1. Vertex(int sizeOfSquare, int x, int y, int vertex_type)<br>
 * 2. getVertex_type()<br>
 * 3. gety()<br>
 * 4. getx()<br>
 * 5. handleMouseClick()<br>
 * 6. handleMousePressed()<br>
 * 7. handleMouseReleased()<br>
 * 8. handleMouseEntered()<br>
 **/
public class Vertex extends JPanel implements MouseListener {

    /**
     * the coordinate of the vertex
     */
    int x, y;

    /**
     * static control for whole map drag drawing
     */
    public static boolean draw = false;

    /**
     * vertex-type:<br>
     * 0 clear vertex path<br>
     * 1: barrier<br>
     * 2: entry<br>
     * 3: exit
     */
    int vertex_type;

    /** checks if the vertex can change value */
    public static boolean canEdit = false;

    /**
     * the pixel size of each vertex
     */
    private final int sizeOfSquare;


    /**
     * the constructor for Vertex
     * @param sizeOfSquare pass in the size of each square
     * @param x the x-coordinate of this Vertex
     * @param y the y-coordinate of this Vertex
     * @param vertex_type the type of this Vertex
     */
    public Vertex(int sizeOfSquare, int x, int y, int vertex_type) {
        this.sizeOfSquare = sizeOfSquare;
        this.x = x;
        this.y = y;
        this.vertex_type = vertex_type;
        setPreferredSize(new Dimension(sizeOfSquare, sizeOfSquare));
        if (vertex_type == 0) {
            setBackground(Color.WHITE);
        } else if (vertex_type == 1) {
            setBackground(Color.DARK_GRAY);
        } else if (vertex_type == 2) {
            setBackground(Color.CYAN);
        } else {
            setBackground(Color.RED);
        };
        addMouseListener(this);
    }

    /**
     * returns the vertex type of the Vertex
     * @return int vertex_type
     */
    public int getVertex_type() {
        return vertex_type;
    }

    /**
     * returns the y-coordinates of the Vertex
     * @return int y
     */
    public int gety() {
        return this.y;
    }

    /**
     * returns the x-coordinates of the Vertex
     * @return int x
     */
    public int getx() {
        return this.x;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // pass
    }

    /**
     * handles the action of mouse click
     * <p>if can edit then can change colour and type of the Vertex as long as it is not the entry or exit</p>
     */
    public void handleMouseClick(){
        if (canEdit){
            // if the point is the entry or exit or outermost barrier then no change
            if (this.vertex_type == 2 || this.vertex_type == 3) {
                return;
            }
            // if the vertex is a PATH >>> change to BARRIER
            if (this.vertex_type == 0) {
                setBackground(Color.DARK_GRAY);
                this.vertex_type = 1;
            }
            // if the vertex is a BARRIER >>> change to PATH
            else {
                setBackground(Color.WHITE);
                this.vertex_type = 0;
            }
        }
    }

    /**
     * action if press mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // for all the vertex passed, change
        handleMouseClick();
        handleMousePressed();
    }

    /**
     * handles the action of mouse pressed
     * <p>if mouse pressed then change the status of draw to true</p>
     */
    public void handleMousePressed(){
        draw = true;
    }

    /**
     * action if released mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        handleMouseReleased();
    }

    /**
     * handles the action of mouse released
     * <p>if mouse released then change the status of draw to false</p>
     */
    public void handleMouseReleased(){
        draw = false;
    }

    /**
     * action if mouse entered Vertex
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // pass
        handleMouseEntered();
    }

    /**
     * handles the action of mouse entered Vertex
     * <p>if mouse entered and is drawing time then draw!</p>
     */
    public void handleMouseEntered(){
        if (draw){
            handleMouseClick();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // pass
    }

}
