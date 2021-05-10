package com.example.csit314_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class Vaccination {
    String NRIC;
    String vaccination_brand;
    String first_vaccination;
    String second_vaccination;

    DatabaseHelper dbHelper;

    public void addVaccination(String NRIC, String vaccination_brand,String first_vaccination, Context context) {
        //OPEN DB
        dbHelper = new DatabaseHelper(context);
        try{
            dbHelper.createDataBase();
        } catch (IOException e){
            e.printStackTrace();
        }
        if (dbHelper.openDataBase()) {
            //attempt to search for vaccination
            //add vaccination
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("NRIC", NRIC);
            values.put("Vaccination_Brand", vaccination_brand);
            values.put("First_Vaccination", first_vaccination);

            db.insert("UserData", null, values);


        }
    }
}
