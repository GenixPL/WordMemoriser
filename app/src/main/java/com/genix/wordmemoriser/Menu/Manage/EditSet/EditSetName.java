package com.genix.wordmemoriser.Menu.Manage.EditSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.genix.wordmemoriser.Databases.SetsDatabase;
import com.genix.wordmemoriser.Databases.WordsDatabase;
import com.genix.wordmemoriser.R;

public class EditSetName extends AppCompatActivity {

    private String selectedSetName;
    private int selectedSetID;
    private WordsDatabase wdb;
    private SetsDatabase sdb;
    private EditText editSetName_EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_set_name);

        Intent receivedIntent = getIntent();
        selectedSetID = receivedIntent.getIntExtra("setID", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");

        wdb = new WordsDatabase(this, selectedSetName);
        sdb = new SetsDatabase(this);

        editSetName_EditText = findViewById(R.id.editSetName_EditText);
        editSetName_EditText.setText(selectedSetName);
    }

    private boolean hasProperName(String newSetName){
        if(newSetName.equals("")) {
            toastMessage("You must enter a name");
            return false;

        } else if(hasWhiteSpaces(newSetName)) {
            toastMessage("Sorry you can use only letters and numbers for now :(");
            return false;

        } else if(hasDigitFirst(newSetName)){
            toastMessage("The name can't start with a digit :(");
            return false;

        } else {
            return true;
        }
    }

    private boolean hasDigitFirst(String newSetName){
        return Character.isDigit(newSetName.charAt(0));
    }

    private boolean hasWhiteSpaces(String newSetName){
        for (int i = 0; i < newSetName.length(); i++){
            char sign = newSetName.charAt(i);

            if(!Character.isDigit(sign) && !Character.isLetter(sign))
                return true;
            }
        return false;
    }

    protected void saveAndGoToEditSet_But(View view) {
        String newSetName = editSetName_EditText.getText().toString();

        if(newSetName.equals(selectedSetName)){
            Intent intent = new Intent(EditSetName.this, EditSet.class);
            intent.putExtra("setName", newSetName);
            intent.putExtra("setID", selectedSetID);
            startActivity(intent);
            finish();
            return;

        }

        if(hasProperName(newSetName)) {
            wdb.changeTableName(selectedSetName, newSetName);
            sdb.updateName(newSetName, selectedSetID, selectedSetName);

            Intent intent = new Intent(EditSetName.this, EditSet.class);
            intent.putExtra("setName", newSetName);
            intent.putExtra("setID", selectedSetID);
            startActivity(intent);
            finish();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
