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
    private String NRIC;
    private String gender;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String username;
    private String password;
    public  String role;
    private boolean hasCovid;

    DatabaseHelper userDBHelper;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void addUser (String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        //OPEN DB
        userDBHelper = new DatabaseHelper(context);
        try {
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(userDBHelper.openDataBase()) {
            //attempt to search for this user
            //add the user
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
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
            db.insert("UserData", null, values);
        }
        userDBHelper.close();
    }

    public User findSingleUserByUsername(String username, Context context) {
        userDBHelper = new DatabaseHelper(context);
        try {
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(userDBHelper.openDataBase()) {
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
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
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;
    }

    public User findSingleUserByNRIC(String NRIC, Context context) {
        userDBHelper = new DatabaseHelper(context);
        try {
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userDBHelper.openDataBase()) {
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
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
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;
    }

    public List<User> findUserSpecial(String NRIC, String userType, String username, Context context) {
        userDBHelper = new DatabaseHelper(context);
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
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userDBHelper.openDataBase()) {
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
            String query = String.format("SELECT * FROM UserData WHERE%s%s%s", userTypestr, NRICstr, usernamestr);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToNext()) {
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
                cursor.close();
                tempList.add(data);
            }
        }
        return tempList;
    }
}
