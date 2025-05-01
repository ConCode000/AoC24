package Day1To5;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day4P2 {
    private static final int SIZE = 140;
    private static List<String> inputStringArray = new ArrayList<>();
    private static char[][] charArray = new char[SIZE][SIZE];
    private static final int[][] directions = {
        {-1, -1}, // diagonal oben links 
        {-1, 1}, //diagonal oben rechts 
        {1, -1}, //diagonal unten links
        {1, 1} //diagonal unten rechts 
    };
    
    public static boolean checkAllDirections(int startX, int startY){
        int counter = 0; 
        for (int[] ns : directions){
            if (charArray[startX + ns[0]][startY + ns[1]] == 'S') {
                ++counter; 
            } else if (charArray[startX + ns[0]][startY + ns[1]] == 'M'){
                --counter; 
            } else {
                return false; 
            }
        }
        return counter ==0 && charArray[startX - 1][startY - 1] != charArray[startX + 1][startY + 1];
    }

    public static void main(String[] args) throws IOException{
        inputStringArray = Files.readAllLines(Path.of("file.txt"));
        for(int i =0; i<inputStringArray.size();i++){
            charArray[i] = inputStringArray.get(i).toCharArray();
        }
        int counter = 0; 
        char cTarget = 'A';
        //durch array gehen aber nich die Ränder beachten 
        for(int row=1; row<SIZE-1; row++){
            for(int col=1; col<SIZE-1; col++){
                //ein A finden 
                if(charArray[row][col] == cTarget){
                    //für jede richtung prüfen, ob diagonal 2xM/2xS steht (nicht gegenüber) 
                    if(checkAllDirections(row, col)){   
                        counter++;
                    }
                }
            }
        }
        System.out.println(counter);
    }
}
