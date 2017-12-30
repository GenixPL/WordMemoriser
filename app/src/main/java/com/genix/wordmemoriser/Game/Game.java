package com.genix.wordmemoriser.Game;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.genix.wordmemoriser.Database.WordsDatabase;
import com.genix.wordmemoriser.R;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private ArrayList<Words> wordsArray;
    private int positionOfWords;
    private int goodAnswers;
    private ArrayList<EditText> editTextsArray;
    private String tempWord1;
    private String tempWord2;

    private String selectedSetName;
    private WordsDatabase wdb;

    private TextView word1_Text;
    private LinearLayout horizontal_Layout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        wordsArray = new ArrayList<>(0);
        positionOfWords = 0;
        goodAnswers = 0;
        editTextsArray = new ArrayList<>(0);
        tempWord1 = "";
        tempWord2 = "";

        Intent receivedIntent = getIntent();
        selectedSetName = receivedIntent.getStringExtra("setName");
        wdb = new WordsDatabase(this, selectedSetName);

        word1_Text = findViewById(R.id.word1_text);
        horizontal_Layout = findViewById(R.id.horizontal_layout);

        initializeWordsArray();
        if (wordsArray.size() == 0){
            toastMessage("You can't play with empty set");
            finish();
            return;
        }
        setWords();
        createScreen();
    }

    void initializeWordsArray(){
        Cursor data = wdb.getDataFromTable(selectedSetName);

        while (data.moveToNext()) {
            wordsArray.add(new Words(data.getString(1), data.getString(2)));
        }
    }

    void setWords(){
        tempWord1 = wordsArray.get(positionOfWords).getWord1();
        tempWord2 = wordsArray.get(positionOfWords).getWord2();

        tempWord2 = removeWhiteSpaces(tempWord2);
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

    boolean isWhite(char sign){
        if(sign == ' ' || sign == '\n' || sign == '\t')
            return true;
        else
            return false;
    }

    void createScreen(){
        boolean isLast = false;
        for(int i = 0; i < tempWord2.length(); i++){
            if((i + 1) == tempWord2.length())
                isLast = true;

            EditText tempText = createEditText(isLast);
            editTextsArray.add(tempText);
            horizontal_Layout.addView(tempText);
        }

        word1_Text.setText(tempWord1);
    }

    EditText createEditText(boolean isLast){
        EditText toReturn = new EditText(this);

        toReturn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        toReturn.setGravity(Gravity.CENTER);
        toReturn.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        toReturn.setInputType(InputType.TYPE_CLASS_TEXT);
        toReturn.setTextSize(30);
        toReturn.setSelectAllOnFocus(true);

        if(isLast)
            toReturn.setImeOptions(EditorInfo.IME_ACTION_DONE);
        else
            toReturn.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        return toReturn;
    }

    public void goToNext_But(View view) {
        if(checkWords(getWord2()))
            goodAnswers++;

        if((positionOfWords + 1) == wordsArray.size()) {
            double score = (goodAnswers * 100)/ wordsArray.size();
            toastMessage("You scored: " + score + "%");
            finish();
        } else {
            positionOfWords++;
            editTextsArray.clear();
            setWords();
            horizontal_Layout.removeAllViews();
            createScreen();
        }
    }

    String getWord2(){
        String toReturn = "";

        for(int i = 0; i < editTextsArray.size(); i++){
            toReturn += editTextsArray.get(i).getText();
        }

        return toReturn;
    }

    boolean checkWords( String currentText){
        currentText = removeWhiteSpaces(currentText);
        String properText = removeWhiteSpaces(wordsArray.get(positionOfWords).getWord2());

        if(properText.equals(currentText))
            return true;
        else
            return false;
    }

    public void checkWords_But(View view) {
        if(checkWords(getWord2()))
            toastMessage("Good :)");
        else
            toastMessage("Bad :(");
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
