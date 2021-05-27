package com.example.csit314_project;

import android.content.Context;

public class controller_toggleCovid {
    public User targetUser;

    public controller_toggleCovid(User targetUser) {
        this.targetUser = targetUser;
    }

    public void toggleCovid(Context context) {
        if (targetUser != null) {
            targetUser.setCovid(context);
        }
    }
}
