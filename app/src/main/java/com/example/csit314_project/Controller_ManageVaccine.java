package com.example.csit314_project;

import android.content.Context;
import android.content.Intent;

public class Controller_ManageVaccine {
    public void displayVaccine(Context context){
        Intent mainIntent = new Intent(context, Activity_ViewVaccine.class);
        context.startActivity(mainIntent);
    }

    public void displayVaccine(String fakeNRIC, Context context){
        Intent mainIntent = new Intent(context, Activity_ViewVaccine.class);
        mainIntent.putExtra("SINGLE_NRIC", fakeNRIC);
        context.startActivity(mainIntent);
    }

    public Vaccination validateFindVaccinationByNRIC(String NRIC, Context context) {
        Vaccination vaccination = new Vaccination();

        return vaccination.findVaccinationByNRIC(NRIC, context);
    }

    public void validateUpdateVaccineBrand(String NRIC, String selectedBrand, Context context) {

        Vaccination vaccination = new Vaccination();
        vaccination = vaccination.findVaccinationByNRIC(NRIC, context);

        if (vaccination != null) {
            vaccination.setBrand(selectedBrand, NRIC, context);
        }
    }

    public void validateUpdateFirstVaccineDate(String NRIC, String firstVaccine, Context context) {

        Vaccination vaccination = new Vaccination();
        vaccination = vaccination.findVaccinationByNRIC(NRIC, context);
        String[] splitDate = firstVaccine.split("/");
        if (splitDate[0].length() < 2)
        {
            splitDate[0] = "0" + splitDate[0];
        }
        if (splitDate[1].length() < 2)
        {
            splitDate[1] = "0" + splitDate[1];
        }
        String fixedFirstVaccine = splitDate[0] + "/" + splitDate[1]+ "/" + splitDate[2];
        if (vaccination != null) {
            vaccination.setFirstVaccine(fixedFirstVaccine, NRIC, context);
        }
    }

    public void validateUpdateSecondVaccineDate(String NRIC, String secondVaccine, Context context) {

        Vaccination vaccination = new Vaccination();
        vaccination = vaccination.findVaccinationByNRIC(NRIC, context);
        String[] splitDate = secondVaccine.split("/");
        if (splitDate[0].length() < 2)
        {
            splitDate[0] = "0" + splitDate[0];
        }
        if (splitDate[1].length() < 2)
        {
            splitDate[1] = "0" + splitDate[1];
        }
        String fixedSecondVaccine = splitDate[0] + "/" + splitDate[1]+ "/" + splitDate[2];
        if (vaccination != null) {
            vaccination.setSecondVaccine(fixedSecondVaccine, NRIC, context);
        }
    }

    public boolean validateAddVaccineBrand(String fakeNRIC, String selectedBrand, Context context) {
        Vaccination vaccination = new Vaccination();
        return vaccination.AddVaccineBrand(fakeNRIC,selectedBrand, context );
    }
}
