/*
filename    GenericManageUserActivity.java
authors     Zheng Qingping, Derron, Jason
UOW email   qzheng011@uowmail.edu.au
Course:     CSIT314
Brief Description:
GenericManageUserActivity class
*/
package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_ManageUser extends Activity {

    String fakeNRIC;
    ListView userInfo;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_manageuser);
        UserController UC = UserController.getInstance();

        userInfo = findViewById(R.id.list_UserInfo);

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> adapter;

        displayUserInfo(arrayList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        userInfo.setAdapter(adapter);

        userInfo.setOnItemClickListener((parent, view, position, id) -> {
            String text = (String) parent.getItemAtPosition(position);
            //if clicked on suspend and you are health_org
            if(text.contains("Suspen") && UC.currentUser.role.equals("Health_Org")) {
                Controller_SuspendUser controller_suspendUser = new Controller_SuspendUser();
                controller_suspendUser.toggleSuspension(fakeNRIC, Activity_ManageUser.this);
                fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
                User displayedUser = UC.validateOnSearchUser(fakeNRIC, Activity_ManageUser.this);
                arrayList.set(7, "Toggle Suspended: " + displayedUser.isSuspend);
            }
            //if clicked on covid and you are health_staff
            else if(text.contains("Covid") && UC.currentUser.role.equals("Health_Staff")) {
                UC.toggleCovid(fakeNRIC, Activity_ManageUser.this);
                fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
                User displayedUser = UC.validateOnSearchUser(fakeNRIC, Activity_ManageUser.this);
                arrayList.set(6, "Toggle Covid: " + displayedUser.hasCovid);
            }
            else if(text.contains("Vac")) {
                //perform actions for view of vaccinations
                generateVaccineDialog();
            }
            else if(text.contains("Travel")) {
                //perform actions for view of travel history
                generateTravelHistoryDialog();
            }
            adapter.notifyDataSetChanged();
        });

        Button btn_sendAlert = findViewById(R.id.btn_sendAlert);
        btn_sendAlert.setOnClickListener(view -> {
            //sendAlert btn interaction here
            //TODO INTERACTION FOR SENDING ALERT
            generateSendAlertDialog();
        });

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> {
            //back btn interaction here
            Activity_ManageUser.this.finish();
        });

        if (UC.currentUser.role.equals("Health_Staff")) {
            //set visibility of button SEND ALERT
            btn_sendAlert.setVisibility(View.VISIBLE);
        }
    }

    /*
    Function Name: generateSendAlertDialog
    Brief Description: creates a Dialog based on R.layout.healthstaff_sendalert
    Parameters: None
    */
    void generateSendAlertDialog() {
        Intent mainIntent = new Intent(Activity_ManageUser.this, Activity_SendAlert.class);
        mainIntent.putExtra("SINGLE_NRIC", fakeNRIC);
        Activity_ManageUser.this.startActivity(mainIntent);

    }

    /*
    Function Name: generateVaccineDialog
    Brief Description: creates a Dialog based on R.layout.manage_vaccination
    Parameters: None
    */
    void generateVaccineDialog() {

        Controller_ManageVaccine controller_manageVaccine = new Controller_ManageVaccine();

        controller_manageVaccine.displayVaccine(fakeNRIC, Activity_ManageUser.this);
    }

    /*
    Function Name: generateTravelHistoryDialog
    Brief Description: creates a Dialog based on R.layout.manage_travelhistory
    Parameters: None
    */
    void generateTravelHistoryDialog() {
        Controller_ViewTravelHistory controller_viewTravelHistory = new Controller_ViewTravelHistory();

        controller_viewTravelHistory.displayTravelHistory(fakeNRIC, Activity_ManageUser.this);

        
    }

    /*
    Function Name: displayUserInfo
    Brief Description: Default user info printing
    Parameters: None
    */
    void displayUserInfo(ArrayList<String> arrayList) {
        fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");

        UserController UC = UserController.getInstance();
        User displayedUser = UC.validateOnSearchUser(fakeNRIC, Activity_ManageUser.this);

        arrayList.add("First Name: " + displayedUser.firstName);
        arrayList.add("Last Name: " + displayedUser.lastName);
        arrayList.add("Gender: " + displayedUser.gender);
        arrayList.add("Email: " + displayedUser.email);
        arrayList.add("Contact: " + displayedUser.contactNumber);
        arrayList.add("User Type: " + displayedUser.role);
        arrayList.add("Toggle Covid: " + displayedUser.hasCovid);
        arrayList.add("Toggle Suspended: " + displayedUser.isSuspend);
        arrayList.add("Click to view Vaccinations");
        arrayList.add("Click to view Travel History");
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