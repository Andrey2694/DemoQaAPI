package helpers;

import java.util.Random;

public class RandomNumber {
    public static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
