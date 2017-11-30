package com.genix.wordmemoriser.PopUps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.genix.wordmemoriser.Database.WordsDatabase;
import com.genix.wordmemoriser.R;

public class EditWordsPopUp extends AppCompatActivity{

    private EditText word1_editText, word2_editText;
    WordsDatabase wdb;
    private String selectedWord1;
    private String selectedWord2;
    private int selectedWordID;
    private String selectedSetName;
    private int selectedSetID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_edit_words);

        Intent receivedIntent = getIntent();
        selectedWordID = receivedIntent.getIntExtra("wordID", -1);
        selectedSetID = receivedIntent.getIntExtra("setID", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");
        selectedWord1 = receivedIntent.getStringExtra("word1");
        selectedWord2 = receivedIntent.getStringExtra("word2");

        String tableName = selectedSetName + "_table";
        wdb = new WordsDatabase(this, tableName);

        word1_editText = findViewById(R.id.word1_editText);
        word1_editText.setText(selectedWord1);
        word2_editText = findViewById(R.id.word2_editText);
        word2_editText.setText(selectedWord2);
    }

    public void saveWords_But(View view) {

        String item1 = word1_editText.getText().toString();
        String item2 = word2_editText.getText().toString();

        if(item1.equals("") || item2.equals(""))
            toastMessage("You must enter both names");
        else {
            wdb.updateWords(selectedWordID, selectedWord1, item1, selectedWord2, item2);

            Intent editSetIntent = new Intent(EditWordsPopUp.this, EditSetPopUp.class);
            editSetIntent.putExtra("id", selectedSetID);
            editSetIntent.putExtra("setName", selectedSetName);
            startActivity(editSetIntent);
            finish();
        }
    }

    public void deleteWords_But(View view) {
        wdb.deleteWords(selectedWordID, selectedWord1, selectedWord2);
        word1_editText.setText("");
        word2_editText.setText("");

        Intent editSetIntent = new Intent(EditWordsPopUp.this, EditSetPopUp.class);
        editSetIntent.putExtra("id", selectedSetID);
        editSetIntent.putExtra("setName", selectedSetName);
        startActivity(editSetIntent);
        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
