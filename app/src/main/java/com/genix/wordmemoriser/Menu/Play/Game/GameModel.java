package com.genix.wordmemoriser.Menu.Play.Game;

import android.content.Context;
import android.database.Cursor;

import com.genix.wordmemoriser.Databases.WordsDatabase;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Genix on 2018-01-17.
 */

public class GameModel extends Observable{

    private Context appContext;
    private String selectedSetName;
    private WordsDatabase wdb;
    private ArrayList<Words> wordsArray;
    private int currentPositionInWordsArray;
    private int goodAnswers;
    private int allAnswers;


    GameModel(Context context, String selectedSetName){
        this.selectedSetName = selectedSetName;
        appContext = context;
        wdb = new WordsDatabase(appContext, selectedSetName);

        wordsArray = new ArrayList<>(1);

        currentPositionInWordsArray = 0;
        goodAnswers = 0;
        allAnswers = 0;

        initializeWordsArray();
    }

    private void initializeWordsArray(){
        Cursor data = wdb.getDataFromTable(selectedSetName);

        while (data.moveToNext()) {
            String tempWord1 = data.getString(1);
            String tempWord2 = data.getString(2);
            int spacesInWord2 = countSpacesInWord(tempWord2);
            wordsArray.add(new Words(tempWord1, tempWord2, spacesInWord2));
        }
    }

    private int countSpacesInWord(String word){
        int toReturn = 0;

        for(int i = 0; i < word.length(); i++){
            char currentChar = word.charAt(i);
            if(currentChar == ' ' || currentChar == '\n' || currentChar == '\t')
                toReturn++;
        }

        return toReturn;
    }

}
