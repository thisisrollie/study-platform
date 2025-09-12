package com.rolliedev.service.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomUtil {

    private static final Random RANDOM = new Random();

    public static int nextInt(int maxExclusive) {
        return RANDOM.nextInt(maxExclusive);
    }

    public static int nextIntFromOne(int maxInclusive) {
        return RANDOM.nextInt(maxInclusive) + 1;
    }
}
