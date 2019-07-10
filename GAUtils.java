import java.util.*;

public class GAUtils {
    public static double getRandomDoubleInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static boolean getRandomBoolean(){
        return new Random().nextBoolean();
    }

    public static double getRandomMutation() {
        return new Random().nextDouble();
    }

    public static boolean[] getRandomBoolArray(int size) {
        boolean[] boolArray = new boolean[size];
        for(int i = 0 ; i < boolArray.length ; i++){
            boolArray[i]= (Math.random() > 0.5);
        }
        return boolArray;
    }

    public static boolean[] getTrueBoolArray(int size) {
        boolean[] boolArray = new boolean[size];
        for(int i = 0 ; i < boolArray.length ; i++){
            boolArray[i]= true;
        }
        return boolArray;
    }

    public static boolean[] getFalseBoolArray(int size) {
        boolean[] boolArray = new boolean[size];
        for(int i = 0 ; i < boolArray.length ; i++){
            boolArray[i]= false;
        }
        return boolArray;
    }
}
