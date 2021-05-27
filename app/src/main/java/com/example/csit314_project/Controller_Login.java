package com.example.csit314_project;

import android.content.Context;

public class Controller_Login {
    /*
        Function Name: validateOnLogin
        Brief Description: verifies the entry from the calling boundary and the User class
        Parameters:
        username : string received from the boundary
        password : string received from the boundary
        context : app context for the database opening
        */
    protected boolean validateOnLogin(String username, String password, Context context) {
        User user = new User();
        user = user.findSingleUserByUsername(username, context);
        UserController UC = UserController.getInstance();
        //if there is such a user
        if (user != null) {
            //double check password
            if (user.password.equals(password) && !user.isSuspend) {
                UC.currentUser = user;
                return true;
            }
            return false;
        }
        return false;
    }
}
