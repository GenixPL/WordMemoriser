package com.genix.wordmemoriser.Activities.Play.Game;

import android.content.Context;
import android.database.Cursor;

import com.genix.wordmemoriser.Databases.WordsDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Genix on 2018-01-17.
 */

public class GameModel {

    private Context appContext;
    private String selectedSetName;
    private WordsDatabase wdb;
    private ArrayList<WordsForGame> wordsArray;
    protected ArrayList<String> word2Tokens;
    private int currentPositionInWordsArray;
    private int goodAnswers;
    private int allAnswers;
    private boolean[] isMarkedCorrectness;


    GameModel(Context context, String selectedSetName) throws IndexOutOfBoundsException{
        this.selectedSetName = selectedSetName;
        appContext = context;
        wdb = new WordsDatabase(appContext, selectedSetName);

        wordsArray = new ArrayList<>(1);
        word2Tokens = new ArrayList<>(1);

        currentPositionInWordsArray = 0;
        goodAnswers = 0;
        allAnswers = 0;

        initializeWordsArray();
        if(wordsArray.size() == 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void initializeWordsArray(){
        Cursor data = wdb.getDataFromTable(selectedSetName);

        while (data.moveToNext()) {
            String tempWord1 = data.getString(1);
            String tempWord2 = data.getString(2);
            int spacesInWord2 = countWhiteSpacesInWord(tempWord2);
            wordsArray.add(new WordsForGame(tempWord1, tempWord2, spacesInWord2));
        }

        StringTokenizer stringTokenizer = new StringTokenizer(wordsArray.get(0).getWord2());
        while (stringTokenizer.hasMoreTokens()){
            word2Tokens.add(stringTokenizer.nextToken());
        }

        isMarkedCorrectness = new boolean[wordsArray.size()];
    }

    private int countWhiteSpacesInWord(String word){
        int toReturn = 0;

        for(int i = 0; i < word.length(); i++){
            char currentChar = word.charAt(i);
            if(currentChar == ' ')
                toReturn++;
        }

        return toReturn;
    }

    protected void setCorrectness(boolean isError){
        if(isMarkedCorrectness[currentPositionInWordsArray]){
            return;
        } else {
            if(isError){
                allAnswers++;
                isMarkedCorrectness[currentPositionInWordsArray] = true;
            } else {
                allAnswers++;
                goodAnswers++;
                isMarkedCorrectness[currentPositionInWordsArray] = true;
            }
        }
    }

    protected double getCorrectness(){
        return (goodAnswers * 100)/ wordsArray.size();
    }

    protected String getCurrentWord1(){
        return wordsArray.get(currentPositionInWordsArray).getWord1();
    }

    protected String getCurrentWord2(){
        String toReturn = "";

        for(int i = 0; i < word2Tokens.size(); i++){
            if(i + 1 < word2Tokens.size()) {
                toReturn += word2Tokens.get(i) + " ";
            } else {
                toReturn += word2Tokens.get(i);
            }
        }

        return toReturn;
    }

    protected void takeAnotherWords() throws IndexOutOfBoundsException{
        if(currentPositionInWordsArray + 1 > wordsArray.size()) {
            throw new IndexOutOfBoundsException();
        }

        currentPositionInWordsArray++;

        StringTokenizer stringTokenizer = new StringTokenizer(wordsArray.get(currentPositionInWordsArray).getWord2());
        word2Tokens.clear();
        while (stringTokenizer.hasMoreTokens()){
            word2Tokens.add(stringTokenizer.nextToken());
        }
    }
}
