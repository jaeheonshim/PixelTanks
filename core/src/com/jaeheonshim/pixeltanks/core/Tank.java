package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Tank {
    private Vector2 position = new Vector2();
    private float rotation = 90;
    private Vector2 velocity = new Vector2();

    public static final float ROTATION_SPEED = 120;
    public static final float MOVEMENT_SPEED = 50;

    public void update(float delta) {
        handleInput(delta);

        position.add(new Vector2(velocity).scl(delta));
    }

    private void handleInput(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            rotation += delta * ROTATION_SPEED;
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            rotation -= delta * ROTATION_SPEED;
        }

        rotation = rotation % 360;

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            setVelocity(MOVEMENT_SPEED);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            setVelocity(MOVEMENT_SPEED * -1);
        } else {
            setVelocity(0);
        }
    }

    private void setVelocity(float magnitude) {
        velocity.set(MathUtils.cos(MathUtils.degreesToRadians * rotation) * magnitude, MathUtils.sin(MathUtils.degreesToRadians * rotation) * magnitude);
    }

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
