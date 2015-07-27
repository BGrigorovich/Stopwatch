package com.example.user.timer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

public class StopwatchFragment extends Fragment implements View.OnClickListener {
    boolean timerRunning = false;

    TextView timeTextView;
    Button startButton;
    Button resetButton;

    Handler handler;
    Stopwatch stopwatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timer, null);
    }

    @Override
    public void onStart() {
        super.onStart();

        startButton = (Button) getView().findViewById(R.id.startButton);
        resetButton = (Button) getView().findViewById(R.id.resetButton);
        timeTextView = (TextView) getView().findViewById(R.id.timeTextView);
        startButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        stopwatch = new Stopwatch();
        handler = new Handler();
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
                handler.post(runStopwatch);
        }
    });

    Runnable runStopwatch = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runStopwatch, 10);
            timeTextView.setText(stopwatch.toString());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                if (stopwatch.isRunning) {
                    stopwatch.stop();
                    handler.removeCallbacks(runStopwatch);
                    startButton.setText(R.string.start);
                } else {
                    stopwatch.start();
                    handler.post(thread);
                    startButton.setText(R.string.stop);
                }
                break;
            case R.id.resetButton:
                stopwatch.reset();
                handler.removeCallbacks(runStopwatch);
                timeTextView.setText(R.string.timeZero);
                startButton.setText(R.string.start);
                break;
        }
    }
}
