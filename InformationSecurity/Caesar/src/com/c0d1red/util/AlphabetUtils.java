package com.c0d1red.util;

public class AlphabetUtils {
    private static final String RU_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String EN_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION_ALPHABET = ".,;:!?[]- ";
    private static final String ALPHABET = RU_ALPHABET + EN_ALPHABET + DIGITS + PUNCTUATION_ALPHABET;

    public String getCharacterWithOffset(String character, int offset) {
        int charIdx = ALPHABET.indexOf(character);
        int offsetCharIdx = formatIndexToAlphabetSize(charIdx + offset);
        return String.valueOf(ALPHABET.charAt(offsetCharIdx));
    }

    private int formatIndexToAlphabetSize(int index){
        if(index >= ALPHABET.length()){
            index %= ALPHABET.length();
        } else if (index < 0){
            index = ALPHABET.length() + index;
            index %= ALPHABET.length();
        }
        return index;
    }

}
