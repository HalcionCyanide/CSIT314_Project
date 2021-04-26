package com.example.csit314_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginController LC = LoginController.getInstance();
        //assign a default value just in-case
        int targetLayout;
        User user = LC.currentUser;
        switch (user.role) {
            case PUBLIC:
                targetLayout = R.layout.activity_public_main;
                break;
            case BUSINESS:
                targetLayout = R.layout.activity_business_main;
                break;
            case HEALTH_STAFF:
                targetLayout = R.layout.activity_healthstaff_main;
                break;
            case HEALTH_ORG:
                targetLayout = R.layout.activity_healthorg_main;
                break;
            default:
                Log.e("LOGIN", "User has no user-type, setting to public for now");
                targetLayout = R.layout.activity_public_main;
                //something went wrong
                break;
        }
        setContentView(targetLayout);

        TextView currUser = findViewById(R.id.txt_currentUser);
        currUser.setText(user.getUsername());

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Log out!", Toast.LENGTH_SHORT).show();
                LC.logout();
                Intent mainIntent = new Intent(MainMenuActivity.this, LoginActivity.class);
                MainMenuActivity.this.startActivity(mainIntent);
                MainMenuActivity.this.finish();
            }
        });
    }
}
