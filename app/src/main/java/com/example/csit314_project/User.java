package com.example.csit314_project;

public class User {
    private String username;
    private String password;

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

    public boolean Login (){
        //TODO IMPLEMENT CODE, GRAB THE USER DATA FROM THE DATABASE
        //OPEN DB
        //CHECK WHERE USER AND PASS MATCH
        //POPULATE OTHER INFO HERE
        return true;
    }
}
