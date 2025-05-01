package Day1To5;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2P2 {
    static List<List<Integer>> allLevelsIntArr;

    public static boolean checkMonotonieSteigend(List<Integer> x) {
        for (int i = 1; i < x.size(); i++) {
            if (x.get(i) <= x.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkMonotonieFallend(List<Integer> x) {
        for (int i = 1; i < x.size(); i++) {
            if (x.get(i) >= x.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSafe(List<Integer> x) {
        boolean safeFlag = checkMonotonieSteigend(x) || checkMonotonieFallend(x);
        for (int i = 1; i < x.size(); i++) {
            int delta = Math.abs(x.get(i) - x.get(i - 1));
            if (delta < 1 || delta > 3) {
                return safeFlag=false;
            }
        }
        return safeFlag;
    }

    public static boolean isSafeWithDampener(List<Integer> x) {
        if (isSafe(x)) {
            return true;
        }
        for (int i = 0; i < x.size(); i++) {
            List<Integer> workList = new ArrayList<>(x);
            workList.remove(i);
            if (isSafe(workList)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException{
        //get and parse input 
        allLevelsIntArr = Files.readAllLines(Path.of("file.txt")).stream()
        .map(str -> Arrays.stream(str.split(" "))
                            .map(Integer::parseInt).toList())
        .toList();

        int safeCounter = 0;
        for (List<Integer> x : allLevelsIntArr) {
            if (isSafeWithDampener(x)) {
                safeCounter++;
            }
        }
        System.out.println("Safe reports count: " + safeCounter);
    }
}