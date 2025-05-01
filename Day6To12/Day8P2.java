package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day8P2 {
    private static char[][] charGrid;
    private static List<String> inputStrings; 
    private static int sizeRows;
    private static int sizeCols;

    // Map: Zeichen (Frequenz) -> Liste von Positionen [row, col]
    private static Map<Character, List<int[]>> charPositionsMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        inputStrings = Files.readAllLines(Path.of("file.txt"));
        sizeRows = inputStrings.size();
        sizeCols = inputStrings.get(0).length();
        charGrid = new char[sizeRows][sizeCols];

        createCharGrid();
        createCharPositionMap();

        // berechnet die Antinodes für alle Frequenzen
        calculateAntinodes();
    }

    public static void createCharGrid() {
        for (int row = 0; row < inputStrings.size(); row++) {
            String line = inputStrings.get(row);
            for (int col = 0; col < line.length(); col++) {
                char element = line.charAt(col);
                charGrid[row][col] = element;
            }
        }
    }

    public static void createCharPositionMap() {
        for (int row = 0; row < charGrid.length; row++) {
            for (int col = 0; col < charGrid[row].length; col++) {
                char element = charGrid[row][col];
                if (element != '.') {
                    charPositionsMap.computeIfAbsent(element, k -> new ArrayList<>()).add(new int[]{row, col});
                }
            }
        }
    }

    public static int gcd(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }
    
    // alle Antinoden für alle Frequenzen berechnen  
    public static void calculateAntinodes() {
        Set<String> antinodes = new HashSet<>();

        for (Map.Entry<Character, List<int[]>> frequencyEntry : charPositionsMap.entrySet()) {
            List<int[]> antennaPositions = frequencyEntry.getValue();

            if (antennaPositions.size() < 2) continue;

            for (int i = 0; i < antennaPositions.size(); i++) {
                for (int j = i + 1; j < antennaPositions.size(); j++) {
                    int[] A = antennaPositions.get(i);
                    int[] B = antennaPositions.get(j);

                    int rowA = A[0];
                    int colA = A[1];
                    int rowB = B[0];
                    int colB = B[1];

                    int dx = colB - colA;
                    int dy = rowB - rowA;

                    //kleinste ganzzahlige Schrittgröße berechnen,
                    //mit der man von einem Gitterpunkt zum nächsten kommst
                    //ohne Punkte dazwischen zu überspringen.
                    int g = gcd(Math.abs(dx), Math.abs(dy));
                    int stepX = dx / g;
                    int stepY = dy / g;

                    // In positive Richtung laufen
                    int curRow = rowA;
                    int curCol = colA;
                    while (!isOutOfBounds(curRow, curCol)) {
                        antinodes.add(curRow + "," + curCol);
                        curRow += stepY;
                        curCol += stepX;
                    }

                    // In negative Richtung laufen
                    curRow = rowA - stepY;
                    curCol = colA - stepX;
                    while (!isOutOfBounds(curRow, curCol)) {
                        antinodes.add(curRow + "," + curCol);
                        curRow -= stepY;
                        curCol -= stepX;
                    }
                }
            }
        }
        System.out.println("Anzahl der Antinodes: " + antinodes.size());
    }
    public static boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= sizeRows || col < 0 || col >= sizeCols;
    }
}
