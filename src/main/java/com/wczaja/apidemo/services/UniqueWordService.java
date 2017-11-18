package com.wczaja.apidemo.services;

import com.wczaja.apidemo.resources.UniqueWord;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class UniqueWordService {

    /**
     *
     * @param paragraph
     * @return
     */
    public List<UniqueWord> getUniqueWordsFromParagraph(String paragraph) {
        Map<String, Integer> uniqueWordMap = new HashMap<>();

        // Splits paragraph into String Array using regex to split on all non-letter chars
        String[] words = paragraph.split("[\\W]+");

        for (String word : words) {
            if (uniqueWordMap.containsKey(word)) {
                // If uniqueWordMap already contains word, just increment the count
                uniqueWordMap.put(word, uniqueWordMap.get(word) + 1);
            } else {
                // Else it's a new word, initialize count as 1
                uniqueWordMap.put(word, 1);
            }
        }

        return getUniqueWordsFromMap(uniqueWordMap);
    }

    /**
     * Snazzy Java 8 Lambda which sorts the uniqueWordMap by the keys, and maps to a List
     * of UniqueWord objects
     *
     * @param uniqueWordMap Map of unique words as keys, and their counts as the values
     * @return List of UniqueWord objects
     */
    private List<UniqueWord> getUniqueWordsFromMap(Map<String, Integer> uniqueWordMap) {
        return uniqueWordMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(item -> new UniqueWord(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
    }
}
