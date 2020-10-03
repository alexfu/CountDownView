package com.alexfu.countdownview.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.alexfu.countdownview.CountDownView;
import com.alexfu.countdownview.CountDownListener;

public class MainActivity extends AppCompatActivity implements CountDownListener {
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
        countDownView.setListener(this);
    }

    @Override
    public void onFinishCountDown() {
        Toast.makeText(this, "Time up!!!", Toast.LENGTH_LONG).show();
    }
}
