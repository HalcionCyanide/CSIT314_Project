package com.example.csit314_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends Activity {

    TextView txtUsername;
    TextView txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        displayLogin();
    }

    void displayLogin() {
        txtUsername = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                boolean canLogin = false;
                try {
                    canLogin = onLogin(txtUsername.getText().toString(), txtPassword.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(canLogin){
                    //toast here
                    displaySuccess();
                    //transition to next page
                    UserController UC = UserController.getInstance();
                    //assign a default value just in-case
                    Class<?> nextActivity = null; // THIS WILL BREAK APP, BE CAREFUL
                    User user = UC.currentUser;
                    switch (user.role) {
                        case "Public_User":
                            nextActivity = PublicMainActivity.class;
                            break;
                        case "Business_User":
                            nextActivity = BusinessMainActivity.class;
                            break;
                        case "Health_Staff":
                            nextActivity = HealthStaffMainActivity.class;
                            break;
                        case "Health_Org":
                            nextActivity = HealthOrgMainActivity.class;
                            break;
                    }
                    Intent mainIntent = new Intent(LoginActivity.this, nextActivity);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();
                }
                else {
                    //toast here
                    displayError();
                    //decline the thing
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
            }
        });
    }

    boolean onLogin(String username, String password) throws IOException {
        //let the login controller handle it, for now....
        UserController UC = UserController.getInstance();
        return UC.validateOnLogin(username, password, LoginActivity.this);
    }

    void displaySuccess() {
        Toast.makeText(getApplicationContext(), "Login Success, welcome " + txtUsername.getText() , Toast.LENGTH_SHORT).show();
    }

    void displayError() {
        Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
    }
}
