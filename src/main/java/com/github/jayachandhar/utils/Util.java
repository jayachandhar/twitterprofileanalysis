package com.github.jayachandhar.utils;

import java.util.*;

public class Util {
    public final static Set<String> WORDS_TO_AVOID = new HashSet<>(Arrays.asList("to", "the", "and", "a", "is", "for", "of", "with", "in", "you", "at", "by", "are", "it", "on", "this", "i", "that", "what", "from", "not", "our", "as", "the", "all", "they", "we", "your", "see", "have", "no", "so", "us", "can", "will", "get", "would", "an", "be", "how", "close", "which", "very", "me", "my", "make", "many", "up", "has", "know", "every", "was", "still", "out", "been", "got", "need", "take", "just", "order", "other", "all", "use", "new", "my", "or", "any", "must", "so", "there", "this", "next", "come", "between", "help", "tell", "only", "their", "win", "these", "happen", "sure", "here", "a", "am", "did", "do", "down", "never", "she", "he", "helps", "back", "go", "although", "happens", "taking", "soon", "if", "made", "nothing", "even", "too", "may", "could", "off", "lets", "find", "than", "have", "and", "any", "today", "no", "until", "on", "about", "myself", "each", "into", "they", "but", "to", "given", "not", "now", "doing", "lot", "across", "his", "should", "most", "who", "here", "why", "will", "only", "day", "let", "like", "more"));

    public static Map sortMap(Map unsortedMap) {
        List<Map.Entry<Object, Object>> list = new ArrayList(unsortedMap.entrySet());
        Map<Object, Object> sortedMap = new LinkedHashMap<>();
        Collections.sort(list, new Comparator<Map.Entry<Object, Object>>() {
            public int compare(Map.Entry<Object, Object> o1, Map.Entry<Object, Object> o2) {
                if (o1.getValue().getClass() == Integer.class)
                    return ((Integer) o2.getValue()).compareTo(((Integer) o1.getValue()));
                else
                    return ((String) o2.getValue()).compareTo(((String) o1.getValue()));

            }
        });
        for (Map.Entry<Object, Object> objectEntry : list) {
            sortedMap.put(objectEntry.getKey(), objectEntry.getValue());
        }

        return sortedMap;
    }
}