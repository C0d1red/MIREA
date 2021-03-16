package com.c0d1red;

import java.util.Arrays;

import static com.c0d1red.PureArrayCalculator.calculate;

public class Main {

    public static void main(String[] args) {
        testPureArrayCalculator();
    }

    private static void testPureArrayCalculator() {
        int[] testArray = {0, 1, 2, 3, 4, 5, 6};
        int result = calculate(testArray, testArray.length);
        System.out.format("Result of calculating array %s: \n%s", Arrays.toString(testArray), result);
    }
}
