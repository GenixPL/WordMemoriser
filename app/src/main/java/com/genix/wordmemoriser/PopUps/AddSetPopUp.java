package com.genix.wordmemoriser.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import com.genix.wordmemoriser.Activities.ManageSets;
import com.genix.wordmemoriser.Database.SetsDatabase;
import com.genix.wordmemoriser.Database.WordsDatabase;
import com.genix.wordmemoriser.R;

public class AddSetPopUp extends Activity {

    private EditText getName_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_set);

        getName_Text = findViewById(R.id.editText);
    }

    public void saveAndGoBack_But(View view){

        SetsDatabase sdb = new SetsDatabase(this);

        String item = getName_Text.getText().toString();
        if(item.equals(""))
            toastMessage("You must enter a name");
        else {
            boolean worked = sdb.addSet(getName_Text.getText().toString());
            String tableName = getName_Text.getText().toString();
            WordsDatabase wdb = new WordsDatabase(this, tableName);
            wdb.createTable(tableName);

            if (worked)
                toastMessage("New set has been added");
            else
                toastMessage("Something went wrong");

            startActivity(new Intent(this, ManageSets.class));
            finish();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

