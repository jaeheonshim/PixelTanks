package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.math.Vector2;

public class Tank {
    private Vector2 position = new Vector2();
    private float rotation;
    private Vector2 velocity = new Vector2();

    public Vector2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
