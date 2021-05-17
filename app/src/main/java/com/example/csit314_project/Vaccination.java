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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Vaccination {
    public String NRIC;
    public String vaccination_brand;
    public String first_vaccination;
    public String second_vaccination;

    DatabaseHelper dbHelper;

    public Vaccination findVaccinationByNRIC(String NRIC, Context context){
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "SELECT * FROM Vaccination WHERE NRIC = '" + NRIC + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                Vaccination data = new Vaccination();
                data.NRIC = cursor.getString(0);
                data.vaccination_brand = cursor.getString(1);
                data.first_vaccination = cursor.getString(2);
                data.second_vaccination = cursor.getString(3);
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;

    }

    public void setBrand(String selectedBrand, String NRIC, Context context){
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Vaccination_Brand", selectedBrand);
            db.update("Vaccination", values, "NRIC = '" + NRIC + "'", null);
        }
        dbHelper.close();
    }

    public void setFirstVaccine(String firstVaccine, String NRIC, Context context){
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("First_Vaccination", firstVaccine);
            db.update("Vaccination", values, "NRIC = '" + NRIC + "'", null);
        }
        dbHelper.close();
    }

    public void setSecondVaccine(String secondVaccine, String NRIC, Context context){
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Second_Vaccination", secondVaccine);
            db.update("Vaccination", values, "NRIC = '" + NRIC + "'", null);
        }
        dbHelper.close();
    }
}
