package com.company.currentlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseUser;

public class SplashhScreenActivity extends AppCompatActivity {

    ImageView iv_big, iv_nari;

    String userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashh_screen);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs",MODE_PRIVATE);
        userLoggedIn = sp.getString("boolean","");


        if(userLoggedIn.equals("true")){

            Intent intent = new Intent(this,MainActivity.class);
            String userno = sp.getString("userNumber","");
            intent.putExtra("userNumber",userno);
            finish();
            startActivity(intent);
        }

        else {

            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            iv_big = findViewById(R.id.iv_big);
            iv_nari = findViewById(R.id.iv_nari);

            iv_nari.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));

            new CountDownTimer(6000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Intent i = new Intent(SplashhScreenActivity.this, BasicLoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }.start();
        }
    }
}