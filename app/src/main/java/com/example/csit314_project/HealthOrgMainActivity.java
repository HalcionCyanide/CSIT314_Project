package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HealthOrgMainActivity extends Activity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthorg_main);
        displayPage();

    }

    void displayPage() {
        UserController LC = UserController.getInstance();
        TextView currUser = findViewById(R.id.txt_currentUser);
        currUser.setText(LC.currentUser.getUsername());

        Button btn_addUser = findViewById(R.id.btn_addUser);
        btn_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUserDialog();
            }
        });

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Log out!", Toast.LENGTH_SHORT).show();
                LC.logout();
                Intent mainIntent = new Intent(HealthOrgMainActivity.this, LoginActivity.class);
                HealthOrgMainActivity.this.startActivity(mainIntent);
                HealthOrgMainActivity.this.finish();
            }
        });
    }

    public void addNewUserDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.healthorg_adduser, null);
        EditText txt_firstName = userPopup.findViewById(R.id.txt_firstName);
        EditText txt_lastName = userPopup.findViewById(R.id.txt_lastName);
        EditText txt_NRIC = userPopup.findViewById(R.id.txt_NRIC);
        EditText txt_email = userPopup.findViewById(R.id.txt_email);
        EditText txt_contact = userPopup.findViewById(R.id.txt_contact);

        Spinner spinner_gender = userPopup.findViewById(R.id.spinner_gender);
        String txt_gender = spinner_gender.getSelectedItem().toString();

        Spinner spinner_role = userPopup.findViewById(R.id.spinner_role);
        String txt_role = spinner_role.getSelectedItem().toString();

        Button btn_confAddUser = userPopup.findViewById(R.id.btn_confaddUser);
        Button btn_cancel = userPopup.findViewById(R.id.btn_cancel);

        dialogBuilder.setView(userPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        String username = txt_firstName.getText().toString() + txt_lastName.getText().toString() + "90";
        String password = txt_firstName.getText().toString() + txt_lastName.getText().toString() + txt_NRIC.getText().toString();

        TextView txt_resultUsername = userPopup.findViewById(R.id.txt_resultUsername);
        TextView txt_resultPassword = userPopup.findViewById(R.id.txt_resultPassword);
        //parse username and password btw
        txt_resultUsername.setText(username);
        txt_resultPassword.setText(password);

        btn_confAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: PROCESS THE INFO AND ADD THE PERSON
                User.USER_TYPE user_type;
                switch (txt_role) {
                    case "Public User":
                        user_type = User.USER_TYPE.PUBLIC;
                        break;
                    case "Business":
                        user_type = User.USER_TYPE.BUSINESS;
                        break;
                    case "Health Staff":
                        user_type = User.USER_TYPE.HEALTH_STAFF;
                        break;
                    case "Health Org":
                        user_type = User.USER_TYPE.HEALTH_ORG;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + txt_role);
                }

                //call controller
                if (onAddUser(
                        txt_NRIC.getText().toString(),
                        txt_gender,
                        txt_firstName.getText().toString(),
                        txt_lastName.getText().toString(),
                        txt_email.getText().toString(),
                        txt_contact.getText().toString(),
                        username,
                        password,
                        user_type,
                        HealthOrgMainActivity.this)) {
                    displaySuccess();
                }
                else {
                    displayError();
                }
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    boolean onAddUser(String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, User.USER_TYPE userType, Context context) {
        UserController UC = UserController.getInstance();
        return UC.validateOnAddUser(NRIC, gender, firstName, lastName, email, contactNumber, username, password, userType, context);
    }

    void displaySuccess() {
        Toast.makeText(getApplicationContext(), "Operation Success", Toast.LENGTH_SHORT).show();
    }

    void displayError() {
        Toast.makeText(getApplicationContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
    }
}
