package com.genix.wordmemoriser.Menu.Manage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.genix.wordmemoriser.Adapters.SetNamesForAdapter;
import com.genix.wordmemoriser.Adapters.SetNamesListAdapter;
import com.genix.wordmemoriser.Databases.SetsDatabase;
import com.genix.wordmemoriser.Menu.Manage.AddSet.AddSet;
import com.genix.wordmemoriser.Menu.Manage.EditSet.EditSet;
import com.genix.wordmemoriser.R;

import java.util.ArrayList;

public class ManageSets extends AppCompatActivity{

    private SetsDatabase sdb;
    private ListView sets_ListView;
    private SetNamesListAdapter singleAdapter;

    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        getSupportActionBar().hide();
        setContentView(R.layout.manage_sets);

        sdb = new SetsDatabase(this);
        sets_ListView = findViewById(R.id.sets_listView);

        displayListView();
        setOnClickListenerForListView();
    }

    private void setOnClickListenerForListView(){
        sets_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = Integer.parseInt(view.getTag().toString());
                String setName = singleAdapter.getSetName(i).toString();

                Intent editSetIntent = new Intent(ManageSets.this, EditSet.class);
                editSetIntent.putExtra("setID", id);
                editSetIntent.putExtra("setName", setName);
                startActivity(editSetIntent);
                finish();
            }
        });
    }

    public void goToAddSet_But(View view){
        startActivity(new Intent(this, AddSet.class));
        finish();
    }

    private void displayListView(){
        Cursor data = sdb.getData();
        ArrayList<SetNamesForAdapter> listedData = new ArrayList<>();

        while(data.moveToNext()){
            listedData.add(new SetNamesForAdapter(data.getString(1), data.getInt(0)));
        }

        singleAdapter = new SetNamesListAdapter(this, listedData);
        sets_ListView.setAdapter(singleAdapter);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}