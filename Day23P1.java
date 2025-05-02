import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.*;

public class Day23P1 {
    static List<String> inputStrings = new ArrayList<>();
    static List<String[]> parsedInputStrings = new ArrayList<>();
    static Map<String, List<String>> graphMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        inputStrings = Files.readAllLines(Path.of("file.txt"));
        parsedInputStrings = inputStrings.stream().map(x -> x.split("-")).collect(Collectors.toList());
        parsedInputStrings.stream().forEach(pair -> {
            String key1 = pair[0];
            String key2 = pair[1];
            graphMap.computeIfAbsent(key1, k -> new ArrayList<>()).add(key2);
            graphMap.computeIfAbsent(key2, k -> new ArrayList<>()).add(key1);
        });

        List<List<String>> lists = findTriangles();
        int tCounter = 0;
        for (List<String> triangle : lists) {
            boolean hasTNode = triangle.stream().anyMatch(node -> node.startsWith("t"));
            if (hasTNode) {
                tCounter++;
            }
        }
        System.out.println("Result " + tCounter);

        //for (List<String> list : lists) {
        //    for (String a : list) {
        //        System.out.print(a + " ");
        //    }
        //    System.out.println();
        //}

    }

    public static List<List<String>> findTriangles() {
        List<List<String>> triangles = new ArrayList<>();

        for (String nodeA : graphMap.keySet()) {
            List<String> neighborsA = graphMap.get(nodeA);
            for (String nodeB : neighborsA) {
                List<String> neighborsB = graphMap.get(nodeB);
                if (neighborsB == null)
                    continue;

                // intersection von neighborsA und neighborsB finden 
                for (String nodeC : neighborsB) {
                    if (neighborsA.contains(nodeC) && !nodeC.equals(nodeA) && !nodeC.equals(nodeB)) {
                        List<String> triangle = Arrays.asList(nodeA, nodeB, nodeC);
                        Collections.sort(triangle); // um duplicates zu vermeiden 
                        if (!triangles.contains(triangle)) {
                            triangles.add(triangle);
                        }
                    }
                }
            }
        }

        return triangles;
    }

}
