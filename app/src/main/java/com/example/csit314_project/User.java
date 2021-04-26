package com.example.csit314_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class User {
    private String NRIC;
    private String gender;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String username;
    private String password;
    public USER_TYPE role;

    UserDatabaseHelper userDBHelper;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    private static final String USERS_DB_NAME ="users.db";// Database name
    public enum USER_TYPE {
        PUBLIC,
        HEALTH_STAFF,
        BUSINESS,
        HEALTH_ORG,
    }

    public boolean login (String username, Context context){
        //TODO IMPLEMENT CODE, GRAB THE USER DATA FROM THE DATABASE
        //OPEN DB
        userDBHelper = new UserDatabaseHelper(context);
        try {
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean success = userDBHelper.openDataBase();
        if(success) {
            User user = validateUser(username);
            LoginController.getInstance().currentUser = user;
            return user != null;
        }
        return false;
    }

    User validateUser(String username) {
        SQLiteDatabase db = userDBHelper.getWritableDatabase();
        String query = "SELECT * FROM User_Data WHERE Username = '" + username + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null && cursor.moveToFirst()) {
            User data = new User();
            data.NRIC = cursor.getString(0);
            data.gender = cursor.getString(1);
            data.firstName = cursor.getString(2);
            data.lastName = cursor.getString(3);
            data.email = cursor.getString(4);
            data.contactNumber = cursor.getString(5);
            data.username = cursor.getString(6);
            data.password = cursor.getString(7);
            switch (cursor.getString(8)) {
                case "Public _User":
                    data.role = USER_TYPE.PUBLIC;
                    break;
                case "Business _User":
                    data.role = USER_TYPE.BUSINESS;
                    break;
                case "Health_Staff":
                    data.role = USER_TYPE.HEALTH_STAFF;
                    break;
                case "Health_Org":
                    data.role = USER_TYPE.HEALTH_ORG;
                    break;
            }
            cursor.close();
            return data;
        }
        else {
            return null;
        }
    }
}
