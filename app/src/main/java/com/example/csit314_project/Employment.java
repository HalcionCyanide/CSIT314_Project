/*
filename	Employment.java
authors    Derron, Jason
Course: 	CSIT314
Brief Description: Employment base class
*/
package com.example.csit314_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class Employment {
    String NRIC;
    String employementLocation;


    DatabaseHelper dbHelper;

    public String findLocationByNRIC(String NRIC, Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "SELECT * FROM Employment WHERE NRIC = '" + NRIC + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                String location = cursor.getString(1);
                cursor.close();
                return location;

            } else {
                return null;
            }
        }
        return null;
    }

    public List<Employment> gatherEmployment(String location, Context context){
        List<Employment> result = new ArrayList<>();
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "SELECT * FROM Employment WHERE Employment_Location = '" + location + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null ) {
                while (cursor.moveToNext()){
                    Employment data = new Employment();
                    data.NRIC =  cursor.getString(0);
                    data.employementLocation =  cursor.getString(1);

                    result.add(data);
                }
                cursor.close();
            }
        }
        return result;
    }
}
