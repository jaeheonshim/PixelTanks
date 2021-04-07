package com.jaeheonshim.pixeltanks.core;

import com.jaeheonshim.pixeltanks.core.util.Timer;

public class ClientState {
    private boolean fullAuto;
    private Timer autoTimer = new Timer(AUTO_FIRE_TIMER);

    public static final float AUTO_FIRE_TIMER = 0.1f;

    public void update(float delta) {
        autoTimer.update(delta);
    }

    public boolean isFullAuto() {
        return fullAuto;
    }

    public void setFullAuto(boolean fullAuto) {
        this.fullAuto = fullAuto;
    }

    public Timer getAutoTimer() {
        return autoTimer;
    }
}
