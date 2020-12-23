package com.c0d1red.util;

import java.util.Random;

public class StringUtil {
    private static final int RANDOM_STRING_LENGTH = 10;
    private static final int LETTER_A = 97;
    private static final int LETTER_Z = 122;

    public static String generateRandomString() {
        Random random = new Random();
        return random.ints(LETTER_A, LETTER_Z + 1)
                .limit(RANDOM_STRING_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
