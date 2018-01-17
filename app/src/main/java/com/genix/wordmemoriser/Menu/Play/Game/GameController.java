package com.genix.wordmemoriser.Menu.Play.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.genix.wordmemoriser.R;

/**
 * Created by Genix on 2018-01-17.
 */

public class GameController extends AppCompatActivity {

    private String selectedSetName;
    private GameModel model;
    protected TextView word1_textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        Intent receivedIntent = getIntent();
        selectedSetName = receivedIntent.getStringExtra("setName");

        model = new GameModel(this, selectedSetName);

        word1_textView = findViewById(R.id.word1_text);

    }

    public void checkWords_But(View view) {
    }

    public void goToNextWord_But(View view) {
    }
}
