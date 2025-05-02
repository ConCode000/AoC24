
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
A = (xPreis * y_B - yPreis * x_B) / (x_A * y_B - y_A * x_B)
B = (x_A * yPreis - y_A * xPreis) / (x_A * y_B - y_A * x_B)

 */

public class Day13P2 {

    static List<String> inputStringList = new ArrayList<>();
    static List<BigInteger> inputNumbers = new ArrayList<>();
    static List<List<BigInteger>> listOfArcades = new ArrayList<>();
    static final BigInteger OFFSET = new BigInteger("10000000000000"); // 10^13

    public static void main(String[] args) throws IOException {
        inputStringList = Files.readAllLines(Path.of("file.txt"));
        inputStringList.removeIf(String::isEmpty);
        parseInput();

        BigInteger totalCost = BigInteger.ZERO;
        int solvedCount = 0;

        // Jede Maschine durchgehen
        for (List<BigInteger> arcade : listOfArcades) {
            BigInteger cost = solveMachine(arcade);
            if (cost != null) {
                totalCost = totalCost.add(cost);
                solvedCount++;
            }
        }

        System.out.println("Anzahl gewonnener Preise: " + solvedCount);
        if (solvedCount > 0) {
            System.out.println("Gesamtkosten: " + totalCost);
        } else {
            System.out.println("Keine Lösung ");
        }
    }

    public static BigInteger solveMachine(List<BigInteger> coeff) {
        // coeff = x_A, y_A, x_B, y_B, xPrice, yPrice
        BigInteger x_A = coeff.get(0);
        BigInteger y_A = coeff.get(1);
        BigInteger x_B = coeff.get(2);
        BigInteger y_B = coeff.get(3);

        // Preiskoordinaten + OFFSET
        BigInteger xPrice = coeff.get(4).add(OFFSET);
        BigInteger yPrice = coeff.get(5).add(OFFSET);

        // det = a_x*b_y - a_y*b_x
        BigInteger det = x_A.multiply(y_B).subtract(y_A.multiply(x_B));
        if (det.equals(BigInteger.ZERO)) {
            System.out.println("scheiße");
            return null;
        }

        // detA = p_x*b_y - p_y*b_x
        BigInteger detA = xPrice.multiply(y_B).subtract(yPrice.multiply(x_B));

        // detB = a_x*p_y - a_y*p_x
        BigInteger detB = x_A.multiply(yPrice).subtract(y_A.multiply(xPrice));

        // prüfen ob A und B ganzzahlig, nichtnegativ und exakt lösbar sind, wenn exat lösbar hat man minimal Lösung für die Maschine 
        //(d. h. ob detA und detB ohne Rest durch det teilbar sind).
        if (!isDivisible(detA, det) || !isDivisible(detB, det)) {
            System.out.println("scheiße");
            return null;
        }

        BigInteger a = detA.divide(det);
        BigInteger b = detB.divide(det);

        if (a.compareTo(BigInteger.ZERO) < 0 || b.compareTo(BigInteger.ZERO) < 0) {
            System.out.println("scheiße");
            return null;
        }

        // Kosten = 3*A + B
        BigInteger cost = a.multiply(BigInteger.valueOf(3)).add(b);

        System.out.printf("Lösung gefunden: A = %s, B = %s, Kosten = %s Tokens\n", a, b, cost);
        return cost;
    }

    public static boolean isDivisible(BigInteger numerator, BigInteger denominator) {
        if (denominator.equals(BigInteger.ZERO)) {
            return false;
        }
        BigInteger[] qr = numerator.divideAndRemainder(denominator);
        return qr[1].equals(BigInteger.ZERO);
    }

    public static void parseInput() {
        String regex = "\\d+";
        for (String str : inputStringList) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                inputNumbers.add(new BigInteger(matcher.group()));
            }
        }
        // Gruppieren
        List<BigInteger> temp = new ArrayList<>();
        for (BigInteger x : inputNumbers) {
            temp.add(x);
            if (temp.size() == 6) {
                listOfArcades.add(new ArrayList<>(temp));
                temp.clear();
            }
        }
    }
}
