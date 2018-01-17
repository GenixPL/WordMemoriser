package com.genix.wordmemoriser.Menu.Manage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.genix.wordmemoriser.Databases.SetsDatabase;
import com.genix.wordmemoriser.Menu.Manage.AddSet.AddSet;
import com.genix.wordmemoriser.Menu.Manage.EditSet.EditSet;
import com.genix.wordmemoriser.R;

import java.util.ArrayList;

public class ManageSets extends AppCompatActivity{

    private SetsDatabase sdb;
    private ListView sets_ListView;

    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.manage_sets);

        sdb = new SetsDatabase(this);
        sets_ListView = findViewById(R.id.sets_listView);

        displayListView();

        sets_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String setName = adapterView.getItemAtPosition(i).toString();

                Cursor data = sdb.getItemId(setName);
                int itemID = -1;

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                if(itemID > -1){
                    Intent editSetIntent = new Intent(ManageSets.this, EditSet.class);
                    editSetIntent.putExtra("setID", itemID);
                    editSetIntent.putExtra("setName", setName);
                    startActivity(editSetIntent);
                    finish();

                } else {
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    public void goToAddSet_But(View view){
        startActivity(new Intent(this, AddSet.class));
        finish();
    }

    private void displayListView(){
        Cursor data = sdb.getData();
        ArrayList<String> listedData = new ArrayList<>();

        while(data.moveToNext()){
            listedData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listedData);
        sets_ListView.setAdapter(adapter);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}