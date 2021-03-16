package com.c0d1red;

import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        testPureArrayLetterProcessor();
    }

    private static void testPureArrayLetterProcessor() {
        String[] arr = {"cccccccc", "ddd", "bbbbbbbbbb", "b", "aaa", "cccc", "c"};
        HashMap<Character, Integer> result = new HashMap<>();
        PureArrayLetterProcessor pureArrayLetterProcessor = new PureArrayLetterProcessor();
        result = pureArrayLetterProcessor.process(arr, arr.length, result);
        System.out.format("Result for array %s: \n%s", Arrays.toString(arr), result);
    }
}
