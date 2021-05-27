package com.example.csit314_project;

import android.content.Context;

public class Controller_ToggleCovid {
    public User targetUser;

    public Controller_ToggleCovid(User targetUser) {
        this.targetUser = targetUser;
    }

    public void toggleCovid(Context context) {
        if (targetUser != null) {
            targetUser.setCovid(context);
        }
    }
}
