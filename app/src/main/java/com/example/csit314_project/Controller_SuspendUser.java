package com.example.csit314_project;

import android.content.Context;

public class Controller_SuspendUser {

    /*
   Function Name: toggleSuspension
   Brief Description: Causes single user to flip suspension
   Parameters:
   nric : string received from the boundary
   context : app context for the database opening
   */
    public void toggleSuspension(String nric, Context context) {
        User user = new User();
        user = user.findSingleUserByNRIC(nric, context);
        //if the user cannot be found
        if (user != null) {
            user.setSuspend(!user.isSuspend, nric, context);
        }


    }
}
