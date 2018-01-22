package com.genix.wordmemoriser.Activities.Play.Game;

public class WordsForGame {

    private String word1;
    private String word2;
    private int spacesInWord2;

    WordsForGame(String word1, String word2, int spacesInWord2){
        this.word1 = word1;
        this.word2 = word2;
        this.spacesInWord2 = spacesInWord2;
    }

    protected String getWord1(){
        return word1;
    }

    protected String getWord2(){
        return word2;
    }

    protected int getSpacesInWord2() {
        return spacesInWord2;
    }
}
