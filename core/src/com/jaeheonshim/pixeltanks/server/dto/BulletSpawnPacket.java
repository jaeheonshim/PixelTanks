package com.jaeheonshim.pixeltanks.server.dto;

import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class BulletSpawnPacket {
    private String uuid;
    private Vector2 position;
    private float rotation;

    public BulletSpawnPacket() {
    }

    public BulletSpawnPacket(String uuid, Vector2 position, float rotation) {
        this.uuid = uuid;
        this.position = position;
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
}
