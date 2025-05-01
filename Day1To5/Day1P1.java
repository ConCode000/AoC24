package Day1To5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;


public class Day1P1{

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
            if(i%2==0){ 
                firstRowInts.add(allInputIts.get(i));
            }else{
                secondRowInts.add(allInputIts.get(i));
            }
        }

        Collections.sort(firstRowInts);
        Collections.sort(secondRowInts);
    } 

    public static void main(String[] args){
        getData();
        formatInput();

        int sum =0;
        for(int i=0; i<firstRowInts.size();i++){
            sum += Math.abs(firstRowInts.get(i) - secondRowInts.get(i));
        }

        System.out.println(sum);

    }
}