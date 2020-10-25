package com.c0d1red.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSAMapper {
    private static final String RU_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String EN_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION_ALPHABET = ".,;:!?[]- ";
    private static final String ALPHABET = RU_ALPHABET + EN_ALPHABET + DIGITS + PUNCTUATION_ALPHABET;
    private static final String SEPARATOR = ".";

    public static int getAlphabetLength() {
        return ALPHABET.length();
    }

    public List<BigInteger> termToNumbers(String term) {
        List<BigInteger> numbers = new ArrayList<>();
        char[] letters = term.toCharArray();
        for (char letter : letters) {
            numbers.add(BigInteger.valueOf(ALPHABET.indexOf(letter)));
        }
        return numbers;
    }

    public String numbersToTerm(List<BigInteger> numbers) {
        StringBuilder word = new StringBuilder();
        for (BigInteger number : numbers) {
            int position = number.intValue();
            if (number.compareTo(BigInteger.valueOf(getAlphabetLength())) >= 0) {
                position = number.mod(BigInteger.valueOf(getAlphabetLength())).intValue();
            }
            word.append(ALPHABET.charAt(position));
        }
        return word.toString();
    }

    public String numbersToString(List<BigInteger> numbers) {
        StringBuilder word = new StringBuilder();
        for (BigInteger number : numbers) {
            word.append(number).append(SEPARATOR);
        }
        word.deleteCharAt(word.length() - 1);
        return word.toString();
    }

    public List<BigInteger> stringToNumbers(String term) {
        List<BigInteger> numbers = new ArrayList<>();
        String[] letters = term.split("\\" + SEPARATOR);
        for (String letter : letters) {
            numbers.add(new BigInteger(letter));
        }
        return numbers;
    }

}
