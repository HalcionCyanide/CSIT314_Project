package com.example.csit314_project;

import android.content.Context;

import java.io.IOException;
import java.util.List;

public class UserController {

    private static UserController INSTANCE = null;

    private UserController() {};

    //just a temporary holder for the user
    public User currentUser;

    public static UserController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserController();
        }
        return(INSTANCE);
    }

    protected boolean validateOnLogin(String username, String password, Context context) throws IOException {
        User user = new User();
        user = user.findSingleUserByUsername(username, context);
        //if there is such a user
        if (user != null) {
            //double check password
            if (user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
            return false;
        }
        return false;
    }

    protected boolean validateOnAddUser(String NRIC, String gender, String firstName, String lastName, String email, String contactNumber, String username, String password, String userType, Context context) {
        User user = new User();
        //if the user cannot be found
        if (user.findSingleUserByNRIC(NRIC, context) == null) {
            user.addUser(NRIC, gender, firstName, lastName, email, contactNumber, username, password, userType, context);
            return true;
        }
        return false;
    }

    protected void logout() {
        currentUser = null;
    }

    public List<User> validateOnSearchUser(String nric, String username, String userType, Context context) {
        User user = new User();
        //if the user cannot be found
        return user.findUserSpecial(nric, userType, username, context);
    }
}
