package hu.latzkoo.raft;

import java.util.Random;

public class Util {

    public static int getRandomNumber(int max) {
        return new Random().nextInt(max);
    }

}
