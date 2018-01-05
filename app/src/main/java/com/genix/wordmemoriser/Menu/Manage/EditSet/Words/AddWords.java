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

public class AddWords extends AppCompatActivity {

    private EditText word1_editText, word2_editText;
    private String selectedSetName;
    private int selectedSetID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_words);

        word1_editText = findViewById(R.id.word1_editText);
        word2_editText = findViewById(R.id.word2_editText);

        Intent receivedIntent = getIntent();
        selectedSetID = receivedIntent.getIntExtra("setID", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");
    }

    protected void saveWords_But(View view) {
        WordsDatabase wdb = new WordsDatabase(this, selectedSetName);

        String word1 = word1_editText.getText().toString();
        String word2 = word2_editText.getText().toString();

        word1 = removeWhiteSpaceFromEndIfExists(word1);
        word2 = removeWhiteSpaceFromEndIfExists(word2);

        if(word1.equals("") || word2.equals("")) {
            toastMessage("You must enter both words");

        } else if(hasDash(word1)) {
            toastMessage("You can't use dashes in first word yet :(");

        } else {
            wdb.addWords(word1, word2);

            Intent editSetIntent = new Intent(AddWords.this, EditSet.class);
            editSetIntent.putExtra("setName", selectedSetName);
            editSetIntent.putExtra("setID", selectedSetID);
            startActivity(editSetIntent);
            finish();
        }
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
        if(sign == ' ' || sign == '\n' || sign == '\t')
            return true;
        else
            return false;
    }

    private boolean hasDash(String word){
        for (int i = 0; i < word.length(); i++)
            if(word.charAt(i) == '-')
                return true;

        return false;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
