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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GenericManageUserActivity extends Activity {

    String fakeNRIC;
    ListView userInfo;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_manageuser);
        UserController UC = UserController.getInstance();

        userInfo = findViewById(R.id.list_UserInfo);

        displayUserInfo();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        userInfo.setAdapter(adapter);

        userInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) parent.getItemAtPosition(position);
                //if clicked on suspend and you are health_org
                if(text.contains("Suspen") && UC.currentUser.role.equals("Health_Org")) {
                    UC.toggleSuspension(fakeNRIC, GenericManageUserActivity.this);
                    fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
                    User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);
                    arrayList.set(7, "Toggle Suspended: " + displayedUser.isSuspend);
                    adapter.notifyDataSetChanged();
                }
                //if clicked on covid and you are health_staff
                else if(text.contains("Covid") && UC.currentUser.role.equals("Health_Staff")) {
                    UC.toggleCovid(fakeNRIC, GenericManageUserActivity.this);
                    fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
                    User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);
                    arrayList.set(6, "Toggle Covid: " + displayedUser.hasCovid);
                    adapter.notifyDataSetChanged();
                }
                else if(text.contains("Vac")) {
                    //perform actions for view of vaccinations
                    generateVaccineDialog();
                }
                else if(text.contains("Travel")) {
                    //perform actions for view of travel history
                    generateTravelHistoryDialog();
                }

            }
        });

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back btn interaction here
                GenericManageUserActivity.this.finish();
            }
        });

        Button btn_sendAlert = findViewById(R.id.btn_sendAlert);
        btn_sendAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendAlert btn interaction here
                //TODO IN SPRINT 4
            }
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

        /*TODO SPIT THE INFO HERE, ITS TIED INSIDE THE USER.vaccinations
        I HAVE BINDED THE UI ELEMENTS FOR YOU
        */
        UserController UC = UserController.getInstance();
        User user = UC.validateOnSearchUser(fakeNRIC,GenericManageUserActivity.this);
        ListView vListView = userPopup.findViewById(R.id.list_vaccination);
        TextView txt_NRIC = userPopup.findViewById(R.id.txt_NRIC);

        txt_NRIC.setText(fakeNRIC);

        arrayList.clear();
        arrayList.add("Vaccination Brand: " + user.vaccinations.vaccination_brand);
        arrayList.add("First Vaccination: " + user.vaccinations.first_vaccination);
        arrayList.add("Second Vaccination: " + user.vaccinations.second_vaccination);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        vListView.setAdapter(adapter);
        ListView list_TravelHistory = userPopup.findViewById(R.id.list_TravelHistory);

        Button btn_addVax = userPopup.findViewById(R.id.btn_addVaccination);
        btn_addVax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ADD VACCINATION
                Vaccination newVaccination = new Vaccination();
                newVaccination.NRIC = fakeNRIC;

                user.vaccinations = newVaccination;

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

        /*TODO SPIT THE INFO HERE, ITS TIED INSIDE THE USER.travelhistories
        I HAVE BINDED THE UI ELEMENTS FOR YOU
        */

        TextView txt_NRIC = userPopup.findViewById(R.id.txt_NRIC);
        ListView list_TravelHistory = userPopup.findViewById(R.id.list_TravelHistory);

        txt_NRIC.setText(fakeNRIC);

        arrayList.clear();
        for (int i=0; i < user.travelHistories.size(); i++)
        {
            String temptravhist = user.travelHistories.get(i).timeIn + user.travelHistories.get(i).timeOut + user.travelHistories.get(i).location;
            arrayList.add(temptravhist);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        list_TravelHistory.setAdapter(adapter);

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

    /*
    Function Name: displayUserInfo
    Brief Description: Default user info printing
    Parameters: None
    */
    void displayUserInfo() {
        fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");

        UserController UC = UserController.getInstance();
        User displayedUser = UC.validateOnSearchUser(fakeNRIC, GenericManageUserActivity.this);

        arrayList = new ArrayList<>();
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