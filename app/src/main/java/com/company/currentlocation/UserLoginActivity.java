package com.company.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class UserLoginActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users");

    EditText et_numberLogin, et_enterOTP;
    Button bt_otpSend, bt_verify;
    TextView tv_goToRegister;

    String userLoginNumber;

    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String verificationId;

    public static SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        et_numberLogin = findViewById(R.id.et_numberLogin);
        bt_otpSend = findViewById(R.id.bt_otpSend);
        et_enterOTP = findViewById(R.id.et_enterOTP);
        bt_verify = findViewById(R.id.bt_verifyOTP);
        tv_goToRegister = findViewById(R.id.tv_goToRegister);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        bt_otpSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLoginNumber = "+91" + et_numberLogin.getText().toString();
                Log.d("some",userLoginNumber);
                Boolean b = numberVerificationInDatabase(userLoginNumber);
                if(b) {
                    sendVerificationCode(userLoginNumber);
                }

            }
        });

        bt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(et_enterOTP.getText().toString());
            }
        });

        tv_goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserLoginActivity.this, RegisterActivity.class);
                startActivity(i);



            }
        });
    }

    private boolean numberVerificationInDatabase(String userLoginNumber){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Boolean b=false;

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().toString().equals(userLoginNumber)){

                        et_enterOTP.setVisibility(View.VISIBLE);
                        bt_verify.setVisibility(View.VISIBLE);
                        b = true;

                    }
                }

                if(!b){

                    Toast.makeText(UserLoginActivity.this, "User Not Registered", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


            et_enterOTP.setVisibility(View.VISIBLE);
            bt_verify.setVisibility(View.VISIBLE);
            return true;





    }

    private void sendVerificationCode(String number) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    verificationId = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                    final String code = phoneAuthCredential.getSmsCode();


                    if (code != null) {

                        et_enterOTP.setText(code);


                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {


                    Toast.makeText(UserLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);


        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(UserLoginActivity.this, "OTP VERIFIED", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(UserLoginActivity.this,MainActivity.class);
                            i.putExtra("userNumber", "+91" + et_numberLogin.getText().toString());
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("userNumber","+91" + et_numberLogin.getText().toString());
                            editor.putString("boolean","true");
                            editor.commit();
                            startActivity(i);


                        }

                        else {

                            Toast.makeText(UserLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){

            Intent i = new Intent(UserLoginActivity.this, MainActivity.class);
            SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs",MODE_PRIVATE);
            String userno = sp.getString("userNumber","");
            i.putExtra("userNumber",userno);
            startActivity(i);
            finish();

        }
    }
}