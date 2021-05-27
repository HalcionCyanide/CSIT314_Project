package com.example.csit314_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activity_ViewTravelHistory extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_travelhistory);

        String fakeNRIC;

        UserController UC = UserController.getInstance();
        if(!UC.currentUser.role.equals("Public_User")) {
            fakeNRIC = getIntent().getStringExtra("SINGLE_NRIC");
        }
        else{
            fakeNRIC = UC.currentUser.NRIC;
        }
        User user = UC.validateOnSearchUser(fakeNRIC, Activity_ViewTravelHistory.this);




        TextView txt_NRIC = findViewById(R.id.txt_NRIC);
        ListView list_TravelHistory = findViewById(R.id.list_TravelHistory);

        txt_NRIC.setText(fakeNRIC);

        ArrayList<String> THArrayList = new ArrayList<>();

        Controller_ViewTravelHistory controller_viewTravelHistory = new Controller_ViewTravelHistory();
        List<TravelHistory> targetTH = controller_viewTravelHistory.validateGetListOfTravelHistoryByNRIC(fakeNRIC, Activity_ViewTravelHistory.this);

        for (int i=0; i < targetTH.size(); i++)
        {
            String tempTravHist =
                    "Time in: " + targetTH.get(i).timeIn + "\n" +
                            "Time out: " +  targetTH.get(i).timeOut + "\n" +
                            "Duration: " + targetTH.get(i).getDuration() + "\n"+
                            "Location: " + targetTH.get(i).location;
            THArrayList.add(tempTravHist);
        }

        ArrayAdapter<String> THadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, THArrayList);
        list_TravelHistory.setAdapter(THadapter);

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> Activity_ViewTravelHistory.this.finish());

    }
}
