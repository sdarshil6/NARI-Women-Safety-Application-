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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuardianInformationActivity extends AppCompatActivity {

    EditText et_name1, et_phone1, et_phone3, et_phone2; //et_name2, et_phone2;
    Button bt_done;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_information);

        firebaseDatabase = FirebaseDatabase.getInstance();


        et_name1 = findViewById(R.id.et_name1);
        et_phone1 = findViewById(R.id.et_phone1);
        et_phone2 = findViewById(R.id.et_phone2);
        et_phone3 = findViewById(R.id.et_phone3);




        bt_done = findViewById(R.id.bt_done);

        Intent intent = getIntent();
        String userNumber = intent.getStringExtra("userNumber");
        Log.d("User Number GuardianInformation Activity", userNumber);

        databaseReference = firebaseDatabase.getReference().child("Users").child(userNumber).child("GuardianInfo");

        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_phone2.getText().toString() == null || et_phone3.getText().toString() == null ) {


                    GuardianInfo guardianInfo = new GuardianInfo(et_name1.getText().toString(), "+91" + et_phone1.getText().toString(), "+91" + et_phone2.getText().toString(), "+91" + et_phone3.getText().toString());
                    databaseReference.setValue(guardianInfo);
                    Intent in = new Intent(GuardianInformationActivity.this, MainActivity.class);
                    in.putExtra("userNumber", userNumber);
                    startActivity(in);
                }
                else{
                    GuardianInfo guardianInfo = new GuardianInfo(et_name1.getText().toString(), "+91" + et_phone1.getText().toString(), "+91" + et_phone2.getText().toString(), "+91" + et_phone3.getText().toString());
                    databaseReference.setValue(guardianInfo);
                    Intent in = new Intent(GuardianInformationActivity.this, MainActivity.class);
                    in.putExtra("userNumber", userNumber);
                    startActivity(in);
                }

                if(et_phone2.getText().toString() == null && et_phone3.getText().toString() == null ) {


                    GuardianInfo guardianInfo = new GuardianInfo(et_name1.getText().toString(), "+91" + et_phone1.getText().toString(), "+91" + et_phone2.getText().toString(), "+91" + et_phone3.getText().toString());
                    databaseReference.setValue(guardianInfo);
                    Intent in = new Intent(GuardianInformationActivity.this, MainActivity.class);
                    in.putExtra("userNumber", userNumber);
                    startActivity(in);
                }

            }
        });
    }

}