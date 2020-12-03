package com.c0d1red.util;

import java.util.*;
import java.util.stream.Collectors;

public class BasicUtils {
    public static final int PAIR_COUNT = 2;

    public static List<String> getKeysSortedByValueFrom(Map<String, Float> unsortedMap){
        Set<String> sortedSet = unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
                .keySet();
        return new ArrayList<>(sortedSet);
    }

    public static String getStringFromArray(String[] array){
        return String.join("", array);
    }

    public static String[] getStringArrayFromList(List<String> list){
        return list.toArray(new String[0]);
    }

}
