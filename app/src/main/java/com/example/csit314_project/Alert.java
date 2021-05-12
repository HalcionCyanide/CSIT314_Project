/*
filename    Alert.java
authors     Derron, Jason
Course:     CSIT314
Brief Description:
Alert base class
*/
package com.example.csit314_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Alert {
    public String NRIC;
    public String dateTime;
    public String message;
    public boolean acknowledge;

    DatabaseHelper dbHelper;

    public void addAlert(String nric, String dateTime, String message, boolean acknowledge, Context context) {
        //OPEN DB
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            //add vaccination
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("NRIC", nric);
            values.put("DateTime", dateTime);
            values.put("Message", message);
            values.put("Acknowledge", acknowledge);

            db.insert("Alerts", null, values);
        }
    }
}
