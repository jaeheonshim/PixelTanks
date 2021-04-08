package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.math.Vector2;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class World {
    private Map<UUID, Tank> tanks = new HashMap<>();
    private Map<UUID, Bullet> bullets = new HashMap<>();

    public static final float HEIGHT = 2700;
    public static final float WIDTH = 4800;

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

        List<UUID> deadBullets = new ArrayList<>();
        for(Bullet bullet : bullets.values()) {
            bullet.update(delta);
            if(bullet.isDead()) {
                deadBullets.add(bullet.getUuid());
            }
        }

        for(UUID uuid : deadBullets) {
            removeBullet(uuid);
        }
    }

    public Bullet spawnBullet(Vector2 position, float rotation, Tank controllingTank) {
        Bullet bullet = new Bullet(UUID.randomUUID(), controllingTank.getUuid(), position, rotation);
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

    public void removeBullet(UUID uuid) {
        bullets.remove(uuid);
    }

    public List<UUID> checkCollisions() {
        List<UUID> despawnedProjectiles = new ArrayList<>();

        for(Tank tank : tanks.values()) {
            List<UUID> deadBullets = new ArrayList<>();

            for(Bullet bullet : bullets.values()) {
                if(CollisionUtil.polyCircle(tank.getCollider().getTransformedVertices(), bullet.getCollider().x, bullet.getCollider().y, bullet.getCollider().radius)) {
                    if(!tank.getUuid().equals(bullet.getFiredBy())) {
                        tank.onProjectileCollison(bullet.getUuid(), bullet.getFiredBy());
                        deadBullets.add(bullet.getUuid());
                    }
                }
            }

            for(UUID uuid : deadBullets) {
                bullets.remove(uuid);
            }

            despawnedProjectiles.addAll(deadBullets);
        }

        return despawnedProjectiles;
    }
}
