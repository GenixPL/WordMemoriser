package com.genix.wordmemoriser.Activities.Play;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.genix.wordmemoriser.Adapters.SetNamesForAdapter;
import com.genix.wordmemoriser.Adapters.SetNamesListAdapter;
import com.genix.wordmemoriser.Databases.SetsDatabase;
import com.genix.wordmemoriser.Activities.Play.Game.GameView;
import com.genix.wordmemoriser.R;

import java.util.ArrayList;

public class Play extends AppCompatActivity{

    private SetsDatabase sdb;
    private ListView sets_ListView;
    private SetNamesListAdapter singleAdapter;
    public static boolean switchedWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.play);

        sets_ListView = findViewById(R.id.sets_listView);
        sdb = new SetsDatabase(this);

        displayListView();
        setOnItemClickListenerForLstView();

        ToggleButton button = findViewById(R.id.switchWords_but);
        button.setText("Switch words");
        button.setTextOff("Switch words");
        button.setTextOn("Words switched");
        switchedWords = false;
    }

    private void setOnItemClickListenerForLstView(){
        sets_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = Integer.parseInt(view.getTag().toString());
                String setName = singleAdapter.getSetName(i).toString();

                Intent editSetIntent = new Intent(Play.this, GameView.class);
                editSetIntent.putExtra("id", id);
                editSetIntent.putExtra("setName", setName);
                startActivity(editSetIntent);

            }
        });
    }

    private void displayListView(){
        Cursor data = sdb.getData();
        ArrayList<SetNamesForAdapter> dataToList = new ArrayList<>(0);

        while(data.moveToNext()){
            dataToList.add(new SetNamesForAdapter(data.getString(1), data.getInt(0)));
        }

        singleAdapter = new SetNamesListAdapter(this, dataToList);
        sets_ListView.setAdapter(singleAdapter);
    }

    public void SwitchWords_But(View view){
        switchedWords = !switchedWords;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
