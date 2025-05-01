package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day12P1 {
    static List<String> inputStringList = new ArrayList<>();
    static char[][] grid;
    static int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    static List<int[]> results = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        inputStringList = Files.readAllLines(Path.of("file.txt"));
        int sizeRow = inputStringList.size();
        int sizeCol = inputStringList.get(0).length();
        grid = new char[sizeRow][sizeCol];
        createCharGrid();
        printGrid(sizeRow, sizeCol);
        solve(sizeRow, sizeCol);
        int sum = 0;
        for (int[] x : results) {
            System.out.println("area " + x[0] + " per " + x[1]);
            int area = x[0];
            int per = x[1];
            int product = area * per;
            sum += product;
        }
        System.out.println("result " + sum);
    }

    public static void solve(int sizeRow, int sizeCol) {
        boolean[][] viseted = new boolean[sizeRow][sizeCol];
        for (int row = 0; row < sizeRow; row++) {
            for (int col = 0; col < sizeCol; col++) {
                if (viseted[row][col] != true) {
                    char plantTyp = grid[row][col];
                    int[] point = { row, col };
                    int[] areaAndPerimeter = floodFill(point, plantTyp, viseted);
                    results.add(areaAndPerimeter);

                }
            }
        }
    }

    public static int[] floodFill(int[] point, char plantTyp, boolean[][] viseted) {
        int sizeRow = inputStringList.size();
        int sizeCol = inputStringList.get(0).length();

        Queue<int[]> queue = new LinkedList<>();
        viseted[point[0]][point[1]] = true;
        queue.add(point);
        int area = 0;
        int perimeter = 0;

        while (!queue.isEmpty()) {
            int[] currentPoint = queue.poll();
            area++;
            int row = currentPoint[0];
            int col = currentPoint[1];

            for (int[] dir : directions) {
                int newX = row + dir[0];
                int newY = col + dir[1];

                boolean inBound = newX >= 0 && newX < sizeRow && newY >= 0 && newY < sizeCol;
                if (!inBound || grid[newX][newY] != plantTyp) {
                    perimeter++;

                } else if (!viseted[newX][newY]) {
                    viseted[newX][newY] = true;
                    queue.add(new int[] { newX, newY });
                }
            }
        }
        int[] areaAndPerimeter = { area, perimeter };
        return areaAndPerimeter;
    }

    public static void createCharGrid() {
        for (int row = 0; row < inputStringList.size(); row++) {
            String line = inputStringList.get(row);
            for (int col = 0; col < line.length(); col++) {
                grid[row][col] = line.charAt(col);
            }
        }
    }

    public static void printGrid(int sizeRow, int sizeCol) {
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeCol; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}