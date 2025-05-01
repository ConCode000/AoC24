package Day1To5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day4P1 {
    private static final int SIZE = 140;
    static List<String> inputStringArray = new ArrayList<>();
    static char[][] charArray = new char[SIZE][SIZE];

    static final int[][] directions = {
        {0,-1},  //links 
        {0, 1}, //rechts 
        {-1, 0}, //oben 
        {1, 0}, //unten
        {-1, -1}, // diagonal oben links 
        {-1, 1}, //diagonal oben rechts 
        {1, -1}, //diagonal unten links
        {1, 1} //diagonal unten rechts 
    };

    public static void formatInput(){
        int row =0;
        for(String str : inputStringArray){
            char[] cArray = str.toCharArray();
            for(int i =0; i<SIZE;i++){
                charArray[row][i] = cArray[i];
            }
            row++;
        }
    }
    

    public static boolean checkAllDirections(int[] direction, int startX, int startY, int dirX, int dirY, int anzahlRows, int anzahlCols){
        String targetWord = "XMAS";
        int steps = targetWord.length() - 1;
        for(int step=1; step<=steps; step++){
            int newX = startX + step * dirX;
            int newY = startY + step * dirY;
            //check for index out of bound 
            if(newX < 0 || newX >= anzahlCols || newY < 0 || newY >= anzahlRows) return false;
            // check ob "XMAS"
            if (charArray[newX][newY] != targetWord.charAt(step)) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException{
        inputStringArray = Files.readAllLines(Path.of("file.txt"));
        formatInput();
        int counter =0;

        int anzahlRows = charArray.length;
        int anzahlCols = charArray[0].length;

        char cTarget = 'X';
        //durch array gehen 
        for(int row=0; row<anzahlRows; row++){
            for(int col=0; col<anzahlCols; col++){
                //ein X finden 
                if(charArray[row][col] == cTarget){
                    for(int[] dir : directions){
                        //für jede richtung prüfen, ob MAS steht 
                        if(checkAllDirections(dir, row, col, dir[0], dir[1], anzahlRows, anzahlCols)){     //anzahlRow und anzahlCols kann ich noch durch SIZE ersetzten ...
                            counter++;
                        }

                    }
                }
            }
        }
        System.out.println(counter);
    }
}
