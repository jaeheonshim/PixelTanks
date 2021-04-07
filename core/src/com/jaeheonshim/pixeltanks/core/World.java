package com.jaeheonshim.pixeltanks.core;

import java.util.*;

public class World {
    private Map<UUID, Tank> tanks = new HashMap<>();

    public static final float HEIGHT = 5400;
    public static final float WIDTH = 9600;

    public void removeTank(UUID uuid) {
        tanks.remove(uuid);
    }

    public void addTank(Tank tank) {
        tanks.put(tank.getUuid(), tank);
    }

    public Tank getTank(UUID uuid) {
        return tanks.get(uuid);
    }

    public List<Tank> getTanks() {
        return new ArrayList<>(tanks.values());
    }

    public void clientCorrection(float delta) {
        for (Tank tank : tanks.values()) {
            tank.handlePosiitonInterpolation(delta);
        }
    }

    public void update(float delta) {
        for (Tank tank : tanks.values()) {
            tank.update(delta);
        }
    }
}
