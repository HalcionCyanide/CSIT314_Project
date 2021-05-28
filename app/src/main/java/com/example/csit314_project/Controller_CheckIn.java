package com.example.csit314_project;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Controller_CheckIn {


    public void displayCheckin(ArrayList<String> checkInLocations, ArrayAdapter<String> checkInLocationsAdapter, Context context){
        Intent mainIntent = new Intent(context, Activity_CheckIn.class);
        mainIntent.putStringArrayListExtra("checkInLocations", checkInLocations);

        mainIntent.putExtra("checkInLocationsAdapter", checkInLocationsAdapter);

        //input val here
        context.startActivity(mainIntent);
    }
}
