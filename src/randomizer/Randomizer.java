package randomizer;

import java.util.Random;

public class Randomizer {

    public static int randomNumberGenerator(int max) {
        return new Random().nextInt(max);
    }

}
