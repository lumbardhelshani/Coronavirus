package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leo.simplearcloader.SimpleArcLoader;
import com.lumbardhelshani.coronavirus.Adapters.CountryListAdapter;
import com.lumbardhelshani.coronavirus.Models.Country;
import com.lumbardhelshani.coronavirus.Models.CountryCovidData;
import com.lumbardhelshani.coronavirus.R;
import com.lumbardhelshani.coronavirus.Retrofit.CovidService;
import com.lumbardhelshani.coronavirus.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;

public class CountriesActivity extends AppCompatActivity {
    @BindView(R.id.searchEditTxt) EditText searchEditTxt;
    ListView listView;
    @BindView(R.id.loader) SimpleArcLoader loader;
    public static List<Country> countryModelsList = new ArrayList<>();
    Country country;
    CountryListAdapter adapter;
    @BindView(R.id.bottomNavigation) BottomNavigationView bottomNavigation;

    CovidService covidService = RetrofitClient.getRetrofitInstance().create(CovidService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        setUpBottomNavigation();
        getCountryCovidData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailCountryActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

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

    private void getCountryCovidData() {

        Call<List<CountryCovidData>> call = covidService.getCountriesStatistics();

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
}
