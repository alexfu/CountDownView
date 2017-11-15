package com.alexfu.countdownview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alexfu.countdownview.CountDownView;

public class MainActivity extends AppCompatActivity {
    private CountDownView countDownView;
    private Button startButton;
    private Button resetButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownView = findViewById(R.id.view_count_down);
        startButton = findViewById(R.id.button_start);
        resetButton = findViewById(R.id.button_reset);
        stopButton = findViewById(R.id.button_stop);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                countDownView.start();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                countDownView.reset();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                countDownView.stop();
            }
        });
    }
}
