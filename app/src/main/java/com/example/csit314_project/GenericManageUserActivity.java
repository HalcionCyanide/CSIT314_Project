package com.example.csit314_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GenericManageUserActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_manageuser);
        // TODO manageuser work here
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            //back btn interaction here

            }
        }
        
        Button btn_suspend = findViewById(R.id.btn_suspend);
        btn_suspend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //back btn interaction here

            }
        }
        Button btn_sendAlert = findViewById(R.id.btn_sendAlert);
        btn_sendAlert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //back btn interaction here

            }
        }
    }


}
