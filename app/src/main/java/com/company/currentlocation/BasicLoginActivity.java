package com.company.currentlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BasicLoginActivity extends AppCompatActivity {
    Button bt_userLogin, bt_guardianLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_login);

        bt_userLogin = findViewById(R.id.bt_userLogin);
        bt_guardianLogin = findViewById(R.id.bt_guardianLogin);

        bt_userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(BasicLoginActivity.this, UserLoginActivity.class);
                startActivity(i);

            }
        });

        bt_guardianLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(BasicLoginActivity.this,GuardianLoginActivity.class);
                startActivity(i);

            }
        });
    }
}