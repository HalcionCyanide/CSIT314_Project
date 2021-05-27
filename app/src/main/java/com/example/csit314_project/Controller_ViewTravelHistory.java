package com.example.csit314_project;

import android.content.Context;
import android.content.Intent;

import java.util.List;

public class Controller_ViewTravelHistory {
    public void displayTravelHistory(Context context){
        Intent mainIntent = new Intent(context, Activity_ViewTravelHistory.class);
        context.startActivity(mainIntent);
    }

    public void displayTravelHistory(String fakeNRIC, Context context){
        Intent mainIntent = new Intent(context, Activity_ViewTravelHistory.class);
        mainIntent.putExtra("SINGLE_NRIC", fakeNRIC);
        context.startActivity(mainIntent);
    }

    public List<TravelHistory> validateGetListOfTravelHistoryByNRIC(String NRIC, Context context) {
        TravelHistory travelHistory = new TravelHistory();
        return travelHistory.getListOfTravelHistoryByNRIC(NRIC, context);
    }

    public List<TravelHistory> validateOnSearchDate(String NRIC, String date, Context context)
    {
        Employment employment = new Employment();
        String location = employment.findLocationByNRIC(NRIC, context);

        TravelHistory travelHistory = new  TravelHistory();
        String[] splitDate = date.split("/");
        if (splitDate[0].length() < 2)
        {
            splitDate[0] = "0" + splitDate[0];
        }
        if (splitDate[1].length() < 2)
        {
            splitDate[1] = "0" + splitDate[1];
        }
        String fixedDate = splitDate[0] + "/" + splitDate[1]+ "/" + splitDate[2];

        return travelHistory.findCustomersByDateAndLocation(location, fixedDate, context);
    }
}
