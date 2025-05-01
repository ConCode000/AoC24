package Day1To5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2P1 {

    static ArrayList<String> allLevelsString = new ArrayList<>();
    static ArrayList<Integer[]> allLevelsIntArr = new ArrayList<>();

    public static void getData() {
        try {
            File myObj = new File("file.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                allLevelsString.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Integer[] parseLevelToInt(String[] eachNumInLevel) {
        int levelLen = eachNumInLevel.length;
        Integer[] levelInInts = new Integer[levelLen];

        for (int i = 0; i < levelLen; i++) {
            levelInInts[i] = Integer.parseInt(eachNumInLevel[i]);
        }

        return levelInInts;
    }

    public static void formatInput() {
        for (String level : allLevelsString) {
            String[] eachNumInLevel = level.split(" ");
            Integer[] levelInInts = parseLevelToInt(eachNumInLevel);
            allLevelsIntArr.add(levelInInts);
        }
    }

    public static boolean checkMonotonieSteigend(Integer[] x) {
        for (int i = 1; i < x.length; i++) {
            if (x[i] <= x[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkMonotonieFallend(Integer[] x) {
        for (int i = 1; i < x.length; i++) {
            if (x[i] >= x[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkDifferences(Integer[] x) {
        for (int i = 1; i < x.length; i++) {
            int delta = Math.abs(x[i] - x[i - 1]);
            if (delta < 1 || delta > 3) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSafe(Integer[] x) {
        boolean isMonotonic = checkMonotonieSteigend(x) || checkMonotonieFallend(x);
        boolean validDifferences = checkDifferences(x);
        return isMonotonic && validDifferences;
    }

    public static void main(String[] args) {
        getData();
        formatInput();

        int safeCounter = 0;

        for (Integer[] x : allLevelsIntArr) {
            if (isSafe(x)) {
                safeCounter++;
            }
        }
        System.out.println("Number of safe reports: " + safeCounter);
    }
}