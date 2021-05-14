/*
filename    GenericManageUserActivity.java
authors     Zheng Qingping, Derron, Jason
UOW email   qzheng011@uowmail.edu.au
Course:     CSIT314
Brief Description:
GenericManageUserActivity class
*/
package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GenericManageUserActivity extends Activity {

    String fakeNRIC;
    ListView userInfo;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_manageuser);
        UserController UC = UserController.getInstance();

        userInfo = findViewById(R.id.list_UserInfo);

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> adapter;

        displayUserInfo(arrayList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        userInfo.setAdapter(adapter);

        userInfo.setOnItemClickListener((parent, view, position, id) -> {
            String text = (String) parent.getItemAtPosition(position);
            //if clicked on suspend and you are health_org
            if(text.contains("Suspen") && UC.currentUser.role.equals("Health_Org")) {
                UC.toggleSuspension(fakeNRIC, GenericManageUserActivity.this);
                fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
                User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);
                arrayList.set(7, "Toggle Suspended: " + displayedUser.isSuspend);
            }
            //if clicked on covid and you are health_staff
            else if(text.contains("Covid") && UC.currentUser.role.equals("Health_Staff")) {
                UC.toggleCovid(fakeNRIC, GenericManageUserActivity.this);
                fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
                User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);
                arrayList.set(6, "Toggle Covid: " + displayedUser.hasCovid);
            }
            else if(text.contains("Vac")) {
                //perform actions for view of vaccinations
                generateVaccineDialog();
            }
            else if(text.contains("Travel")) {
                //perform actions for view of travel history
                generateTravelHistoryDialog();
            }
            adapter.notifyDataSetChanged();
        });

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> {
            //back btn interaction here
            GenericManageUserActivity.this.finish();
        });

        Button btn_sendAlert = findViewById(R.id.btn_sendAlert);
        btn_sendAlert.setOnClickListener(view -> {
            //sendAlert btn interaction here
            //TODO INTERACTION FOR SENDING ALERT
        });

        if (UC.currentUser.role.equals("Health_Staff")) {
            //set visibility of button SEND ALERT
            btn_sendAlert.setVisibility(View.VISIBLE);
        }
    }

    /*
    Function Name: generateVaccineDialog
    Brief Description: creates a Dialog based on R.layout.manage_vaccination
    Parameters: None
    */
    void generateVaccineDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.manage_vaccination, null);

        UserController UC = UserController.getInstance();
        User user = UC.validateOnSearchUser(fakeNRIC,GenericManageUserActivity.this);
        ListView vListView = userPopup.findViewById(R.id.list_vaccination);
        TextView txt_NRIC = userPopup.findViewById(R.id.txt_NRIC);

        txt_NRIC.setText(fakeNRIC);

        ArrayList<String> vaccineArrayList = new ArrayList<>();

        vaccineArrayList.add("Vaccination Brand: " + user.vaccinations.vaccination_brand);
        vaccineArrayList.add("First Vaccination: " + user.vaccinations.first_vaccination);
        vaccineArrayList.add("Second Vaccination: " + user.vaccinations.second_vaccination);

        ArrayAdapter<String> vaccineAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vaccineArrayList);
        vListView.setAdapter(vaccineAdapter);

        vListView.setOnItemClickListener((parent, view, position, id) -> {
            String text = (String) parent.getItemAtPosition(position);
            if (text.contains("Brand")) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(GenericManageUserActivity.this);
                alertDialog.setTitle("Edit Brand");

                //POPULATE SPINNER
                final Spinner input = new Spinner(GenericManageUserActivity.this);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        GenericManageUserActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.Vax_Types));
                input.setAdapter(arrayAdapter);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alertDialog.setPositiveButton("OK", (dialog, which) -> {
                    //TODO INTERACTION FOR CHANGING THE BRAND
                    //THIS IS THE EQUIVALENT OF ONCLICK
                    String selectedBrand = input.getSelectedItem().toString();
                });
                alertDialog.setView(input);
                alertDialog.show();
            }
            else if (text.contains("First")) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(GenericManageUserActivity.this);
                alertDialog.setTitle("Edit First Vaccine");
                final EditText input = new EditText(GenericManageUserActivity.this);
                input.setFocusable(false);
                input.setHint("Select First Vaccine Date");
                DatePicker datePicker = new DatePicker(this, input);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alertDialog.setPositiveButton("OK", (dialog, which) -> {
                    //TODO INTERACTION FOR CHANGING THE FIRST VACCINE
                    //THIS IS THE EQUIVALENT OF ONCLICK
                    //WRITE TO DATABASE
                    //USER NRIC is just fakeNRIC
                    //Date to write is input.getText.toString()

                });
                alertDialog.setView(input);
                alertDialog.show();

            }
            else if (text.contains("Second")) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(GenericManageUserActivity.this);
                alertDialog.setTitle("Edit Second Vaccine");
                final EditText input = new EditText(GenericManageUserActivity.this);
                input.setFocusable(false);
                input.setHint("Select Second Vaccine Date");
                DatePicker datePicker = new DatePicker(this, input);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alertDialog.setPositiveButton("OK", (dialog, which) -> {
                    //TODO INTERACTION FOR CHANGING THE Second VACCINE
                    //THIS IS THE EQUIVALENT OF ONCLICK
                    //WRITE TO DATABASE
                    //USER NRIC is just fakeNRIC
                    //Date to write is input.getText.toString()
                });
                alertDialog.setView(input);
                alertDialog.show();
            }
        });


        Button btn_back = userPopup.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> dialog.dismiss());
        dialogBuilder.setView(userPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    /*
    Function Name: generateTravelHistoryDialog
    Brief Description: creates a Dialog based on R.layout.manage_travelhistory
    Parameters: None
    */
    void generateTravelHistoryDialog() {
        UserController UC = UserController.getInstance();
        User user = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View userPopup = getLayoutInflater().inflate(R.layout.manage_travelhistory, null);

        TextView txt_NRIC = userPopup.findViewById(R.id.txt_NRIC);
        ListView list_TravelHistory = userPopup.findViewById(R.id.list_TravelHistory);

        txt_NRIC.setText(fakeNRIC);

        ArrayList<String> THArrayList = new ArrayList<>();

        for (int i=0; i < user.travelHistories.size(); i++)
        {
            String tempTravHist = user.travelHistories.get(i).timeIn + user.travelHistories.get(i).timeOut + user.travelHistories.get(i).location;
            THArrayList.add(tempTravHist);
        }

        ArrayAdapter<String> THadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, THArrayList);
        list_TravelHistory.setAdapter(THadapter);

        Button btn_back = userPopup.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> dialog.dismiss());
        dialogBuilder.setView(userPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    /*
    Function Name: displayUserInfo
    Brief Description: Default user info printing
    Parameters: None
    */
    void displayUserInfo(ArrayList<String> arrayList) {
        fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");

        UserController UC = UserController.getInstance();
        User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);

        arrayList.add("First Name: " + displayedUser.firstName);
        arrayList.add("Last Name: " + displayedUser.lastName);
        arrayList.add("Gender: " + displayedUser.gender);
        arrayList.add("Email: " + displayedUser.email);
        arrayList.add("Contact: " + displayedUser.contactNumber);
        arrayList.add("User Type: " + displayedUser.role);
        arrayList.add("Toggle Covid: " + displayedUser.hasCovid);
        arrayList.add("Toggle Suspended: " + displayedUser.isSuspend);
        arrayList.add("Click to view Vaccinations");
        arrayList.add("Click to view Travel History");
    }
}