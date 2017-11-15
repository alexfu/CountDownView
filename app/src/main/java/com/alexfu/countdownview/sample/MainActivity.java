package com.alexfu.countdownview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alexfu.countdownview.CountDownView;

public class MainActivity extends AppCompatActivity {
    private CountDownView countDownView;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownView = findViewById(R.id.view_count_down);
        startButton = findViewById(R.id.button_start);
        stopButton = findViewById(R.id.button_stop);

        if (savedInstanceState == null) {
            countDownView.setStartDuration(1000 * 1000);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                countDownView.start();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                countDownView.stop();
            }
        });
    }
}
