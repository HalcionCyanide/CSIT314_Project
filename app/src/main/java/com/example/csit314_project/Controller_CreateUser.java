package com.example.csit314_project;

import android.content.Context;

public class Controller_CreateUser {

    protected boolean validateOnAddUser(String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        User user = new User();
        //if the user cannot be found
        if (user.findSingleUserByNRIC(NRIC, context) == null) {
            user.addUser(NRIC, gender, firstName, lastName, email, contactNumber, username, password, userType, context);
            return true;
        }
        return false;
    }
}
