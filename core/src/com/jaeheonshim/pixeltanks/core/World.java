package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class World {
    private Map<UUID, Tank> tanks = new HashMap<>();
    private Map<UUID, Bullet> bullets = new HashMap<>();

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

        for(Bullet bullet : bullets.values()) {
            bullet.update(delta);
        }
    }

    public Bullet spawnBullet(Vector2 position, float rotation) {
        Bullet bullet = new Bullet(UUID.randomUUID(), position, rotation);
        bullets.put(bullet.getUuid(), bullet);

        return bullet;
    }

    public void addBullet(Bullet bullet) {
        bullets.put(bullet.getUuid(), bullet);
    }

    public Bullet getBullet(UUID uuid) {
        return bullets.get(uuid);
    }

    public List<Bullet> getBullets() {
        return new ArrayList<>(bullets.values());
    }
}
