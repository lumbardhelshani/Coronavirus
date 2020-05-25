package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leo.simplearcloader.SimpleArcLoader;
import com.lumbardhelshani.coronavirus.Adapters.CountryListAdapter;
import com.lumbardhelshani.coronavirus.Listeners.OnSwipeTouchListener;
import com.lumbardhelshani.coronavirus.Models.Country;
import com.lumbardhelshani.coronavirus.Models.CountryCovidData;
import com.lumbardhelshani.coronavirus.Models.WorldCovidData;
import com.lumbardhelshani.coronavirus.R;
import com.lumbardhelshani.coronavirus.Retrofit.CovidService;
import com.lumbardhelshani.coronavirus.Retrofit.RetrofitClient;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class CountriesActivity extends AppCompatActivity {
    // Bind Views with ButterKnife
    @BindView(R.id.searchEditTxt) EditText searchEditTxt;
    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.countriesLoader) SimpleArcLoader loader;
    @BindView(R.id.countriesLayout) RelativeLayout countriesLayout;
    @BindView(R.id.bottomNavigation) BottomNavigationView bottomNavigation;

    public static List<Country> countryModelsList = new ArrayList<>();
    Country country;
    CountryListAdapter adapter;

    // Get an instance of Retrofit (It is a singeleton class)
    CovidService covidService = RetrofitClient.getRetrofitInstance().create(CovidService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        ButterKnife.bind(this);
        setUpBottomNavigation();

        //
        getCountryCovidData();
        setSwipeListener();

        //Set the On Item Click Listener to the list view which contains the countries
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailCountryActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        setSearchEditTxtListener();


    }

    //This method set the listener to the search edit text and uses an adapter to filter the searched items
    private void setSearchEditTxtListener() {
        searchEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //Here is handled the swipe right and left listener
    @SuppressLint("ClickableViewAccessibility")
    private void setSwipeListener() {
        countriesLayout.setOnTouchListener(new OnSwipeTouchListener(CountriesActivity.this) {

            public void onSwipeRight() {
                startActivity(new Intent(getApplicationContext(), WorldStatsActivity.class));
                Toast.makeText(CountriesActivity.this, "WORLD", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                startActivity(new Intent(getApplicationContext(), SymptomsActivity.class));
                Toast.makeText(CountriesActivity.this, "SYMPTOMS", Toast.LENGTH_SHORT).show();
            }


        });
        listView.setOnTouchListener(new OnSwipeTouchListener(CountriesActivity.this) {

            public void onSwipeRight() {
                startActivity(new Intent(getApplicationContext(), WorldStatsActivity.class));
                Toast.makeText(CountriesActivity.this, "WORLD", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                startActivity(new Intent(getApplicationContext(), SymptomsActivity.class));
                Toast.makeText(CountriesActivity.this, "SYMPTOMS", Toast.LENGTH_SHORT).show();
            }


        });
    }

    //Here is set up the bottom navigation and its item select listener
    private void setUpBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.countries);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.countries:
                        return true;
                    case R.id.world:
                        startActivity(new Intent(getApplicationContext(), WorldStatsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.symptopms:
                        startActivity(new Intent(getApplicationContext(), SymptomsActivity.class));
                        overridePendingTransition(0, 0);
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
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    //This method makes a request to the webservice and gets the data in json and maps it to the model Country Covid Data
    private void getCountryCovidData() {

        Call<List<CountryCovidData>> call = covidService.getCountriesStatistics();
        loader.start();
        call.enqueue(new Callback<List<CountryCovidData>>() {
            @Override
            public void onResponse(Call<List<CountryCovidData>> call, retrofit2.Response<List<CountryCovidData>> response) {
                for (int i = 0; i < response.body().size(); i++) {

                    CountryCovidData model = response.body().get(i);
                    String countryName = model.getCountry();
                    String cases = String.valueOf(model.getCases());
                    String todayCases = String.valueOf(model.getTodayCases());
                    String deaths = String.valueOf(model.getDeaths());
                    String todayDeaths = String.valueOf(model.getTodayDeaths());
                    String recovered = String.valueOf(model.getRecovered());
                    String active = String.valueOf(model.getActive());
                    String critical = String.valueOf(model.getCritical());
                    String flagUrl = model.getCountryInfo().getFlag();
                    country = new Country(flagUrl, countryName, cases, todayCases, deaths, todayDeaths, recovered, active, critical);
                    countryModelsList.add(country);

                    //Undo comment for this method in case you want to put in your db all country cases from Countries Activity
                    //putCountryData(model);
                }
                adapter = new CountryListAdapter(CountriesActivity.this, countryModelsList);
                listView.setAdapter(adapter);
                loader.stop();
                loader.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<CountryCovidData>> call, Throwable t) {
                loader.stop();
                loader.setVisibility(View.GONE);
                Toast.makeText(CountriesActivity.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //This method puts the data in mysql db using the Laravel API
    private void putCountryData(CountryCovidData model) {
        String url = "http://192.168.1.81:8000/api/";
        try{
            loader.start();
            Log.d("Desbug" , "HINI NE METODEEE");
            Toast.makeText(CountriesActivity.this, "OnMethod",Toast.LENGTH_SHORT);


            String requestURL = url+"registerCountryCase";
            final JSONObject jsonBody = new JSONObject("{\"countryName\":\""+model.getCountry()+"\" ,\"cases\":\""+model.getTodayCases()+"\" , \"date\":\""+getTodayDate()+"\"}");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, requestURL, jsonBody, (com.android.volley.Response.Listener<JSONObject>) response -> {
                        Log.d("DEBUG", "RESPONSE " + response.toString());
                        Toast.makeText(CountriesActivity.this, "OnResponse", Toast.LENGTH_SHORT);
                        loader.stop();
                        loader.setVisibility(View.GONE);
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loader.stop();
                            loader.setVisibility(View.GONE);
                            Log.d("DEBUG", "ERROROnResponse " + error.getMessage());
                            Toast.makeText(CountriesActivity.this, "OnError", Toast.LENGTH_SHORT);
                        }});
            requestQueue.add(jsonObjectRequest);

        }catch (Exception e){
            Log.d("DEBUG" , "Exception error " + e.getMessage());
        }
    }

    //This method returns today date to use it on the method above
    private String getTodayDate(){
        Date systemDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(systemDate);
        return date;
    }
}
