package com.example.csit314_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Activity_ViewCheckIn extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_searchdate);

        String sampleDate = getIntent().getStringExtra("datePickerDate");
        List<TravelHistory> travelHistories = searchDate(sampleDate);
        displayCustomers(travelHistories);

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> {
            Activity_ViewCheckIn.this.finish();
        });
    }

    List<TravelHistory> searchDate(String date)
    {
        UserController UC = UserController.getInstance();
        Controller_ViewTravelHistory controller_viewTravelHistory = new Controller_ViewTravelHistory();
        return controller_viewTravelHistory.validateOnSearchDate(UC.currentUser.NRIC, date,Activity_ViewCheckIn.this);
    }

    public void displayCustomers(List<TravelHistory> customerList) {
        ListView vListView = findViewById(R.id.list_visitors);
        TextView txt_location = findViewById(R.id.txt_location);

        ArrayList<String> visitorStringArray = new ArrayList<>();

        for (int i = 0; i < customerList.size(); i++) {
            String temp = "NRIC : " + customerList.get(i).NRIC + "\n" +
                    "Check In : " + customerList.get(i).timeIn + "\n" +
                    "Check Out : " + customerList.get(i).timeOut;

            visitorStringArray.add(temp);
        }
        ArrayAdapter<String> visitorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visitorStringArray);
        vListView.setAdapter(visitorAdapter);

        if(customerList.isEmpty()){
            displayError();
        }
        else {
            displaySuccess();
            txt_location.setText(customerList.get(0).location);
        }
    }

    /*
   Function Name: displaySuccess
   Brief Description: Prints Success toast
   Parameters: None
   */
    public void displaySuccess() {
        Toast.makeText(getApplicationContext(), "Operation Success", Toast.LENGTH_SHORT).show();
    }

    /*
    Function Name: displayError
    Brief Description: Prints Error toast
    Parameters: None
    */
    public void displayError() {
        Toast.makeText(getApplicationContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
    }
}
