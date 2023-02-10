package com.company.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.company.currentlocation.databinding.ActivityMainBinding;
import com.company.currentlocation.databinding.ActivityUserProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends BaseDrawerActivity {

    private TextView tv_myName, tv_gName, tv_gNum, tv_myNum;
    private ActivityUserProfileBinding activityUserProfileBinding;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main); //AppCompat...
        setContentView(activityUserProfileBinding.getRoot()); //BaseDrawer....
        allocateActivityTitle("User Profile");

        tv_myName = findViewById(R.id.tv_myName);
        tv_gName = findViewById(R.id.tv_gName);
        tv_gNum = findViewById(R.id.tv_gNum);
        tv_myNum = findViewById(R.id.tv_myNum);

        databaseReference = firebaseDatabase.getReference().child("Users").child(MainActivity.userNum);
        populateAllTextViews();
    }

    private void populateAllTextViews(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_myName.setText(snapshot.child("fullname").getValue().toString());
                tv_gName.setText(snapshot.child("GuardianInfo").child("guardianName1").getValue().toString());
                tv_gNum.setText(snapshot.child("GuardianInfo").child("guardianPhone1").getValue().toString());
                tv_myNum.setText(snapshot.child("phone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}