package com.example.csit314_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GenericManageUserActivity extends Activity {

    String fakeNRIC;
    ListView userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_manageuser);
        UserController UC = UserController.getInstance();

        userInfo = findViewById(R.id.list_UserInfo);

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back btn interaction here
                GenericManageUserActivity.this.finish();
            }
        });

        displayUserInfo();

        Button btn_suspend = findViewById(R.id.btn_suspend);
        btn_suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fakeNRIC.equals(UC.currentUser.NRIC)){
                    //suspend btn interaction here
                    UC.toggleSuspension(fakeNRIC, GenericManageUserActivity.this);
                    //kick user back, i legit dk liao
                    GenericManageUserActivity.this.finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "You can't suspend yourself!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn_sendAlert = findViewById(R.id.btn_sendAlert);
        btn_sendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendAlert btn interaction here
                //TODO IN SPRINT 4
            }
        });

        //RECALL: DIFFERENT USER TYPES HAS ACCESS TO DIFFERENT BUTTONS
        if (UC.currentUser.role.equals("Health_Org")) {
            //set visibility of button SUSPEND
            btn_suspend.setVisibility(View.VISIBLE);
        }
        else if (UC.currentUser.role.equals("Health_Staff")) {
            //set visibility of button SEND ALERT
            btn_sendAlert.setVisibility(View.VISIBLE);
        }
    }

    void displayUserInfo() {
        fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");

        UserController UC = UserController.getInstance();
        User displayedUser = UC.validateOnSearchUser(fakeNRIC, this);
        ArrayList<String> displayedInfo = new ArrayList<String>();
        displayedInfo.add("Username: " + displayedUser.username);
        displayedInfo.add("Password: " + displayedUser.password);
        displayedInfo.add("Gender: " + displayedUser.gender);
        displayedInfo.add("First Name: " + displayedUser.firstName);
        displayedInfo.add("Last Name: " + displayedUser.lastName);
        displayedInfo.add("Email: " + displayedUser.email);
        displayedInfo.add("Contact: " + displayedUser.contactNumber);
        displayedInfo.add("User Type: " + displayedUser.role);
        displayedInfo.add("Has Covid: " + displayedUser.hasCovid);
        displayedInfo.add("Is Suspended: " + displayedUser.isSuspend);
        ArrayAdapter<String> usersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayedInfo);
        userInfo.setAdapter(usersAdapter);
    }
}
