package Day1To5;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


public class Day5P2 {
    static List<String> inputStringArray = new ArrayList<>();
    static Map<Integer, List<Integer>> map = new HashMap<>();
    static List<List<Integer>> part2UpdateIntegers = new ArrayList<>();
   
    public static void parseInput() {
        List<String> part1Rules = new ArrayList<>();
        List<String> part2Updates = new ArrayList<>();
        boolean isPart2 = false;
        for (String line : inputStringArray) {
            if (line.trim().isEmpty()) { 
                isPart2 = true;
                continue;
            }
            if (isPart2) part2Updates.add(line);
            else part1Rules.add(line);
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
    
        // Rules in einer Map mappen
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
    
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int key = entry.getKey();        
            List<Integer> values = entry.getValue(); //  werte die nach dem Schlüssel kommen müssen
    
            if (!lookUpMapForUpdate.containsKey(key)) continue;
    
            int keyPosition = lookUpMapForUpdate.get(key);
    
            for (int value : values) {

                if (lookUpMapForUpdate.containsKey(value)) {
                    int valuePosition = lookUpMapForUpdate.get(value); // Positioon des wertes im Update
                    // Falls key nach dem Wert im Update steht, ist die Regel verletzt
                    if (keyPosition > valuePosition) {
                        return false; // Update ist nicht gültig
                    }
                }
            }
        }
        //wenn keine regel verletzt wurde ...
        return true;
    }

    public static List<Integer> sortUpdate(List<Integer> update) {
        List<Integer> mutableUpdate = new ArrayList<>(update); //weil update wegen toList beim einlsen nicht veränderbar ist
        boolean sorted;
        do {
            sorted = true; 
            for (int i = 0; i < mutableUpdate.size(); i++) {
                for (int j = i + 1; j < mutableUpdate.size(); j++) {
                    //immer zwei paare anschauen 
                    int pageA = mutableUpdate.get(i);
                    int pageB = mutableUpdate.get(j);
                    
                    //Prüfen, ob es eine Regel gibt, dass pageA vor pageB kommen muss
                    //also prüfen ob pageA in der Map als key existiert und ob pageB in der Liste von Seiten für den key pageA enthalten ist
                    //wenn ja dann muss ja die pageA nach pageB kommen.

                    if (map.containsKey(pageA) && map.get(pageA).contains(pageB)) {
                        // wenn true also es gibt die regel, dann Tausche die pages
                        //pageA muss vor pageB stehen
                        mutableUpdate.set(i, pageB); //also pageB auf index von pageA setzen 
                        mutableUpdate.set(j, pageA);
                        sorted = false; // false weil liste ja noch nicht sortiert war
                    }
                }
            }
        } while (!sorted); //solange bis nichtmehr false
    
        return mutableUpdate; 
    }

    public static void getSum(List<List<Integer>> invalidUpdateSorted){
        int sumMiddleNumbersCounter =0;
        for(List<Integer> update: invalidUpdateSorted){
            for(int i=0; i<update.size(); i++){
                int middle = update.size()/2;
                if(i==middle){
                    sumMiddleNumbersCounter+=update.get(middle);

                }
            }
        }
        System.out.println(sumMiddleNumbersCounter);
    }
    

    public static void main(String[] args) throws IOException{
        inputStringArray = Files.readAllLines(Path.of("file2.txt"));
        parseInput();
   
        List<List<Integer>> invalidUpdate = new ArrayList<>();
        // durch alle updates laufen und speichere alle INVALIDE Updates 
        for (List<Integer> update : part2UpdateIntegers) {
            if(!isUpdateValid(update)){
                invalidUpdate.add(update);
            }
        }

        //invalid updates sotieren 

        List<List<Integer>> invalidUpdateSorted = new ArrayList<>();
        for (List<Integer> update : invalidUpdate) {
            invalidUpdateSorted.add(sortUpdate(update)); 
        }

        getSum(invalidUpdateSorted);

    }
}