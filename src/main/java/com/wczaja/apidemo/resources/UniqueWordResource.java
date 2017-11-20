package com.wczaja.apidemo.resources;

public class UniqueWordResource {
    private final String word;
    private final int count;

    public UniqueWordResource(String word, int count) {
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
