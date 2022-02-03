package me.itsadrift.adrifthardcore.utils;

import java.util.Random;

public class RandArray {

    private static Random rand = new Random();

    public static <T> T randomFrom(T... items) {
        return items[rand.nextInt(items.length)];
    }

}
