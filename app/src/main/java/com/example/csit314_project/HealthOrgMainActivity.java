package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.List;

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

        Button btn_searchUser = findViewById(R.id.btn_searchUser);
        btn_searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUserDialog();
            }
        });

        Button btn_logout = findViewById(R.id.btn_search);
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
        final String[] txt_gender = new String[1];

        Spinner spinner_role = userPopup.findViewById(R.id.spinner_role);
        final String[] txt_role = new String[1];

        TextView txt_resultUsername = userPopup.findViewById(R.id.txt_resultUsername);
        TextView txt_resultPassword = userPopup.findViewById(R.id.txt_resultPassword);

        Button btn_confAddUser = userPopup.findViewById(R.id.btn_confaddUser);
        Button btn_cancel = userPopup.findViewById(R.id.btn_cancel);

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
        btn_confAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Parse Role
                User.USER_TYPE user_type;
                switch (txt_role[0]) {
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
                        throw new IllegalStateException("Unexpected value: " + txt_role[0]);
                }

                String username = txt_resultUsername.getText().toString();
                String password = txt_resultPassword.getText().toString();

                //call controller
                if (onAddUser(
                        txt_NRIC.getText().toString(),
                        txt_gender[0],
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

        dialogBuilder.setView(userPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    public void searchUserDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.healthorg_search, null);
        //TODO ADD BUTTON FUNCTIONALITY

        Button btn_search = userPopup.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO CODE HERE, REFER TO addNewUserDialog
                //TODO onSearchUser THIS IS YOUR NEW FRIEND
            }
        });

        Button btn_back = userPopup.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(userPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    boolean onAddUser(String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, User.USER_TYPE userType, Context context) {
        UserController UC = UserController.getInstance();
        return UC.validateOnAddUser(NRIC, gender, firstName, lastName, email, contactNumber, username, password, userType, context);
    }

    List<User> onSearchUser(String NRIC, String userType, String username, Context context) {
        UserController UC = UserController.getInstance();
        return UC.validateOnSearchUser(NRIC, userType, username, context);
    }

    void displaySuccess() {
        Toast.makeText(getApplicationContext(), "Operation Success", Toast.LENGTH_SHORT).show();
    }

    void displayError() {
        Toast.makeText(getApplicationContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
    }
}
