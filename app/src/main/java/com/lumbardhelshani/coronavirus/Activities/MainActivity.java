package com.lumbardhelshani.coronavirus.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lumbardhelshani.coronavirus.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSeeWorldStats) void seeWorldStats(View view) {
        startActivity(new Intent(getApplicationContext(),WorldStatsActivity.class));
    }

}
