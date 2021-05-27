package com.example.csit314_project;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_AcknowledgeAlert {
    public void acknowledge(String NRIC, ListView alertListView, ArrayAdapter<String> alertAdapter, Context context){
        alertListView.setOnItemClickListener((parent, view, position, id) -> {
            String text = (String)parent.getItemAtPosition(position);
            //splice the text and feed it to onAcknowledgeAlert
            String dateTime = text.substring(text.lastIndexOf("On:") + 4, text.lastIndexOf("\n"));
            String message = text.substring(text.lastIndexOf("\n") + 1);
            if (onAcknowledgeAlert(NRIC, dateTime, message, context)) {
                //delete the acknowledge one from the listview
                alertAdapter.remove(text);
                displaySuccess(context);
            }
            else {
                displayError(context);
            }
        });

        alertListView.setAdapter(alertAdapter);
    }

    boolean onAcknowledgeAlert(String NRIC, String dateTime, String message, Context context) {
        Controller_AcknowledgeAlerts controller_acknowledgeAlerts = new Controller_AcknowledgeAlerts();
        return controller_acknowledgeAlerts.validateOnAcknowledgeAlert(NRIC, dateTime, message, context);
    }

    /*
   Function Name: displaySuccess
   Brief Description: Prints Success toast
   Parameters: None
   */
    void displaySuccess(Context context) {
        Toast.makeText(context, "Operation Success", Toast.LENGTH_SHORT).show();
    }

    /*
    Function Name: displayError
    Brief Description: Prints Error toast
    Parameters: None
    */
    void displayError(Context context) {
        Toast.makeText(context, "Operation Failed!", Toast.LENGTH_SHORT).show();
    }
}
