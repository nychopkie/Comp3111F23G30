package A;
import java.util.Arrays;

public class Library {
    public static void main(String arg[]) {
        System.out.println("Hello, JUnit 4!");
    }



    public static int[] mySort(int[] inputArray) {
        int[] sorted = inputArray;
        Arrays.sort(sorted);
        return sorted;
    }
    public static boolean detectOdd(int inputNumber) {
        if (inputNumber%2 == 1) {
            return true;
        }else {
            return false;
        }
    }
}
