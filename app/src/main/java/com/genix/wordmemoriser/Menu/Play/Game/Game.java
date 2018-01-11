package com.genix.wordmemoriser.Menu.Play.Game;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.genix.wordmemoriser.Databases.WordsDatabase;
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

    private void initializeWordsArray(){
        Cursor data = wdb.getDataFromTable(selectedSetName);

        while (data.moveToNext()) {
            wordsArray.add(new Words(data.getString(1), data.getString(2)));
        }
    }

    private void setWords(){
        tempWord1 = wordsArray.get(positionOfWords).getWord1();
        tempWord2 = wordsArray.get(positionOfWords).getWord2();

        tempWord2 = removeWhiteSpaceFromEndIfExists(tempWord2);
        tempWord1 = removeWhiteSpaceFromEndIfExists(tempWord1);
    }

    private String removeWhiteSpaceFromEndIfExists(String word){
        String toReturn = word;
        char lastChar = toReturn.charAt(toReturn.length() - 1);

        while (isWhite(lastChar)) {
            toReturn = toReturn.substring(0, toReturn.length() - 2);
            lastChar = toReturn.charAt(toReturn.length() - 1);
        }

        return toReturn;
    }

    private boolean isWhite(char sign){
        return sign == ' ' || sign == '\n' || sign == '\t';
    }

    private void createScreen(){
        boolean isLast = false;

        for(int i = 0; i < tempWord2.length(); i++){
            if((i + 1) == tempWord2.length())
                isLast = true;

            EditText tempText = createEditText(isLast);
            editTextsArray.add(tempText);
            horizontal_Layout.addView(tempText);
        }
        setAddTextChangedListener();

        word1_Text.setText(tempWord1);
    }

    private void setAddTextChangedListener(){
        for(int i = 0; i < (editTextsArray.size() - 1); i++){
            final EditText currentText = editTextsArray.get(i);
            final EditText nextText = editTextsArray.get(i + 1);
            currentText.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(currentText.getText().toString().length() == 1)
                        nextText.requestFocus();

                }

                @Override
                public void afterTextChanged(Editable s) {
                    currentText.setBackgroundResource(R.drawable.edit_text_standard);
                }
            });
        }
    }

    private EditText createEditText(boolean isLast){
        final EditText toReturn = new EditText(this);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        toReturn.setGravity(Gravity.CENTER);
        toReturn.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        toReturn.setInputType(InputType.TYPE_CLASS_TEXT);
        toReturn.setTextSize(30);
        toReturn.setSelectAllOnFocus(true);
        toReturn.setBackgroundResource(R.drawable.edit_text_standard);
        toReturn.setEms(1);

        if(isLast) {
            toReturn.setImeOptions(EditorInfo.IME_ACTION_DONE);
            params.setMargins(16,8,16,8);
            toReturn.setLayoutParams(params);

        } else {
            toReturn.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            params.setMargins(16,8,0,8);
            toReturn.setLayoutParams(params);

        }

        return toReturn;
    }

    public void goToNextWord_But(View view) {
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

    private String getWord2(){
        String toReturn = "";

        for(int i = 0; i < editTextsArray.size(); i++){
            toReturn += editTextsArray.get(i).getText();
        }

        return toReturn;
    }

    private boolean checkWords(String currentText){
        String properText = wordsArray.get(positionOfWords).getWord2();

        return properText.equals(currentText);
    }

    private void colorEditTexts(){
        String properText = wordsArray.get(positionOfWords).getWord2();

        for(int i = 0; i < editTextsArray.size(); i++){
            EditText currentEditText = editTextsArray.get(i);

            if(currentEditText.getText().toString().equals("")){
                currentEditText.setBackgroundResource(R.drawable.edit_text_red);
                continue;
            }

            char currentChar = currentEditText.getText().toString().charAt(0);
            char properChar = properText.charAt(i);


            if (currentChar != properChar)
                currentEditText.setBackgroundResource(R.drawable.edit_text_red);
            else
                currentEditText.setBackgroundResource(R.drawable.edit_text_green);
        }
    }

    public void checkWords_But(View view) {
        if(checkWords(getWord2()))
            toastMessage("Good :)");
        else
            toastMessage("Bad :(");

        colorEditTexts();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

