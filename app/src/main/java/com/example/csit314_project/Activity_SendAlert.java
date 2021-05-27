package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity_SendAlert extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthstaff_sendalert);

    String fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");

    TextView txt_nric = findViewById(R.id.txt_NRIC);
        txt_nric.setText(String.format("Sending to: %s", fakeNRIC));

    EditText txt_message = findViewById(R.id.txt_alertMSG);

    Button btn_send = findViewById(R.id.btn_sendAlert);
        btn_send.setOnClickListener(v -> {
        //SEND THE ALERT HERE
        String msg = txt_message.getText().toString().isEmpty() ? "" : txt_message.getText().toString();
        Controller_SendAlert controllerSendAlert = new Controller_SendAlert(Activity_SendAlert.this);

        //dateTime stuff
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date date = new Date();
        String currDate = formatter.format(date);

        if (controllerSendAlert.validateOnSendAlert(fakeNRIC, currDate, msg)) {
            displaySuccess();
        }
        else {
            displayError();
        }
        Activity_SendAlert.this.finish();
    });

    Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> Activity_SendAlert.this.finish());
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
