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
}
