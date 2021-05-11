/*
filename    Vaccination.java
authors     Zheng Qingping, Derron, Jason
UOW email   qzheng011@uowmail.edu.au
Course:     CSIT314
Brief Description:
Vaccination base class
*/
package com.example.csit314_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class Vaccination {
    public String NRIC;
    public String vaccination_brand;
    public String first_vaccination;
    public String second_vaccination;

    DatabaseHelper dbHelper;

    /*
    Function Name: addVaccination
    Brief Description: Accesses the database, adding a new entry
    Parameters:
    NRIC : Primary key
    vaccination_brand : String of vaccination brand
    first_vaccination : date of first vaccination formatted in dd/MM/yyyy HH:mm
    context : app context for the database opening
    */
    public void addVaccination(String NRIC, String vaccination_brand, String first_vaccination, Context context) {
        //OPEN DB
        dbHelper = new DatabaseHelper(context);
        try{
            dbHelper.createDataBase();
        } catch (IOException e){
            e.printStackTrace();
        }
        if (dbHelper.openDataBase()) {
            //add vaccination
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("NRIC", NRIC);
            values.put("Vaccination_Brand", vaccination_brand);
            values.put("First_Vaccination", first_vaccination);

            db.insert("Vaccination", null, values);
        }
    }
}
