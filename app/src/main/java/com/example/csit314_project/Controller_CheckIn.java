package com.example.csit314_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Controller_CheckIn {

    public void displayCheckIn(ArrayList<String> checkInLocations, Context context){
        Intent mainIntent = new Intent(context, Activity_CheckIn.class);
        mainIntent.putStringArrayListExtra("checkInLocations", checkInLocations);
        //input val here
        ((Activity)context).startActivityForResult(mainIntent, 1234);
    }

    public boolean validateOnAddTravelHistory(String nric, String timeIn, String timeOut, String location, Context context) {
        TravelHistory travelHistory = new TravelHistory();
        return travelHistory.addTravelHistory(nric, timeIn, timeOut, location, context);
    }
}
