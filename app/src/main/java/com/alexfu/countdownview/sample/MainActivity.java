package com.alexfu.countdownview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alexfu.countdownview.CountDownView;

public class MainActivity extends AppCompatActivity {
    private CountDownView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv = findViewById(R.id.view_count_down);
        cv.start();
    }
}
