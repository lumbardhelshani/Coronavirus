package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lumbardhelshani.coronavirus.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;

public class DetailCountryActivity extends AppCompatActivity {
    BarChart barChart;
    private  int countryPosition;
    TextView countryNameTxt,casesTxt,recoveredTxt,criticalTxt,activeTxt,todayCasesTxt,totalDeathsTxt,todayDeathsTxt, detailsOfTxt;
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_country);
        Intent intent = getIntent();
        countryPosition = intent.getIntExtra("position",0);
        findAllViews();

    }
    private void findAllViews() {
        setUpBottomNavigation();
        countryNameTxt = findViewById(R.id.countryNameTxt);
        casesTxt = findViewById(R.id.countryCasesTxt);
        recoveredTxt = findViewById(R.id.recoveredTxt);
        criticalTxt = findViewById(R.id.criticalTxt);
        activeTxt = findViewById(R.id.activeTxt);
        todayCasesTxt = findViewById(R.id.todayCasesTxt);
        totalDeathsTxt = findViewById(R.id.deathsTxt);
        todayDeathsTxt = findViewById(R.id.todayDeathsTxt);
        detailsOfTxt = findViewById(R.id.detailsOfTxt);
        barChart =(BarChart) findViewById(R.id.barChart);
        detailsOfTxt.setText("Details of " + CountriesActivity.countryModelsList.get(countryPosition).getCountryName());
        countryNameTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getCountryName());
        casesTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getCases());
        recoveredTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getRecovered());
        criticalTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getCritical());
        activeTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getActive());
        todayCasesTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getTodayCases());
        totalDeathsTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getDeaths());
        todayDeathsTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getTodayDeaths());
        int redColorValue = Color.RED;
        int yellowColorValue = Color.YELLOW;
        int greenColorValue = Color.GREEN;
        int blueColorValue = Color.BLUE;
        int magentaColorValue = Color.MAGENTA;
        barChart.addBar(new BarModel("Cases",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getCases()),yellowColorValue));
        barChart.addBar(new BarModel("Recovered",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getRecovered()), greenColorValue));
        barChart.addBar(new BarModel("Active",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getActive()),blueColorValue));
        barChart.addBar(new BarModel("Today Cases",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getTodayCases()),magentaColorValue));
        barChart.addBar(new BarModel("Deaths",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getDeaths()),redColorValue));
        barChart.startAnimation();

    }

    private void setUpBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.world);
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
                        startActivity(new Intent(getApplicationContext(), HealthCareActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
