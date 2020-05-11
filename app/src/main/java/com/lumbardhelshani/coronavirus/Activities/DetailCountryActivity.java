package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lumbardhelshani.coronavirus.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailCountryActivity extends AppCompatActivity {
    BarChart barChart;
    private  int countryPosition;
    TextView casesTxt,recoveredTxt,criticalTxt,activeTxt,todayCasesTxt,totalDeathsTxt,todayDeathsTxt, detailsOfTxt;

    BottomNavigationView bottomNavigation;
    ImageView covidSituation;
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
        casesTxt = findViewById(R.id.countryCasesTxt);
        recoveredTxt = findViewById(R.id.recoveredTxt);
        criticalTxt = findViewById(R.id.criticalTxt);
        activeTxt = findViewById(R.id.activeTxt);
        todayCasesTxt = findViewById(R.id.todayCasesTxt);
        totalDeathsTxt = findViewById(R.id.deathsTxt);
        todayDeathsTxt = findViewById(R.id.todayDeathsTxt);
        detailsOfTxt = findViewById(R.id.detailsOfTxt);
        barChart =(BarChart) findViewById(R.id.barChart);
        int length = CountriesActivity.countryModelsList.get(countryPosition).getCountryName().length();
        detailsOfTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getCountryName().substring(0, Math.min(length ,15)));
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
        int actualCases = Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getTodayCases());
        actualSituation(CountriesActivity.countryModelsList.get(countryPosition).getCountryName(),  actualCases);
        barChart.addBar(new BarModel("Cases",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getCases()),yellowColorValue));
        barChart.addBar(new BarModel("Recovered",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getRecovered()), greenColorValue));
        barChart.addBar(new BarModel("Active",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getActive()),blueColorValue));
        barChart.addBar(new BarModel("Deaths",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getDeaths()),redColorValue));
        barChart.addBar(new BarModel("Today Cases",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getTodayCases()),magentaColorValue));
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

    private void actualSituation(String countryName, int actual){
        covidSituation = findViewById(R.id.covidSituation);
        final int actualCases = actual;
        HttpsTrustManager.allowAllSSL();
        String url = "https://covid-api.com/api/reports?date="+ getYesterdayDateString()+"&q="+countryName;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String previous = jsonObject.getString("confirmed");
                            int previousCases = Integer.parseInt(previous);
                            Log.d("debug", "try");
                            if(actualCases > previousCases){
                                covidSituation.setImageResource(R.drawable.ic_up);
                            }
                            else if(actualCases < previousCases){
                                covidSituation.setImageResource(R.drawable.ic_down);
                            }
                            else{
                                covidSituation.setImageResource(R.drawable.ic_equal);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("debug", "catch");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailCountryActivity.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                Log.d("debug", "onerror");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }



}
