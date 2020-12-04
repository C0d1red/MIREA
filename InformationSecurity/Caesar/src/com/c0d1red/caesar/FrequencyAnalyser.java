package com.c0d1red.caesar;

import java.util.*;

import static com.c0d1red.util.BasicUtils.PAIR_COUNT;
import static com.c0d1red.util.BasicUtils.getStringArrayFromList;

public class FrequencyAnalyser {
    private static final int QUANTITY_ONE = 1;

    public Map<String, Float> makeSoloAnalysis(String term){
        String[] characterArray = term.split("");
        Map<String, Integer> quantityMap = countEqualsElements(characterArray);
        return calculateFrequency(quantityMap, characterArray.length);
    }

    public Map<String, Float> makeBigramAnalysis(String term){
        String[] characterArray = splitByPairs(term);
        Map<String, Integer> quantityMap = countEqualsElements(characterArray);
        return calculateFrequency(quantityMap, characterArray.length);
    }

    private String[] splitByPairs(String term){
        List<String> strings = new ArrayList<>();
        int index = 0;
        while (index < term.length()) {
            if(index + 1 < term.length()){
                strings.add(term.substring(index, index + PAIR_COUNT));
            }
            index += PAIR_COUNT;
        }
        return getStringArrayFromList(strings);
    }

    private Map<String, Integer> countEqualsElements(String[] characters) {
        Map<String, Integer> quantityMap = new HashMap<>();
        for (String character: characters) {
            if(quantityMap.containsKey(character)) {
                int quantity = quantityMap.get(character);
                quantityMap.put(character, ++quantity);
            } else {
                quantityMap.put(character, QUANTITY_ONE);
            }
        }
        return quantityMap;
    }

    private Map<String, Float> calculateFrequency(Map<String, Integer> quantityMap, int totalElementsQuantity) {
        Map<String, Float> frequencyMap = new HashMap<>();
        for(Map.Entry<String, Integer> characterQuantity : quantityMap.entrySet()) {
            String character = characterQuantity.getKey();
            int quantity = characterQuantity.getValue();
            float frequency = quantity / (float) totalElementsQuantity;
            frequencyMap.put(character, frequency);
        }
        return frequencyMap;
    }

}
