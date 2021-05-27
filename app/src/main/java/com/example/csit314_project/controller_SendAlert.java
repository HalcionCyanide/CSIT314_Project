package com.example.csit314_project;

import android.content.Context;

public class controller_SendAlert {

    entity_Alert toSend;

    boolean validateOnSendAlert(String NRIC, String dateTime, String message, Context context) {
        if (NRIC.isEmpty() || dateTime.isEmpty() || message.isEmpty()) {
            return false; //disallow empty alerts
        }

        toSend = new entity_Alert(NRIC, dateTime, message);
        toSend.addAlert(context);
        return true;
    }
}
