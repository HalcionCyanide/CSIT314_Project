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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Alert {
    public String NRIC;
    public String dateTime;
    public String message;
    public boolean acknowledge;


    DatabaseHelper dbHelper;

    public Alert() {

    }

    public Alert(String nric, String dateTime, String message) {
        this.NRIC = nric;
        this.dateTime = dateTime;
        this.message = message;
        this.acknowledge = false;
    }

    public Alert(String nric, String dateTime, String message, boolean acknowledge) {
        this.NRIC = nric;
        this.dateTime = dateTime;
        this.message = message;
        this.acknowledge = acknowledge;
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

    public List<Alert> FindAlertListByNRIC(String NRIC, Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        List<Alert> tempList = new ArrayList<>();

        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query =
                    "SELECT * " + "FROM Alerts " +
                            "WHERE ReceiveBy = '" + NRIC + "'";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Alert data = new Alert();
                    data.NRIC = cursor.getString(0);
                    data.dateTime = cursor.getString(1);
                    data.message = cursor.getString(2);
                    data.acknowledge = cursor.getInt(3) > 0;

                    tempList.add(data);
                }
                cursor.close();
            }
        }
        return tempList;
    }
}
