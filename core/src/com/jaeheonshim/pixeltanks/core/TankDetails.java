package com.jaeheonshim.pixeltanks.core;

public class TankDetails {
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
}
