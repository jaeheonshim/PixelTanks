package com.jaeheonshim.pixeltanks.server.dto;

import com.badlogic.gdx.math.Vector2;

public class BulletPositionPacket {
    private String uuid;
    private Vector2 position;

    public BulletPositionPacket() {
    }

    public BulletPositionPacket(String uuid, Vector2 position) {
        this.uuid = uuid;
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public String getUuid() {
        return uuid;
    }
}
