package Day1To5;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Day3P1 {
    static String inputString;
    static StringBuilder parsedString = new StringBuilder();
    static ArrayList<Integer> nums = new ArrayList<>();

    public static void doAndFindMult(String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            parsedString.append((matcher.group()));   
        }
        String regex1 = "\\d+";
        Pattern pattern2 = Pattern.compile(regex1);
        Matcher matcher2 = pattern2.matcher(parsedString);
        while (matcher2.find()) {
            nums.add(Integer.parseInt(matcher2.group()));   
        }
        
        int sum =0;
        for(int i=1; i<nums.size(); i+=2){
            int product = nums.get(i-1) * nums.get(i);
            sum +=product;
        }
        System.out.println("result "+sum);
    }
    public static void main(String[] args) throws IOException{
        String regex = "mul\\(\\d+,\\d+\\)"; //mul(x,y) wobei x und x zahlen sind (auch mehrstellige) 
        inputString = Files.readString(Path.of("file.txt"));
        doAndFindMult(regex);
    }
}
