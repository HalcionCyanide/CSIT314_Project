package com.example.csit314_project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_ViewAlerts extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_viewalerts);

        UserController UC = UserController.getInstance();
        User user = UC.validateOnSearchUser(UC.currentUser.NRIC, Activity_ViewAlerts.this);

        ListView alertListView = findViewById(R.id.list_alerts);
        ArrayList<String> alertArrayList = new ArrayList<>();

        TextView txt_NRIC = findViewById(R.id.txt_NRIC);

        txt_NRIC.setText(user.NRIC);

        for (int i=0; i < user.alerts.size(); i++)
        {
            if(!user.alerts.get(i).acknowledge) {
                String tempAlert = "Received On: " + user.alerts.get(i).dateTime + "\n" +  user.alerts.get(i).message;
                alertArrayList.add(tempAlert);
            }
        }

        ArrayAdapter<String> alertAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alertArrayList);

        Controller_AcknowledgeAlerts controller_acknowledgeAlerts = new Controller_AcknowledgeAlerts();
        controller_acknowledgeAlerts.createActivity_AcknowledgeAlert(user.NRIC, alertListView, alertAdapter, Activity_ViewAlerts.this);

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> Activity_ViewAlerts.this.finish());
    }
}
