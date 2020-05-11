package com.lumbardhelshani.coronavirus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.Collator;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leo.simplearcloader.SimpleArcLoader;
import com.lumbardhelshani.coronavirus.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.CollationElementIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorldStatsActivity extends AppCompatActivity {
    TextView casesTxt,recoveredTxt,criticalTxt,activeTxt,todayCasesTxt,totalDeathsTxt,todayDeathsTxt,affectedCountriesTxt;
    BottomNavigationView bottomNavigation;
    SimpleArcLoader loader;
    ScrollView scrollViewScr;
    PieChart pieChart;
    private String cases;
    private Date systemDate = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String date = df.format(systemDate);
    private Collator MySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_stats);
        findAllViews();
        getCovidData();

            putData();


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
                            cases = jsonObject.getString("cases");
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


    private void putData(){

     /*   try{
            Log.d("Debug" , "HINI NE METODEEE");
            Toast.makeText(WorldStatsActivity.this, "OnMethod",Toast.LENGTH_SHORT);


            String url = "http://192.168.1.81/services/create_world_record.php";
            final JSONObject jsonBody = new JSONObject("{\"cases\":\"300\" , \"date\":\"1999-01-01\"}");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonBody , new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("DEBUG" , "RESPONSE " + response.toString());
                            Toast.makeText(WorldStatsActivity.this, "OnResponse",Toast.LENGTH_SHORT);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.d("DEBUG" , "ERROROnResponse " + error.getMessage());
                            Toast.makeText(WorldStatsActivity.this, "OnError",Toast.LENGTH_SHORT);
                        }
                    });
            requestQueue.add(jsonObjectRequest);

        }catch (Exception e){
            Log.d("DEBUG" , "Exception error " + e.getMessage());
        }



*/




        String url  = getResources().getString(R.string.urlWorld);
        
        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<JsonObjectRequest>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(WorldStatsActivity.this, jsonObject.toString(), Toast.LENGTH_LONG);
                    String success = jsonObject.getString("success");
                    if(success.equals("1")){
                        Toast.makeText(WorldStatsActivity.this, "Inserted Data", Toast.LENGTH_SHORT);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(WorldStatsActivity.this, "Error in inserting data", Toast.LENGTH_SHORT);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body = "no content";
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // exception
                }

                Toast.makeText(WorldStatsActivity.this, "Something went wrong to insert data!" + body, Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cases" , cases);
                params.put("date", date);
                return params;
            }
        };
        requestQueue.add(request);*/


    /*    // Log.i(TAG,"updateType");
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // running on main thread-------
                try {
                    JSONObject res = new JSONObject(response);
                    res.getString("result");
                    System.out.println("Response:" + res.getString("result"));

                }else{
                    CustomTast ct=new CustomTast(context);
                    ct.showCustomAlert("Network/Server Disconnected",R.drawable.disconnect);
                }

            } catch (Exception e) {
                e.printStackTrace();

                //Log.e("Response", "==> " + e.getMessage());
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            // running on main thread-------
            VolleyLog.d(TAG, "Error: " + volleyError.getMessage());

        }
    }) {
        protected Map<String, String> getParams() {
            HashMap<String, String> hashMapParams = new HashMap<String, String>();
            hashMapParams.put("cases", "10");
            hashMapParams.put("date", "1999-01-01");
            System.out.println("Hashmap:" + hashMapParams);
            return hashMapParams;
        }
    };
     AppController.getInstance().addToRequestQueue(request);*/

    }
}
