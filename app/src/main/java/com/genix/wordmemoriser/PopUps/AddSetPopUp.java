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

        String newSetName = getName_Text.getText().toString();
        if(newSetName.equals(""))
            toastMessage("You must enter a name");
        else {
            if(hasWhiteSpaces(newSetName)) {
                toastMessage("Sorry you can use only letters and numbers for now :(");
                return;
            }
            boolean worked = sdb.addSet(newSetName);
            WordsDatabase wdb = new WordsDatabase(this, newSetName);
            wdb.createTable(newSetName);

            if (worked)
                toastMessage("New set has been added");
            else
                toastMessage("Something went wrong");

            startActivity(new Intent(this, ManageSets.class));
            finish();
        }
    }

    boolean hasWhiteSpaces(String string){

        for (int i = 0; i < string.length(); i++){
            char sign = string.charAt(i);

            if(!Character.isDigit(sign) && !Character.isLetter(sign))
                return true;
        }

        return false;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

