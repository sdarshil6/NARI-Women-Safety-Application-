package com.company.currentlocation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import android.location.LocationListener;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.company.currentlocation.databinding.ActivityMaps2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity2 extends FragmentActivity implements LocationListener, OnMapReadyCallback{

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    LocationManager manager;

    private  final int MIN_TIME = 2000; // 1 sec

    private final int MIN_DISTANCE = 1;  // 1 METER

    Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent i = getIntent();
        String userNumber = i.getStringExtra("userNumber");

        reference = firebaseDatabase.getReference().child("Users").child(userNumber).child("LocatinInfo");

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        getlocationupdates();

        readChanges();

    }

    private void readChanges() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {

                        MyLocationSecond location = snapshot.getValue(MyLocationSecond.class);

                        if (location != null) {
                            myMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
                        }
                    }catch (Exception e){
                        Toast.makeText(MapsActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getlocationupdates() {

        if (manager != null){

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                } else {
                    Toast.makeText(this, "NO provider Enabled", Toast.LENGTH_SHORT).show();
                }

            }

            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101){

            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getlocationupdates();
            }
            else{
                Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void savelocation(Location location) {

        reference.setValue(location);
        //Toast.makeText(this, "Live Location Sent", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null){
            savelocation(location);
        }
        else{
            Toast.makeText(this, "No location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(
                25.24104212, 75.90116502);
        myMarker= mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}