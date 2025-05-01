package Day6To12;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day9P1 {
    static String intputString = null;
    static List<Integer> intList = new ArrayList<>();
    static List<String> resultString = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        intputString = Files.readString(Path.of("file.txt"));
        String[] parts = intputString.split("");
        for(String str:parts){
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
            }else{
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
        int bIndex = resultString.size() - 1;

        for (int fIndex = 0; fIndex < bIndex; fIndex++) {
            if (resultString.get(fIndex).equals(".")) {
                while (bIndex > fIndex && resultString.get(bIndex).equals(".")) {
                    bIndex--; 
                }
                if (bIndex > fIndex) {
                    resultString.set(fIndex, resultString.get(bIndex));
                    resultString.set(bIndex, ".");
                    bIndex--; 
                }
            }
        }
    }
    public static void calcCheckSum(){
        int index =0;
        long checkSum =0;
        while (!resultString.get(index).equals(".")) {
            checkSum += (index*Integer.parseInt(resultString.get(index)));
            index++;
        }
        System.out.println();
        System.out.println(checkSum);
    }
}
