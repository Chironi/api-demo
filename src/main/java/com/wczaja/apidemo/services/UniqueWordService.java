package com.wczaja.apidemo.services;

import com.wczaja.apidemo.resources.UniqueWordResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    public List<UniqueWordResource> getUniqueWordsFromParagraph(String paragraph) {
        if (StringUtils.isEmpty(paragraph)) {
            return new ArrayList<>();
        }

        Map<String, Integer> uniqueWordMap = new HashMap<>();

        // Splits paragraph into String Array using regex to split on all non-letter chars
        String[] words = paragraph.split("[\\W]+");

        for (String word : words) {
            // Lower case words so they can eventually be sorted alphabetically
            word = word.toLowerCase();
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
     * of UniqueWordResource objects
     *
     * @param uniqueWordMap Map of unique words as keys, and their counts as the values
     * @return List of UniqueWordResource objects
     */
    private List<UniqueWordResource> getUniqueWordsFromMap(Map<String, Integer> uniqueWordMap) {
        return uniqueWordMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(item -> new UniqueWordResource(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
    }
}
