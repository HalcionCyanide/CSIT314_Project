package com.example.csit314_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_GenerateReport extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthorg_generatereport);

        ListView mostCheckIn = findViewById(R.id.list_mostCheckIn);
        ListView mostCases = findViewById(R.id.list_mostCases);
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> Activity_GenerateReport.this.finish());

        //there were results
        if (onRetrieveCovidStats(mostCases, mostCheckIn, this)) {
            displaySuccess();
        } else {
            displayError();
        }


        btn_back.setOnClickListener(v -> Activity_GenerateReport.this.finish());
    }

    /*
    Function Name: onRetrieveCovidStats
    Brief Description: when triggered, calls for controller to perform the action
    Parameters:
    mostCases : resulting list view to modify
    mostCheckIn : resulting list view to modify
    context : app context for the database opening
    */
    boolean onRetrieveCovidStats(ListView mostCases, ListView mostCheckIn, Context context) {
        Controller_GenerateReport UC = new Controller_GenerateReport();
        List<String> cases, checks;
        checks = UC.validateGetByMostCheckIn(4, context);
        cases = UC.validateGetByMostCases(4, context);

        ArrayList<String> mCases = new ArrayList<>(cases);
        ArrayAdapter<String> casesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCases);
        mostCases.setAdapter(casesAdapter);
        ArrayList<String> mCheck = new ArrayList<>(checks);
        ArrayAdapter<String> checksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCheck);
        mostCheckIn.setAdapter(checksAdapter);

        return (!cases.isEmpty() || !checks.isEmpty());
    }

    /*
    Function Name: displaySuccess
    Brief Description: Prints Success toast
    Parameters: None
    */
    void displaySuccess() {
        Toast.makeText(getApplicationContext(), "Operation Success", Toast.LENGTH_SHORT).show();
    }

    /*
    Function Name: displayError
    Brief Description: Prints Error toast
    Parameters: None
    */
    void displayError() {
        Toast.makeText(getApplicationContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
    }

}

