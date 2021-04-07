package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class Bullet {
    private UUID uuid;
    private Vector2 position = new Vector2();
    private Vector2 velocity;
    private float rotation;

    public Bullet(UUID uuid, Vector2 position, float rotation) {
        this.uuid = uuid;
        this.position = new Vector2(position);
        this.rotation = rotation;

        this.velocity = new Vector2(VELOCITY * MathUtils.cosDeg(rotation), VELOCITY * MathUtils.sinDeg(rotation));
    }

    public static final float VELOCITY = 400;

    public void update(float delta) {
        this.position = position.add(new Vector2(velocity).scl(delta));
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

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}
