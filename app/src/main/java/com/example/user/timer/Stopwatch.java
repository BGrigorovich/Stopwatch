package com.example.user.timer;

public class Stopwatch {
    long startTime;
    long elapsedTime;
    boolean isRunning;

    public void start() {
        this.isRunning = true;
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.isRunning = false;
        this.elapsedTime = getTime();
    }

    public void reset() {
        this.isRunning = false;
        this.elapsedTime = 0;
    }

    public long getTime() {
        return System.currentTimeMillis() - startTime + elapsedTime;
    }

    public String toString() {
        long time = getTime();
        long miliseconds = time % 1000 / 100;
        String milisecondsStr = String.valueOf(miliseconds);
        long seconds = time / 1000 % 60;
        String secondsStr = seconds < 10 ? "0" + String.valueOf(seconds) : String.valueOf(seconds);
        long minutes = time / 60000 % 60;
        String minutesStr = minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes);

        if (time > 3600000) {
            String hoursStr = String.valueOf(time / 3600000 % 60);
            return hoursStr+ ":" + minutesStr + ":" + secondsStr + ":" + milisecondsStr;
        }
        return minutesStr + ":" + secondsStr + ":" + milisecondsStr;
    }
}
