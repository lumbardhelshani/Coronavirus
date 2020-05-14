package com.lumbardhelshani.coronavirus.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lumbardhelshani.coronavirus.Listeners.OnSwipeTouchListener;
import com.lumbardhelshani.coronavirus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    // Bind Views with ButterKnife
    @BindView(R.id.launchLayout) RelativeLayout launchLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeListener();
    }
    //Here is handled the swipe left listener
    @SuppressLint("ClickableViewAccessibility")
    private void setSwipeListener() {
        launchLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeLeft() {
                startActivity(new Intent(getApplicationContext(), WorldStatsActivity.class));
                Toast.makeText(MainActivity.this, "WORLD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //This method is fired when we click the image button
    @OnClick(R.id.btnSeeWorldStats) void seeWorldStats(View view) {
        startActivity(new Intent(getApplicationContext(),WorldStatsActivity.class));
    }

}
