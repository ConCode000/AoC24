
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day22P1{
    static List<String> inputStrings = new ArrayList<>();
    static List<Integer> inputNums = new ArrayList<>();


    public static void main(String[] args) throws IOException{
        inputStrings = Files.readAllLines(Path.of("file.txt"));

        for(String str : inputStrings){
            inputNums.add(Integer.parseInt(str.trim()));
            
        }

        long res =0;
        long sum = 0;
        for(Integer num : inputNums){
            res = getNthSecretNumber(num, 2000);
            sum+=res;
            System.out.print(res+ ", ");
        }
        System.out.println("SUM " + sum);        

    

    }

    public static long getNthSecretNumber(long initialSecret, int n) {
        long secret = initialSecret;
        for (int i = 0; i < n; i++) {
            secret = calcNextSecret(secret);
        }
        return secret;
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

}