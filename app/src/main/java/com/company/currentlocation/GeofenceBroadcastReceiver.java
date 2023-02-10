package com.company.currentlocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Geofence Triggered", Toast.LENGTH_SHORT).show();
        SmsManager smsManager = SmsManager.getDefault();
        MainActivity.newDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String Guardian_Number = snapshot.getValue().toString();
                smsManager.sendTextMessage(Guardian_Number,null,"I have reached the destination",null,null);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("err",error.toString());
            }
        });

        Toast.makeText(context, "Destination message sent to your guardian", Toast.LENGTH_SHORT).show();
    }
}