import MazeMap.*;
import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeMapTest {
    private final int SIZE = 30;

    @Test
    protected void test_getEntry() {
        MazeMap map = new MazeMap();
        Vertex entry_actual = map.getEntry(); // target function
        Vertex entry_expected = null;
        for (int i = 0; i < SIZE; ++i){
            if (map.getMazedata()[i][0].getVertex_type() == 2){
                entry_expected = map.getMazedata()[i][0];
            }
        }
        assertEquals(entry_expected,entry_actual);
    }

    @Test
    protected void test_getExit() {
        MazeMap map = new MazeMap();
        Vertex exit_actual = map.getExit(); // target function
        Vertex exit_expected = null;
        for (int i = 0; i < SIZE; ++i){
            if (map.getMazedata()[i][SIZE-1].getVertex_type() == 3){
                exit_expected = map.getMazedata()[i][SIZE-1];
            }
        }
        assertEquals(exit_expected,exit_actual);
    }

    @Test
    protected void test_getSIZE() {
        MazeMap map = new MazeMap();
        int size_expected = 30;
        int size_actual = map.getSIZE(); // Target function

        assertEquals(size_expected,size_actual);
    }

    @Test
    protected void test_refreshColour() {
        MazeMap map = new MazeMap();
        // initialize the test variables
        boolean flag_actual = true;

        // init the expected colours
        MazeMap map_expected = new MazeMap();
        map_expected.load_MazeMap("Assets/Test_map/MazeMap_SAMPLE2.csv");

        // init the actual colours with target function call
        map.load_MazeMap("Assets/Test_map/MazeMap_SAMPLE2.csv");
        map.refreshColour(); // target function

        // need to check if the colour for each individual vertex is equal
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                // must be all true to return true, else if one is false then test do not pass
                flag_actual = flag_actual && (map_expected.getMazedata()[i][j].getBackground() == map.getMazedata()[i][j].getBackground());
            }
        }

        assertTrue(flag_actual);
    }

    @Test
    protected void test_load_MazeMap() {
        // load a valid map1 - playable (should be able to load)
        MazeMap map1 = new MazeMap();
        assertTrue(map1.load_MazeMap("Assets/Test_map/MazeMap_SAMPLE2.csv"),"This loads a valid csv file"); // target function: load_MazeMap

        // load a file that does not exist
        MazeMap map2 = new MazeMap();
        assertFalse(map2.load_MazeMap("Assets/Test_map/hehehe.csv"),"This loads a file that does not exist"); // target function: load_MazeMap

        // load a file that is not csv
        MazeMap map3 = new MazeMap();
        assertFalse(map3.load_MazeMap("Assets/Test_map/notCsv.txt"),"This loads a file that is not a csv file"); // target function: load_MazeMap

        // load nothing aka pass null
        MazeMap map4 = new MazeMap();
        assertFalse(map4.load_MazeMap(""), "This loads empty string"); // target function: load_MazeMap
    }

    @Test
    protected void test_save_MazeMap() {
        MazeMap map = new MazeMap();
        MazeMap.savePath = "Assets/Test_map/save_map";
        // revert to the basic state
        File file = new File("Assets/Test_map/save_map.csv");
        if (file.exists()){
            file.delete();
        }

        // load a map in
        map.load_MazeMap("Assets/Test_map/MazeMap_SAMPLE2.csv");
        map.save_MazeMap(); // target function

        // load a map in to save another copy
        map.load_MazeMap("Assets/Test_map/MazeMap_SAMPLE2.csv");
        map.save_MazeMap(); // target function

        MazeMap map_expected1 = new MazeMap();
        map_expected1.load_MazeMap("Assets/Test_map/MazeMap_SAMPLE2.csv");

        MazeMap map_expected2 = new MazeMap();
        map_expected2.load_MazeMap("Assets/Test_map/MazeMap_InvalidExample.csv");

        boolean flag1 = true;
        boolean flag2 = true;
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                flag1 = flag1 && (map.getMazedata()[i][j].getVertex_type() == map_expected1.getMazedata()[i][j].getVertex_type());
                // as long as one does not match it is still false
                flag2 = flag2 && (map.getMazedata()[i][j].getVertex_type() == map_expected2.getMazedata()[i][j].getVertex_type());
            }
        }

        assertTrue(flag1); // test to see if it can save the correct map
        assertFalse(flag2); // test to see if it can save the correct map

        //file.delete();
    }

    @Test
    protected void test_getMazedata() {
        MazeMap map = new MazeMap();
        Vertex[][] map_actual = map.getMazedata(); // target function
        assertEquals(map.getMazedata(),map_actual);
    }

    @Test
    protected void test_constructor(){
        MazeMap map = null;
        map = new MazeMap(); // target function

        assertNotEquals(null,map);
    }
}