package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leo.simplearcloader.SimpleArcLoader;
import com.lumbardhelshani.coronavirus.Listeners.OnSwipeTouchListener;
import com.lumbardhelshani.coronavirus.Models.WorldCovidData;
import com.lumbardhelshani.coronavirus.R;
import com.lumbardhelshani.coronavirus.Retrofit.CovidService;
import com.lumbardhelshani.coronavirus.Retrofit.RetrofitClient;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorldStatsActivity extends AppCompatActivity {
    // Bind Views with ButterKnife
    @BindView(R.id.casesTxt) TextView casesTxt;
    @BindView(R.id.recoveredTxt) TextView recoveredTxt;
    @BindView(R.id.criticalTxt) TextView criticalTxt;
    @BindView(R.id.activeTxt) TextView activeTxt;
    @BindView(R.id.todayCasesTxt) TextView todayCasesTxt;
    @BindView(R.id.totalDeathsTxt) TextView totalDeathsTxt;
    @BindView(R.id.todayDeathsTxt) TextView todayDeathsTxt;
    @BindView(R.id.affectedCountriesTxt) TextView affectedCountriesTxt;
    @BindView(R.id.bottomNavigation) BottomNavigationView bottomNavigation;
    @BindView(R.id.worldStatisticsLoader) SimpleArcLoader loader;
    @BindView(R.id.scrollStatsScr) ScrollView scrollViewScr;
    @BindView(R.id.piechart) PieChart pieChart;
    @BindView(R.id.worldStatsLayout) RelativeLayout worldStatsLayout;

    // Get an instance of Retrofit (It is a singeleton class)
    CovidService covidService = RetrofitClient.getRetrofitInstance().create(CovidService.class);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_stats);
        ButterKnife.bind(this);
        setUpBottomNavigation();
        getCovidData();
        setSwipeListener();


    }
    //Here is handled the swipe right and left listener
    @SuppressLint("ClickableViewAccessibility")
    private void setSwipeListener() {
        worldStatsLayout.setOnTouchListener(new OnSwipeTouchListener(WorldStatsActivity.this) {

            public void onSwipeLeft() {
                startActivity(new Intent(getApplicationContext(), CountriesActivity.class));
                Toast.makeText(WorldStatsActivity.this, "COUNTRIES", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //Here is set up the bottom navigation and its item select listener
    private void setUpBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.world);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.countries:
                        startActivity(new Intent(getApplicationContext(), CountriesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.world:
                        return true;
                    case R.id.symptopms:
                        startActivity(new Intent(getApplicationContext(), SymptomsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.healthcare:
                        startActivity(new Intent(getApplicationContext(), HealthCareActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    //This method is used to get the data from a webservice
    private void getCovidData() {
        Call<WorldCovidData> call = covidService.getWorldCovidStatistics();
        loader.start();
        call.enqueue(new Callback<WorldCovidData>() {
            @Override
            public void onResponse(Call<WorldCovidData> call, Response<WorldCovidData> response) {
                fillViewsWithData(response.body());
                putData(response.body());
            }

            @Override
            public void onFailure(Call<WorldCovidData> call, Throwable t) {
                loader.stop();
                loader.setVisibility(View.GONE);
                scrollViewScr.setVisibility(View.VISIBLE);
                Log.d("Debug", "Error msg: " + t.getMessage(), t);
                Toast.makeText(WorldStatsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //This method is used to fill all the views with the data when we get them from the webservice
    private void fillViewsWithData(WorldCovidData worldCovidData) {
        casesTxt.setText(String.valueOf(worldCovidData.getCases()));
        recoveredTxt.setText(String.valueOf(worldCovidData.getRecovered()));
        criticalTxt.setText(String.valueOf(worldCovidData.getCritical()));
        activeTxt.setText(String.valueOf(worldCovidData.getActive()));
        todayCasesTxt.setText(String.valueOf(worldCovidData.getTodayCases()));
        totalDeathsTxt.setText(String.valueOf(worldCovidData.getDeaths()));
        todayDeathsTxt.setText(String.valueOf(worldCovidData.getTodayDeaths()));
        affectedCountriesTxt.setText(String.valueOf(worldCovidData.getAffectedCountries()));
        pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(casesTxt.getText().toString()), Color.parseColor("#FDD835")));
        pieChart.addPieSlice(new PieModel("Recoverd", Integer.parseInt(recoveredTxt.getText().toString()), Color.parseColor("#43A047")));
        pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(totalDeathsTxt.getText().toString()), Color.parseColor("#E53935")));
        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(activeTxt.getText().toString()), Color.parseColor("#3949AB")));
        pieChart.startAnimation();
        loader.stop();
        loader.setVisibility(View.GONE);
        scrollViewScr.setVisibility(View.VISIBLE);
    }

    //This method is to get today date
    private String getTodayDate(){
        Date systemDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(systemDate);
        return date;
    }


    //This method is used to put the data we get from the webservice to our mysql db using Laravel API
    private void putData(WorldCovidData model) {
        String url = "http://192.168.1.81:8000/api/";
        try{
            Log.d("Debug" , "Put Data");
            Toast.makeText(WorldStatsActivity.this, "OnMethod",Toast.LENGTH_SHORT);


            String requestURL = url+"registerWorldCase";
            final JSONObject jsonBody = new JSONObject("{\"cases\":\""+model.getTodayCases()+"\" , \"date\":\""+getTodayDate()+"\"}");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, requestURL, jsonBody, (com.android.volley.Response.Listener<JSONObject>) response -> {
                        Log.d("DEBUG", "RESPONSE " + response.toString());
                        Toast.makeText(WorldStatsActivity.this, "OnResponse", Toast.LENGTH_SHORT);
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("DEBUG", "ERROROnResponse " + error.getMessage());
                            Toast.makeText(WorldStatsActivity.this, "OnError", Toast.LENGTH_SHORT);
                        }});
            requestQueue.add(jsonObjectRequest);

        }catch (Exception e){
            Log.d("DEBUG" , "Exception error " + e.getMessage());
        }


    }
}
