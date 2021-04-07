package com.jaeheonshim.pixeltanks.server.dto;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class BulletSpawnPacket {
    private String uuid;
    private Vector2 position;
    private Vector2 velocity;
    private float rotation;

    public BulletSpawnPacket() {
    }

    public BulletSpawnPacket(String uuid, Vector2 position, Vector2 velocity, float rotation) {
        this.uuid = uuid;
        this.position = position;
        this.velocity = velocity;
        this.rotation = rotation;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public String getUuid() {
        return uuid;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
