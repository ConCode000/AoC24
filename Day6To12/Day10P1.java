package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day10P1 {

    public static int bfsMultipleTargets(int[][] grid, int[] startPunkt) {
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        queue.add(startPunkt);
        visited[startPunkt[0]][startPunkt[1]] = true;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int reachableGoals = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            if (grid[x][y] == 9) {
                reachableGoals++;
            }
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                boolean inBound = newX >= 0 && newX < rows && newY >= 0 && newY < cols;
                if (inBound && !visited[newX][newY] && grid[newX][newY] == grid[x][y] + 1) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
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

        //printTwoDimArray(grid);

        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    int[] startPoint = { i, j };
                    int anzahlErreichteZiele = bfsMultipleTargets(grid, startPoint);
                    sum += anzahlErreichteZiele;
                    System.out.println("Startpunkt: (" + i + ", " + j + ")");
                    System.out.println("Anzahl erreichbarer Ziele: " + anzahlErreichteZiele);
                }
            }
        }
        System.out.println("Ergebnis: " + sum);
        long stopTime = System.nanoTime();
        System.out.println(stopTime - startTime);
    }
}
