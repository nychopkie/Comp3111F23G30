package MazeMap;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class MazeMapTest {
    //private MazeMap MazeMap;
    private final int SIZE = 30;
    MazeMap map;


    @BeforeEach
    void setUp(){
        map = new MazeMap();
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
        
    }

    @Test
    void changeState() {
    }

    @Test
    void load_MazeMap() {
    }

    @Test
    void save_MazeMap() {
    }

    @Test
    void getMazedata() {
    }
}