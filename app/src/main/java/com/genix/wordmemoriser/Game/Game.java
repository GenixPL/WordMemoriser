package com.genix.wordmemoriser.Game;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.genix.wordmemoriser.Database.WordsDatabase;
import com.genix.wordmemoriser.R;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private String selectedSetName;
    private int selectedSetId;
    private Button check_But;
    private Button next_But;
    private TextView word1_Text;
    private EditText word2_Text;
    WordsDatabase wdb;
    private ArrayList<Words> wordsArray;
    private int position;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        check_But = findViewById(R.id.check_but);
        next_But = findViewById(R.id.next_but);
        wordsArray = new ArrayList<>(0);

        word1_Text = findViewById(R.id.word1_text);
        word2_Text = findViewById(R.id.word2_editText);

        final String tableName = selectedSetName + "_table";
        wdb = new WordsDatabase(this, tableName);

        Intent receivedIntent = getIntent();
        selectedSetId = receivedIntent.getIntExtra("id", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");

        Cursor data = wdb.getDataFromTable(selectedSetName + "_table");

        while (data.moveToNext()) {
            wordsArray.add(new Words(data.getString(1), data.getString(2)));
        }

        position = 0;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void checkWords(View view) {
        ;
    }

    public void goToNext(View view) {
        word1_Text.setText(wordsArray.get(position).word1);
        word2_Text.setText(wordsArray.get(position).word2);
        position++;
    }
}
