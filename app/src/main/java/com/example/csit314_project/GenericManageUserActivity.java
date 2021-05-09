package com.example.csit314_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GenericManageUserActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_manageuser);
        // TODO manageuser work here

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back btn interaction here
                //TODO transition back to the calling activity, could be healthorg or healthstaff @qing
            }
        });
        
        Button btn_suspend = findViewById(R.id.btn_suspend);
        btn_suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //suspend btn interaction her
            }
        });
        Button btn_sendAlert = findViewById(R.id.btn_sendAlert);
        btn_sendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendAlert btn interaction here
            }
        });

        //RECALL: DIFFERENT USER TYPES HAS ACCESS TO DIFFERENT BUTTONS
        UserController UC = UserController.getInstance();
        if (UC.currentUser.role.equals("Health_Org")) {
            //set visibility of button SUSPEND
            btn_suspend.setVisibility(View.VISIBLE);
        }
        else if (UC.currentUser.role.equals("Health_Staff")) {
            //set visibility of button SEND ALERT
            btn_sendAlert.setVisibility(View.VISIBLE);
        }

        //

    }
}
