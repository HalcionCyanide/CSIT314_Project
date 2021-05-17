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

    public void addAlert(String nric, String dateTime, String message, Context context) {
        //OPEN DB
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            //add vaccination
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("ReceiveBy", nric);
            values.put("DateTime", dateTime);
            values.put("Message", message);
            values.put("Acknowledge", false); //all Alerts are not acknowledged by default

            db.insert("Alerts", null, values);
        }
    }

    public boolean acknowledgeAlert (String NRIC, String dateTime, String message, Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            //add vaccination
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Acknowledge", true);
            db.update("Alerts", values,
                    "ReceiveBy = '" + NRIC + "'" +
                            " AND DateTime = '" + dateTime + "'" +
                            " AND Message = '" + message + "'",
                    null);
            dbHelper.close();
            return true;
        }
        return false;
    }
}
