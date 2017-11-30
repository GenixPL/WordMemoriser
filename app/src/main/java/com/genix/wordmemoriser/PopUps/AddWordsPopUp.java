package com.genix.wordmemoriser.PopUps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.genix.wordmemoriser.Activities.ManageSets;
import com.genix.wordmemoriser.Database.WordsDatabase;
import com.genix.wordmemoriser.R;

public class AddWordsPopUp extends AppCompatActivity {

    EditText word1_editText, word2_editText;
    private String TABLE_NAME;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_words);

        word1_editText = findViewById(R.id.word1_editText);
        word2_editText = findViewById(R.id.word2_editText);

        Intent receivedIntent = getIntent();
        TABLE_NAME = receivedIntent.getStringExtra("setName") + "_table";
    }


    public void saveWords_But(View view) {
        WordsDatabase wdb = new WordsDatabase(this, TABLE_NAME);

        String word1 = word1_editText.getText().toString();
        String word2 = word2_editText.getText().toString();

        if(word1.equals("") || word2.equals(""))
            toastMessage("You must enter both names");
        else{
            wdb.addWords(word1, word2);

            startActivity(new Intent(this, EditSetPopUp.class));
            finish();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
