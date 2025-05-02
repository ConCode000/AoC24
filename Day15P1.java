import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day15P1 {
    static char[][] grid;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("file.txt"));
        grid = parseGrid(lines);

        String directions = lines.get(lines.size() - 1).trim();
        List<int[]> startPointOfRobot = findPositionsOfChar('@');
        int startPointRow = startPointOfRobot.get(0)[0];
        int startPointCol = startPointOfRobot.get(0)[1];

        System.out.println("STARTPOINT " + startPointRow + " " + startPointCol);
        List<int[]> listOfOCords = findPositionsOfChar('O');
        printGrid();
        // System.out.println("Directions: " + directions);

        solve(directions, listOfOCords, startPointRow, startPointCol);
        calcGps();

    }

    public static void printGrid2() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void solve(String directionString, List<int[]> listOfCords, int startRowRobotPos,
            int startColRobotPos) {
        int rowRobot = startRowRobotPos;
        int colRobot = startColRobotPos;

        for (char charDir : directionString.toCharArray()) {
            int[] dir = getDirection(charDir);
            System.out.println("Move " + charDir);

            int newRow = rowRobot + dir[0]; // ein Schritt weiter vom Roboter
            int newCol = colRobot + dir[1];

            // Wand -> keine Bewegung
            if (grid[newRow][newCol] == '#') {
                printGrid2();
                continue;
            }

            // Box bewegen
            if (grid[newRow][newCol] == 'O') {
                // Prüfen, wie viele Boxen verschoben werden müssen
                List<int[]> boxPositions = new ArrayList<>();
                int currentRow = newRow;
                int currentCol = newCol;

                while (isWithinGrid(currentRow, currentCol) && grid[currentRow][currentCol] == 'O') {
                    boxPositions.add(new int[] { currentRow, currentCol });
                    currentRow += dir[0];
                    currentCol += dir[1];
                }

                // Prüfen, ob hinter der letzten Box Platz ist
                if (!isWithinGrid(currentRow, currentCol) || grid[currentRow][currentCol] != '.') {
                    printGrid2();
                    continue; // Bewegung blockiert
                }

                // Boxen verschieben
                for (int i = boxPositions.size() - 1; i >= 0; i--) {
                    int[] pos = boxPositions.get(i);
                    grid[pos[0] + dir[0]][pos[1] + dir[1]] = 'O'; // Box eine Position weiter
                    grid[pos[0]][pos[1]] = '.'; // Alte Position freigeben
                }

                // Roboterposition aktualisieren
                grid[newRow][newCol] = '@';
                grid[rowRobot][colRobot] = '.';
                rowRobot = newRow;
                colRobot = newCol;

                printGrid2();
                continue;
            }

            // Leeres Feld
            if (grid[newRow][newCol] == '.') {
                grid[newRow][newCol] = '@';
                grid[rowRobot][colRobot] = '.';
                rowRobot = newRow;
                colRobot = newCol;
            }

            printGrid2();
        }
    }

    private static boolean isWithinGrid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public static void calcGps() {
        int gridRows = grid.length;
        int gridCols = grid[0].length;
        int gps = 0;
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                if (grid[i][j] == 'O') {
                    gps += 100 * i + j;
                }
            }
        }
        System.out.println("SUM GPS = " + gps);
    }

    public static int[] getDirection(char dir) {
        int[] dirTupel = new int[2];

        switch (dir) {
            case '^': // hoch
                dirTupel[0] = -1;
                dirTupel[1] = 0;
                return dirTupel;
            case 'v': // runter
                dirTupel[0] = 1;
                dirTupel[1] = 0;
                return dirTupel;
            case '<': // links
                dirTupel[0] = 0;
                dirTupel[1] = -1;
                return dirTupel;
            case '>': // rechts
                dirTupel[0] = 0;
                dirTupel[1] = 1;
                return dirTupel;
            default:
                return null;
        }
    }

    public static List<int[]> findPositionsOfChar(char c) {
        int sizeRows = grid.length;
        int sizeCols = grid[0].length;
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < sizeRows; i++) {
            for (int j = 0; j < sizeCols; j++) {
                if (grid[i][j] == c) {
                    positions.add(new int[] { i, j });
                }
            }
        }
        return positions;
    }

    public static char[][] parseGrid(List<String> lines) {
        int gridRows = lines.size() - 1;
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

    public static void printGrid() {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
}