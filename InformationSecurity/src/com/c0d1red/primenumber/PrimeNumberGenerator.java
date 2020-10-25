package com.c0d1red.primenumber;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeNumberGenerator {

    public static int generate(int min, int max) {
        Random random = new Random();
        List<Integer> primeNumbers = generatePrimes(min, max);
        int randomPosition = random.nextInt(primeNumbers.size() - 1);
        return primeNumbers.get(randomPosition);
    }

    public static int generateWithExclude(int min, int max, int excluded) {
        Random random = new Random();
        List<Integer> primeNumbers = generatePrimes(min, max);
        List<Integer> primeNumbersWithExcluded = excludeFromList(primeNumbers, excluded);
        int randomPosition = random.nextInt(primeNumbersWithExcluded.size() - 1);
        return primeNumbersWithExcluded.get(randomPosition);
    }

    public static int generateWithDivideCheck(int min, int max, int dividend) {
        Random random = new Random();
        List<Integer> primeNumbers =
                generatePrimes(min, max)
                        .stream()
                        .filter(x -> !isDivider(dividend, x))
                        .collect(Collectors.toList());
        int randomPosition = random.nextInt(primeNumbers.size() - 1);
        return primeNumbers.get(randomPosition);
    }

    private static List<Integer> generatePrimes(int min, int max) {
        return IntStream.rangeClosed(min, max)
                .filter(PrimeNumberGenerator::isPrime).boxed()
                .collect(Collectors.toList());
    }

    private static List<Integer> excludeFromList(List<Integer> numbers, int excluded) {
        return numbers
                .stream()
                .filter(x -> x != excluded)
                .collect(Collectors.toList());
    }

    private static boolean isPrime(int number) {
        for (int currentNumber = 2; currentNumber < number; currentNumber++) {
            if (number % currentNumber == 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDivider(int dividend, int divider) {
        return dividend % divider == 0;
    }

}
