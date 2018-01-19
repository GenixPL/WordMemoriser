package com.genix.wordmemoriser.Menu.Manage.EditSet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.genix.wordmemoriser.Adapters.WordsListAdapter;
import com.genix.wordmemoriser.Adapters.WordsForAdapter;
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
    private WordsListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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
        setOnItemClickListenerForListView();
    }

    private void setOnItemClickListenerForListView(){
        words_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = Integer.parseInt(view.getTag().toString());
                String word1 = mListAdapter.getWord1(i).toString();
                String word2 = mListAdapter.getWord2(i).toString();


                Intent editWordIntent = new Intent(EditSet.this, EditWords.class);
                editWordIntent.putExtra("wordID", id);
                editWordIntent.putExtra("word1", word1);
                editWordIntent.putExtra("word2", word2);
                editWordIntent.putExtra("setName", selectedSetName);
                editWordIntent.putExtra("setID", selectedSetID);
                startActivity(editWordIntent);
                finish();
            }
        });
    }

    private void displayListView(){
        Cursor data = wdb.getDataFromTable(selectedSetName);
        ArrayList<WordsForAdapter> listedData = new ArrayList<>();

        while(data.moveToNext()){
            listedData.add(new WordsForAdapter(data.getInt(0), data.getString(1), data.getString(2)));
        }

        mListAdapter = new WordsListAdapter(this, listedData);
        words_ListView.setAdapter(mListAdapter);
    }

    public void goToDeleteSet_But(View view) {
        Intent deleteSetIntent = new Intent(EditSet.this, DeleteSet.class);
        deleteSetIntent.putExtra("setName", selectedSetName);
        deleteSetIntent.putExtra("setID", selectedSetID);
        startActivity(deleteSetIntent);
        finish();
    }

    public void goToEditName_But(View view) {
        Intent editSetNameIntent = new Intent(EditSet.this, EditSetName.class);
        editSetNameIntent.putExtra("setName", selectedSetName);
        editSetNameIntent.putExtra("setID", selectedSetID);
        startActivity(editSetNameIntent);
        finish();
    }

    public void addWord_But(View view) {
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
