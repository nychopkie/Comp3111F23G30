import MazeMap.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

public class VertexTest {

    private static final int PIXEL_SIZE = 25, SIZE = 30;

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

    @Test
    void test_constructor() {
        Vertex vertex_path = null;
        vertex_path = new Vertex(PIXEL_SIZE,1,1, 0); // target function
        assertNotEquals(null,vertex_path);

        Vertex vertex_bound = null;
        vertex_bound = new Vertex(PIXEL_SIZE,0,0, 1); // target function
        assertNotEquals(null,vertex_bound);

        Vertex vertex_entry = null;
        vertex_entry = new Vertex(PIXEL_SIZE,0,1, 2); // target function
        assertNotEquals(null,vertex_entry);

        Vertex vertex_exit = null;
        vertex_exit = new Vertex(PIXEL_SIZE,29,1, 3); // target function
        assertNotEquals(null,vertex_exit);
    }

    @Test
    void test_colourByType() {
        Vertex path = new Vertex(PIXEL_SIZE,0,0,0);
        path.colourByType(); // target function
        assertEquals(CLEAR_VERTEX_COLOUR,path.getBackground());

        Vertex boundary = new Vertex(PIXEL_SIZE,0,0,1);
        boundary.colourByType(); // target function
        assertEquals(BARRIER_COLOUR,boundary.getBackground());

        Vertex entry = new Vertex(PIXEL_SIZE,0,0,2);
        entry.colourByType(); // target function
        assertEquals(ENTRY_VERTEX_COLOUR,entry.getBackground());

        Vertex exit = new Vertex(PIXEL_SIZE,0,0,3);
        exit.colourByType(); // target function
        assertEquals(EXIT_VERTEX_COLOUR,exit.getBackground());
    }

    @Test
    void test_changeEditState() {
        Vertex edit = new Vertex(PIXEL_SIZE,0,0,0);
        edit.changeEditState(true); // target function
        assertTrue(edit.getEditStatus());

        Vertex no_edit = new Vertex(PIXEL_SIZE,0,0,0);
        edit.changeEditState(false); // target function
        assertFalse(no_edit.getEditStatus());
    }

    @Test
    void test_getEditStatus() {
        Vertex edit = new Vertex(PIXEL_SIZE,0,0,0);
        edit.changeEditState(true);
        assertEquals(true,edit.getEditStatus()); // target function

        Vertex no_edit = new Vertex(PIXEL_SIZE,0,0,0);
        edit.changeEditState(false);
        assertEquals(false,no_edit.getEditStatus()); // target function
    }

    @Test
    void test_set_Shortest_Path() {
        Vertex path = new Vertex(PIXEL_SIZE,0,0,0);
        path.set_Shortest_Path(); // target function
        assertEquals(SP_VERTEX_COLOUR,path.getBackground());

        Vertex not_path = new Vertex(PIXEL_SIZE,0,0,1);
        assertNotEquals(SP_VERTEX_COLOUR,not_path.getBackground());
    }

    @Test
    void test_getVertex_type() {
        Vertex path = new Vertex(PIXEL_SIZE,0,0,0);
        assertEquals(0,path.getVertex_type()); // target function

        Vertex boundary = new Vertex(PIXEL_SIZE,0,0,1);
        assertEquals(1,boundary.getVertex_type()); // target function

        Vertex entry = new Vertex(PIXEL_SIZE,0,0,2);
        assertEquals(2,entry.getVertex_type()); // target function

        Vertex exit = new Vertex(PIXEL_SIZE,0,0,3);
        assertEquals(3,exit.getVertex_type()); // target function
    }

    @Test
    void test_gety() {
        Vertex vertex = new Vertex(PIXEL_SIZE,0,0,0);
        assertEquals(0,vertex.gety()); // target function
    }

    @Test
    void test_getx() {
        Vertex vertex = new Vertex(PIXEL_SIZE,0,0,0);
        assertEquals(0,vertex.getx()); // target function
    }

    @Test
    void test_handleMouseClick() {
        Vertex path = new Vertex(PIXEL_SIZE,1,1,0);
        path.changeEditState(true);
        path.handleMouseClick(); // target function
        assertEquals(BARRIER_COLOUR,path.getBackground());

        Vertex boundary = new Vertex(PIXEL_SIZE,1,1,1);
        boundary.changeEditState(true);
        boundary.handleMouseClick(); // target function
        assertEquals(CLEAR_VERTEX_COLOUR,boundary.getBackground());

        Vertex entry = new Vertex(PIXEL_SIZE,0,1,2);
        entry.changeEditState(true);
        entry.handleMouseClick(); // target function
        assertEquals(ENTRY_VERTEX_COLOUR,entry.getBackground());

        Vertex exit = new Vertex(PIXEL_SIZE,29,1,3);
        exit.changeEditState(true);
        exit.handleMouseClick(); // target function
        assertEquals(EXIT_VERTEX_COLOUR,exit.getBackground());
    }

    @Test
    void test_handleMousePressed(){
        Vertex vertex = new Vertex(PIXEL_SIZE,1,1,0);
        vertex.handleMousePressed(); // target function
        assertTrue(Vertex.draw);
    }

    @Test
    void test_handleMouseReleased(){
        Vertex vertex = new Vertex(PIXEL_SIZE,1,1,0);
        vertex.handleMouseReleased(); // target function
        assertFalse(Vertex.draw);
    }

    @Test
    void test_handleMouseEntered() {
        Vertex allow_draw = new Vertex(PIXEL_SIZE,1,1,0);
        allow_draw.changeEditState(true);
        Vertex.draw = true;
        allow_draw.handleMouseEntered(); // target function
        assertEquals(BARRIER_COLOUR,allow_draw.getBackground());

        Vertex not_allow_draw = new Vertex(PIXEL_SIZE,1,1,1);
        not_allow_draw.changeEditState(true);
        Vertex.draw = false;
        not_allow_draw.handleMouseEntered(); // target function
        assertNotEquals(CLEAR_VERTEX_COLOUR,not_allow_draw.getBackground());
    }

}