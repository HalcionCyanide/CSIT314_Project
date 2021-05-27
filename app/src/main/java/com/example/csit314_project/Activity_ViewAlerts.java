package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_ViewAlerts extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_viewalerts);



        UserController UC = UserController.getInstance();
        User user = UC.validateOnSearchUser(UC.currentUser.NRIC, Activity_ViewAlerts.this);
        Controller_ViewAlerts controller_viewAlerts = new Controller_ViewAlerts();
        if(UC.currentUser.role.equals("Public_User")) {
            List<Alert> userAlerts = controller_viewAlerts.validateFindAlertListByNRIC(UC.currentUser.NRIC, Activity_ViewAlerts.this);
        }
        else{

        }

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
        //btn_back.setOnClickListener(v -> dialog.dismiss());



        btn_back.setOnClickListener(v -> Activity_ViewAlerts.this.finish());

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
