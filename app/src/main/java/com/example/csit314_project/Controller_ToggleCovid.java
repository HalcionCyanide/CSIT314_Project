package com.example.csit314_project;

import android.content.Context;

public class Controller_ToggleCovid {
    public User targetUser;

    Context context;

    public Controller_ToggleCovid(User targetUser, Context context) {
        this.targetUser = targetUser;
        this.context = context;
    }

    public void toggleCovid() {
        if (targetUser != null) {
            targetUser.setCovid(!targetUser.hasCovid, context);
        }
    }
}
