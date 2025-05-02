import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14P1 {

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

        for (int t = 0; t < 100; t++) {
            for (int[] row : fild) {
                Arrays.fill(row, 0);
            }
            for (Robot r : robotsList) {
                r.updatePosition(); 
            }
            for (Robot r : robotsList) {
                fild[r.pyROW][r.pxCOL]++;
            }
            printFild();
        }

        int middleX = (SIZEcols - 1) / 2; // = 5
        int middleY = (SIZErows - 1) / 2; // = 3
        
        int topLeft = 0;
        int topRight = 0;
        int bottomLeft = 0;
        int bottomRight = 0;
        
        for (int y = 0; y < SIZErows; y++) {
            for (int x = 0; x < SIZEcols; x++) {
                int count = fild[y][x];
        
                // Wenn kein Roboter an Position
                if (count == 0) continue;
                // Wenn auf Mittellinie
                if (x == middleX || y == middleY) {
                    continue;
                }
                // Quadrant bestimmen
                if (x < middleX && y < middleY) {
                    topLeft += count;
                } else if (x > middleX && y < middleY) {
                    topRight += count;
                } else if (x < middleX && y > middleY) {
                    bottomLeft += count;
                } else if (x > middleX && y > middleY) {
                    bottomRight += count;
                }
            }
        }
        long safetyFactor = topLeft * topRight * bottomLeft * bottomRight;
        System.out.println("Safety Factor = " + safetyFactor);
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

    public static void printFild(){
        for(int[] x : fild){
            for(int o : x){
                System.out.print(o + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}