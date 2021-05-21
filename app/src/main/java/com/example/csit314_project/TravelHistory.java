/*
filename    TravelHistory.java
authors     Zheng Qingping
UOW email   qzheng011@uowmail.edu.au
Course:     CSIT314
Brief Description:
Travel History base class
*/
package com.example.csit314_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TravelHistory {
    public String NRIC;
    public String timeIn;
    public String timeOut;
    public String location;

    DatabaseHelper dbHelper;

    /*
    Function Name: addTravelHistory
    Brief Description: Accesses the database, adding a new entry
    Parameters:
    NRIC : Primary key
    timeIn : time in the location
    timeOut : time out of the location
    location : a random place
    context : app context for the database opening
    */
    public boolean addTravelHistory(String NRIC, String timeIn, String timeOut, String location, Context context) {
        //OPEN DB
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            //add vaccination
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("NRIC", NRIC);
            values.put("CheckIn", timeIn);
            values.put("CheckOut", timeOut);
            values.put("Location", location);

            db.insert("TravelHistory", null, values);
            return true;
        }
        return false;
    }

    /*
    Function Name: getDuration
    Brief Description: returns the duration based on Time in and time out
    Parameters: None
    */
    public String getDuration () {
        return updateDuration(timeIn, timeOut);
    }

    /*
    Function Name: updateDuration
    Brief Description: measures the duration of the visit.
    Parameters:
    timeIn : time of check in, formatted in "dd/MM/yyyy HH:mm"
    timeOut : time of check out, formatted in "dd/MM/yyyy HH:mm"
    */
    private String updateDuration(String timeIn, String timeOut){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        String returnStr = "";

        try {
            Date d1 = sdf.parse(timeIn);
            Date d2 = sdf.parse(timeOut);

            // Calculate time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            // Calculate time difference in
            // seconds, minutes, hours, years,
            // and days

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;

            long difference_In_Years
                    = (difference_In_Time
                    / (1000L * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;

            if (difference_In_Years > 0) {
                returnStr += difference_In_Years
                        + " years, ";
            }

            if (difference_In_Days > 0) {
                returnStr += difference_In_Days
                        + " days, ";
            }

            if (difference_In_Hours > 0) {
                returnStr += difference_In_Hours
                        + " hours, ";
            }

            if (difference_In_Minutes > 0) {
                returnStr += difference_In_Minutes + " minute";
                if (difference_In_Minutes > 1) {
                    returnStr += "s";
                }
            }
        }
        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    /*
    Function Name: getByMostCases
    Brief Description: Accesses the database, returning a list of locations where COVID = 1, sorted.
    Parameters:
    limit : maximum number of entries
    context : app context for the database opening
    */
    public List<String> getByMostCases(int limit, Context context) {
        dbHelper = new DatabaseHelper(context);
        List<String> tempList = new ArrayList<>();

        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query =
                    "SELECT Location, count(HasCovid)" +
                            "FROM UserData, TravelHistory " +
                            "WHERE UserData.NRIC = TravelHistory.NRIC " +
                            "AND HasCovid = 1 " +
                            "GROUP BY Location " +
                            "ORDER BY count(HasCovid) DESC " +
                            "LIMIT " + limit;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String data = cursor.getString(0) + " Cases: " + cursor.getString(1);
                    tempList.add(data);
                }
                cursor.close();
            }
        }
        return tempList;
    }

    /*
    Function Name: getByMostCheckIn
    Brief Description: Accesses the database, returning a list of locations, by number of times Checked-in.
    Parameters:
    limit : maximum number of entries
    context : app context for the database opening
    */
    public List<String> getByMostCheckIn(int limit, Context context) {
        dbHelper = new DatabaseHelper(context);
        List<String> tempList = new ArrayList<>();

        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query =
                    "SELECT Location, count(CheckIn) " +
                    "FROM UserData, TravelHistory " +
                    "WHERE UserData.NRIC = TravelHistory.NRIC " +
                    "GROUP BY Location " +
                    "ORDER BY count(CheckIn) DESC " +
                    "LIMIT " + limit;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String data = cursor.getString(0) + " Check-ins: " + cursor.getString(1);
                    tempList.add(data);
                }
                cursor.close();
            }
        }
        return tempList;
    }

    public List<TravelHistory> findCustomersByDateAndLocation(String location, String date, Context context) {
        dbHelper = new DatabaseHelper(context);
        List<TravelHistory> tempList = new ArrayList<>();

        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query =
                    "SELECT * " + "FROM TravelHistory " +
                    "WHERE Location = '" + location + "'" +
                    " AND CheckIn LIKE '" + date + "%'" ;

            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    TravelHistory data = new TravelHistory();
                    data.NRIC = cursor.getString(0);
                    data.timeIn = cursor.getString(1);
                    data.timeOut = cursor.getString(2);
                    data.location = cursor.getString(3);

                    tempList.add(data);
                }
                cursor.close();
            }
        }
        return tempList;

    }
}
