package com.jaeheonshim.pixeltanks.core;

public class TankDetails {
    private int hp;
    private int availableHp;

    private int ammo;

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
}
