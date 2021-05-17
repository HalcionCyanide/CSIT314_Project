/*
filename	BusinessMainActivity.java
authors     Zheng Qingping
UOW email   qzheng011@uowmail.edu.au
Course: 	CSIT314
Brief Description: BusinessMainActivity class
*/
package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;

public class BusinessMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_main);

        UserController UC = UserController.getInstance();
        TextView currUser = findViewById(R.id.txt_currentUser);
        currUser.setText(UC.currentUser.username);

        DatePicker datePicker = new DatePicker(BusinessMainActivity.this, R.id.txt_datepicker);

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Log out!", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(BusinessMainActivity.this, LoginActivity.class);
            BusinessMainActivity.this.startActivity(mainIntent);
            BusinessMainActivity.this.finish();
        });

        Button btn_viewAlerts = findViewById(R.id.btn_viewAlerts);
        btn_viewAlerts.setOnClickListener(v -> generateViewAlertDialog());

        Button btn_searchByDate = findViewById(R.id.btn_searchByDate);
        btn_searchByDate.setOnClickListener(v -> searchByDate());
    }

    void generateViewAlertDialog() {
        AlertDialog dialog;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.generic_viewalerts, null);

        UserController UC = UserController.getInstance();
        User user = UC.validateOnSearchUser(UC.currentUser.NRIC, BusinessMainActivity.this);
        ListView alertListView = userPopup.findViewById(R.id.list_alerts);
        ArrayList<String> alertArrayList = new ArrayList<>();

        TextView txt_NRIC = userPopup.findViewById(R.id.txt_NRIC);

        txt_NRIC.setText(user.NRIC);

        for (int i=0; i < user.alerts.size(); i++)
        {
            if(!user.alerts.get(i).acknowledge) {
                String tempAlert = "Received On: " + user.alerts.get(i).dateTime + "\n" +  user.alerts.get(i).message;
                alertArrayList.add(tempAlert);
            }
        }
        ArrayAdapter<String> alertAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alertArrayList);

        alertListView.setOnItemClickListener((parent, view, position, id) -> {
            String text = (String)parent.getItemAtPosition(position);
            //splice the text and feed it to onAcknowledgeAlert
            String dateTime = text.substring(text.lastIndexOf("On:") + 4, text.lastIndexOf("\n"));
            String message = text.substring(text.lastIndexOf("\n") + 1);
            if (onAcknowledgeAlert(user.NRIC, dateTime, message, BusinessMainActivity.this)) {
                //delete the acknowledge one from the listview
                alertAdapter.remove(text);
                displaySuccess();
            }
            else {
                displayError();
            }
        });
        alertListView.setAdapter(alertAdapter);
        Button btn_back = userPopup.findViewById(R.id.btn_back);
        //btn_back.setOnClickListener(v -> dialog.dismiss());

        alertDialog.setView(userPopup);
        dialog = alertDialog.create();

        btn_back.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    void searchByDate()
    {

        TextView txt_date = findViewById(R.id.txt_datepicker);


        String date = txt_date.getText().toString();

        if (!date.isEmpty()) {
            AlertDialog dialog;
            AlertDialog.Builder visitorDialog = new AlertDialog.Builder(this);
            final View userPopup = getLayoutInflater().inflate(R.layout.business_searchdate, null);


            UserController UC = UserController.getInstance();
            Employment employed = UC.validateOnSearchEmployment(UC.currentUser.NRIC, BusinessMainActivity.this);

            List<TravelHistory> visitorList = UC.validateOnSearchVisitorsByDate(employed.employementLocation, date, BusinessMainActivity.this);
            ListView vListView = userPopup.findViewById(R.id.list_visitors);
            TextView txt_location = userPopup.findViewById(R.id.txt_location);
            txt_location.setText(employed.employementLocation);

            ArrayList<String> visitorStringArray = new ArrayList<>();

            for (int i = 0; i < visitorList.size(); i++) {
                String temp = "NRIC : " + visitorList.get(i).NRIC + "\n" +
                        "Check In : " + visitorList.get(i).timeIn + "\n" +
                        "Check Out : " + visitorList.get(i).timeOut;

                visitorStringArray.add(temp);
            }
            ArrayAdapter<String> visitorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visitorStringArray);
            vListView.setAdapter(visitorAdapter);

            Button btn_back = userPopup.findViewById(R.id.btn_back);

            visitorDialog.setView(userPopup);
            dialog = visitorDialog.create();
            btn_back.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
            displaySuccess();
        }
        else displayError();


    }

    boolean onAcknowledgeAlert(String NRIC, String dateTime, String message, Context context) {
        UserController UC = UserController.getInstance();
        return UC.validateOnAcknowledgeAlert(NRIC, dateTime, message, context);
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

