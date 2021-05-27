package com.example.csit314_project;

import android.content.Context;

import java.util.List;

public class Controller_GenerateReport {
    public List<String> validateGetByMostCheckIn(int limit, Context context) {
        TravelHistory travelHistory = new TravelHistory();
        return travelHistory.getByMostCheckIn(limit, context);
    }

    public List<String> validateGetByMostCases(int limit, Context context) {
        TravelHistory travelHistory = new TravelHistory();
        return travelHistory.getByMostCases(limit, context);
    }
}
