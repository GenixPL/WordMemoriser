package com.genix.wordmemoriser.Menu.Manage.AddSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.genix.wordmemoriser.Databases.SetsDatabase;
import com.genix.wordmemoriser.Databases.WordsDatabase;
import com.genix.wordmemoriser.Menu.Manage.ManageSets;
import com.genix.wordmemoriser.R;

public class AddSet extends AppCompatActivity {

    private EditText getName_Text;
    private SetsDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.add_set);

        getName_Text = findViewById(R.id.editText);
        sdb = new SetsDatabase(this);
    }

    public void saveAndGoBack_But(View view){
        String newSetName = getName_Text.getText().toString();

        if(hasProperName(newSetName)){
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

        } else if (sdb.hasInside(newSetName)) {
            toastMessage("Set with such a name already exists");
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

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
