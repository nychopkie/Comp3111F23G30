package A;
import java.util.Arrays;

public class DummyCreation {
    // create a text version in terminal, later make it gui

    public static void main(String arg[]){
        System.out.println("Hello, JUnit 4!");
    }

    public static int[][] createMap(int x, int y) {
        int[][] map = new int[x][y];
        for (int [] i: map){
            for (int j: i){
                i[j] = 0;
            }
        }
        return map;
    }

    public static int[][] changeValue(int[][] map, int x, int y){
        if (map[x][y] == 0) map[x][y] = 1;
        else map[x][y] = 0;

        return map;
    }

}
