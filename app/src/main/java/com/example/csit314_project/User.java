/******************************************************************************
 filename	User.java
 authors    Zheng Qingping, Derron, Jason
 UOW email	qzheng011@uowmail.edu.au
 Course: 	CSIT314
 Brief Description:
 Health Org main Activity
 ******************************************************************************/

package com.example.csit314_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {
    public String NRIC;
    public String gender;
    public String firstName;
    public String lastName;
    public String email;
    public String contactNumber;
    public String username;
    public String password;
    public String role;
    public boolean hasCovid;
    public boolean isSuspend;
    public List<TravelHistory> travelHistories;
    public List<Vaccination> vaccinations;

    DatabaseHelper dbHelper;

    public void setSuspend(boolean suspend, String nric, Context context) {
        dbHelper = new DatabaseHelper(context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("IsSuspend", suspend);
            int i = db.update("UserData", values, "NRIC = '" + nric + "'", null);
        }
        dbHelper.close();
    }

    public void setCovid(boolean covid, String nric, Context context) {
        dbHelper = new DatabaseHelper(context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("HasCovid", covid);
            int i = db.update("UserData", values, "NRIC = '" + nric + "'", null);
        }
        dbHelper.close();
    }

    public void addUser (String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        //OPEN DB
        dbHelper = new DatabaseHelper(context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(dbHelper.openDataBase()) {
            //attempt to search for this user
            //add the user
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("NRIC", NRIC);
            values.put("Gender", gender);
            values.put("FirstName", firstName);
            values.put("LastName", lastName);
            values.put("Email", email);
            values.put("ContactNumber", Integer.valueOf(contactNumber)); //CONTACT NUMBER IS A INTEGER IN THE TABLE.
            values.put("Username", username);
            values.put("Password", password);
            values.put("Roles", userType);
            values.put("HasCovid", false);
            values.put("IsSuspend", false);
            db.insert("UserData", null, values);
        }
        dbHelper.close();
    }

    public User findSingleUserByUsername(String username, Context context) {
        dbHelper = new DatabaseHelper(context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "SELECT * FROM UserData WHERE Username = '" + username + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                User data = new User();
                data.NRIC = cursor.getString(0);
                data.gender = cursor.getString(1);
                data.firstName = cursor.getString(2);
                data.lastName = cursor.getString(3);
                data.email = cursor.getString(4);
                data.contactNumber = cursor.getString(5);
                data.username = cursor.getString(6);
                data.password = cursor.getString(7);
                data.role = cursor.getString(8);
                data.hasCovid = cursor.getLong(9) != 0;
                data.isSuspend = cursor.getLong(10) != 0;
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;
    }

    public User findSingleUserByNRIC(String NRIC, Context context) {
        dbHelper = new DatabaseHelper(context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = "SELECT * FROM UserData WHERE NRIC = '" + NRIC + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                User data = new User();
                data.NRIC = cursor.getString(0);
                data.gender = cursor.getString(1);
                data.firstName = cursor.getString(2);
                data.lastName = cursor.getString(3);
                data.email = cursor.getString(4);
                data.contactNumber = cursor.getString(5);
                data.username = cursor.getString(6);
                data.password = cursor.getString(7);
                data.role = cursor.getString(8);
                data.hasCovid = cursor.getInt(9) != 0;
                data.isSuspend = cursor.getLong(10) != 0;
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;
    }

    public List<User> findUserSpecial(String NRIC, String userType, String username, Context context) {
        dbHelper = new DatabaseHelper(context);
        List<User> tempList = new ArrayList<User>();

        String userTypestr = ""; //this will trigger a crash
        if (!userType.isEmpty()) {
            userTypestr = " Roles = '" + userType + "'";
        }

        String NRICstr = "";
        if (!NRIC.isEmpty()) {
            NRICstr += " AND NRIC = '" + NRIC + "'";
        }

        String usernamestr = "";
        if (!username.isEmpty()) {
            usernamestr = " AND Username = '" + username + "'";
        }

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = String.format("SELECT * FROM UserData WHERE%s%s%s", userTypestr, NRICstr, usernamestr);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    User data = new User();
                    data.NRIC = cursor.getString(0);
                    data.gender = cursor.getString(1);
                    data.firstName = cursor.getString(2);
                    data.lastName = cursor.getString(3);
                    data.email = cursor.getString(4);
                    data.contactNumber = cursor.getString(5);
                    data.username = cursor.getString(6);
                    data.password = cursor.getString(7);
                    data.role = cursor.getString(8);
                    data.hasCovid = cursor.getInt(9) != 0;
                    data.isSuspend = cursor.getLong(10) != 0;
                    tempList.add(data);
                }
                cursor.close();
            }
        }
        return tempList;
    }
}
