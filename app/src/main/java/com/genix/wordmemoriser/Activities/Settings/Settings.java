package com.genix.wordmemoriser.Activities.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.genix.wordmemoriser.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.settings);
    }

}
