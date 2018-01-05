package com.genix.wordmemoriser.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.genix.wordmemoriser.Menu.Manage.ManageSets;
import com.genix.wordmemoriser.Menu.Play.Play;
import com.genix.wordmemoriser.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main_menu);
    }

    public void goToManageSets_But(View view) {
        startActivity(new Intent(this, ManageSets.class));
    }

    public void goToPlay_But(View view) {
        startActivity(new Intent(this, Play.class));
    }

    public void goToSettings_But(View view) {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }
}
