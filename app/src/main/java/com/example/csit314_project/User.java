package com.example.csit314_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    public  USER_TYPE role;

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

    public void addUser (String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, USER_TYPE userType, Context context) {
        //OPEN DB
        userDBHelper = new UserDatabaseHelper(context);
        try {
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(userDBHelper.openDataBase()) {
            //attempt to search for this user
            //add the user
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
            String decodeUserType = "";
            switch (userType) {
                case PUBLIC:
                    decodeUserType = "Public_User";
                    break;
                case HEALTH_STAFF:
                    decodeUserType = "Health_Staff";
                    break;
                case HEALTH_ORG:
                    decodeUserType = "Health_Org";
                    break;
                case BUSINESS:
                    decodeUserType = "Business_User";
                    break;
            }
            ContentValues values = new ContentValues();
            values.put("NRIC", NRIC);
            values.put("Gender", gender);
            values.put("FirstName", firstName);
            values.put("LastName", lastName);
            values.put("Email", email);
            values.put("ContactNumber", Integer.valueOf(contactNumber)); //CONTACT NUMBER IS A INTEGER IN THE TABLE.
            values.put("Username", username);
            values.put("Password", password);
            values.put("Roles", decodeUserType);
            db.insert("User_Data", null, values);
        }
        userDBHelper.close();
    }

    public User findUserByUsername(String username, Context context) {
        userDBHelper = new UserDatabaseHelper(context);
        try {
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(userDBHelper.openDataBase()) {
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
            String query = "SELECT * FROM User_Data WHERE Username = '" + username + "'";
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
                switch (cursor.getString(8)) {
                    case "Public_User":
                        data.role = USER_TYPE.PUBLIC;
                        break;
                    case "Business_User":
                        data.role = USER_TYPE.BUSINESS;
                        break;
                    case "Health_Staff":
                        data.role = USER_TYPE.HEALTH_STAFF;
                        break;
                    case "Health_Org":
                        data.role = USER_TYPE.HEALTH_ORG;
                        break;
                    default:
                        Log.e("ROLE", "No role set");
                        break;
                }
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;
    }

    public User findUserByNRIC(String NRIC, Context context) {
        userDBHelper = new UserDatabaseHelper(context);
        try {
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userDBHelper.openDataBase()) {
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
            String query = "SELECT * FROM User_Data WHERE NRIC = '" + NRIC + "'";
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
                switch (cursor.getString(8)) {
                    case "Public_User":
                        data.role = USER_TYPE.PUBLIC;
                        break;
                    case "Business_User":
                        data.role = USER_TYPE.BUSINESS;
                        break;
                    case "Health_Staff":
                        data.role = USER_TYPE.HEALTH_STAFF;
                        break;
                    case "Health_Org":
                        data.role = USER_TYPE.HEALTH_ORG;
                        break;
                    default:
                        Log.e("ROLE", "No role set");
                        break;
                }
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;
    }

    public User findUserByUserType(String userType, Context context) {
        userDBHelper = new UserDatabaseHelper(context);
        try {
            userDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userDBHelper.openDataBase()) {
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
            String query = "SELECT * FROM User_Data WHERE Roles = '" + userType + "'";
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
                switch (cursor.getString(8)) {
                    case "Public_User":
                        data.role = USER_TYPE.PUBLIC;
                        break;
                    case "Business_User":
                        data.role = USER_TYPE.BUSINESS;
                        break;
                    case "Health_Staff":
                        data.role = USER_TYPE.HEALTH_STAFF;
                        break;
                    case "Health_Org":
                        data.role = USER_TYPE.HEALTH_ORG;
                        break;
                    default:
                        Log.e("ROLE", "No role set");
                        break;
                }
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;
    }
}
