package com.example.csit314_project;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class GenericManageUserActivity extends Activity {

    String fakeNRIC;
    ListView userInfo;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_manageuser);
        UserController UC = UserController.getInstance();

        userInfo = findViewById(R.id.list_UserInfo);

        displayUserInfo();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        userInfo.setAdapter(adapter);

        userInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) parent.getItemAtPosition(position);
                //if clicked on suspend and you are health_org
                if(text.contains("Suspen") && UC.currentUser.role.equals("Health_Org")) {
                    UC.toggleSuspension(fakeNRIC, GenericManageUserActivity.this);
                    fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
                    User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);
                    arrayList.set(7, "Toggle Suspended: " + displayedUser.isSuspend);
                    adapter.notifyDataSetChanged();
                }
                //if clicked on covid and you are health_staff
                else if(text.contains("Covid") && UC.currentUser.role.equals("Health_Staff")) {
                    UC.toggleCovid(fakeNRIC, GenericManageUserActivity.this);
                    fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
                    User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);
                    arrayList.set(6, "Toggle Covid: " + displayedUser.hasCovid);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back btn interaction here
                GenericManageUserActivity.this.finish();
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

        if (UC.currentUser.role.equals("Health_Staff")) {
            //set visibility of button SEND ALERT
            btn_sendAlert.setVisibility(View.VISIBLE);
        }
    }

    void displayUserInfo() {
        fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");

        UserController UC = UserController.getInstance();
        User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);

        arrayList = new ArrayList<>();
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
}