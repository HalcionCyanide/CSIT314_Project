package com.example.csit314_project;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
                    canLogin = onSubmit(txtUsername.getText().toString(), txtPassword.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(canLogin){
                    //toast here
                    displaySuccess();
                    //transition to next page
                    Intent mainIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
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

    boolean onSubmit(String username, String password) throws IOException {
        //let the login controller handle it, for now....
        LoginController LC = LoginController.getInstance();
        return LC.validate(username, password, LoginActivity.this);
    }

    void displaySuccess() {
        Toast.makeText(getApplicationContext(), "Login Success, welcome " + txtUsername.getText() , Toast.LENGTH_SHORT).show();
    }

    void displayError() {
        Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
    }
}
