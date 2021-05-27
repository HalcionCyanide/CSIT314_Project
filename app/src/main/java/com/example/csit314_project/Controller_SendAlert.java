package com.example.csit314_project;

import android.content.Context;

public class Controller_SendAlert {

    Alert toSend;
    Context callingContext;

    public Controller_SendAlert(Context context) {
        callingContext = context;
    }

    boolean validateOnSendAlert(String NRIC, String dateTime, String message) {
        if (NRIC.isEmpty() || dateTime.isEmpty() || callingContext == null) {
            return false; //disallow empty alerts
        }

        toSend = new Alert(NRIC, dateTime, message);
        return toSend.addAlert(callingContext);
    }
}
