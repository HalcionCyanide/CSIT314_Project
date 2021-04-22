package com.example.csit314_project;

public class LoginController {

    private static LoginController INSTANCE = null;

    private LoginController() {};

    public User currentUser;

    public static LoginController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginController();
        }
        return(INSTANCE);
    }

    protected boolean validate(String username, String password) {
        User user = new User(username, password);
        if (user.login()) {
            currentUser = user;
            return true;
        }
        return false;
    }

    protected void logout() {
        currentUser = null;
    }
}
