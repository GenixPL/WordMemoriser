package com.genix.wordmemoriser.Menu.Manage.EditSet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.genix.wordmemoriser.Menu.Manage.EditSet.Words.AddWords;
import com.genix.wordmemoriser.Databases.SetsDatabase;
import com.genix.wordmemoriser.Databases.WordsDatabase;
import com.genix.wordmemoriser.Menu.Manage.EditSet.Words.EditWords;
import com.genix.wordmemoriser.R;

import java.util.ArrayList;

public class EditSet extends AppCompatActivity{

    private SetsDatabase sdb;
    private WordsDatabase wdb;
    private String selectedSetName;
    private int selectedSetID;
    private TextView setName_Text;
    private ListView words_ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_set);

        sdb = new SetsDatabase(this);
        wdb = new WordsDatabase(this, selectedSetName);

        Intent receivedIntent = getIntent();
        selectedSetID = receivedIntent.getIntExtra("setID", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");

        setName_Text = findViewById(R.id.setName_Text);
        words_ListView = findViewById(R.id.words_ListView);

        setName_Text.setText(selectedSetName);

        displayListView();

        words_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String input = adapterView.getItemAtPosition(i).toString();
                String word1 = giveFirstWord(input);
                String word2 = giveSecondWord(input);

                Cursor data = wdb.getItemIdFromTable(word1, word2, selectedSetName);
                int itemID = -1;

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                if(itemID > -1){
                    Intent editWordIntent = new Intent(EditSet.this, EditWords.class);
                    editWordIntent.putExtra("wordID", itemID);
                    editWordIntent.putExtra("word1", word1);
                    editWordIntent.putExtra("word2", word2);
                    editWordIntent.putExtra("setName", selectedSetName);
                    editWordIntent.putExtra("setID", selectedSetID);
                    startActivity(editWordIntent);
                    finish();

                } else {
                    toastMessage("No ID associated with those words");
                }
            }
        });
    }

    private void displayListView(){
        Cursor data = wdb.getDataFromTable(selectedSetName);
        ArrayList<String> listedData = new ArrayList<>();

        while(data.moveToNext()){
            String toInsert = data.getString(1) + " - " + data.getString(2);
            listedData.add(toInsert);
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listedData);
        words_ListView.setAdapter(adapter);
    }

    private String giveFirstWord(String input){
        String toReturn = "";
        boolean flag = true;

        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '-')
                flag = false;

            if(flag)
                toReturn += input.charAt(i);
            else
                break;
        }

        toReturn = toReturn.substring(0, toReturn.length() - 1); // removing last white space

        return toReturn;
    }

    private String giveSecondWord(String input){
        String toReturn = "";
        boolean flag = false;

        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '-'){
                flag = true;
            }

            if(flag)
                toReturn += input.charAt(i);
            else
                continue;
        }

        toReturn = toReturn.substring(2, toReturn.length()); // removing first white space

        return toReturn;
    }

    protected void goToDeleteSet_But(View view) {
        Intent deleteSetIntent = new Intent(EditSet.this, DeleteSet.class);
        deleteSetIntent.putExtra("setName", selectedSetName);
        deleteSetIntent.putExtra("setID", selectedSetID);
        startActivity(deleteSetIntent);
        finish();
    }

    protected void goToEditName_But(View view) {
        Intent editSetNameIntent = new Intent(EditSet.this, EditSetName.class);
        editSetNameIntent.putExtra("setName", selectedSetName);
        editSetNameIntent.putExtra("setID", selectedSetID);
        startActivity(editSetNameIntent);
        finish();
    }

    protected void addWord_But(View view) {
        Intent editSetIntent = new Intent(EditSet.this, AddWords.class);
        editSetIntent.putExtra("setName", selectedSetName);
        editSetIntent.putExtra("setID", selectedSetID);
        startActivity(editSetIntent);

        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
