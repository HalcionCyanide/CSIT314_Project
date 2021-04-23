package com.example.csit314_project;

public class User {
    private String username;
    private String password;
    private String NRIC;

    public String getUsername() {
        return username;
    }
    public String getNRIC() {
        return NRIC;
    }
    public USER_TYPE getUserType() {
        return userType;
    }

    private enum USER_TYPE {
        PUBLIC,
        HEALTH_STAFF,
        BUSINESS,
        HEALTH_ORG
    }

    private USER_TYPE userType;

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login (){
        //TODO IMPLEMENT CODE, GRAB THE USER DATA FROM THE DATABASE
        //OPEN DB
        //CHECK WHERE USER AND PASS MATCH
        //POPULATE OTHER INFO HERE
        return true;
    }
}
