package com.example.csit314_project;

import android.content.Context;
import android.content.Intent;

import java.util.List;

public class Controller_ViewAlerts {
    public void displayAlerts(Context context){
        Intent mainIntent = new Intent(context, Activity_ViewAlerts.class);
        context.startActivity(mainIntent);
    }

    public List<Alert> validateFindAlertListByNRIC(String nric, Context context) {
        Alert alert = new Alert();
        return alert.FindAlertListByNRIC(nric, context);
    }
}
