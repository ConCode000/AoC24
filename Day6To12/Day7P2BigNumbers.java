package Day6To12;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day7P2BigNumbers {
    static List<String> inputStrings = new ArrayList<>();
    static List<BigInteger> resultList = new ArrayList<>();
    static List<List<BigInteger>> numsLists = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        inputStrings = Files.readAllLines(Path.of("file.txt"));
        parseInput();

        // alle Kommentare wie im Original belassen
        BigInteger totalSum = BigInteger.ZERO;
        for (int i = 0; i < numsLists.size(); i++) {
            BigInteger aimResult = resultList.get(i);
            List<BigInteger> numList = numsLists.get(i);

            // anzahl der benötigiten Operatoren 
            int operatorCount = numList.size() - 1;

            // alle möglichen Operator combinations für eine liste aus numsList holen 
            List<char[]> operatorCombos = generateCombinationsForCount(operatorCount);

            boolean lineMatches = false;
            for (char[] operatorenKombination : operatorCombos) {
                BigInteger currIterResult = calc(operatorenKombination, numList);
                // Hier statt double-Vergleich: BigInteger-Vergleich
                if (currIterResult.compareTo(aimResult) == 0) {
                    lineMatches = true;
                    break;
                }
            }
            if (lineMatches) {
                totalSum = totalSum.add(aimResult);
            }
        }
        System.out.println("Gesamtsumme: " + totalSum);
    }

    public static BigInteger calc(char[] operatorenKombination, List<BigInteger> numList) {
        BigInteger result = numList.get(0); // erste zahl kann man schonmal hinzufügen 
        for (int i = 0; i < operatorenKombination.length; i++) {
            char operator = operatorenKombination[i];
            BigInteger nextNum = numList.get(i + 1); // next Zahl aus der Liste
            switch (operator) {
                case '+':
                    result = result.add(nextNum);
                    break;
                case '*':
                    result = result.multiply(nextNum);
                    break;
                case 'x':
                    String resAsString = result.toString();
                    String nexNumAsString = nextNum.toString();
                    String concatenated = resAsString + nexNumAsString;
                    result = new BigInteger(concatenated);
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
            resultList.add(new BigInteger(partsOfEquation[0].trim()));
            String[] temp = partsOfEquation[1].trim().split(" ");
            List<BigInteger> currentNums = new ArrayList<>();
            for (String numString : temp) {
                if (!numString.isEmpty()) { 
                    currentNums.add(new BigInteger(numString.trim()));
                }
            }
            numsLists.add(currentNums);
        }
    }

    // gibt alle Operatorenkombinationen zurück für eine anzhal an benötigeten operatoren 
    public static List<char[]> generateCombinationsForCount(int operatorCount) {
        char[] operators = {'+', '*', 'x'};
        List<char[]> combinations = new ArrayList<>();

        // Es gibt 2^(operatorCount) Kombinationen, weil an jeder Stelle '+' oder '*' stehen kann
        int totalCombinations = (int) Math.pow(operators.length, operatorCount);


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