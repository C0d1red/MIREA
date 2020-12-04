package com.c0d1red.caesar;

import com.c0d1red.util.AlphabetUtils;

import java.util.*;

import static com.c0d1red.util.BasicUtils.*;

public class Caesar {
    private final AlphabetUtils alphabetUtils;
    private int key;
    private int revertKey;

    public Caesar() {
        key = getRandomInt();
        alphabetUtils = new AlphabetUtils();
    }

    public Caesar(int key) {
        alphabetUtils = new AlphabetUtils();
        this.key = key;
        this.revertKey = key * -1;
    }

    public void setKey(int key) {
        this.key = key;
        this.revertKey = key * -1;
    }

    public int getKey() {
        return key;
    }

    public String encrypt(String term) {
        String[] symbolArray = term.split("");
        return shiftCharacters(symbolArray, key);
    }

    public String decrypt(String encryptedTerm) {
        String[] symbolArray = encryptedTerm.split("");
        return shiftCharacters(symbolArray, revertKey);
    }

    public String decryptBySoloFrequency(String encryptedTerm,
                                         Map<String, Float> decryptedFrequencyMap,
                                         Map<String, Float> encryptedFrequencyMap) {
        Map<String, String> encryptDecryptMap = createEncryptDecryptMapFrom(decryptedFrequencyMap, encryptedFrequencyMap);
        return replaceSoloCharactersByMap(encryptedTerm, encryptDecryptMap);
    }

    public String decryptByBigramFrequency(String encryptedTerm,
                                           Map<String, Float> decryptedFrequencyMap,
                                           Map<String, Float> encryptedFrequencyMap) {
        Map<String, String> encryptDecryptMap = createEncryptDecryptMapFrom(decryptedFrequencyMap, encryptedFrequencyMap);
        return replaceBigramCharactersByMap(encryptedTerm, encryptDecryptMap);
    }

    private Map<String, String> createEncryptDecryptMapFrom(Map<String, Float> decryptedFrequencyMap,
                                                            Map<String, Float> encryptedFrequencyMap){
        List<String> sortedEncryptedFrequencyList = getKeysSortedByValueFrom(encryptedFrequencyMap);
        List<String> sortedDecryptedFrequencyList = getKeysSortedByValueFrom(decryptedFrequencyMap);
        return fillEncryptDecryptMap(sortedEncryptedFrequencyList, sortedDecryptedFrequencyList);
    }

    private Map<String, String> fillEncryptDecryptMap(List<String> encryptedList, List<String> decryptedList) {
        Map<String, String> encryptDecryptMap = new HashMap<>();
        for (int i = 0; i < encryptedList.size(); i++) {
            String encrypted = encryptedList.get(i);
            String decrypted = decryptedList.get(i);
            encryptDecryptMap.put(encrypted, decrypted);
        }
        return encryptDecryptMap;
    }

    private String replaceSoloCharactersByMap(String encryptedTerm, Map<String, String> encryptDecryptMap){
        String[] decryptedCharacters = encryptedTerm.split("");
        for (int i = 0; i < decryptedCharacters.length; i++) {
            decryptedCharacters[i] = encryptDecryptMap.get(decryptedCharacters[i]);
        }
        return getStringFromArray(decryptedCharacters);
    }

    private String replaceBigramCharactersByMap(String encryptedTerm, Map<String, String> encryptDecryptMap){
        String decryptedTerm = encryptedTerm;

        int index = 0;
        while (index < decryptedTerm.length()) {
            if(index + 1 < decryptedTerm.length()) {
                String pair = decryptedTerm.substring(index, index + PAIR_COUNT);
                if (index == 0){
                    decryptedTerm = encryptDecryptMap.get(pair) + decryptedTerm.substring(index + PAIR_COUNT);
                } else {
                    decryptedTerm = decryptedTerm.substring(0, index - 1) + encryptDecryptMap.get(pair) + decryptedTerm.substring(index + PAIR_COUNT - 1);
                }
            }
            index += PAIR_COUNT;
        }

        return decryptedTerm;
    }

    private String shiftCharacters(String[] charactersArray, int offset) {
        for (int i = 0; i < charactersArray.length; i++) {
            String character = charactersArray[i];
            charactersArray[i] = alphabetUtils.getCharacterWithOffset(character, offset);
        }
        return getStringFromArray(charactersArray);
    }

}
