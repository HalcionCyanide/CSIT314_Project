package com.example.csit314_project;

import android.content.Context;

import java.io.IOException;

public class LoginController {

    private static LoginController INSTANCE = null;

    private LoginController() {};

    //just a temporary holder for the user
    public User currentUser;

    public static LoginController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginController();
        }
        return(INSTANCE);
    }

    protected boolean validate(String username, String password, Context context) throws IOException {
        User user = new User();
        if (user.login(username, context)) {
            //validate password
            return currentUser.getPassword().equals(password);
        }
        else {
            return false;
        }
    }

    protected void logout() {
        currentUser = null;
    }
}
