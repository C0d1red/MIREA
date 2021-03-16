package com.c0d1red;

import java.util.HashMap;

public class PureArrayLetterProcessor {

    public HashMap<Character, Integer> process(String[] array, int idx, HashMap<Character, Integer> identifierStrength) {
        if(idx <= 0) {
            return identifierStrength;
        }

        String arrayElement = array[idx - 1];
        int arrayElementStrength = arrayElement.length();
        Character elementIdentifier = arrayElement.charAt(0);

        if (identifierStrength.containsKey(elementIdentifier)) {
            int mapElementStrength = identifierStrength.get(elementIdentifier);
            if (mapElementStrength < arrayElementStrength) {
                identifierStrength.put(elementIdentifier, arrayElementStrength);
            }
        } else {
            identifierStrength.put(elementIdentifier, arrayElementStrength);
        }
        return process(array, idx - 1, identifierStrength);
    }
}
