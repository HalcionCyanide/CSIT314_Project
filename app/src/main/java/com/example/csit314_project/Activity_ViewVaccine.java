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

import java.util.ArrayList;

public class Activity_ViewVaccine extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_vaccination);

        String fakeNRIC;
        UserController UC = UserController.getInstance();
        User user = UC.currentUser;
        ListView vListView = findViewById(R.id.list_vaccination);
        TextView txt_NRIC = findViewById(R.id.txt_NRIC);
        if(!user.role.equals("Public_User")) {
            fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
        }
        else{
            fakeNRIC = user.NRIC;
        }

        txt_NRIC.setText(fakeNRIC);

        Controller_ManageVaccine controller_manageVaccine = new Controller_ManageVaccine();
        Vaccination targetVac = controller_manageVaccine.validateFindVaccinationByNRIC(fakeNRIC, Activity_ViewVaccine.this);

        ArrayList<String> vaccineArrayList = new ArrayList<>();
        vaccineArrayList.add("Vaccination Brand: " + targetVac.vaccination_brand);
        vaccineArrayList.add("First Vaccination: " + targetVac.first_vaccination);
        vaccineArrayList.add("Second Vaccination: " + targetVac.second_vaccination);

        ArrayAdapter<String> vaccineAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vaccineArrayList);
        vListView.setAdapter(vaccineAdapter);

        if (UC.currentUser.role.equals("Health_Staff")) {
            vListView.setOnItemClickListener((parent, view, position, id) -> {
                String text = (String) parent.getItemAtPosition(position);
                if (text.contains("Brand")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_ViewVaccine.this);
                    alertDialog.setTitle("Edit Brand");

                    //POPULATE SPINNER
                    final Spinner input = new Spinner(Activity_ViewVaccine.this);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                            Activity_ViewVaccine.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Vax_Types));
                    input.setAdapter(arrayAdapter);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);

                    alertDialog.setPositiveButton("OK", (dialog, which) -> {
                        //THIS IS THE EQUIVALENT OF ONCLICK
                        String selectedBrand = input.getSelectedItem().toString();
                        controller_manageVaccine.validateUpdateVaccineBrand(fakeNRIC, selectedBrand, Activity_ViewVaccine.this);
                        Vaccination displayVac = controller_manageVaccine.validateFindVaccinationByNRIC(fakeNRIC, Activity_ViewVaccine.this);
                        vaccineArrayList.set(0, "Vaccination Brand: " + displayVac.vaccination_brand);
                        vaccineAdapter.notifyDataSetChanged();
                    });
                    alertDialog.setView(input);
                    alertDialog.show();
                } else if (text.contains("First")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_ViewVaccine.this);
                    alertDialog.setTitle("Edit First Vaccine");
                    final EditText input = new EditText(Activity_ViewVaccine.this);
                    input.setFocusable(false);
                    input.setHint("Select First Vaccine Date");
                    DatePicker datePicker = new DatePicker(this, input);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);

                    alertDialog.setPositiveButton("OK", (dialog, which) -> {
                        //THIS IS THE EQUIVALENT OF ONCLICK

                        String firstVaccine = input.getText().toString();
                        if (!firstVaccine.isEmpty()) {
                            controller_manageVaccine.validateUpdateFirstVaccineDate(fakeNRIC, firstVaccine, Activity_ViewVaccine.this);
                            Vaccination displayVac = controller_manageVaccine.validateFindVaccinationByNRIC(fakeNRIC, Activity_ViewVaccine.this);
                            vaccineArrayList.set(1, "First Vaccination: " + displayVac.first_vaccination);
                            vaccineAdapter.notifyDataSetChanged();

                        }

                    });
                    alertDialog.setView(input);
                    alertDialog.show();

                } else if (text.contains("Second")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_ViewVaccine.this);
                    alertDialog.setTitle("Edit Second Vaccine");
                    final EditText input = new EditText(Activity_ViewVaccine.this);
                    input.setFocusable(false);
                    input.setHint("Select Second Vaccine Date");
                    DatePicker datePicker = new DatePicker(this, input);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);

                    alertDialog.setPositiveButton("OK", (dialog, which) -> {
                        //THIS IS THE EQUIVALENT OF ONCLICK
                        String secondVaccine = input.getText().toString();
                        if (!secondVaccine.isEmpty()) {
                            controller_manageVaccine.validateUpdateSecondVaccineDate(fakeNRIC, secondVaccine, Activity_ViewVaccine.this);
                            Vaccination displayVac = controller_manageVaccine.validateFindVaccinationByNRIC(fakeNRIC, Activity_ViewVaccine.this);
                            vaccineArrayList.set(2, "Second Vaccination: " + displayVac.second_vaccination);
                            vaccineAdapter.notifyDataSetChanged();
                        }
                    });
                    alertDialog.setView(input);
                    alertDialog.show();
                }

            });
        }


        Button btn_back = findViewById(R.id.btn_back);
        //btn_back.setOnClickListener(v -> dialog.dismiss());



        btn_back.setOnClickListener(v -> Activity_ViewVaccine.this.finish());

    }
}
