import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day16P1 {
    static char[][] grid;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("file.txt"));
        grid = parseGrid(lines);

        List<int[]> startPositions = findPositionsOfChar('S');
        List<int[]> endPositions = findPositionsOfChar('E');

        int startRow = startPositions.get(0)[0];
        int startCol = startPositions.get(0)[1];
        int endRow = endPositions.get(0)[0];
        int endCol = endPositions.get(0)[1];

        int result = dijkstraWithRotation(startRow, startCol, endRow, endCol);
        System.out.println("Minimale Distanz: " + result);
    }

    public static int dijkstraWithRotation(int startRow, int startCol, int endRow, int endCol) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Distanzen initialisieren
        int[][][] distances = new int[rows][cols][4];
        for (int[][] layer : distances) {
            for (int[] row : layer) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
        }

        // Startzustand initialisieren (nach Osten schauen)
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(startRow, startCol, 1, 0)); // Start mit Richtung Osten (1)
        distances[startRow][startCol][1] = 0;

        // Bewegungsrichtungen  0 = Norden, 1 = Osten, 2 = Süden, 3 = Westen
        int[] rowMoves = {-1, 0, 1, 0};
        int[] colMoves = {0, 1, 0, -1};

        while (!pq.isEmpty()) {
            State current = pq.poll();

            // Ziel erreicht
            if (current.row == endRow && current.col == endCol) {
                return current.cost;
            }

            if (current.cost > distances[current.row][current.col][current.direction]) {
                continue;
            }

            // Vorwärtsbewegung
            int newRow = current.row + rowMoves[current.direction];
            int newCol = current.col + colMoves[current.direction];
            if (isValid(newRow, newCol)) {
                int newCost = current.cost + 1; // Vorwärtsbewegung kostet 1
                if (newCost < distances[newRow][newCol][current.direction]) {
                    distances[newRow][newCol][current.direction] = newCost;
                    pq.add(new State(newRow, newCol, current.direction, newCost));
                }
            }

            // Drehungen
            for (int turn = -1; turn <= 1; turn += 2) { // -1 = Links, +1 = Rechts
                int newDirection = (current.direction + turn + 4) % 4; // Neue Richtung (zyklisch)
                int newCost = current.cost + 1000; // Drehung kostet 1000
                if (newCost < distances[current.row][current.col][newDirection]) {
                    distances[current.row][current.col][newDirection] = newCost;
                    pq.add(new State(current.row, current.col, newDirection, newCost));
                }
            }
        }

        return -1; 
    }

    public static boolean isValid(int row, int col) {
        return row >= 0 && col >= 0 && row < grid.length && col < grid[0].length && grid[row][col] != '#';
    }

    static class State implements Comparable<State> {
        int row, col, direction, cost;

        State(int row, int col, int direction, int cost) {
            this.row = row;
            this.col = col;
            this.direction = direction;
            this.cost = cost;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static List<int[]> findPositionsOfChar(char c) {
        int sizeRows = grid.length;
        int sizeCols = grid[0].length;
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < sizeRows; i++) {
            for (int j = 0; j < sizeCols; j++) {
                if (grid[i][j] == c) {
                    positions.add(new int[]{i, j});
                }
            }
        }
        return positions;
    }

    public static char[][] parseGrid(List<String> lines) {
        int gridRows = lines.size();
        int gridCols = lines.get(0).length();
        char[][] grid = new char[gridRows][gridCols];
        for (int i = 0; i < gridRows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j);
            }
        }
        return grid;
    }
}
