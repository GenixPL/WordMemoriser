package com.genix.wordmemoriser.PopUps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.genix.wordmemoriser.Database.SetsDatabase;
import com.genix.wordmemoriser.Database.WordsDatabase;
import com.genix.wordmemoriser.R;

public class EditSetNamePopUp extends AppCompatActivity {

    private String selectedSetName;
    private int selectedID;
    private WordsDatabase wdb;
    private SetsDatabase sdb;
    private EditText editSetName_EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_edit_set_name);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");

        wdb = new WordsDatabase(this, selectedSetName);
        sdb = new SetsDatabase(this);

        editSetName_EditText = findViewById(R.id.editSetName_EditText);
        editSetName_EditText.setText(selectedSetName);
    }

    boolean hasWhiteSpaces(String string){
        for (int i = 0; i < string.length(); i++){
            char sign = string.charAt(i);

            if(!Character.isDigit(sign) && !Character.isLetter(sign))
                return true;
            }
        return false;
    }

    public void save_But(View view) {
        String newSetName = editSetName_EditText.getText().toString();

        if(newSetName.equals(selectedSetName)){
            Intent intent = new Intent(EditSetNamePopUp.this, EditSetPopUp.class);
            intent.putExtra("setName", newSetName);
            intent.putExtra("id", selectedID);
            startActivity(intent);
            finish();

        } else if (newSetName.equals("")) {
            toastMessage("You must enter a name");

        } else if (hasWhiteSpaces(newSetName)) {
            toastMessage("Sorry you can use only letters and numbers for now :(");

        } else {
            wdb.changeTableName(selectedSetName + "_table", newSetName + "_table"); //DON'T KNOW WHY THIS DOESN'T WORK WITHOUT "_TABLE"
            sdb.updateName(newSetName, selectedID, selectedSetName);

            Intent intent = new Intent(EditSetNamePopUp.this, EditSetPopUp.class);
            intent.putExtra("setName", newSetName);
            intent.putExtra("id", selectedID);
            startActivity(intent);
            finish();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
