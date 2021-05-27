package com.example.csit314_project;

import android.content.Context;

import java.util.List;

public class Controller_SearchUsers {

    /*
    Function Name: validateOnSearchUser
    Brief Description: returns List of users based on parameters from boundary
    Parameters:
    nric : string received from the boundary
    userType : string received from the boundary
    username : string received from the boundary
    context : app context for the database opening
    */
    public List<User> validateOnSearchUser(String nric, String userType, String nameContains, Context context) {
        User user = new User();
        //if the user cannot be found
        return user.findUserSpecial(nric, userType, nameContains, context);
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
}
