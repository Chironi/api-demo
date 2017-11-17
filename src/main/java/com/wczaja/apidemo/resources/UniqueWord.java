package com.wczaja.apidemo.resources;

public class UniqueWord {
    private final String word;
    private final int count;

    public UniqueWord(String word, int count) {
        this.word = word;
        this.count = count;
    }
    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }
}
