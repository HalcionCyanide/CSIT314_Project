package com.example.csit314_project;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Controller_AcknowledgeAlerts {

    public void createActivity_AcknowledgeAlert(String nric, ListView alertListView, ArrayAdapter<String> alertAdapter, Context context) {
        Activity_AcknowledgeAlert activity_acknowledgeAlert = new Activity_AcknowledgeAlert();

        activity_acknowledgeAlert.acknowledge(nric, alertListView, alertAdapter, context);
    }

    public boolean validateOnAcknowledgeAlert(String nric, String dateTime, String message, Context context) {
        Alert alert = new Alert(nric, dateTime, message);
        return alert.acknowledgeAlert( context);
    }
}
