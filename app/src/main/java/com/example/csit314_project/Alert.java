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


    public Alert(String nric, String dateTime, String message) {
        this.NRIC = nric;
        this.dateTime = dateTime;
        this.message = message;
        this.acknowledge = false;
    }
    
    public boolean addAlert (Context context) {
        //OPEN DB
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            //add vaccination
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("ReceiveBy", this.NRIC);
            values.put("DateTime", this.dateTime);
            values.put("Message", this.message);
            values.put("Acknowledge", false); //all Alerts are not acknowledged by default

            db.insert("Alerts", null, values);
            return true;
        }
        return false;
    }

    public boolean acknowledgeAlert (Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            //add vaccination
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Acknowledge", true);
            db.update("Alerts", values,
                    "ReceiveBy = '" + this.NRIC + "'" +
                            " AND DateTime = '" + this.dateTime + "'" +
                            " AND Message = '" + this.message + "'",
                    null);
            dbHelper.close();
            return true;
        }
        return false;
    }
}
