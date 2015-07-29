package com.example.user.timer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;

public class StopwatchFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    TextView timeTextView;
    Button startButton;
    Button resetButton;

    Handler handler;
    Stopwatch stopwatch;

    private int deltaX;
    private int deltaY;

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
        timeTextView.setOnTouchListener(this);

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
                timeTextView.setText(R.string.time_zero);
                startButton.setText(R.string.start);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                deltaX = X - lParams.leftMargin;
                deltaY = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                layoutParams.leftMargin = X - deltaX;
                layoutParams.topMargin = Y - deltaY;
                v.setLayoutParams(layoutParams);
                break;
        }
        return true;
    }
}
