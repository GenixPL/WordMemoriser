package com.genix.wordmemoriser.PopUps;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.genix.wordmemoriser.Activities.ManageSets;
import com.genix.wordmemoriser.Database.SetsDatabase;
import com.genix.wordmemoriser.Database.WordsDatabase;
import com.genix.wordmemoriser.R;

import java.util.ArrayList;

public class EditSetPopUp extends AppCompatActivity{

    SetsDatabase sdb;
    WordsDatabase wdb;
    private String selectedSetName;
    private int selectedID;
    private EditText setName_editText;
    private ListView words_ListView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_edit_set);

        sdb = new SetsDatabase(this);
        setName_editText = findViewById(R.id.setName_editText);

        String tableName = selectedSetName + "_table";
        wdb = new WordsDatabase(this, tableName);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");

        setName_editText.setText(selectedSetName);

        words_ListView = findViewById(R.id.words_ListView);
        displayListView();

        words_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String input = adapterView.getItemAtPosition(i).toString();
                String word1 = giveFirstWord(input);
                String word2 = giveSecondWord(input);

                Cursor data = wdb.getItemId(word1, word2);
                int itemID = -1;

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                if(itemID > -1){
                    Intent editSetIntent = new Intent(EditSetPopUp.this, EditWordsPopUp.class);
                    editSetIntent.putExtra("id", itemID);
                    editSetIntent.putExtra("word1", word1);
                    editSetIntent.putExtra("word2", word2);
                    editSetIntent.putExtra("setName", selectedSetName);
                    startActivity(editSetIntent);
                    finish();
                } else {
                    toastMessage("No ID associated with those words");
                }
            }
        });
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

        toReturn = toReturn.substring(toReturn.length() - 1); // removing last white space

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

        toReturn = toReturn.substring(1, input.length()); // removing first white space

        return toReturn;
    }

    public void delete_But(View view) {
        sdb.deleteName(selectedID, selectedSetName);
        setName_editText.setText("");

        String tableName = selectedSetName + "_table";
        wdb.deleteTable(tableName);

        startActivity(new Intent(this, ManageSets.class));
        finish();
    } //AD "ARE U SURE" QUESTION

    public void save_But(View view) {
        String item = setName_editText.getText().toString();
        if(item.equals(""))
            toastMessage("You must enter a name");
        else
            sdb.updateName(item, selectedID, selectedSetName);

        startActivity(new Intent(this, ManageSets.class));
        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void displayListView(){
        Cursor data = wdb.getData();
        ArrayList<String> listedData = new ArrayList<>();

        while(data.moveToNext()){
            String toInsert = data.getString(1) + " - " + data.getString(2);
            listedData.add(toInsert); // 1 - first column (not 0)
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listedData);
        words_ListView.setAdapter(adapter);
    }

    public void addWord_But(View view) {
        Intent editSetIntent = new Intent(EditSetPopUp.this, AddWordsPopUp.class);
        editSetIntent.putExtra("setName", selectedSetName);
        startActivity(editSetIntent);

        finish();
    }
}
