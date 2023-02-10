package com.company.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base_drawer, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_about:
                overridePendingTransition(0,0);
                break;

            case R.id.nav_logout:
                RegisterActivity.auth.signOut();
//                UserLoginActivity.sp.edit().clear().commit();
                finish();
                startActivity(new Intent(BaseDrawerActivity.this, BasicLoginActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_profile:

                startActivity(new Intent(BaseDrawerActivity.this, UserProfileActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_helplineNumbers:

                startActivity(new Intent(BaseDrawerActivity.this, IMP_CONTACT.class));
                overridePendingTransition(0, 0);
                break;

        }
        return false;
    }

    protected void allocateActivityTitle(String titleString){

        if(getSupportActionBar() != null){

            getSupportActionBar().setTitle(titleString);

        }

    }

}