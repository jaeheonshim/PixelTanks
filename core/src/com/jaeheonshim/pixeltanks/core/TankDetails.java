package com.jaeheonshim.pixeltanks.core;

import com.jaeheonshim.pixeltanks.core.util.Timer;

public class TankDetails {
    private int hp;
    private int availableHp;

    private int ammo;

    private Timer damageTintTimer = new Timer(0.5f);
    private boolean takingDamage;

    public TankDetails() {
    }

    public TankDetails(int ammo) {
        this.ammo = ammo;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAvailableHp() {
        return availableHp;
    }

    public void setAvailableHp(int availableHp) {
        this.availableHp = availableHp;
    }

    public void setTakingDamage(boolean takingDamage) {
        this.takingDamage = takingDamage;
    }

    public boolean isTakingDamage() {
        return takingDamage;
    }

    public Timer getDamageTintTimer() {
        return damageTintTimer;
    }
}
