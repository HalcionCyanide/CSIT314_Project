/*
filename    UserController.java
authors     Zheng Qingping
UOW email   qzheng011@uowmail.edu.au
Course:     CSIT314
Brief Description:
UserController singleton managing persistent information in app lifetime
*/
package com.example.csit314_project;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserController {

    private static UserController INSTANCE = null;

    private UserController() {}

    //just a temporary holder for the user
    public User currentUser;

    /*
    Function Name: getInstance
    Brief Description: Returns the static Instance of the UserController
    Parameters: None
    */
    public static UserController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserController();
        }
        return(INSTANCE);
    }


    /*
    Function Name: validateOnSearchUser
    Brief Description: overload, returns single user based on parameters from boundary
    Parameters:
    nric : string received from the boundary
    context : app context for the database opening
    */
    public User validateOnSearchUser(String nric, Context context) {
        User user = new User();
        //if the user cannot be found
        return user.findSingleUserByNRIC(nric, context);
    }

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

    /*
    Function Name: toggleCovid
    Brief Description: Causes single user to flip suspension
    Parameters:
    nric : string received from the boundary
    context : app context for the database opening
    */
    public void toggleCovid(String nric, Context context) {
        User user = new User();
        user = user.findSingleUserByNRIC(nric, context);
        //if the user cannot be found
        if (user != null) {
            user.setCovid(!user.hasCovid, nric, context);
        }
    }
}
