package com.genix.wordmemoriser.Activities.Manage.EditSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.genix.wordmemoriser.Databases.SetsDatabase;
import com.genix.wordmemoriser.Databases.WordsDatabase;
import com.genix.wordmemoriser.Activities.Manage.ManageSets;
import com.genix.wordmemoriser.R;

public class DeleteSet extends AppCompatActivity {

    private String selectedSetName;
    private int selectedSetID;
    private WordsDatabase wdb;
    private SetsDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.delete_set);

        Intent receivedIntent = getIntent();
        selectedSetName = receivedIntent.getStringExtra("setName");
        selectedSetID = receivedIntent.getIntExtra("setID", -1);

        sdb = new SetsDatabase(this);
        wdb = new WordsDatabase(this, selectedSetName);
    }

    public void goToManageSet_But(View view) {
        Intent manageSetIntent = new Intent(this, EditSet.class);
        manageSetIntent.putExtra("setName", selectedSetName);
        manageSetIntent.putExtra("setID", selectedSetID);
        startActivity(manageSetIntent);
        finish();
    }

    public void deleteSetAndGoToManageSets_But(View view) {
        sdb.deleteName(selectedSetID, selectedSetName);
        wdb.deleteTable(selectedSetName);

        startActivity(new Intent(this, ManageSets.class));
        finish();
    }
}
