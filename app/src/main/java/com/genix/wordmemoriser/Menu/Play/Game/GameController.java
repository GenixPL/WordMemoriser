package com.genix.wordmemoriser.Menu.Play.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Genix on 2018-01-17.
 */

public class GameController extends AppCompatActivity {

    private String selectedSetName;
    private GameModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();
        selectedSetName = receivedIntent.getStringExtra("setName");

        model = new GameModel(this, selectedSetName);
    }
}
