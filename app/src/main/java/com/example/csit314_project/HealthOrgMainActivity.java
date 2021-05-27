/*
filename   HealthOrgMainActivity.java
authors    Zheng Qingping, Derron, Jason
UOW email	qzheng011@uowmail.edu.au
Course: 	CSIT314
Brief Description: Health Org main Activity
*/

package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

public class HealthOrgMainActivity extends Activity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthorg_main);
        displayPage();
    }

    /*
    Function Name: displayPage
    Brief Description: Prints UI
    Parameters: None
    */
    void displayPage() {
        UserController UC = new UserController();
        TextView currUser = findViewById(R.id.txt_currentUser);
        currUser.setText(UC.currentUser.username);

        Button btn_addUser = findViewById(R.id.btn_addUser);
        btn_addUser.setOnClickListener(v -> addNewUserDialog());

        Button btn_searchUser = findViewById(R.id.btn_searchUser);
        btn_searchUser.setOnClickListener(v -> searchUserDialog());

        Button btn_generateReport = findViewById(R.id.btn_generateReport);
        btn_generateReport.setOnClickListener(v -> generateReportDialog());

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Log out!", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(HealthOrgMainActivity.this, LoginActivity.class);
            HealthOrgMainActivity.this.startActivity(mainIntent);
            HealthOrgMainActivity.this.finish();
        });
    }

    /*
    Function Name: addNewUserDialog
    Brief Description: creates a Dialog based on R.layout.healthorg_adduser
    Parameters: None
    */
    public void addNewUserDialog() {

        Intent mainIntent = new Intent(HealthOrgMainActivity.this, Activity_CreateUser.class);
        HealthOrgMainActivity.this.startActivity(mainIntent);

    }

    /*
    Function Name: searchUserDialog
    Brief Description: creates a Dialog based on R.layout.generic_searchuser
    Parameters: None
    */
    public void searchUserDialog() {
        Intent mainIntent = new Intent(HealthOrgMainActivity.this, Activity_SearchForUser.class);
        HealthOrgMainActivity.this.startActivity(mainIntent);

    }

    /*
    Function Name: generateReportDialog
    Brief Description: creates a Dialog based on R.layout.healthorg_generatereport
    Parameters: None
    */
    public void generateReportDialog() {
        Intent mainIntent = new Intent(HealthOrgMainActivity.this, Activity_GenerateReport.class);
        HealthOrgMainActivity.this.startActivity(mainIntent);

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
