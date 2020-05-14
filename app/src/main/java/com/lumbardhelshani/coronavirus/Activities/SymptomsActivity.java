package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lumbardhelshani.coronavirus.Listeners.OnSwipeTouchListener;
import com.lumbardhelshani.coronavirus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SymptomsActivity extends AppCompatActivity {
    // Bind Views with ButterKnife
    @BindView(R.id.bottomNavigation) BottomNavigationView bottomNavigation;
    @BindView(R.id.symptomsLayout) RelativeLayout symptomsLayout;
    @BindView(R.id.symptomsScroll) ScrollView symptomsScroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        ButterKnife.bind(this);
        setSwipeListener();
        setUpBottomNavigation();
    }
    //Here is handled the swipe right and left listener
    @SuppressLint("ClickableViewAccessibility")
    private void setSwipeListener() {
        symptomsLayout.setOnTouchListener(new OnSwipeTouchListener(SymptomsActivity.this) {

            public void onSwipeRight() {
                startActivity(new Intent(getApplicationContext(), CountriesActivity.class));
                Toast.makeText(SymptomsActivity.this, "COUNTRIES", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                startActivity(new Intent(getApplicationContext(), HealthCareActivity.class));
                Toast.makeText(SymptomsActivity.this, "HEALTHCARE", Toast.LENGTH_SHORT).show();
            }

        });
        symptomsScroll.setOnTouchListener(new OnSwipeTouchListener(SymptomsActivity.this) {

            public void onSwipeRight() {
                startActivity(new Intent(getApplicationContext(), CountriesActivity.class));
                Toast.makeText(SymptomsActivity.this, "COUNTRIES", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                startActivity(new Intent(getApplicationContext(), HealthCareActivity.class));
                Toast.makeText(SymptomsActivity.this, "HEALTHCARE", Toast.LENGTH_SHORT).show();
            }


        });
    }
    //Here is set up the bottom navigation and its item select listener
    private void setUpBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.symptopms);
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
                        return true;
                    case R.id.healthcare:
                        startActivity(new Intent(getApplicationContext(), HealthCareActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
