package com.c0d1red.util;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberUtil {
    private static final int MAX_NATURAL_NUMBER = 10000;
    private static final int PRIME_START_NUMBER = 2;

    public static int generateRandomNum() {
        return getRandomNumber(Integer.MAX_VALUE);
    }

    public static int generateRandomPrime() {
        List<Integer> primeNumbers = generatePrimes();
        int randomPosition = getRandomNumber(primeNumbers.size());
        return primeNumbers.get(randomPosition);
    }

    private static List<Integer> generatePrimes() {
        return IntStream.rangeClosed(0, MAX_NATURAL_NUMBER)
                .filter(NumberUtil::isPrime).boxed()
                .collect(Collectors.toList());
    }

    private static int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max - 1);
    }

    private static boolean isPrime(int number) {
        for (int currentNumber = PRIME_START_NUMBER; currentNumber < number; currentNumber++) {
            if (number % currentNumber == 0) {
                return false;
            }
        }
        return true;
    }

}

