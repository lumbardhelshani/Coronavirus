package com.lumbardhelshani.coronavirus.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lumbardhelshani.coronavirus.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void seeWorldStats(View view) {

        startActivity(new Intent(getApplicationContext(),WorldStatsActivity.class));

    }

}
