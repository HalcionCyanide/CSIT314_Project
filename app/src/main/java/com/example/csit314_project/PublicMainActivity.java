/*
filename   PublicMainActivity.java
authors    Zheng Qingping
UOW email	qzheng011@uowmail.edu.au
Course: 	CSIT314
Brief Description: Public user main Activity
*/
package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PublicMainActivity extends Activity {

    ArrayList<String> checkInLocations = new ArrayList<>();
    ListView list_currentLocations;
    ArrayAdapter<String> checkInLocationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_main);
        displayPage();
    }

    void displayPage() {
        UserController UC = UserController.getInstance();

        TextView currUser = findViewById(R.id.txt_currentUser);
        currUser.setText(UC.currentUser.username);

        //check-in listview initialisations
        list_currentLocations = findViewById(R.id.list_currentLocations);
        checkInLocationsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkInLocations);
        list_currentLocations.setAdapter(checkInLocationsAdapter);

        list_currentLocations.setOnItemClickListener((parent, view, position, id) -> {
            //try to check out the user
            String text = (String) parent.getItemAtPosition(position);
            String NRIC = UC.currentUser.NRIC;
            String timeIn = text.substring((text.lastIndexOf("at ") + 3));
            String location = text.substring((text.lastIndexOf("to: ") + 4), (text.lastIndexOf("at ") - 1));

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            Date date = new Date();
            String timeOut = formatter.format(date);

            if (onAddTravelHistory(NRIC, timeIn, timeOut, location, PublicMainActivity.this)){
                //this is a local view
                checkInLocationsAdapter.remove(text);
                displaySuccess();
            }
            else {
                displayError();
            }
        });

        Button btn_checkIn = findViewById(R.id.btn_checkIn);
        btn_checkIn.setOnClickListener(v -> generateCheckInDialog());

        Button btn_viewVax = findViewById(R.id.btn_viewVax);
        btn_viewVax.setOnClickListener(v -> generateViewVaccineDialog());

        Button btn_viewAlerts = findViewById(R.id.btn_viewAlerts);
        btn_viewAlerts.setOnClickListener(v -> generateViewAlertDialog());

        Button btn_viewTravelHistory = findViewById(R.id.btn_viewTH);
        btn_viewTravelHistory.setOnClickListener(v -> generateViewTHDialog());

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Log out!", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(PublicMainActivity.this, LoginActivity.class);
            PublicMainActivity.this.startActivity(mainIntent);
            PublicMainActivity.this.finish();
        });
    }

    void generateCheckInDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PublicMainActivity.this);
        alertDialog.setTitle("Checking in to....");
        final EditText input = new EditText(PublicMainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        alertDialog.setPositiveButton("OK", (dialog, which) -> {
            //grab current time in-case
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            Date date = new Date();
            //adding current location to local list
            checkInLocations.add("Checked in to: " + input.getText().toString() + " at " + formatter.format(date));
            //update the list in UI
            checkInLocationsAdapter.notifyDataSetChanged();
        });
        alertDialog.setView(input);
        alertDialog.show();
    }

    void generateViewVaccineDialog() {
        Controller_ManageVaccine controller_manageVaccine = new Controller_ManageVaccine();
        controller_manageVaccine.displayVaccine(PublicMainActivity.this);

        
    }

    void generateViewAlertDialog() {
        Controller_ViewAlerts controller_viewAlerts = new Controller_ViewAlerts();
        controller_viewAlerts.displayAlerts(PublicMainActivity.this);
    }

    void generateViewTHDialog() {
        Controller_ViewTravelHistory controller_viewTravelHistory = new Controller_ViewTravelHistory();
        controller_viewTravelHistory.displayTravelHistory(PublicMainActivity.this);


    }

    boolean onAddTravelHistory (String NRIC, String timeIn, String timeOut, String location, Context context) {
        UserController UC = UserController.getInstance();
        return UC.validateOnAddTravelHistory(NRIC, timeIn, timeOut, location, context);
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
