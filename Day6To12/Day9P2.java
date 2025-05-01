package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day9P2 {
    static String inputString = null;
    static List<Integer> intList = new ArrayList<>();
    static List<String> resultString = new ArrayList<>();
    
    static class FileInfo {
        int fileId;
        int startIndex;
        int length;
        FileInfo(int fileId, int startIndex, int length) {
            this.fileId = fileId;
            this.startIndex = startIndex;
            this.length = length;
        }
    }

    public static void main(String[] args) throws IOException {
        inputString = Files.readString(Path.of("file.txt"));
        String[] parts = inputString.split("");
        for(String str: parts){
            intList.add(Integer.parseInt(str));
        }

        int fileIdIndex =0;
        for (int i = 0; i < intList.size(); i++) {
            if(i%2==0){
                int count = intList.get(i);
                for (int j = 0; j < count; j++) {
                    resultString.add(String.valueOf(fileIdIndex));
                }
                fileIdIndex++;
            } else {
                int count = intList.get(i);
                for (int j = 0; j < count; j++) {
                    resultString.add(".");
                }
            }
        }

        alg();
        resultString.forEach(System.out::print);
        calcCheckSum();
    } 

    public static void alg() {
        List<FileInfo> files = getFiles(); // für alle files startindex und länge finden 

        // Dateien nach absteigender ID sotieren 
        files.sort((a,b) -> Integer.compare(b.fileId, a.fileId));

        // Versuchen jede Datei nach links zu verschieben
        for (FileInfo file : files) {
            // Suche freien Bereich links von file.startIndex
            int targetPos = findFreeBlockLeft(file.startIndex, file.length);
            if (targetPos != -1) {
                moveFile(file.fileId, file.length, targetPos);

            }
        }
    }

    // alle Informationen über alle Dateien ermitteln also ID, Startindex und Länge
    private static List<FileInfo> getFiles() {
        List<FileInfo> files = new ArrayList<>();
        int currentFile = -1;
        int startIndex = -1;
        int length = 0;

        for (int i = 0; i < resultString.size(); i++) {
            String val = resultString.get(i);
            if (!val.equals(".")) {
                int fileId = Integer.parseInt(val);
                if (fileId != currentFile) {
                    // Neue Datei beginnt
                    if (currentFile != -1) {
                        // vorherige Datei speiren 
                        files.add(new FileInfo(currentFile, startIndex, length));
                    }
                    currentFile = fileId;
                    startIndex = i;
                    length = 1;
                } else {
                    length++;
                }
            }
        }
        // Letzte Datei speichern, falls eine vorhanden
        if (currentFile != -1) {
            files.add(new FileInfo(currentFile, startIndex, length));
        }
        return files;
    }

    // Sucht einen freien zusammenhängenden Block von size mit '.'
    // limitIndex ist der Start der aktuellen Datei, man sucht also einen Platz mit EndindexVomFile < fileStartIndex
    private static int findFreeBlockLeft(int fileStartIndex, int size) {
        // schaut daher mögliche Startpunkte von links nach rechts an,
        // hört aber bei fileStartIndex auf.

        //bereich von Länge size vollständig links von fileStartIndex liegt
        //-> Start dieses freien Bereichs darf maximal bei fileStartIndex - size sein
        int maxStart = fileStartIndex - size; 
        if (maxStart < 0) {
            return -1; 
        }

        for (int i = 0; i <= maxStart; i++) {
            boolean canFit = true;
            for (int j = 0; j < size; j++) {
                if (!resultString.get(i+j).equals(".")) {
                    canFit = false;
                    break;
                }
            }
            if (canFit) {
                return i;//gültiger Startpunkt für die Datei
            }
        }
        return -1;
    }

    // Bewegt eine Datei mit gegebener fileId und length an eine neue Position targetPos.
    // das an targetPos genug '.' sind ist bereits sicher 
    private static void moveFile(int fileId, int length, int targetPos) {
        String fid = String.valueOf(fileId);

        // Zuerst Datei im alten Bereich löschen
        for (int i = 0; i < resultString.size(); i++) {
            if (resultString.get(i).equals(fid)) {
                resultString.set(i, ".");
            }
        }

        // Dann Datei an neuer Stelle einfügen
        for (int i = 0; i < length; i++) {
            resultString.set(targetPos + i, fid);
        }
    }

    public static void calcCheckSum(){
        long checkSum =0;
        for (int i = 0; i < resultString.size(); i++) {
            String val = resultString.get(i);
            if (!val.equals(".")) {
                checkSum += (i * Integer.parseInt(val));
            }
        }
        System.out.println();
        System.out.println(checkSum);
    }
}