package com.example.csit314_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_SearchForUser extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_searchuser);

        ListView results = findViewById(R.id.list_userResults);
        EditText txt_NRIC = findViewById(R.id.txt_NRICinput);
        EditText txt_username = findViewById(R.id.txt_NameInput);

        Spinner spinner_role = findViewById(R.id.spinner_role);
        final String[] txt_role = new String[1];
        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_role[0] = spinner_role.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btn_search = findViewById(R.id.btn_logout);
        btn_search.setOnClickListener(v -> {
            if (onSearchUser(results, txt_NRIC.getText().toString(), txt_role[0], txt_username.getText().toString(), Activity_SearchForUser.this)) {
                //if there are results
                displaySuccess();
            }
            else {
                displayError();
            }
        });


        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> Activity_SearchForUser.this.finish());
    }

    /*
    Function Name: onSearchUser
    Brief Description: when triggered, calls for controller to perform the action
    Parameters:
    result : resulting list view to modify
    NRIC, userType, username : parameters from UI, passed to controller
    context : app context for the database opening
    */
    boolean onSearchUser(ListView result, String NRIC, String userType, String username, Context context) {
        Controller_SearchUsers UC = new Controller_SearchUsers();
        List<User> tempList = UC.validateOnSearchUser(NRIC, userType, username, context);

        ArrayList<String> lvUsers = new ArrayList<>();

        for (int i=0; i < tempList.size(); i++)
        {
            String tempString = tempList.get(i).NRIC + ", " + tempList.get(i).firstName + " " + tempList.get(i).lastName;
            lvUsers.add(tempString);
        }

        ArrayAdapter<String> usersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lvUsers);
        result.setAdapter(usersAdapter);

        result.setOnItemClickListener((parent, view, position, id) -> {
            Intent mainIntent = new Intent(Activity_SearchForUser.this, Activity_ManageUser.class);
            String text = (String) parent.getItemAtPosition(position);
            String fakeNRIC = text.substring(0, text.indexOf(","));
            mainIntent.putExtra("SINGLE_NRIC", fakeNRIC);
            Activity_SearchForUser.this.startActivity(mainIntent);
        });

        return !tempList.isEmpty();
    }

    /*
   Function Name: displaySuccess
   Brief Description: Prints Success toast
   Parameters: None
   */
    void displaySuccess() {
        Toast.makeText(getApplicationContext(), "Operation Success", Toast.LENGTH_SHORT).show();
    }

    /*
    Function Name: displayError
    Brief Description: Prints Error toast
    Parameters: None
    */
    void displayError() {
        Toast.makeText(getApplicationContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
    }
}
