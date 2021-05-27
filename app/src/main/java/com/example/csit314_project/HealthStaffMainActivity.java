/*
filename    HealthStaffMainActivity.java
author      Zheng Qingping
UOW email	qzheng011@uowmail.edu.au
Course: 	CSIT314
Brief Description: Health Staff main Activity
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

import java.util.ArrayList;
import java.util.List;

public class HealthStaffMainActivity extends Activity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthstaff_main);

        UserController UC = new UserController();

        TextView txt_currUser = findViewById(R.id.txt_currentUser);
        txt_currUser.setText(UC.currentUser.username);

        Button btn_searchUser = findViewById(R.id.btn_searchUser);
        btn_searchUser.setOnClickListener(v -> searchUserDialog());

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Log out!", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(HealthStaffMainActivity.this, LoginActivity.class);
            HealthStaffMainActivity.this.startActivity(mainIntent);
            HealthStaffMainActivity.this.finish();
        });
    }

    /*
    Function Name: searchUserDialog
    Brief Description: creates a Dialog based on R.layout.generic_searchuser
    Parameters: None
    */
    public void searchUserDialog() {
        Intent mainIntent = new Intent(HealthStaffMainActivity.this, Activity_SearchForUser.class);
        HealthStaffMainActivity.this.startActivity(mainIntent);
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
