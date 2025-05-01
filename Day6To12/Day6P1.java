package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day6P1 {
    private static final int SIZE = 130; 
    private static int[][] obstacleMap = new int[SIZE][SIZE];
    private static int[][] visited = new int[SIZE][SIZE];
    private static int[] directions = {-1, 0, 1, 0, -1}; // Hoch, Rechts, Runter, Links
    private static int direction = 0; // als erstes hoch lufen (0)

    public static void main(String[] args) throws IOException {
        // Karte einlesen
        List<String> inputStringArray = Files.readAllLines(Path.of("file.txt"));
        // Startposition finden
        int[] startPos = findStartPos(inputStringArray);
        // Hinderniskarte erstellen
        createObstacleMap(inputStringArray);
        // Wächterweg simulieren
        simulateGuardPath(startPos[1], startPos[0]); // row, col
        // Besuchte Punkte zählen
        int visitedPoints = countVisitedPoints();
        System.out.println("Besuchte Punkte: " + visitedPoints);

        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                System.out.print(visited[i][j]);
            }
            System.out.println();
        }
    }

    public static int[] findStartPos(List<String> inputStringArray) {
        int[] xyPos = new int[2];
        for (int i = 0; i < inputStringArray.size(); i++) {
            int index = inputStringArray.get(i).indexOf('^');
            if (index != -1) {
                xyPos[0] = index; // x-Koordinate (col)
                xyPos[1] = i;     // y-Koordinate (row)
                return xyPos;
            }
        }
        throw new IllegalArgumentException("Keine Startposition (^) gefunden!");
    }

    public static void createObstacleMap(List<String> inputStringArray) {
        for (int row = 0; row < SIZE; row++) {
            String line = inputStringArray.get(row);
            for (int col = 0; col < SIZE; col++) {
                if (line.charAt(col) == '#') {
                    obstacleMap[row][col] = 1;
                }
            }
        }
    }

    public static boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public static void simulateGuardPath(int startRow, int startCol) {
        int row = startRow;
        int col = startCol;
        visited[row][col] = 1; // Startposition markieren
    
        while (true) {
            // Nächste Position basierend auf aktueller Richtung
            int nextRow = row + directions[direction];
            int nextCol = col + directions[direction + 1];
            // Prüfen, ob die nächste Position außerhalb der Karte liegt
            if (!isWithinBounds(nextRow, nextCol)) break;
            
            if (obstacleMap[nextRow][nextCol] == 0) {
                // Kein Hindernis, weitergehen
                row = nextRow;
                col = nextCol;
                visited[row][col]++;
            } else {
                // Hindernis, Richtung ändern
                direction = (direction + 1) % 4;
            }
        }
    }
    
    public static int countVisitedPoints() {
        int count = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (visited[row][col] > 0) {
                    count++;
                }
            }
        }
        return count;
    }
}