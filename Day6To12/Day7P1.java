package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day7P1 {
    static List<String> inputStrings = new ArrayList<>();
    static List<Double> resultList = new ArrayList<>();
    static List<List<Integer>> numsLists = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        inputStrings = Files.readAllLines(Path.of("file.txt"));
        parseInput();

        double totalSum = 0;
        for (int i = 0; i < numsLists.size(); i++) {
            double aimResult = resultList.get(i);
            List<Integer> numList = numsLists.get(i);

            // anzahl der benötigiten Operatoren 
            int operatorCount = numList.size() - 1;

            // alle möglichen Operator combinations für eine liste aus numsList holen 
            List<char[]> operatorCombos = generateCombinationsForCount(operatorCount);

            boolean lineMatches = false;
            for (char[] operatorenKombination : operatorCombos) {
                double currIterResult = calc(operatorenKombination, numList);
                if (currIterResult == aimResult) {
                    lineMatches = true;
                    break;
                }
            }
            if (lineMatches) {
                totalSum += aimResult;
            }
        }
        System.out.println("Gesamtsumme: " + (long) totalSum);
    }

    public static double calc(char[] operatorenKombination, List<Integer> numList) {
        double result = numList.get(0); // erste zahl kann man schonmal hinzufügen 
        for (int i = 0; i < operatorenKombination.length; i++) {
            char operator = operatorenKombination[i];
            int nextNum = numList.get(i + 1); // next Zahl aus der Liste
            switch (operator) {
                case '+':
                    result += nextNum;
                    break;
                case '*':
                    result *= nextNum;
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public static void parseInput() {
        for (String equation : inputStrings) {
            String[] partsOfEquation = equation.split(":");
            resultList.add(Double.parseDouble(partsOfEquation[0].trim()));
            String[] temp = partsOfEquation[1].trim().split(" ");
            List<Integer> currentNums = new ArrayList<>();
            for (String numString : temp) {
                if (!numString.isEmpty()) { 
                    currentNums.add(Integer.parseInt(numString.trim()));
                }
            }
            numsLists.add(currentNums);
        }
    }

    // gibt alle Operatorenkombinationen zurück für eine anzhal an benötigeten operatoren 
    public static List<char[]> generateCombinationsForCount(int operatorCount) {
        char[] operators = {'+', '*'};
        List<char[]> combinations = new ArrayList<>();

        // Es gibt 2^(operatorCount) Kombinationen, weil an jeder Stelle '+' oder '*' stehen kann
        //int totalCombinations = (int) Math.pow(operators.length, operatorCount);
        int totalCombinations = (int) 1<< operatorCount;

        for (int i = 0; i < totalCombinations; i++) {
            char[] combination = new char[operatorCount];
            int temp = i;
            for (int j = 0; j < operatorCount; j++) {
                combination[j] = operators[temp % operators.length]; //temp % operators.length ist immer 0 oder 1
                temp /= operators.length; //ist immer 0 also verschiebt von 1 auf 0 weil es ja nur 2 operatoren gibt 
                //wollte es allgemein machen 
            }
            combinations.add(combination);
        }
        return combinations;
    }
}
