package Day1To5;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3P2Refectored {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        String inputString = Files.readString(Path.of("file.txt"));

        String parsedString = Pattern.compile("don't\\(\\)|do\\(\\)|mul\\(\\d+,\\d+\\)").matcher(inputString).results().map(m -> m.group()).collect(Collectors.joining());
        
        parsedString = parsedString.replaceAll("don't\\(\\).*?do\\(\\)", "").replaceAll("do\\(\\)", "");

        ArrayList<Integer> numbers = Pattern.compile("\\d+") //results() returns a stream of matches found in the input
        .matcher(parsedString).results().map(m -> Integer.parseInt(m.group())).collect(Collectors.toCollection(ArrayList::new));

        int sum = IntStream.range(1, numbers.size()).filter(i -> i % 2 == 1) // only odd indics because i need pairs 
        .map(i -> numbers.get(i - 1) * numbers.get(i)).sum(); 

        System.out.println("Result: " + sum);
        long stopTime = System.nanoTime();
        System.out.println(stopTime - startTime);
    }
}