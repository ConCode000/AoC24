package Day1To5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day3P2 {

    static String inputString;
    static StringBuilder parsedString = new StringBuilder();
    static ArrayList<Integer> nums = new ArrayList<>();

    public static void doMult(){
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parsedString);
        while (matcher.find()) {
            nums.add(Integer.parseInt(matcher.group()));   
        }
        int sum =0;
        for(int i=1; i<nums.size(); i+=2){
            int product = nums.get(i-1) * nums.get(i);
            sum +=product;
        }
        System.out.println("result "+sum);
    }

    public static void regexDeleteFunc(String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parsedString);
        while (matcher.find()) {
            parsedString.delete(matcher.start(), matcher.end());
            matcher = pattern.matcher(parsedString);
        }
    }

    public static void main(String[] args) throws IOException{
        long startTime = System.nanoTime();

        inputString = Files.readString(Path.of("file.txt"));

        String regexDoDont = "don't\\(\\)|do\\(\\)|mul\\(\\d+,\\d+\\)";
        String regexDelete = "don't\\(\\).*?do\\(\\)";
        String regexDeleteDo = "do\\(\\)";

        Pattern pattern = Pattern.compile(regexDoDont);
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            parsedString.append((matcher.group()));     
        }

        regexDeleteFunc(regexDelete);
        regexDeleteFunc(regexDeleteDo);
        doMult();
        long stopTime = System.nanoTime();
        System.out.println(stopTime - startTime);
    }
}
