package com.genix.wordmemoriser.Menu.Play.Game;

import android.content.Context;
import android.database.Cursor;

import com.genix.wordmemoriser.Databases.WordsDatabase;

import java.util.ArrayList;
import java.util.Observable;
import java.util.StringTokenizer;

/**
 * Created by Genix on 2018-01-17.
 */

public class GameModel {

    private Context appContext;
    private String selectedSetName;
    private WordsDatabase wdb;
    private ArrayList<Words> wordsArray;
    protected ArrayList<String> word2Tokens;
    private int currentPositionInWordsArray;
    private int goodAnswers;
    private int allAnswers;


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
            wordsArray.add(new Words(tempWord1, tempWord2, spacesInWord2));
        }

        StringTokenizer stringTokenizer = new StringTokenizer(wordsArray.get(0).getWord2());
        while (stringTokenizer.hasMoreTokens()){
            word2Tokens.add(stringTokenizer.nextToken());
        }
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

    protected String getCurrentWord1(){
        return wordsArray.get(currentPositionInWordsArray).getWord1();
    }

    protected int getCurrentNumberOfSpaces(){
        return wordsArray.get(currentPositionInWordsArray).getSpacesInWord2() + 1;
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
