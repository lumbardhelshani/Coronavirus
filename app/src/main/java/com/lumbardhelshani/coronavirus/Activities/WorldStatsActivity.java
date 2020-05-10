package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leo.simplearcloader.SimpleArcLoader;
import com.lumbardhelshani.coronavirus.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class WorldStatsActivity extends AppCompatActivity {
    TextView casesTxt,recoveredTxt,criticalTxt,activeTxt,todayCasesTxt,totalDeathsTxt,todayDeathsTxt,affectedCountriesTxt;
    BottomNavigationView bottomNavigation;
    SimpleArcLoader loader;
    ScrollView scrollViewScr;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_stats);
        findAllViews();
        getCovidData();

    }

    private void findAllViews() {
        setUpBottomNavigation();
        casesTxt = findViewById(R.id.casesTxt);
        recoveredTxt = findViewById(R.id.recoveredTxt);
        criticalTxt = findViewById(R.id.criticalTxt);
        activeTxt = findViewById(R.id.activeTxt);
        todayCasesTxt = findViewById(R.id.todayCasesTxt);
        totalDeathsTxt = findViewById(R.id.totalDeathsTxt);
        todayCasesTxt = findViewById(R.id.todayCasesTxt);
        todayDeathsTxt = findViewById(R.id.todayDeathsTxt);
        affectedCountriesTxt = findViewById(R.id.tvAffectedCountries);
        loader = findViewById(R.id.loader);
        scrollViewScr = findViewById(R.id.scrollStatsScr);
        pieChart = findViewById(R.id.piechart);
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

    private void getCovidData(){
        String url  = "https://corona.lmao.ninja/v2/all/";
        loader.start();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            fillViewsWithData(jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loader.stop();
                            loader.setVisibility(View.GONE);
                            loader.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE);
                scrollViewScr.setVisibility(View.VISIBLE);
                Toast.makeText(WorldStatsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }

    private void fillViewsWithData(JSONObject jsonObject) throws JSONException {
        casesTxt.setText(jsonObject.getString("cases"));
        recoveredTxt.setText(jsonObject.getString("recovered"));
        criticalTxt.setText(jsonObject.getString("critical"));
        activeTxt.setText(jsonObject.getString("active"));
        todayCasesTxt.setText(jsonObject.getString("todayCases"));
        totalDeathsTxt.setText(jsonObject.getString("deaths"));
        todayDeathsTxt.setText(jsonObject.getString("todayDeaths"));
        affectedCountriesTxt.setText(jsonObject.getString("affectedCountries"));
        pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(casesTxt.getText().toString()), Color.parseColor("#FDD835")));
        pieChart.addPieSlice(new PieModel("Recoverd",Integer.parseInt(recoveredTxt.getText().toString()), Color.parseColor("#43A047")));
        pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(totalDeathsTxt.getText().toString()), Color.parseColor("#E53935")));
        pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(activeTxt.getText().toString()), Color.parseColor("#3949AB")));
        pieChart.startAnimation();
        loader.stop();
        loader.setVisibility(View.GONE);
        scrollViewScr.setVisibility(View.VISIBLE);
    }
}
