package com.company.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    TextView tv_message;
    EditText et_otpText;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String verificationId;

    Button bt_submit;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    String phone;
    String fullname;
    String age;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        phone = bundle.getString("pn");
        fullname = bundle.getString("fullname");
        age = bundle.getString("age");
        gender = bundle.getString("gender");

        et_otpText = findViewById(R.id.et_otpText);

        tv_message = findViewById(R.id.tv_message);
        bt_submit = findViewById(R.id.bt_submit);




        tv_message.setText("An otp has been sent to the mobile number: " + phone);
        sendVerificationCode(phone);


        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(et_otpText.getText().toString());
            }
        });


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

                        et_otpText.setText(code);


                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {


                    Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

                                        Toast.makeText(OTPActivity.this, "OTP VERIFIED", Toast.LENGTH_SHORT).show();
                                        RegisterActivity.auth = mAuth;

                                        databaseReference = firebaseDatabase.getReference().child("Users").child(phone);
                                        UserData userData = new UserData(phone, fullname, age, gender);
                                        databaseReference.setValue(userData);
                                        Intent i = new Intent(OTPActivity.this, GuardianInformationActivity.class);
                                        i.putExtra("userNumber", phone);
                                        Log.d("User Number OTP Activity", phone);
                                        startActivity(i);

                                    }

                                    else {

                                        Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
    }








}