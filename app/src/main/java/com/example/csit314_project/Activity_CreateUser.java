package com.example.csit314_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_CreateUser extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthorg_adduser);

        EditText txt_firstName = findViewById(R.id.txt_firstName);
        EditText txt_lastName = findViewById(R.id.txt_lastName);
        EditText txt_NRIC = findViewById(R.id.txt_NRIC);
        EditText txt_email = findViewById(R.id.txt_email);
        EditText txt_contact = findViewById(R.id.txt_contact);

        Spinner spinner_gender = findViewById(R.id.spinner_gender);
        final String[] txt_gender = new String[1];

        Spinner spinner_role = findViewById(R.id.spinner_role);
        final String[] txt_role = new String[1];

        TextView txt_resultUsername = findViewById(R.id.txt_resultUsername);
        TextView txt_resultPassword = findViewById(R.id.txt_resultPassword);

        Button btn_confAddUser = findViewById(R.id.btn_confaddUser);
        Button btn_cancel = findViewById(R.id.btn_cancel);

        txt_firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_resultUsername.setText(String.format("%s%s90", txt_firstName.getText().toString(), txt_lastName.getText().toString()));
                txt_resultPassword.setText(String.format("%s%s%s",
                        txt_firstName.getText().toString(),
                        txt_lastName.getText().toString(),
                        txt_NRIC.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_resultUsername.setText(String.format("%s%s90", txt_firstName.getText().toString(), txt_lastName.getText().toString()));
                txt_resultPassword.setText(String.format("%s%s%s",
                        txt_firstName.getText().toString(),
                        txt_lastName.getText().toString(),
                        txt_NRIC.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_NRIC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_resultUsername.setText(String.format("%s%s90", txt_firstName.getText().toString(), txt_lastName.getText().toString()));
                txt_resultPassword.setText(String.format("%s%s%s",
                        txt_firstName.getText().toString(),
                        txt_lastName.getText().toString(),
                        txt_NRIC.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_gender[0] = spinner_gender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_role[0] = spinner_role.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_confAddUser.setOnClickListener(v -> {
            //Parse Role

            String username = txt_resultUsername.getText().toString();
            String password = txt_resultPassword.getText().toString();

            //call controller
            boolean willFail = (
                    txt_firstName.getText().toString().isEmpty() ||
                            txt_lastName.getText().toString().isEmpty() ||
                            txt_NRIC.getText().toString().isEmpty() ||
                            txt_email.getText().toString().isEmpty() ||
                            txt_contact.getText().toString().isEmpty()
            );
            if (!willFail && onAddUser(
                    txt_NRIC.getText().toString(),
                    txt_gender[0],
                    txt_firstName.getText().toString(),
                    txt_lastName.getText().toString(),
                    txt_email.getText().toString(),
                    txt_contact.getText().toString(),
                    username,
                    password,
                    txt_role[0],
                    Activity_CreateUser.this)) {
                displaySuccess();
            } else {
                displayError();
            }
            Activity_CreateUser.this.finish();

        });
        btn_cancel.setOnClickListener(v -> Activity_CreateUser.this.finish());
    }


    /*
    Function Name: onAddUser
    Brief Description: when triggered, calls for controller to perform the action
    Parameters:
    ALL parameters from User class
    context : app context for the database opening
    */
    boolean onAddUser(String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        Controller_CreateUser controller_createUser = new Controller_CreateUser();
        return controller_createUser.validateOnAddUser(NRIC, gender, firstName, lastName, email, contactNumber, username, password, userType, context);
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
