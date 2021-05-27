package com.example.csit314_project;

import android.content.Context;
import android.content.Intent;

import java.util.List;

public class Controller_BusinessInfo {
    public void create_Activity_BusinessInfo(Context context) {
        Intent mainIntent = new Intent(context, Activity_BusinessInfo.class);
        context.startActivity(mainIntent);
    }

    public String validateOnRetrieveEmploymentLocation(String nric, Context context) {

            Employment employment = new Employment();
            return employment.findLocationByNRIC(nric, context);
    }

    public List<User> validateOnRetrieveColleagues(String location, Context context){
        Employment employment = new Employment();
        List<Employment> colleagues = employment.gatherEmployment(location, context);
        User user = new User();
        return user.findUsersByEmployment(colleagues, context);

    }
}
