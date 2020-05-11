package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lumbardhelshani.coronavirus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthCareActivity extends AppCompatActivity {
    @BindView(R.id.bottomNavigation) BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care);
        ButterKnife.bind(this);
        setUpBottomNavigation();
    }

    private void setUpBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.healthcare);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.countries:
                        startActivity(new Intent(getApplicationContext(), CountriesActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.world:
                        startActivity(new Intent(getApplicationContext(), WorldStatsActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.symptopms:
                        startActivity(new Intent(getApplicationContext(), SymptomsActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.healthcare:
                        return true;
                }
                return false;
            }
        });
    }
}
