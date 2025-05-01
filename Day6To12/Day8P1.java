package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day8P1 {
    private static char[][] charGrid;
    private static List<String> inputStrings; 
    private static int sizeRows;
    private static int sizeCols;

    // Map: Zeichen (Frequenz) -> Liste von Positionen [row, col]
    private static Map<Character, List<int[]>> charPositionsMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        inputStrings = Files.readAllLines(Path.of("file.txt"));
        sizeRows = inputStrings.size();
        sizeCols = inputStrings.get(0).length();
        charGrid = new char[sizeRows][sizeCols];

        createCharGrid();
        createCharPositionMap();

        // berechnet die Antinodes für alle Frequenzen
        Set<String> antinodes = calculateAntinodes();

        System.out.println("Anzahl der Antinodes: " + antinodes.size());
    }

    public static void createCharGrid() {
        for (int row = 0; row < inputStrings.size(); row++) {
            String line = inputStrings.get(row);
            for (int col = 0; col < line.length(); col++) {
                char element = line.charAt(col);
                charGrid[row][col] = element;
            }
        }
    }

    //Liest alle AntennenpositionenMap erstellen 
    public static void createCharPositionMap() {
        for (int row = 0; row < charGrid.length; row++) {
            for (int col = 0; col < charGrid[row].length; col++) {
                char element = charGrid[row][col];
                if (element != '.') {
                    charPositionsMap.computeIfAbsent(element, k -> new ArrayList<>()).add(new int[]{row, col});
                }
            }
        }
    }

    
    // alle Antinoden für alle Frequenzen berechnen  
    public static Set<String> calculateAntinodes() {
        Set<String> antinodes = new HashSet<>();

        for (Map.Entry<Character, List<int[]>> frequencyEntry : charPositionsMap.entrySet()) {
            // positions enthält alle (Zeilen,Spalten) Koordinaten aller Antennen dieser Frequenz
            List<int[]> antennaPositions = frequencyEntry.getValue(); 

            // jetzt jedes mögliche "Antennenpaar" von der Frequenz anschauen 
            // i durchläuft die Antennenliste von 0 bis zur vorletzten Antenne.
            for (int i = 0; i < antennaPositions.size(); i++) {
                // j startet immer ein Element nach i, damit wir jedes Paar nur einmal betrachten.
                for (int j = i + 1; j < antennaPositions.size(); j++) {
                    // A und B sind hier die zwei Antennen (jeweils mit row und col)
                    int[] antennaA = antennaPositions.get(i);
                    int[] antennaB = antennaPositions.get(j);

                    // Aus antennaA, antennaB Zeilen und Spaltenkoordinaten holen 
                    int antennaARow = antennaA[0];
                    int antennaACol = antennaA[1];
                    int antennaBRow = antennaB[0];
                    int antennaBCol = antennaB[1];

                    //  beide Antinoden (p1 und p2) berechnen 
                    // p1 = 2*A - B ( für Zeile und Spalte berechnen)

                    /*
                    Beipiel 
                    Antenne A: antennaARow = 4, antennaACol = 10
                    Antenne B: antennaBRow = 6, antennaBCol = 14

                    antinode1Row = 2 * antennaARow - antennaBRow
                    antinode1Row = 2 * 4 - 6 = 2
                    antinode1Col = 2 * antennaACol - antennaBCol = 6

                    => p1 Koordinate ist (2, 6)

                     */

                    int antinode1Row = 2 * antennaARow - antennaBRow;
                    int antinode1Col = 2 * antennaACol - antennaBCol;

                    // p2 = 2*B - A
                    int antinode2Row = 2 * antennaBRow - antennaARow;
                    int antinode2Col = 2 * antennaBCol - antennaACol;

                    // prüfen ob p1 innerhalb des Grids liegt
                    if (!isOutOfBounds(antinode1Row, antinode1Col)) {
                        antinodes.add(antinode1Row + "," + antinode1Col);
                    }

                    // prüfen ob p2 innerhalb des Grids liegt
                    if (!isOutOfBounds(antinode2Row, antinode2Col)) {
                        antinodes.add(antinode2Row + "," + antinode2Col);
                    }
                }
            }
        }

        return antinodes;
    }

    public static boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= sizeRows || col < 0 || col >= sizeCols;
    }
}
