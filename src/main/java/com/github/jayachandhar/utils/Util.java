package com.github.jayachandhar.utils;

import java.util.*;

public class Util {
    public final static Set<String> WORDS_TO_AVOID = new HashSet<>(Arrays.asList("to", "the", "and", "a", "is", "for", "of", "with", "in", "you", "at", "by", "are", "it", "on", "this", "i", "that", "what", "from", "not", "our", "as", "the", "all", "they", "we", "your", "see", "have", "no", "so", "us", "can", "will", "get", "would", "an", "be", "how", "close", "which", "very", "me", "my", "make", "many", "up", "has", "know", "every", "was", "still", "out", "been", "got", "need", "take", "just", "order", "other", "all", "use", "new", "my", "or", "any", "must", "so", "there", "this", "next", "come", "between", "help", "tell", "only", "their", "win", "these", "happen", "sure", "here", "a", "am", "did", "do", "down", "never", "she", "he", "helps", "back", "go", "although", "happens", "taking", "soon", "if", "made", "nothing", "even", "too", "may", "could", "off", "lets", "find", "than", "have", "and", "any", "today", "no", "until", "on", "about", "myself", "each", "into", "they", "but", "to", "given", "not", "now", "doing", "lot", "across", "his", "should", "most", "who", "here", "why", "will", "only", "day", "let", "like", "more"));

    public static Map<String, Integer> sortMap(Map<String, Integer> unsortedMap, int minValue) {

        List<Map.Entry<String, Integer>> list = new ArrayList(unsortedMap.entrySet());
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (Map.Entry<String, Integer> objectEntry : list) {
            if (objectEntry.getValue() > minValue) {
                sortedMap.put(objectEntry.getKey(), objectEntry.getValue());
            }
        }

        return sortedMap;
    }
}