package com.genix.wordmemoriser.PopUps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.genix.wordmemoriser.Activities.ManageSets;
import com.genix.wordmemoriser.Database.SetsDatabase;
import com.genix.wordmemoriser.R;

/**
 * Created by Genix on 2017-11-30.
 */

public class EditSetPopUp extends AppCompatActivity{

    SetsDatabase sdb;
    private String selectedSetName;
    private int selectedID;
    private EditText setName_Text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_edit_set);

        sdb = new SetsDatabase(this);
        setName_Text = findViewById(R.id.setName_editText);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedSetName = receivedIntent.getStringExtra("setName");

        setName_Text.setText(selectedSetName);
    }

    public void delete_But(View view) {
        sdb.deleteName(selectedID, selectedSetName);
        setName_Text.setText("");

        startActivity(new Intent(this, ManageSets.class));
        finish();
    }

    public void save_But(View view) {
        String item = setName_Text.getText().toString();
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
}
