
import Jama.Matrix;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day13P1 {
    static List<String> inputStringList = new ArrayList<>();
    static List<Integer> inputNumbers = new ArrayList<>();
    static List<List<Integer>> listOfArcades = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        inputStringList = Files.readAllLines(Path.of("file.txt"));
        inputStringList.removeIf(String::isEmpty);
        parseInput();

        int totalCost = 0;
        int solvedCount = 0;

        for (List<Integer> list : listOfArcades) {
            Integer cost = solve(list);
            if (cost != null) {
                totalCost += cost;
                solvedCount++;
            }
        }

        System.out.println("Anzahl gewonnener Preise: " + solvedCount);
        if (solvedCount > 0) {
            System.out.println("Gesamtkosten: " + totalCost);
        } else {
            System.out.println("Keine Lösung für irgendeine Maschine gefunden.");
        }
    }

    public static Integer solve(List<Integer> coeff) {
        double x_A = coeff.get(0);
        double y_A = coeff.get(1);
        double x_B = coeff.get(2);
        double y_B = coeff.get(3);
        double xPrice = coeff.get(4);
        double yPrice = coeff.get(5);

        double[][] coefficients = { { x_A, x_B }, { y_A, y_B } };
        double[] resultVector = { xPrice, yPrice };

        return calc(coefficients, resultVector);
    }

    public static Integer calc(double[][] coefficients, double[] results) {
        Matrix A = new Matrix(coefficients);
        Matrix b = new Matrix(results, results.length);

        //A muss regulär sein
        if (A.det() == 0.0) {
            System.out.println("scheiße");
            return null;
        }

        try {
            Matrix solution = A.solve(b);
            double a = solution.get(0, 0);
            double bValue = solution.get(1, 0);

            if (isInteger(a) && isInteger(bValue) && a >= 0 && bValue >= 0 && a <= 100 && bValue <= 100) {
                int aInt = (int) Math.round(a);
                int bInt = (int) Math.round(bValue);
                int cost = 3 * aInt + bInt;
                System.out.printf("Lösung gefunden: a = %d, b = %d, Kosten = %d Tokens\n", aInt, bInt, cost);
                return cost;
            } else {
                System.out.println("scheiße");
                return null;
            }
        } catch (Exception e) {
            System.out.println("noch mehr scheiße: " + e.getMessage());
            return null;
        }
    }

    public static boolean isInteger(double value) {
        double tolerance = 1e-9;
        return Math.abs(value - Math.round(value)) < tolerance;
    }

    public static void parseInput() {
        String regex = "\\d+";
        for (String str : inputStringList) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                inputNumbers.add(Integer.parseInt(matcher.group()));
            }
        }
        // Gruppieren in 6er packen  
        List<Integer> temp = new ArrayList<>();
        for (Integer x : inputNumbers) {
            temp.add(x);
            if (temp.size() == 6) {
                listOfArcades.add(new ArrayList<>(temp));
                temp.clear();
            }
        }
    }
}