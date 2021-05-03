package com.example.csit314_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HealthStaffMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthstaff_main);

        UserController LC = UserController.getInstance();
        TextView currUser = findViewById(R.id.txt_currentUser);
        currUser.setText(LC.currentUser.getUsername());

        Button btn_logout = findViewById(R.id.btn_search);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Log out!", Toast.LENGTH_SHORT).show();
                LC.logout();
                Intent mainIntent = new Intent(HealthStaffMainActivity.this, LoginActivity.class);
                HealthStaffMainActivity.this.startActivity(mainIntent);
                HealthStaffMainActivity.this.finish();
            }
        });
    }
}
