package Day6To12;
import java.util.ArrayList;
import java.util.List;


public class Day11P1 {
    static List<Long> inputList = new ArrayList<>(List.of(41078L, 18L, 7L, 0L, 4785508L, 535256L, 8154L, 447L));

    public static void alg(){
        int i = 0;
        while (i < inputList.size()) {
            Long stone = inputList.get(i);

            if (stone == 0) {
                inputList.set(i, 1L);
            } else if (String.valueOf(stone).length() % 2 == 0) { 
                String stoneAsString = stone.toString();
                int halfIndex = stoneAsString.length() / 2;

                String leftSub = stoneAsString.substring(0, halfIndex);
                String rightSub = stoneAsString.substring(halfIndex);

                long leftStone = Long.parseLong(leftSub);
                long rightStone = Long.parseLong(rightSub);

                inputList.set(i, leftStone);
                inputList.add(i + 1, rightStone);
                i++; // eingefügten rechten Stein überspringen 
            } else {
                inputList.set(i, stone * 2024);
            }

            i++; 
        }

        //System.out.println(inputList);
    
    }
    public static void main(String[] args) {

        boolean flag = true;
        int i=0;
        while(flag){
            alg();
            i++;
            if(i==75){
                flag=false;
                System.out.println("number of stones " + inputList.size());
            }
        }

    }

    
}
