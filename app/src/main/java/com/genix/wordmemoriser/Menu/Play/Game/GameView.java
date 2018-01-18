package com.genix.wordmemoriser.Menu.Play.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.genix.wordmemoriser.R;

import java.util.ArrayList;

/**
 * Created by Genix on 2018-01-17.
 */

public class GameView extends AppCompatActivity {

    private String selectedSetName;
    private GameModel gModel;
    private TextView word1_textView;
    private LinearLayout vertical_linearLayout;
    private ArrayList<ArrayList<EditText>> viewArray;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        Intent receivedIntent = getIntent();
        selectedSetName = receivedIntent.getStringExtra("setName");

        try{
            gModel = new GameModel(this, selectedSetName);
        } catch (IndexOutOfBoundsException e){
            //EMPTY SET
            toastMessage("You cannot play with empty set");
            finish();
            return;
        }

        word1_textView = findViewById(R.id.word1_text);
        vertical_linearLayout = findViewById(R.id.vertical_LinearLayout);

        viewArray = new ArrayList<>(1);

        initializeView();
    }

    private void initializeView(){
        vertical_linearLayout.removeAllViews();
        viewArray.clear();
        word1_textView.setText(gModel.getCurrentWord1());

        for(int i = 0; i < gModel.word2Tokens.size(); i++){
            vertical_linearLayout.addView(createHorizontalView(i));
        }

        setAddTextChangedListener();
    }

    private void setAddTextChangedListener(){
    //BE CAREFUL!!! LAST ROW AND LAST ELEMENT MUST BE SETTED SEPARATELY
        for (int i = 0; i < viewArray.size() - 1; i++){
            for (int j = 0; j < viewArray.get(i).size(); j++){
                final EditText currentEditText = viewArray.get(i).get(j);
                final EditText nextEditText;
                if(j + 1 == viewArray.get(i).size()) {
                    nextEditText = viewArray.get(i + 1).get(0);
                } else {
                    nextEditText = viewArray.get(i).get(j + 1);
                }

                currentEditText.addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(currentEditText.getText().toString().length() == 1) {
                            nextEditText.requestFocus();
                        }

                    }

                    public void afterTextChanged(Editable s) {
                        currentEditText.setBackgroundResource(R.drawable.edit_text_standard);
                    }
                });
            }
        }
        //LAST ROW
        final int lastRow = viewArray.size() - 1;
        int lastElement = 0;
        for (int i = 0; i < viewArray.get(lastRow).size() - 1; i++) {
            final EditText currentEditText = viewArray.get(lastRow).get(i);
            final EditText nextEditText = viewArray.get(lastRow).get(i + 1);

            currentEditText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(currentEditText.getText().toString().length() == 1) {
                        nextEditText.requestFocus();
                    }
                }

                public void afterTextChanged(Editable s) {
                    currentEditText.setBackgroundResource(R.drawable.edit_text_standard);
                }
            });
            lastElement++;
        }
        //LAST ELEMENT
        final EditText tempCurrentEditText = viewArray.get(lastRow).get(lastElement);
        tempCurrentEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            public void afterTextChanged(Editable editable) {
                tempCurrentEditText.setBackgroundResource(R.drawable.edit_text_standard);
            }
        });
    }

    private HorizontalScrollView createHorizontalView(int index){
        HorizontalScrollView toReturn = new HorizontalScrollView(this);
        LinearLayout tempLayout = new LinearLayout(this);

        tempLayout.setGravity(Gravity.CENTER);
        toReturn.setFillViewport(true);

        viewArray.add(new ArrayList<EditText>());
        for (int i = 0; i < gModel.word2Tokens.get(index).length(); i++) {
            EditText tempEditText = createEditText();
            viewArray.get(index).add(tempEditText);
            tempLayout.addView(tempEditText);
        }

        toReturn.addView(tempLayout);
        return toReturn;
    }

    private EditText createEditText(){
        EditText toReturn = new EditText(this);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(4,4,4,4);

        toReturn.setEms(1);
        toReturn.setGravity(Gravity.CENTER);
        toReturn.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        toReturn.setInputType(InputType.TYPE_CLASS_TEXT);
        toReturn.setTextSize(30);
        toReturn.setSelectAllOnFocus(true);
        toReturn.setBackgroundResource(R.drawable.edit_text_standard);

        toReturn.setLayoutParams(params);

        return toReturn;
    }

    public void checkWords_But(View view) {
        gModel.setCorrectness(checkAndColorEditTexts());
    }

    private String getTextFromEditTexts(){
        String toReturn = "";
        for(int i = 0; i < viewArray.size(); i++){
            for(int j = 0; j < viewArray.get(i).size(); j++){
                toReturn += viewArray.get(i).get(j).getText();
            }
            toReturn += " ";
        }
        return toReturn;
    }

    private boolean checkAndColorEditTexts(){
        String proper = gModel.getCurrentWord2();
        boolean hasError = false;
        int currentPositionInProper = 0;

        for(int i = 0; i < viewArray.size(); i++){
            for(int j = 0; j < viewArray.get(i).size(); j++){
                //ERROR SECURITY
                if(viewArray.get(i).get(j).getText().toString().equals(""))
                    viewArray.get(i).get(j).setText(" ");

                char currentCharFromEditText = viewArray.get(i).get(j).getText().toString().charAt(0);

                if(proper.charAt(currentPositionInProper) == currentCharFromEditText) {
                    viewArray.get(i).get(j).setBackgroundResource(R.drawable.edit_text_green);
                } else {
                    viewArray.get(i).get(j).setBackgroundResource(R.drawable.edit_text_red);
                    hasError = true;
                }
                currentPositionInProper++;
            }
            currentPositionInProper++;
        }

        return hasError;
    }

    public void goToNextWord_But(View view) {
        gModel.setCorrectness(checkAndColorEditTexts());

        try {
            gModel.takeAnotherWords();
        } catch (IndexOutOfBoundsException e){
            //END OF WORD'S ARRAY
            toastMessage("You scored: " + gModel.getCorrectness() + "%");
            finish();
            return;
        }

        initializeView();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
