
package com.company.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class GuardianLoginActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    EditText et_guardianNumberLogin, et_userPhoneNumber, et_enterGuardianOTP, et_guardianName;
    Button bt_guardianOTPSend, bt_verifyGuardianOTP, bt_ok;

    String userLoginNumber;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_login);

        et_guardianNumberLogin = findViewById(R.id.et_guardianNumberLogin);
        et_guardianName = findViewById(R.id.et_guardianName);
        et_userPhoneNumber = findViewById(R.id.et_userPhoneNumber);
        et_enterGuardianOTP =findViewById(R.id.et_enterGuardianOTP);
        bt_guardianOTPSend = findViewById(R.id.bt_guardianOTPSend);
        bt_verifyGuardianOTP = findViewById(R.id.bt_verifyGuardianOTP);
        bt_ok = findViewById(R.id.bt_ok);

        databaseReference = firebaseDatabase.getReference();


        bt_guardianOTPSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLoginNumber = "+91" + et_guardianNumberLogin.getText().toString();

                sendVerificationCode(userLoginNumber);
            }
        });

        bt_verifyGuardianOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(et_enterGuardianOTP.getText().toString());
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                compare();
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
                    et_enterGuardianOTP.setVisibility(View.VISIBLE);
                    bt_verifyGuardianOTP.setVisibility(View.VISIBLE);
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                    final String code = phoneAuthCredential.getSmsCode();


                    if (code != null) {

                        et_enterGuardianOTP.setText(code);


                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {


                    Toast.makeText(GuardianLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

                            Toast.makeText(GuardianLoginActivity.this, "OTP VERIFIED", Toast.LENGTH_SHORT).show();
                            GuardianInfo guardianInfo = new GuardianInfo(et_guardianName.getText().toString(),"+91" + et_guardianNumberLogin.getText().toString());
                            databaseReference.child("Guardians").setValue(guardianInfo);
                            databaseReference.child("Guardians").child("user-info").setValue("+91" + et_userPhoneNumber.getText().toString());

                        }

                        else {

                            Toast.makeText(GuardianLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    void compare(){

        databaseReference.child("Users").child("+91" + et_userPhoneNumber.getText().toString()).child("GuardianInfo").child("guardianPhone1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cGuardianNumber = snapshot.getValue().toString();
                if(cGuardianNumber.equals(userLoginNumber)){

                    databaseReference.child("Guardians").child("user-info").setValue("+91" + et_userPhoneNumber.getText().toString());
                    Intent i = new Intent(GuardianLoginActivity.this, MapsActivity.class);
                    i.putExtra("userNumber", "+91" + et_userPhoneNumber.getText().toString());
                    startActivity(i);

                }
                else{
                    Toast.makeText(GuardianLoginActivity.this, "Not Equal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GuardianLoginActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
            }
        });

    }
}