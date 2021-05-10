package com.example.csit314_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TravelHistory {
    public String NRIC;
    public String timeIn;
    public String timeOut;
    public String location;

    public String getDuration () {
        return updateDuration(timeIn, timeOut);
    }
    private String updateDuration(String timeIn, String timeOut){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;

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
                returnStr += difference_In_Minutes
                        + " minutes, ";
            }

            if (difference_In_Seconds > 0) {
                returnStr += difference_In_Seconds
                        + " seconds, ";
            }
        }
        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    DatabaseHelper dbHelper;

    public List<String> getByMostCases(int limit, Context context) {
        dbHelper = new DatabaseHelper(context);
        List<String> tempList = new ArrayList<String>();

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    String data = cursor.getString(0) + " " + cursor.getString(1);
                    tempList.add(data);
                }
                cursor.close();
            }
        }
        return tempList;
    }

    public List<String> getByMostCheckIn(int limit, Context context) {
        dbHelper = new DatabaseHelper(context);
        List<String> tempList = new ArrayList<String>();

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    String data = cursor.getString(0) + " " + cursor.getString(1);
                    tempList.add(data);
                }
                cursor.close();
            }
        }
        return tempList;
    }
}
