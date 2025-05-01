package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6P2 {
    static List<String> inputStringArray = new ArrayList<>();
    private static final int SIZE = 130;
    private static int[][] obstacleMap = new int[SIZE][SIZE];
    private static int[] directions = { -1, 0, 1, 0, -1 };
    private static int direction = 0;
    static Set<String> visitedStates = new HashSet<>();

    public static void main(String[] args) throws IOException {
        inputStringArray = Files.readAllLines(Path.of("file.txt"));
        createObstacleMap();
        int trapPositions = calculateTrapPositions();
        System.out.println("Result: " + trapPositions);
    }

    public static int calculateTrapPositions() {
        int trapCount = 0;
        int[] startPos = findStartPos(); // Startposition holen
        int startRow = startPos[1];
        int startCol = startPos[0];
        // Iterieren über fast alle möglichen Positionen auf der map
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                //auf Startposition darf kein temp hinderniss platzieren 
                if (row == startRow && col == startCol) continue;
                if (obstacleMap[row][col] == 0) {
                    // Temporär ein Hindernis setzen
                    obstacleMap[row][col] = 1;
                    if (isLoopDetected()) trapCount++;
                    obstacleMap[row][col] = 0;  // Hindernis wieder entfernen
                }
            }
        }
        return trapCount;
    }

    public static boolean isLoopDetected() {
        int[] startPos = findStartPos();
        int row = startPos[1];
        int col = startPos[0];
        direction = 0; // Start mit Richtung nach oben
        visitedStates.clear(); // Besuchte Zustände zurücksetzen
        while (true) {
            // Zustand speichern also die Position + Richtung
            String state = row + "," + col + "," + direction;
            if (visitedStates.contains(state)) {
                return true; // loop erkannt
            }
            visitedStates.add(state);

            int nextRow = row + directions[direction];
            int nextCol = col + directions[direction + 1];

            if (!isWithinBounds(nextRow, nextCol)) {
                break; // Wächter verlässt die map
            }
            if (obstacleMap[nextRow][nextCol] == 0) { // wenn freies Feld 
                // Weitergehen
                row = nextRow;
                col = nextCol;
            } else {
                // Richtung ändern
                direction = (direction + 1) % 4;
            }
        }
        return false;
    }

    public static boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public static void createObstacleMap() {
        for (int row = 0; row < SIZE; row++) {
            String line = inputStringArray.get(row);
            for (int col = 0; col < SIZE; col++) {
                if (line.charAt(col) == '#') {
                    obstacleMap[row][col] = 1;
                }
            }
        }
    }

    public static int[] findStartPos() {
        int[] xyPos = new int[2];
        for (int i = 0; i < inputStringArray.size(); i++) {
            int index = inputStringArray.get(i).indexOf('^');
            if (index != -1) {
                xyPos[0] = index; // x-Koordinate (col)
                xyPos[1] = i; // y-Koordinate (row)
                return xyPos;
            }
        }
        throw new IllegalArgumentException("Keine Startposition (^) gefunden!");
    }
}
