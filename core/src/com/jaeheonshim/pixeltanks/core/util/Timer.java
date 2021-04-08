package com.jaeheonshim.pixeltanks.core.util;

public class Timer {
    private float timer;
    private float value;

    public Timer() {
    }

    public Timer(float value) {
        this.value = value;
        this.timer = value;
    }

    public boolean isFinished() {
        return timer <= 0;
    }

    public void update(float delta) {
        timer -= delta;
    }

    public void reset() {
        this.timer = this.value;
    }
}
