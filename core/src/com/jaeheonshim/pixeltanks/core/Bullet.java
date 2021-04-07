package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class Bullet {
    private UUID uuid;
    private Vector2 position = new Vector2();
    private float rotation;

    public Bullet(UUID uuid, Vector2 position, float rotation) {
        this.uuid = uuid;
        this.position = new Vector2(position);
        this.rotation = rotation;
    }

    public static final float VELOCITY = 400;

    public void update(float delta) {
        position.add(VELOCITY * MathUtils.cosDeg(rotation) * delta, VELOCITY * MathUtils.sinDeg(rotation) * delta);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public UUID getUuid() {
        return uuid;
    }
}
