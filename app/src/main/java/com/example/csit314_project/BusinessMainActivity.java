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
            BusinessMainActivity.this.finish();
        });

        Button btn_viewAlerts = findViewById(R.id.btn_viewAlerts);
        btn_viewAlerts.setOnClickListener(v -> generateViewAlertDialog());

        Button btn_searchByDate = findViewById(R.id.btn_searchByDate);
        btn_searchByDate.setOnClickListener(v -> {
            TextView txt_date = findViewById(R.id.txt_datepicker);
            String date = txt_date.getText().toString();
            if(!date.isEmpty()){
                searchDateButton(date);
            }
        });

        Button btn_viewColleagues = findViewById(R.id.btn_viewColleagues);
        btn_viewColleagues.setOnClickListener(v -> viewInfo());
    }

    void generateViewAlertDialog() {
        Controller_ViewAlerts controller_viewAlerts = new Controller_ViewAlerts();
        controller_viewAlerts.displayAlerts(BusinessMainActivity.this);
    }

    public void searchDateButton(String date){
        Intent mainIntent = new Intent(BusinessMainActivity.this, Activity_ViewCheckIn.class);
        mainIntent.putExtra("datePickerDate", date);
        BusinessMainActivity.this.startActivity(mainIntent);
        //BusinessMainActivity.this.finish();
    }

    public void viewInfo() {
        Controller_BusinessInfo controller_businessInfo = new Controller_BusinessInfo();
        controller_businessInfo.create_Activity_BusinessInfo(BusinessMainActivity.this);
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

