package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lumbardhelshani.coronavirus.Listeners.OnSwipeTouchListener;
import com.lumbardhelshani.coronavirus.Managers.HttpsTrustManager;
import com.lumbardhelshani.coronavirus.Models.CountryCovidData;
import com.lumbardhelshani.coronavirus.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailCountryActivity extends AppCompatActivity {
    // Bind Views with ButterKnife
    @BindView(R.id.barChart) BarChart barChart;
    @BindView(R.id.countryCasesTxt) TextView casesTxt;
    @BindView(R.id.recoveredTxt) TextView recoveredTxt;
    @BindView(R.id.criticalTxt) TextView criticalTxt;
    @BindView(R.id.activeTxt) TextView activeTxt;
    @BindView(R.id.todayCasesTxt) TextView todayCasesTxt;
    @BindView(R.id.deathsTxt) TextView totalDeathsTxt;
    @BindView(R.id.todayDeathsTxt) TextView todayDeathsTxt;
    @BindView(R.id.detailsOfTxt) TextView detailsOfTxt;
    @BindView(R.id.bottomNavigation) BottomNavigationView bottomNavigation;
    @BindView(R.id.covidSituation) ImageView covidSituation;
    @BindView(R.id.detailCountryLayout) LinearLayout detailCountryLayout;

    private  int countryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_country);
        ButterKnife.bind(this);
        setSwipeListener();
        Intent intent = getIntent();
        countryPosition = intent.getIntExtra("position",0);
        fillAllViews();
    }

    //Here is handled the swipe right and left listener
    @SuppressLint("ClickableViewAccessibility")
    private void setSwipeListener() {
        detailCountryLayout.setOnTouchListener(new OnSwipeTouchListener(DetailCountryActivity.this) {

            public void onSwipeRight() {
                startActivity(new Intent(getApplicationContext(), WorldStatsActivity.class));
                Toast.makeText(DetailCountryActivity.this, "WORLD", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                startActivity(new Intent(getApplicationContext(), SymptomsActivity.class));
                Toast.makeText(DetailCountryActivity.this, "SYMPTOMS", Toast.LENGTH_SHORT).show();
            }


        });
    }
    //This method fill all views in the detail country activity layout with data
    private void fillAllViews() {
        setUpBottomNavigation();
        int length = CountriesActivity.countryModelsList.get(countryPosition).getCountryName().length();
        detailsOfTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getCountryName().substring(0, Math.min(length ,15)));
        casesTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getCases());
        recoveredTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getRecovered());
        criticalTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getCritical());
        activeTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getActive());
        todayCasesTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getTodayCases());
        totalDeathsTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getDeaths());
        todayDeathsTxt.setText(CountriesActivity.countryModelsList.get(countryPosition).getTodayDeaths());

        putCountryData(CountriesActivity.countryModelsList.get(countryPosition).getCountryName(), CountriesActivity.countryModelsList.get(countryPosition).getTodayDeaths());
        int redColorValue = Color.RED;
        int yellowColorValue = Color.YELLOW;
        int greenColorValue = Color.GREEN;
        int blueColorValue = Color.BLUE;
        int magentaColorValue = Color.MAGENTA;
        int actualCases = Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getTodayCases());
        actualSituation(CountriesActivity.countryModelsList.get(countryPosition).getCountryName(),  actualCases);

        //Below the barchart is constructed to display the statistics for the specific country
        barChart.addBar(new BarModel("Cases",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getCases()),yellowColorValue));
        barChart.addBar(new BarModel("Recovered",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getRecovered()), greenColorValue));
        barChart.addBar(new BarModel("Active",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getActive()),blueColorValue));
        barChart.addBar(new BarModel("Today Cases",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getTodayCases()),magentaColorValue));
        barChart.addBar(new BarModel("Deaths",Integer.parseInt(CountriesActivity.countryModelsList.get(countryPosition).getDeaths()),redColorValue));
        barChart.startAnimation();
    }

    //Here is set up the bottom navigation and its item select listener
    private void setUpBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.countries);
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

    //This methods get data from a web service for yesterday cases to compare with today and then based on the comparision sets a different image
    private void actualSituation(String countryName, int actual){
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
                            else if( actualCases == previousCases){
                                covidSituation.setImageResource(R.drawable.equal);
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

    //This method is used to get yesterday date
    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    //This method is used to put the data for that specific country to mysql db using the Laravel API
    private void putCountryData(String name, String cases) {
        String url = "http://192.168.1.81:8000/api/";
        try{
            Log.d("Debug" , "HINI NE METODEEE");
            Toast.makeText(DetailCountryActivity.this, "OnMethod",Toast.LENGTH_SHORT);


            String requestURL = url+"registerCountryCase";
            final JSONObject jsonBody = new JSONObject("{\"countryName\":\""+name+"\" ,\"cases\":\""+cases+"\" , \"date\":\""+getTodayDate()+"\"}");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, requestURL, jsonBody, (com.android.volley.Response.Listener<JSONObject>) response -> {
                        Log.d("DEBUG", "RESPONSE " + response.toString());
                        Toast.makeText(DetailCountryActivity.this, "OnResponse", Toast.LENGTH_SHORT);
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("DEBUG", "ERROROnResponse " + error.getMessage());
                            Toast.makeText(DetailCountryActivity.this, "OnError", Toast.LENGTH_SHORT);
                        }});
            requestQueue.add(jsonObjectRequest);

        }catch (Exception e){
            Log.d("DEBUG" , "Exception error " + e.getMessage());
        }
    }

    //This method returns today date in string
    private String getTodayDate(){
        Date systemDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(systemDate);
        return date;
    }
}
