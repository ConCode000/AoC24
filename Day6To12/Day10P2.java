package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day10P2 {
    public static List<List<int[]>> validPaths = new ArrayList<>();
    public static int bfsMultipleTargets(int[][] grid, int[] startPunkt) {
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<List<int[]>> queue = new LinkedList<>();
        List<int[]> initialPath = new ArrayList<>();
        initialPath.add(startPunkt);
        queue.add(initialPath);

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        int reachableGoals = 0;

        while (!queue.isEmpty()) {
            List<int[]> currentPath = queue.poll();
            int[] current = currentPath.get(currentPath.size() - 1);
            int x = current[0];
            int y = current[1];

            // Wenn Ziel von höhe 9 erreicht dann speichert es den Pfad 
            if (grid[x][y] == 9) {
                reachableGoals++;
                validPaths.add(new ArrayList<>(currentPath)); 
                continue;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY] == grid[x][y] + 1) {
                    // Neuen Pfad erstellen und hinzufügen
                    List<int[]> newPath = new ArrayList<>(currentPath);
                    newPath.add(new int[] { newX, newY });
                    queue.add(newPath);
                }
            }
        }

        return reachableGoals;
    }

    public static void printTwoDimArray(int[][] array) {
        for (int[] row : array) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    public static void printPaths() {
        for (List<int[]> path : validPaths) {
            for (int[] point : path) {
                System.out.print(Arrays.toString(point) + " -> ");
            }
            System.out.println("END");
        }
    }

    public static void findUniquePaths() {
        Set<String> uniquePaths = new HashSet<>();

        for (List<int[]> path : validPaths) {
            // Konvertiert Pfad in String damit vergleichen einfacher ist 
            StringBuilder sb = new StringBuilder();
            for (int[] point : path) {
                sb.append(Arrays.toString(point));
            }
            uniquePaths.add(sb.toString());
        }

        System.out.println("Anzahl eindeutiger Pfade: " + uniquePaths.size());
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        String[] lines = Files.lines(Path.of("file.txt")).toArray(String[]::new);
        int rows = lines.length;
        int cols = lines[0].length();
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Character.getNumericValue(lines[i].charAt(j));
            }
        }
        printTwoDimArray(grid);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    int[] startPoint = {i, j};
                    bfsMultipleTargets(grid, startPoint);
                }
            }
        }
        findUniquePaths();
        long stopTime = System.nanoTime();
        System.out.println(stopTime - startTime);

    }
}
