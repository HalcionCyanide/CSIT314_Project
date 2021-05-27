package com.example.csit314_project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_BusinessInfo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_colleagues);

        UserController UC = UserController.getInstance();
        Controller_BusinessInfo controller_businessInfo = new Controller_BusinessInfo();
        String location = controller_businessInfo.validateOnRetrieveEmploymentLocation(UC.currentUser.NRIC, Activity_BusinessInfo.this);

        List<User> colleagues = controller_businessInfo.validateOnRetrieveColleagues(location, Activity_BusinessInfo.this);

        ListView vListView = findViewById(R.id.list_colleagues);
        TextView txt_location = findViewById(R.id.txt_location);

        txt_location.setText(location);

        ArrayList<String> colleaguesStringArray = new ArrayList<>();
        for (int i = 0; i < colleagues.size(); i++) {
            String temp = "NRIC : " + colleagues.get(i).NRIC + "\n" +
                    "Name : " + colleagues.get(i).firstName + " " + colleagues.get(i).lastName;

            colleaguesStringArray.add(temp);
        }

        ArrayAdapter<String> colleaguesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, colleaguesStringArray);
        vListView.setAdapter(colleaguesAdapter);

        Button btn_back = findViewById(R.id.btn_back);


        btn_back.setOnClickListener(v -> Activity_BusinessInfo.this.finish());

        displaySuccess();

    }
    /*
     Function Name: displaySuccess
     Brief Description: Prints Success toast
     Parameters: None
     */
    public void displaySuccess() {
        Toast.makeText(getApplicationContext(), "Operation Success", Toast.LENGTH_SHORT).show();
    }

    /*
    Function Name: displayError
    Brief Description: Prints Error toast
    Parameters: None
    */
    public void displayError() {
        Toast.makeText(getApplicationContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
    }





}
