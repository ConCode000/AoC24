import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day23P2 {
    static Map<String, List<String>> graphMap = new HashMap<>();
    static Set<String> largestClique = new HashSet<>();

    public static void main(String[] args) throws IOException {
        List<String> inputStrings = Files.readAllLines(Path.of("file.txt"));
        inputStrings.stream()
            .map(line -> line.split("-"))
            .forEach(pair -> {
                graphMap.computeIfAbsent(pair[0], k -> new ArrayList<>()).add(pair[1]);
                graphMap.computeIfAbsent(pair[1], k -> new ArrayList<>()).add(pair[0]);
            });
        findLargestClique();

        List<String> sortedClique = new ArrayList<>(largestClique);
        Collections.sort(sortedClique);
        String password = String.join(",", sortedClique);

        System.out.println("Password to the LAN party: " + password);
    }


    public static void findLargestClique() {
        /*
        R: The current clique being constructed.
        P: The set of nodes that can still be added to the clique.
        X: The set of nodes that must not be added to the clique.
        When both P and X are empty, the algorithm has found a maximal clique.
        */

        Set<String> r = new HashSet<>();
        Set<String> p = new HashSet<>(graphMap.keySet());
        Set<String> x = new HashSet<>();

        bronKerbosch(r, p, x);
    }

    // Bron–Kerbosch Algorithm
    public static void bronKerbosch(Set<String> r, Set<String> p, Set<String> x) {
        if (p.isEmpty() && x.isEmpty()) {
            if (r.size() > largestClique.size()) {
                largestClique = new HashSet<>(r);
            }
            return;
        }
        Set<String> pCopy = new HashSet<>(p);
        for (String v : pCopy) {
            Set<String> newR = new HashSet<>(r);
            newR.add(v);

            Set<String> newP = new HashSet<>(p);
            newP.retainAll(new HashSet<>(graphMap.get(v)));

            Set<String> newX = new HashSet<>(x);
            newX.retainAll(new HashSet<>(graphMap.get(v)));

            bronKerbosch(newR, newP, newX);

            p.remove(v);
            x.add(v);
        }
    }

}
