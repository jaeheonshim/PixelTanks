package com.jaeheonshim.pixeltanks.server.dto;

import com.badlogic.gdx.math.Vector2;

public class TankInformationPacket {
    private String uuid;
    private Vector2 position;
    private float rotation;
    private float velocity;

    public TankInformationPacket() {
    }

    public TankInformationPacket(String uuid, Vector2 position, float rotation, float velocity) {
        this.uuid = uuid;
        this.position = position;
        this.rotation = rotation;
        this.velocity = velocity;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
