package com.example.csit314_project;

import android.content.Context;

public class Controller_ToggleCovid {
    public User targetUser;

    public Controller_ToggleCovid(User targetUser) {
        this.targetUser = targetUser;
    }

    public void toggleCovid(Context context) {
        User user = new User();
        if (targetUser != null) {
            user.setCovid(!targetUser.hasCovid, targetUser.NRIC, context);
        }
    }
}
