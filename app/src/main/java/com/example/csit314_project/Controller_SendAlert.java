package com.example.csit314_project;

import android.content.Context;

public class Controller_SendAlert {

    Alert toSend;

    boolean validateOnSendAlert(String NRIC, String dateTime, String message, Context context) {
        if (NRIC.isEmpty() || dateTime.isEmpty() || message.isEmpty()) {
            return false; //disallow empty alerts
        }

        toSend = new Alert(NRIC, dateTime, message);
        return toSend.addAlert(context);
    }
}
