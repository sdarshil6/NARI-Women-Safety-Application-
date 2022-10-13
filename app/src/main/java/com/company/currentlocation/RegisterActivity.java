package com.company.currentlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_fullname, et_gender, et_age, et_pn, et_goToLogin;
    private Button bt_register;

    public static FirebaseAuth auth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_fullname = findViewById(R.id.et_fullname);
        et_gender = findViewById(R.id.et_gender);
        et_age = findViewById(R.id.et_age);
        et_pn = findViewById(R.id.et_pn);
        et_goToLogin = findViewById(R.id.et_goToLogin);

        bt_register = findViewById(R.id.bt_register);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname = et_fullname.getText().toString();
                String gender = et_gender.getText().toString();
                String age = et_age.getText().toString();
                String pn = "+91" + et_pn.getText().toString();
                Log.d("User Number Register Activity", pn);

                Bundle bundle = new Bundle();
                bundle.putString("fullname", fullname);
                bundle.putString("gender", gender);
                bundle.putString("age", age);
                bundle.putString("pn", pn);


                Intent i = new Intent(RegisterActivity.this, OTPActivity.class);
                i.putExtras(bundle);
                startActivity(i);



            }
        });

        et_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RegisterActivity.this, UserLoginActivity.class);
                startActivity(i);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){

            Intent i = new Intent(RegisterActivity.this, GuardianInformationActivity.class);
            startActivity(i);
            finish();

        }

    }
}