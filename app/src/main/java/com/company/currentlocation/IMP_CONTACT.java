package com.company.currentlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class IMP_CONTACT extends AppCompatActivity implements SelectListener{

    RecyclerView recyclerView;
    List<MyModel> myModelList;

    CustomAdapter customAdapter;

    static  int  PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_contact);
        recyclerView = findViewById(R.id.recycler_main);


        // CHECK FOR THE CALL PERMISSION
        if(ContextCompat.checkSelfPermission(IMP_CONTACT.this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(IMP_CONTACT.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }


        displayItems();
    }

    private void displayItems() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        myModelList = new ArrayList<>();

        myModelList.add(new MyModel("Police","100"));
        myModelList.add(new MyModel("Fire Station","101"));
        myModelList.add(new MyModel("Ambulance","108"));
        myModelList.add(new MyModel("Women Helpline","181"));
        myModelList.add(new MyModel("Citizen's center","155303"));
        myModelList.add(new MyModel("Child Helpline","1098"));
        myModelList.add(new MyModel("District Helpline","1077"));
        myModelList.add(new MyModel("Crime Stopper Helpline","1090"));

        customAdapter = new CustomAdapter(this,myModelList,this);
        recyclerView.setAdapter(customAdapter);

    }

    @Override
    public void onItemClicked(MyModel myModel) {
        Toast.makeText(this,String.valueOf(myModel.getAge()), Toast.LENGTH_SHORT).show();
//        String phoneno = myModel.getAge();
//        Intent i = new Intent(Intent.ACTION_CALL);
//        i.setData(Uri.parse("tel:"+phoneno));
//        startActivity(i);
    }
}