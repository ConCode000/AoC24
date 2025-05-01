package Day1To5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;


public class Day1P2{

    static ArrayList<Integer> allInputIts = new ArrayList<>();
    static ArrayList<Integer> firstRowInts = new ArrayList<>();
    static ArrayList<Integer> secondRowInts = new ArrayList<>();

    public static void getData() {
        try {
            File myObj = new File("file.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                Integer data = myReader.nextInt();
                allInputIts.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void formatInput(){
        for(int i=0; i<allInputIts.size(); i++){
            if(i%2==0){ //wenn index gerade
                firstRowInts.add(allInputIts.get(i));
            }else{
                secondRowInts.add(allInputIts.get(i));
            }
        }

        Collections.sort(firstRowInts);
        Collections.sort(secondRowInts);
    }


    public static int countNumber(int num){
        int count =0;
        for(Integer x : secondRowInts){
            if(num == x){
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args){
        getData();
        formatInput();

        int sumSimilarityScore =0;

        for(Integer x : firstRowInts){
            int similarityScoreForNum = countNumber(x);
            sumSimilarityScore += similarityScoreForNum*x;
        }

        System.out.println(sumSimilarityScore);

    }
}