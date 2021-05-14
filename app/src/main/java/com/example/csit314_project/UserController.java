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

import java.util.List;

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
        //if there is such a user
        if (user != null) {
            //double check password
            if (user.password.equals(password) && !user.isSuspend) {
                this.currentUser = user;
                return true;
            }
            return false;
        }
        return false;
    }

    /*
    Function Name: validateOnAddUser
    Brief Description: tests the database if a user can be added
    Parameters:
    All parameters from the User class
    context : app context for the database opening
    */
    protected boolean validateOnAddUser(String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        User user = new User();
        //if the user cannot be found
        if (user.findSingleUserByNRIC(NRIC, context) == null) {
            user.addUser(NRIC, gender, firstName, lastName, email, contactNumber, username, password, userType, context);
            return true;
        }
        return false;
    }

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
