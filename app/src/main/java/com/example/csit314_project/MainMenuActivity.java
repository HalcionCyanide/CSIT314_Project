package com.example.csit314_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_main);

        LoginController LC = LoginController.getInstance();
        TextView currUser = findViewById(R.id.txt_currentUser);

        currUser.setText(LC.currentUser.getUsername());

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
