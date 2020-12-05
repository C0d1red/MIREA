package com.c0d1red;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberGenerator {
    private static final int MAX_NUMBER = 10000;
    private static final int PRIME_START_NUMBER = 2;

    public static int generateRandomNatural() {
        List<Integer> naturalNumbers = generateNaturals();
        int randomPosition = getRandomNumber(naturalNumbers.size());
        return naturalNumbers.get(randomPosition);
    }

    public static int generateRandomPrime() {
        List<Integer> primeNumbers = generatePrimes();
        int randomPosition = getRandomNumber(primeNumbers.size());
        return primeNumbers.get(randomPosition);
    }

    public static int generatePrimitiveRootFor(int number) {
        for (int primitiveCandidate = 2; primitiveCandidate < number; primitiveCandidate++)
            if (isPrimitiveRoot(primitiveCandidate, number))
                return primitiveCandidate;
        return 0;
    }

    private static boolean isPrimitiveRoot(int candidate, int number) {
        List<Integer> multiplicativeSequenceList = new ArrayList<>();
        int last = 1;
        for (int i = 0; i < number - 1; i++) {
            last = (last * candidate) % number;
            if (multiplicativeSequenceList.contains(last))
                return false;
            multiplicativeSequenceList.add(last);
        }
        return true;
    }

    private static List<Integer> generateNaturals() {
        return IntStream.rangeClosed(0, MAX_NUMBER)
                .boxed()
                .collect(Collectors.toList());
    }

    private static List<Integer> generatePrimes() {
        return IntStream.rangeClosed(0, MAX_NUMBER)
                .filter(NumberGenerator::isPrime).boxed()
                .collect(Collectors.toList());
    }

    private static int getRandomNumber(int max){
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

