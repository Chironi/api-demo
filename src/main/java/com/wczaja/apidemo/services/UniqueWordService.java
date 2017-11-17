package com.wczaja.apidemo.services;

import com.wczaja.apidemo.resources.UniqueWord;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UniqueWordService {

    public List<UniqueWord> getUniqueWordsFromParagraph(String paragraph) {
        Map<String, Integer> uniqueWordMap = new HashMap<>();

        String[] words = paragraph.split("[\\W]+");

        for (String word : words) {
            if (uniqueWordMap.containsKey(word)) {
                uniqueWordMap.put(word, uniqueWordMap.get(word)+1);
            } else {
                uniqueWordMap.put(word, 1);
            }
        }

        List<UniqueWord> uniqueWords = uniqueWordMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(item -> new UniqueWord(item.getKey(), item.getValue()))
                .collect(Collectors.toList());

        return uniqueWords;
    }
}
