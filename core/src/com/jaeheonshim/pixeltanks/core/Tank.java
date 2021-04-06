package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class Tank {
    private UUID uuid;

    private Vector2 position = new Vector2();
    private Vector2 remotePosition = new Vector2();

    private float rotation = 90;
    private Vector2 velocity = new Vector2();

    private TankDriveState driveState = TankDriveState.STOP;

    public static final float ROTATION_SPEED = 120;
    public static final float MOVEMENT_SPEED = 50;
    public static final float DIFFERENCE_CORRECTION_THRESHOLD = 0;
    public static final float INTERPOLATION_CONSTANT = 25;

    public Tank(UUID uuid) {
        this.uuid = uuid;
    }

    public void update(float delta) {
        //handleInput(delta);

        //handlePosiitonInterpolation(delta);
        position.add(new Vector2(velocity).scl(delta));
    }

    public void handlePosiitonInterpolation(float delta) {
        float diffX = remotePosition.x - position.x;
        float diffY = remotePosition.y - position.y;

        if(Math.abs(diffX) <= DIFFERENCE_CORRECTION_THRESHOLD || Math.abs(diffY) <= DIFFERENCE_CORRECTION_THRESHOLD) {
            position.x = remotePosition.x;
            position.y = remotePosition.y;
        } else {
            position.x += diffX * delta * INTERPOLATION_CONSTANT;
            position.y += diffY * delta * INTERPOLATION_CONSTANT;
        }
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

    public void setVelocity(float magnitude) {
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

    public UUID getUuid() {
        return uuid;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public TankDriveState getDriveState() {
        return driveState;
    }

    public void setDriveState(TankDriveState driveState) {
        this.driveState = driveState;
    }

    public Vector2 getRemotePosition() {
        return remotePosition;
    }

    public void setRemotePosition(Vector2 remotePosition) {
        this.remotePosition = remotePosition;
    }
}
