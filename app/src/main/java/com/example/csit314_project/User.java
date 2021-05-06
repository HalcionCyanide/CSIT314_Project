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
    public  USER_TYPE role;
    private boolean hasCovid;

    DatabaseHelper userDBHelper;

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
            if (!userType.isEmpty()){
                NRICstr += " AND";
            }
            NRICstr += " NRIC = '" + NRIC + "'";
        }

        String usernamestr = "";
        if (!username.isEmpty()) {
            if (!userType.isEmpty() || !NRIC.isEmpty()){
                usernamestr += " AND";
            }
            usernamestr = " Username = '" + username + "'";
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
                data.hasCovid = cursor.getInt(9) != 0;
                cursor.close();
                tempList.add(data);
            }
        }
        return tempList;
    }
}
