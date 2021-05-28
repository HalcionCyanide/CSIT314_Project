package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Activity_CheckIn extends Activity {

    public static int SENT_ITEM = 1234;
    ArrayList<String> checkInLocations;
    EditText txt_checkInLocation;
    Button btn_confirmCheckIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_checkin);

        checkInLocations = getIntent().getExtras().getStringArrayList("checkInLocations");
        txt_checkInLocation = findViewById(R.id.txt_checkInLocation);

        btn_confirmCheckIn = findViewById(R.id.btn_confirmCheckIn);
        btn_confirmCheckIn.setOnClickListener(v -> {
            //do other check in stuff here
            String trueLocation = txt_checkInLocation.getText().toString();
            //grab current time in-case
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            Date date = new Date();
            //adding current location to local list
            checkInLocations.add("Checked in to: " + trueLocation + " at " + formatter.format(date));

            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("checkInLocations", checkInLocations);
            setResult(SENT_ITEM, resultIntent);
            Activity_CheckIn.this.finish();
        });
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
