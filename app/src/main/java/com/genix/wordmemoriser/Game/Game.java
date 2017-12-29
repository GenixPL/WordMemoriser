package com.genix.wordmemoriser.Game;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.genix.wordmemoriser.Database.WordsDatabase;
import com.genix.wordmemoriser.R;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private String selectedSetName;

    private TextView word1_Text;
    private EditText word2_Text;
    WordsDatabase wdb;

    private ArrayList<Words> wordsArray;
    private int position;
    private int goodAnswers;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        wordsArray = new ArrayList<>(0);

        word1_Text = findViewById(R.id.word1_text);
        word2_Text = findViewById(R.id.word2_editText);

        wdb = new WordsDatabase(this, selectedSetName);

        Intent receivedIntent = getIntent();
        selectedSetName = receivedIntent.getStringExtra("setName");

        Cursor data = wdb.getDataFromTable(selectedSetName);

        while (data.moveToNext()) {
            wordsArray.add(new Words(data.getString(1), data.getString(2)));
        }

        position = 0;
        goodAnswers = 0;
        word1_Text.setText(wordsArray.get(position).getWord1());
        word2_Text.setText("");
    }

    public void checkWords_But(View view) {
        String properTranslation = wordsArray.get(position).getWord2();
        String currentText = word2_Text.getText().toString();

        if(checkWords(properTranslation, currentText))
            toastMessage("Good :)");
        else
            toastMessage("Bad :(");
    }

    public void goToNext(View view) {

        if(checkWords(wordsArray.get(position).getWord2(), word2_Text.getText().toString()))
            goodAnswers++;

        if((position + 1) == wordsArray.size()) {
            double score = (goodAnswers * 100)/ wordsArray.size();
            toastMessage("You scored: " + score + "%");
            finish();
        } else {
            position++;
            word1_Text.setText(wordsArray.get(position).getWord1());
            word2_Text.setText("");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    String removeWhiteSpaces(String word){
        String toReturn = "";

        for(int i = 0; i < word.length(); i++){

            if((i + 1) == word.length() && isWhite(word.charAt(i)))
                    continue;

            toReturn += word.charAt(i);
        }

        return toReturn;
    }

    boolean checkWords(String properTranslation, String currentText){
        currentText = removeWhiteSpaces(currentText);

        if(properTranslation.equals(currentText))
            return true;
        else
            return false;
    }

    boolean isWhite(char sign){
        if(sign == ' ' || sign == '\n' || sign == '\t')
            return true;
        else
            return false;
    }
}
