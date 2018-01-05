package com.genix.wordmemoriser.Menu.Manage.EditSet.Words;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.genix.wordmemoriser.Databases.WordsDatabase;
import com.genix.wordmemoriser.Menu.Manage.EditSet.EditSet;
import com.genix.wordmemoriser.R;

public class EditWords extends AppCompatActivity{

    private EditText word1_editText, word2_editText;
    private WordsDatabase wdb;
    private String selectedWord1;
    private String selectedWord2;
    private int selectedWordID;
    private String selectedSetName;
    private int selectedSetID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_words);

        Intent receivedIntent = getIntent();
        selectedWordID = receivedIntent.getIntExtra("wordID", -1);
        selectedSetID = receivedIntent.getIntExtra("setID", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");
        selectedWord1 = receivedIntent.getStringExtra("word1");
        selectedWord2 = receivedIntent.getStringExtra("word2");

        wdb = new WordsDatabase(this, selectedSetName);

        word1_editText = findViewById(R.id.word1_editText);
        word1_editText.setText(selectedWord1);
        word2_editText = findViewById(R.id.word2_editText);
        word2_editText.setText(selectedWord2);
    }

    protected void saveWords_But(View view) {

        String word1 = word1_editText.getText().toString();
        String word2 = word2_editText.getText().toString();

        word1 = removeWhiteSpaceFromEndIfExists(word1);
        word2 = removeWhiteSpaceFromEndIfExists(word2);

        if(word1.equals("") || word2.equals("")) {
            toastMessage("You must enter both names");

        } else if(hasDash(word1)){
            toastMessage("You can't use dashes in first word yet :(");

        } else {
            wdb.updateWords(selectedWordID, selectedWord1, word1, selectedWord2, word2);

            Intent editSetIntent = new Intent(EditWords.this, EditSet.class);
            editSetIntent.putExtra("setID", selectedSetID);
            editSetIntent.putExtra("setName", selectedSetName);
            startActivity(editSetIntent);
            finish();
        }
    }

    private boolean hasDash(String word){
        for (int i = 0; i < word.length(); i++)
            if(word.charAt(i) == '-')
                return true;

        return false;
    }

    protected void deleteWords_But(View view) {
        wdb.deleteWords(selectedWordID, selectedWord1, selectedWord2);
        word1_editText.setText("");
        word2_editText.setText("");

        Intent editSetIntent = new Intent(EditWords.this, EditSet.class);
        editSetIntent.putExtra("setID", selectedSetID);
        editSetIntent.putExtra("setName", selectedSetName);
        startActivity(editSetIntent);
        finish();
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

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
