import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14P2 {

    static final int SIZErows = 103;
    static final int SIZEcols = 101;
    static int[][] fild = new int[SIZErows][SIZEcols];

    static class Robot {
        int pxCOL, pyROW;  // aktuelle Position
        int vxCOL, vyROW;  // Geschwindigkeit
    
        Robot(int pxCOL, int pyROW, int vxCOL, int vyROW) {
            this.pxCOL = pxCOL;
            this.pyROW = pyROW;
            this.vxCOL = vxCOL;
            this.vyROW = vyROW;
        }
    
        void updatePosition() {
            pxCOL = (pxCOL + vxCOL) % SIZEcols;
            pyROW = (pyROW + vyROW) % SIZErows;
            if (pxCOL < 0) pxCOL += SIZEcols;
            if (pyROW < 0) pyROW += SIZErows;
        }
    }

    static List<Robot> robotsList = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        List<String> inputStringList = Files.readAllLines(Path.of("file.txt"));
        parseInput(inputStringList);

        List<char[][]> allFields = new ArrayList<>();

        for (int t = 0; t < 7450; t++) {
            for (int[] row : fild) {
                Arrays.fill(row, 0);
            }
            for (Robot r : robotsList) {
                r.updatePosition(); 
            }

            for (Robot r : robotsList) {
                fild[r.pyROW][r.pxCOL]++;
            }
            allFields.add(convertFildToCharArray());
        }
        printFieldsInRows(allFields, 8);
    }

    public static void parseInput(List<String> inputStringList) {
        String regex = "-?\\d+";
        Pattern pattern = Pattern.compile(regex);

        for (String line : inputStringList) {
            Matcher matcher = pattern.matcher(line);
            List<Integer> nums = new ArrayList<>();
            while (matcher.find()) {
                nums.add(Integer.parseInt(matcher.group()));
            }
            if (nums.size() == 4) {
                robotsList.add(new Robot(nums.get(0), nums.get(1), nums.get(2), nums.get(3)));
            }
        }
    }
    public static char[][] convertFildToCharArray() {
        char[][] result = new char[SIZErows][SIZEcols];
        for (int y = 0; y < SIZErows; y++) {
            for (int x = 0; x < SIZEcols; x++) {
                if (fild[y][x] > 0) {
                    result[y][x] = '#';
                } else {
                    result[y][x] = '.';
                }
            }
        }
        return result;
    }

    // Druckt eine Liste von Feldern aus, jeweils 8 nebeneinander
    public static void printFieldsInRows(List<char[][]> fields, int fieldsPerLine) {
        int fieldCount = fields.size();
        if (fieldCount == 0) return;

        int rows = fields.get(0).length;    
        int cols = fields.get(0)[0].length; 

        for (int start = 0; start < fieldCount; start += fieldsPerLine) {
            int end = Math.min(start + fieldsPerLine, fieldCount);
            for (int i = start; i < end; i++) {
                String indexStr = Integer.toString(i);
                int padding = (cols - indexStr.length()) / 2;
                for (int p = 0; p < padding; p++) System.out.print(" ");
                System.out.print(indexStr);
                int rest = cols - padding - indexStr.length();
                for (int p = 0; p < rest; p++) System.out.print(" ");
                System.out.print("  ");
            }
            System.out.println();
            for (int row = 0; row < rows; row++) {
                for (int i = start; i < end; i++) {
                    char[][] f = fields.get(i);
                    for (int c = 0; c < cols; c++) {
                        System.out.print(f[row][c]);
                    }
                    System.out.print("  "); // zwei Leerzeichen zwischen den Feldern
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }
}
