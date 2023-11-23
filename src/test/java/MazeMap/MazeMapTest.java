package MazeMap;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class MazeMapTest {
    //private MazeMap MazeMap;
    private final int SIZE = 30;
    private final String valid_path = "Assets/Test_map/MazeMap_SAMPLE2.csv";
    private final String valid_unplayable_path = "Assets/Test_map/MazeMap_InvalidExample.csv";
    private final String non_existing_path = "Assets/Test_map/hehehe.csv";
    private final String not_csv_path = "Assets/Test_map/notCsv.txt";
    private final String save_path = "Assets/Test_map/save_map.csv";
    MazeMap map;


    @BeforeEach
    void setUp(){
        map = new MazeMap();
        map.setSavePath(save_path);
    }

    @Test
    void getEntry() {
        Vertex entry_actual = map.getEntry(); // target function
        Vertex entry_expected = null;
        for (int i = 0; i < SIZE; ++i){
            if (map.MazeMapData[i][0].vertex_type == 2){
                entry_expected = map.MazeMapData[i][0];
            }
        }
        assertEquals(entry_expected,entry_actual);
    }

    @Test
    void getExit() {
        Vertex exit_actual = map.getExit(); // target function
        Vertex exit_expected = null;
        for (int i = 0; i < SIZE; ++i){
            if (map.MazeMapData[i][SIZE-1].vertex_type == 3){
                exit_expected = map.MazeMapData[i][SIZE-1];
            }
        }
        assertEquals(exit_expected,exit_actual);
    }

    @Test
    void getROWS() {
        int row_expected = 30;
        int row_actual = map.getROWS(); // Target function

        assertEquals(row_expected,row_actual);
    }

    @Test
    void getCOLS() {
        int col_expected = 30;
        int col_actual = map.getCOLS(); // Target function

        assertEquals(col_expected,col_actual);
    }

    @Test
    void refreshColour() {
        // initialize the test variables
        boolean flag_actual = true;

        // init the expected colours
        MazeMap map_expected = new MazeMap();
        map_expected.load_MazeMap(valid_path);

        // init the actual colours with target function call
        map.load_MazeMap(valid_path);
        map.refreshColour(); // target function

        // need to check if the colour for each individual vertex is equal
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                // must be all true to return true, else if one is false then test do not pass
                flag_actual = flag_actual && (map_expected.MazeMapData[i][j].getBackground() == map.MazeMapData[i][j].getBackground());
            }
        }

        assertTrue(flag_actual);
    }

    @Test
    void changeState() {
        // case 1: change to editable
        MazeMap edit_change_expected = new MazeMap(); // this is a test map that is editable to be compared with the actual function calls
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                edit_change_expected.MazeMapData[i][j].changeEditState(true);
            }
        }
        // init map to be editable
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                map.MazeMapData[i][j].changeEditState(true);
            }
        }

        // 1a: edit --> edit
        map.changeState(true); // target function
        boolean flag1a = true;
        // check each vertex's status
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                // must be all true to return true, else if one is false then test do not pass
                flag1a = flag1a && (edit_change_expected.MazeMapData[i][j].getEditStatus() == map.MazeMapData[i][j].getEditStatus());
            }
        }
        assertTrue(flag1a);

        // 1b: edit --> non-edit
        map.changeState(false); // target function
        boolean flag1b = false;
        // check each vertex's status
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                // must be all false to return false, else if one is true then test do not pass
                flag1b = flag1b || (edit_change_expected.MazeMapData[i][j].getEditStatus() == map.MazeMapData[i][j].getEditStatus());
            }
        }
        assertFalse(flag1b);

        // case 2: change to non-editable
        MazeMap nonedit_change_expected = new MazeMap(); // this is a test map that is non-editable to be compared with the actual function calls
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                nonedit_change_expected.MazeMapData[i][j].changeEditState(false);
            }
        }
        // init map to be non-editable
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                map.MazeMapData[i][j].changeEditState(false);
            }
        }

        // 2a: non-edit --> non-edit
        map.changeState(false); // target function
        boolean flag2a = true;
        // check each vertex's status
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                // must be all true to return true, else if one is false then test do not pass
                flag2a = flag2a && (nonedit_change_expected.MazeMapData[i][j].getEditStatus() == map.MazeMapData[i][j].getEditStatus());
            }
        }
        assertTrue(flag2a);

        // 2b: non-edit --> edit
        map.changeState(true); // target function
        boolean flag2b = false;
        // check each vertex's status
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                // must be all false to return false, else if one is true then test do not pass
                flag2b = flag2b || (nonedit_change_expected.MazeMapData[i][j].getEditStatus() == map.MazeMapData[i][j].getEditStatus());
            }
        }
        assertFalse(flag2b);

    }

    @Test
    void load_MazeMap() {
        // load a valid map1 - playable (should be able to load)
        assertTrue("This loads a valid csv file",map.load_MazeMap(valid_path)); // target function: load_MazeMap

        // load a file that does not exist
        assertFalse(map.load_MazeMap(non_existing_path),"This loads a file that does not exist"); // target function: load_MazeMap

        // load a file that is not csv
        assertFalse(map.load_MazeMap(not_csv_path),"This loads a file that is not a csv file"); // target function: load_MazeMap

        // load nothing aka pass null
        assertFalse(map.load_MazeMap(""), "This loads empty string"); // target function: load_MazeMap
    }

    @Test
    void save_MazeMap() {
        // revert to the basic state
        File file = new File(save_path);
        if (file.exists()){
            file.delete();
        }

        // load a map in
        map.load_MazeMap(valid_path);
        map.save_MazeMap(); // target function

        MazeMap map_expected1 = new MazeMap();
        map_expected1.load_MazeMap(valid_path);

        MazeMap map_expected2 = new MazeMap();
        map_expected2.load_MazeMap(valid_unplayable_path);

        boolean flag1 = true;
        boolean flag2 = true;
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                flag1 = flag1 && (map.MazeMapData[i][j].getVertex_type() == map_expected1.MazeMapData[i][j].getVertex_type());
                // as long as one does not match it is still false
                flag2 = flag2 && (map.MazeMapData[i][j].getVertex_type() == map_expected2.MazeMapData[i][j].getVertex_type());
            }
        }

        assertTrue(flag1); // test to see if it can save the correct map
        assertFalse(flag2); // test to see if it can save the correct map

        file.delete();
    }

    @Test
    void getMazedata() {
        Vertex[][] map_actual = map.getMazedata(); // target function
        assertEquals(map.MazeMapData,map_actual);
    }
}