package com.c0d1red;

public class PureArrayCalculator {

    public static int calculate(int[] array, int idx) {
        return idx <= 0 ? 0 : (calculate(array, idx - 1) + array[idx - 1]);
    }

}
