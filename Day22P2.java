import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day22P2 {
    static List<String> inputStrings = new ArrayList<>();
    static List<Integer> inputNums = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        inputStrings = Files.readAllLines(Path.of("file.txt"));
        for (String str : inputStrings) {
            inputNums.add(Integer.parseInt(str.trim()));
        }

        List<List<Long>> secretSequences = generateSecretSequences();
        List<List<Integer>> allPrices = new ArrayList<>();
        List<List<Integer>> allChanges = new ArrayList<>();

        for (List<Long> seq : secretSequences) {
            List<Integer> prices = new ArrayList<>(seq.size()); //bisschen effizienter gleich die größe anzugeben 
            for (Long secret : seq) {
                prices.add((int) (secret % 10)); //letzte digit bekommen 
            }
            allPrices.add(prices);

            List<Integer> changes = new ArrayList<>(prices.size() - 1);
            for (int i = 0; i < prices.size() - 1; i++) {
                changes.add(prices.get(i + 1) - prices.get(i));
            }
            allChanges.add(changes);
        }


        List<int[]> patterns = generateAllSequences();

        record PatternSum(int[] pattern, long sumBananas) {}

        PatternSum best = patterns.parallelStream().map(pattern -> {
            long sumBananas = 0;
            for (int k = 0; k < allPrices.size(); k++) {
                sumBananas += getSellPriceOrZero(allPrices.get(k), allChanges.get(k), pattern);
            }
            return new PatternSum(pattern, sumBananas);
        })
        // PatternSumObjekt mit dem größten sumBananas finden 
        .reduce((acc, next) -> next.sumBananas() > acc.sumBananas() ? next : acc)
        // Falls irgendwas schiefläuft oder List leer
        .orElse(new PatternSum(new int[]{0,0,0,0}, 0));


        System.out.println("Best Pattern: " + Arrays.toString(best.pattern()));
        System.out.println("Max Bananas:  " + best.sumBananas());
    }


    // Erzeugt 2001 SecretNumbers pro Käufer... das +1 wegen dem startwert 
    public static List<List<Long>> generateSecretSequences() {
        List<List<Long>> allSequences = new ArrayList<>();
        for (Integer num : inputNums) {
            List<Long> currentSequence = new ArrayList<>(2001);
            long secret = num;          
            // Startwert 
            currentSequence.add(secret);
            
            for (int i = 0; i < 2000; i++) {
                secret = calcNextSecret(secret);
                currentSequence.add(secret);
            }
            allSequences.add(currentSequence);
        }
        return allSequences;
    }


    public static long calcNextSecret(long currSecret) {
        long tmp = currSecret * 64;
        currSecret = currSecret ^ tmp;
        currSecret = currSecret % 16777216;

        tmp = currSecret / 32;
        currSecret = currSecret ^ tmp;
        currSecret = currSecret % 16777216;

        tmp = currSecret * 2048;
        currSecret = currSecret ^ tmp;
        currSecret = currSecret % 16777216;

        return currSecret;
    }


    public static List<int[]> generateAllSequences() {
        List<int[]> sequences = new ArrayList<>(19*19*19*19);
        for (int a = -9; a <= 9; a++) {
            for (int b = -9; b <= 9; b++) {
                for (int c = -9; c <= 9; c++) {
                    for (int d = -9; d <= 9; d++) {
                        sequences.add(new int[]{a, b, c, d});
                    }
                }
            }
        }
        return sequences;
    }


    public static int findFirstOccurrence(List<Integer> changes, int[] pattern) {
        for (int i = 0; i <= changes.size() - 4; i++) {
            if (changes.get(i) == pattern[0] &&
                changes.get(i + 1) == pattern[1] &&
                changes.get(i + 2) == pattern[2] &&
                changes.get(i + 3) == pattern[3]) {
                return i;
            }
        }
        return -1; 
    }

    // Liefert den Verkaufspreis, wenn Pattern erstmals gefunden wird. Sonst 0.
    public static int getSellPriceOrZero(List<Integer> prices, List<Integer> changes, int[] pattern) {
        int idx = findFirstOccurrence(changes, pattern);
        if (idx == -1) {
            return 0; 
        }
        // idx + 4 => Index in prices, an dem verkauft wird 
        return prices.get(idx + 4);
    }
}

 /*
        long maxBananas = 0;
        int[] bestPattern = null;

        for (int[] pattern : patterns) {
            long sumBananas = 0;
            // Für jeden Käufer: Wo (wenn überhaupt) tritt pattern zuerst auf?
            for (int k = 0; k < allPrices.size(); k++) {
                int sellPrice = getSellPriceOrZero(allPrices.get(k), allChanges.get(k), pattern);
                sumBananas += sellPrice;
            }
            // Haben wir ein besseres Ergebnis gefunden?
            if (sumBananas > maxBananas) {
                maxBananas = sumBananas;
                bestPattern = pattern;
            }
        }

        System.out.println("Best Pattern: " + Arrays.toString(bestPattern));
        System.out.println("Max Bananas:  " + maxBananas);
        */
