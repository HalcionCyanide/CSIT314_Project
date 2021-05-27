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
        btn_searchByDate.setOnClickListener(v -> searchDateButton());

        Button btn_viewColleagues = findViewById(R.id.btn_viewColleagues);
        btn_viewColleagues.setOnClickListener(v -> viewInfo());
    }

    void generateViewAlertDialog() {
        Controller_ViewAlerts controller_viewAlerts = new Controller_ViewAlerts();
        controller_viewAlerts.displayAlerts(BusinessMainActivity.this);
    }

    public void searchDateButton(){
        TextView txt_date = findViewById(R.id.txt_datepicker);
        String date = txt_date.getText().toString();
        if (!date.isEmpty()){
            List<TravelHistory> customerList = searchDate(date);
            displayCustomers(customerList);
        }
        else displayError();

    }

    List<TravelHistory> searchDate(String date)
    {
        UserController UC = UserController.getInstance();
        return UC.validateOnSearchDate(UC.currentUser.NRIC, date,BusinessMainActivity.this);
    }

    public void displayCustomers(List<TravelHistory> customerList) {
        AlertDialog dialog;
        AlertDialog.Builder visitorDialog = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.business_searchdate, null);

        ListView vListView = userPopup.findViewById(R.id.list_visitors);
        TextView txt_location = userPopup.findViewById(R.id.txt_location);

        ArrayList<String> visitorStringArray = new ArrayList<>();

        for (int i = 0; i < customerList.size(); i++) {
            String temp = "NRIC : " + customerList.get(i).NRIC + "\n" +
                    "Check In : " + customerList.get(i).timeIn + "\n" +
                    "Check Out : " + customerList.get(i).timeOut;

            visitorStringArray.add(temp);
        }
        ArrayAdapter<String> visitorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visitorStringArray);
        vListView.setAdapter(visitorAdapter);

        Button btn_back = userPopup.findViewById(R.id.btn_back);

        if(customerList.isEmpty()){
            displayError();
        }
        else {
            displaySuccess();
            txt_location.setText(customerList.get(0).location);
        }

        visitorDialog.setView(userPopup);
        dialog = visitorDialog.create();
        btn_back.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }

    public void viewInfo() {
        Controller_BusinessInfo controller_businessInfo = new Controller_BusinessInfo();
        controller_businessInfo.create_Activity_BusinessInfo(BusinessMainActivity.this);

    }



    public void displayEmployment(List<User> colleagues, String location)
    {
        AlertDialog dialog;
        AlertDialog.Builder colleaguesDialog = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.business_colleagues, null);

        ListView vListView = userPopup.findViewById(R.id.list_colleagues);
        TextView txt_location = userPopup.findViewById(R.id.txt_location);

        txt_location.setText(location);

        ArrayList<String> colleaguesStringArray = new ArrayList<>();
        for (int i = 0; i < colleagues.size(); i++) {
            String temp = "NRIC : " + colleagues.get(i).NRIC + "\n" +
                    "Name : " + colleagues.get(i).firstName + " " + colleagues.get(i).lastName;

            colleaguesStringArray.add(temp);
        }

        ArrayAdapter<String> colleaguesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, colleaguesStringArray);
        vListView.setAdapter(colleaguesAdapter);

        Button btn_back = userPopup.findViewById(R.id.btn_back);

        colleaguesDialog.setView(userPopup);
        dialog = colleaguesDialog.create();
        btn_back.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        displaySuccess();
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

