/*
filename   HealthOrgMainActivity.java
authors    Zheng Qingping, Derron, Jason
UOW email	qzheng011@uowmail.edu.au
Course: 	CSIT314
Brief Description: Health Org main Activity
*/

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

public class HealthOrgMainActivity extends Activity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthorg_main);
        displayPage();
    }

    /*
    Function Name: displayPage
    Brief Description: Prints UI
    Parameters: None
    */
    void displayPage() {
        UserController UC = UserController.getInstance();
        TextView currUser = findViewById(R.id.txt_currentUser);
        currUser.setText(UC.currentUser.username);

        Button btn_addUser = findViewById(R.id.btn_addUser);
        btn_addUser.setOnClickListener(v -> addNewUserDialog());

        Button btn_searchUser = findViewById(R.id.btn_searchUser);
        btn_searchUser.setOnClickListener(v -> searchUserDialog());

        Button btn_generateReport = findViewById(R.id.btn_generateReport);
        btn_generateReport.setOnClickListener(v -> generateReportDialog());

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Log out!", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(HealthOrgMainActivity.this, LoginActivity.class);
            HealthOrgMainActivity.this.startActivity(mainIntent);
            HealthOrgMainActivity.this.finish();
        });
    }

    /*
    Function Name: addNewUserDialog
    Brief Description: creates a Dialog based on R.layout.healthorg_adduser
    Parameters: None
    */
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
                    HealthOrgMainActivity.this)) {
                displaySuccess();
            }
            else {
                displayError();
            }
            dialog.dismiss();
        });
        btn_cancel.setOnClickListener(v -> dialog.dismiss());

        dialogBuilder.setView(userPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    /*
    Function Name: searchUserDialog
    Brief Description: creates a Dialog based on R.layout.generic_searchuser
    Parameters: None
    */
    public void searchUserDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.generic_searchuser, null);

        ListView results = userPopup.findViewById(R.id.list_userResults);
        EditText txt_NRIC = userPopup.findViewById(R.id.txt_NRICinput);
        EditText txt_username = userPopup.findViewById(R.id.txt_NameInput);

        Spinner spinner_role = userPopup.findViewById(R.id.spinner_role);
        final String[] txt_role = new String[1];
        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_role[0] = spinner_role.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btn_search = userPopup.findViewById(R.id.btn_logout);
        btn_search.setOnClickListener(v -> {
            if (onSearchUser(results, txt_NRIC.getText().toString(), txt_role[0], txt_username.getText().toString(), HealthOrgMainActivity.this)) {
                //if there are results
                displaySuccess();
            }
            else {
                displayError();
            }
        });

        Button btn_back = userPopup.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> dialog.dismiss());

        dialogBuilder.setView(userPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    /*
    Function Name: generateReportDialog
    Brief Description: creates a Dialog based on R.layout.healthorg_generatereport
    Parameters: None
    */
    public void generateReportDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.healthorg_generatereport, null);

        ListView mostCheckIn = userPopup.findViewById(R.id.list_mostCheckIn);
        ListView mostCases = userPopup.findViewById(R.id.list_mostCases);
        Button btn_back = userPopup.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> dialog.dismiss());

        //there were results
        if(onRetrieveCovidStats(mostCases, mostCheckIn, this)) {
            displaySuccess();
        }
        else {
            displayError();
        }

        dialogBuilder.setView(userPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    /*
    Function Name: onAddUser
    Brief Description: when triggered, calls for controller to perform the action
    Parameters:
    ALL parameters from User class
    context : app context for the database opening
    */
    boolean onAddUser(String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        UserController UC = UserController.getInstance();
        return UC.validateOnAddUser(NRIC, gender, firstName, lastName, email, contactNumber, username, password, userType, context);
    }

    /*
    Function Name: onSearchUser
    Brief Description: when triggered, calls for controller to perform the action
    Parameters:
    result : resulting list view to modify
    NRIC, userType, username : parameters from UI, passed to controller
    context : app context for the database opening
    */
    boolean onSearchUser(ListView result, String NRIC, String userType, String username, Context context) {
        UserController UC = UserController.getInstance();
        List<User> tempList = UC.validateOnSearchUser(NRIC, userType, username, context);

        ArrayList<String> lvUsers = new ArrayList<>();

        for (int i=0; i < tempList.size(); i++)
        {
            String tempString = tempList.get(i).NRIC + ", " + tempList.get(i).firstName + " " + tempList.get(i).lastName;
            lvUsers.add(tempString);
        }

        ArrayAdapter<String> usersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lvUsers);
        result.setAdapter(usersAdapter);

        result.setOnItemClickListener((parent, view, position, id) -> {
            Intent mainIntent = new Intent(HealthOrgMainActivity.this, GenericManageUserActivity.class);
            String text = (String) parent.getItemAtPosition(position);
            String fakeNRIC = text.substring(0, text.indexOf(","));
            mainIntent.putExtra("SINGLE_NRIC", fakeNRIC);
            HealthOrgMainActivity.this.startActivity(mainIntent);
        });

        return !tempList.isEmpty();
    }

    /*
    Function Name: onRetrieveCovidStats
    Brief Description: when triggered, calls for controller to perform the action
    Parameters:
    mostCases : resulting list view to modify
    mostCheckIn : resulting list view to modify
    context : app context for the database opening
    */
    boolean onRetrieveCovidStats(ListView mostCases, ListView mostCheckIn, Context context) {
        UserController UC = UserController.getInstance();
        List<String> cases, checks;
        checks = UC.validateGetByMostCheckIn(4, context);
        cases = UC.validateGetByMostCases(4, context);

        ArrayList<String> mCases = new ArrayList<>(cases);
        ArrayAdapter<String> casesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCases);
        mostCases.setAdapter(casesAdapter);
        ArrayList<String> mCheck = new ArrayList<>(checks);
        ArrayAdapter<String> checksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCheck);
        mostCheckIn.setAdapter(checksAdapter);

        return (!cases.isEmpty() || !checks.isEmpty());
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
