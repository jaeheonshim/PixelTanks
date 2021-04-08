package com.jaeheonshim.pixeltanks.server.dto;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class BulletSpawnPacket {
    private String uuid;
    private String firedBy;
    private Vector2 position;
    private Vector2 velocity;
    private float rotation;
    private boolean despawn;

    public BulletSpawnPacket() {
    }

    public BulletSpawnPacket(String uuid, boolean despawn) {
        this.uuid = uuid;
        this.despawn = despawn;
    }

    public BulletSpawnPacket(String uuid, String firedBy, Vector2 position, Vector2 velocity, float rotation) {
        this.uuid = uuid;
        this.firedBy = firedBy;
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

    public String getFiredBy() {
        return firedBy;
    }

    public boolean isDespawn() {
        return despawn;
    }

    public void setDespawn(boolean despawn) {
        this.despawn = despawn;
    }
}
