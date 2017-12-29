package com.genix.wordmemoriser.Game;

import java.util.ArrayList;

public class Words {

    private String word1;
    private String word2;

    Words(String word1, String word2){
        this.word1 = word1;
        this.word2 = word2;
    }

    protected String getWord1(){
        return word1;
    }

    protected String getWord2(){
        return word2;
    }
}
