package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Activity_CheckIn extends Activity {

    ArrayList<String> checkInLocations = new ArrayList<>();
    ArrayAdapter<String> checkInLocationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_checkin);



        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_CheckIn.this);
        alertDialog.setTitle("Checking in to....");
        final EditText input = new EditText(Activity_CheckIn.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        alertDialog.setPositiveButton("OK", (dialog, which) -> {
            //grab current time in-case
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            Date date = new Date();
            //adding current location to local list
            checkInLocations.add("Checked in to: " + input.getText().toString() + " at " + formatter.format(date));
            //update the list in UI
            checkInLocationsAdapter.notifyDataSetChanged();
        });
        alertDialog.setView(input);
        alertDialog.show();
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
