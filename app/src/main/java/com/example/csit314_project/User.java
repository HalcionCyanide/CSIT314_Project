/*
filename	User.java
authors    Zheng Qingping, Derron, Jason
UOW email	qzheng011@uowmail.edu.au
Course: 	CSIT314
Brief Description:
User base class
*/

package com.example.csit314_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    public List<TravelHistory> travelHistories = new ArrayList<>();
    public Vaccination vaccinations = new Vaccination();
    public List<Alert> alerts = new ArrayList<>();

    DatabaseHelper dbHelper;

    /*
    Function Name: setSuspend
    Brief Description: Accesses the database, Flipping the status of user suspend
    Parameters:
    suspend : new status to set to
    nric : Primary key
    context : app context for the database opening
    */
    public void setSuspend(boolean suspend, String nric, Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("IsSuspend", suspend);
            db.update("UserData", values, "NRIC = '" + nric + "'", null);
        }
        dbHelper.close();
    }

    /*
    Function Name: setCovid
    Brief Description: Accesses the database, Flipping the status of user covid
    Parameters:
    covid : new status to set to
    nric : Primary key
    context : app context for the database opening
    */
    public void setCovid(boolean covid, String nric, Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if(dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("HasCovid", covid);
            db.update("UserData", values, "NRIC = '" + nric + "'", null);
        }
        dbHelper.close();
    }

    /*
    Function Name: addUser
    Brief Description: Accesses the database adding a new entry
    Parameters:
    All member variables of the user
    context : app context for the database opening
    */
    public void addUser (String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        //OPEN DB
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if(dbHelper.openDataBase()) {
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

    /*
    Function Name: findSingleUserByUsername
    Brief Description: Accesses the database returning a single user
    Parameters:
    Username : username to search for
    context : app context for the database opening
    */
    public User findSingleUserByUsername(String username, Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
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
                data.updateTravelAndVaxAndAlerts(data.NRIC, context);
                cursor.close();
                return data;
            } else {
                return null;
            }
        }
        return null;
    }

    /*
    Function Name: findSingleUserByNRIC
    Brief Description: Accesses the database returning a single user
    Parameters:
    NRIC : NRIC to search for
    context : app context for the database opening
    */
    public User findSingleUserByNRIC(String NRIC, Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
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
                data.updateTravelAndVaxAndAlerts(data.NRIC, context);
                cursor.close();

                return data;
            } else {
                return null;
            }
        }
        return null;
    }

    /*
    Function Name: findUserSpecial
    Brief Description: Accesses the database returning a list of users user
    Parameters:
    NRIC : NRIC to search for
    userType : userType to search for
    username : username to search for
    context : app context for the database opening
    */
    public List<User> findUserSpecial(String NRIC, String userType, String nameContains, Context context) {
        dbHelper = new DatabaseHelper(context);
        List<User> tempList = new ArrayList<>();

        String userTypeStr = ""; //this will trigger a crash
        if (!userType.isEmpty()) {
            userTypeStr = " Roles = '" + userType + "'";
        }

        String NRICStr = "";
        if (!NRIC.isEmpty()) {
            NRICStr += " AND NRIC = '" + NRIC + "'";
        }

        String usernameStr = "";
        if (!nameContains.isEmpty()) {
            usernameStr = " AND Username LIKE '%" + nameContains + "%'";
        }

        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String query = String.format("SELECT * FROM UserData WHERE%s%s%s", userTypeStr, NRICStr, usernameStr);
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

                    data.updateTravelAndVaxAndAlerts(data.NRIC, context);

                    tempList.add(data);
                }
                cursor.close();
            }
        }
        return tempList;
    }

    /*
    Function Name: updateTravelAndVax
    Brief Description: Accesses the database , updating the travel history and vaccination record of the calling user
    Parameters:
    NRIC : NRIC to update
    context : app context for the database opening
    */
    void updateTravelAndVaxAndAlerts(String NRIC, Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            String query2 = "SELECT * FROM TravelHistory WHERE NRIC = '" + NRIC + "'";
            Cursor TravelHistCursor = db.rawQuery(query2, null);
            if(TravelHistCursor != null) {
                while (TravelHistCursor.moveToNext()) {
                    TravelHistory dummyHist = new TravelHistory();
                    dummyHist.NRIC = TravelHistCursor.getString(0);
                    dummyHist.timeIn = TravelHistCursor.getString(1);
                    dummyHist.timeOut = TravelHistCursor.getString(2);
                    dummyHist.location = TravelHistCursor.getString(3);

                    travelHistories.add(dummyHist);
                }
                TravelHistCursor.close();
            }

            String query3 = "SELECT * FROM Vaccination WHERE NRIC = '" + NRIC + "'";
            Cursor VaxCursor = db.rawQuery(query3, null);
            if(VaxCursor != null) {
                while (VaxCursor.moveToNext()) {
                    Vaccination dummyVax = new Vaccination();
                    dummyVax.NRIC = VaxCursor.getString(0);
                    dummyVax.vaccination_brand = VaxCursor.getString(1);
                    dummyVax.first_vaccination = VaxCursor.getString(2);
                    dummyVax.second_vaccination = VaxCursor.getString(3);

                    vaccinations = dummyVax;
                }
                VaxCursor.close();
            }

            String query4 = "SELECT * FROM Alerts WHERE ReceiveBy = '" + NRIC + "'";
            Cursor AlertCursor = db.rawQuery(query4, null);
            if(AlertCursor != null) {
                while (AlertCursor.moveToNext()) {
                    Alert alertHist = new Alert();
                    alertHist.NRIC = AlertCursor.getString(0);
                    alertHist.dateTime = AlertCursor.getString(1);
                    alertHist.message = AlertCursor.getString(2);
                    alertHist.acknowledge = AlertCursor.getLong(3) != 0;

                    alerts.add(alertHist);
                }
                AlertCursor.close();
            }
        }
    }

    List<User> findUsersByEmployment(List<Employment> colleagues, Context context)
    {
        List<User> tempList = new ArrayList<>();

        dbHelper.createDataBase();
        if (dbHelper.openDataBase()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (int i = 0; i < colleagues.size(); i++) {
                String query = "SELECT * FROM UserData WHERE NRIC = '" + colleagues.get(i).NRIC + "'";
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
                    data.updateTravelAndVaxAndAlerts(data.NRIC, context);
                    cursor.close();
                    tempList.add(data);
                }

            }
        }
        return tempList;
    }
}
