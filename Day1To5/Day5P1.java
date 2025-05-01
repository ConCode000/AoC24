package Day1To5;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

public class Day5P1 {
    static List<String> inputStringArray = new ArrayList<>();
    static Map<Integer, List<Integer>> map = new HashMap<>();
    static List<List<Integer>> part2UpdateIntegers = new ArrayList<>();

    public static void parseInput() {
        List<String> part1Rules = new ArrayList<>();
        List<String> part2Updates = new ArrayList<>();
        boolean isPart2 = false;
        for (String line : inputStringArray) {
            if (line.trim().isEmpty()) { // sobald leere Zeile gefunden
                isPart2 = true;
                continue; // leere Zeile überspringen
            }
            if (isPart2)
                part2Updates.add(line);
            else
                part1Rules.add(line);
        }

        // in ints umwandeln die Updates
        part2UpdateIntegers = part2Updates.stream()
                .map(str -> Arrays.stream(str.split(","))
                        .map(Integer::parseInt).toList())
                .toList();

        // Liste von ints erzeugen von den Regeln X und Y
        List<Integer> listOfRulesInInts = part1Rules.stream()
                .flatMap(str -> Arrays.stream(str.split("\\|")))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Regeln in Map mappen
        for (int i = 0; i < listOfRulesInInts.size(); i += 2) {
            int key = listOfRulesInInts.get(i);
            int value = listOfRulesInInts.get(i + 1);

            map.computeIfAbsent(key, k -> new ArrayList<>());
            map.get(key).add(value);
            map.get(key).sort(Integer::compareTo); // Sortiert aufsteigend
        }
    }

    public static boolean isUpdateValid(List<Integer> update) {
        Map<Integer, Integer> lookUpMapForUpdate = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            lookUpMapForUpdate.put(update.get(i), i);
        }

        // durch alle Regeln in der Map iterieren 
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int key = entry.getKey();
            List<Integer> values = entry.getValue(); // werte die nach dem Schlüssel kommen müssen

            // wenn key im Update nicht vorhanden ist, überspringt es die Regel
            if (!lookUpMapForUpdate.containsKey(key))
                continue;

            // Position/indxs vom key im Update Array holen
            int keyPosition = lookUpMapForUpdate.get(key);

            for (int value : values) {
                // Wenn Wert von values im Update vorhanden ist dann Positionen überprüfen
                if (lookUpMapForUpdate.containsKey(value)) {
                    int valuePosition = lookUpMapForUpdate.get(value); // Positioon des Wertes im Update

                    // Falls key nach dem Wert im Update steht, ist die Regel verletzt
                    if (keyPosition > valuePosition) {
                        return false; // Update ist nicht gültig
                    }
                }
            }
        }
        // wenn keine regel verletzt wurde ...
        return true;
    }

    public static void main(String[] args) throws IOException {
        inputStringArray = Files.readAllLines(Path.of("file2.txt"));
        parseInput();

        List<List<Integer>> validUpdate = new ArrayList<>();
        // durch alle Updates laufen und wenn Update valid ist dann in validUpdate speichern
        for (List<Integer> update : part2UpdateIntegers) {
            if (isUpdateValid(update)) {
                validUpdate.add(update);
            }
        }
        int sumMiddleNumbersCounter = 0;
        for (List<Integer> update : validUpdate) {
            for (int i = 0; i < update.size(); i++) {
                int middle = update.size() / 2;
                if (i == middle) {
                    sumMiddleNumbersCounter += update.get(middle);

                }
            }
        }
        System.out.println(sumMiddleNumbersCounter);
    }
}