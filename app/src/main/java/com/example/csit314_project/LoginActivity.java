package com.example.csit314_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView txtUsername = findViewById(R.id.txt_username);
        TextView txtPassword = findViewById(R.id.txt_password);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                boolean canLogin = onSubmit(txtUsername.getText().toString(), txtPassword.getText().toString());

                if(canLogin){
                    //toast here
                    Toast.makeText(getApplicationContext(), "Login Success, welcome back, " + txtUsername.getText(), Toast.LENGTH_SHORT).show();
                    //transition to next page
                    Intent mainIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();
                }
                else {
                    //toast here
                    Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                    //decline the thing
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
            }
        });
    }

    boolean onSubmit(String username, String password) {
        //let the login controller handle it, for now....
        LoginController LC = LoginController.getInstance();
        return LC.ValidateLogin(username, password);
    }
}