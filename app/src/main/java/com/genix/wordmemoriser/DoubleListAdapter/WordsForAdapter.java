package com.genix.wordmemoriser.DoubleListAdapter;

/**
 * Created by genix on 1/19/2018.
 */

public class WordsForAdapter {
    private int id;
    private String word1;
    private String word2;

    public WordsForAdapter(int id, String word1, String word2) {
        this.id = id;
        this.word1 = word1;
        this.word2 = word2;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }
}
