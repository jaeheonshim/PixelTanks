package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class Tank {
    private UUID uuid;
    private String name = "Tank";

    private Vector2 position = new Vector2();
    private Vector2 remotePosition = new Vector2();

    private float remoteRotation;
    private float rotation = 0;
    private float velocity;

    private TankDriveState driveState = TankDriveState.STOP;
    private TankRotationState rotationState = TankRotationState.NONE;
    private TankDetails tankDetails = new TankDetails();

    private Polygon collider = new Polygon(new float[] {-20, -35, -8, -47, 39, -47, 51, -35, 51, 55, 43, 64, 25, 64, 25, 80, 8, 80, 8, 64, -12, 64, -20, 52});

    public static final float ROTATION_SPEED = 120;
    public static final float MOVEMENT_SPEED = 280;
    public static final float DIFFERENCE_CORRECTION_THRESHOLD = 0;
    public static final float INTERPOLATION_CONSTANT = 10;

    public Tank(UUID uuid) {
        this.uuid = uuid;
    }

    public Tank(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public void update(float delta) {
        //handleInput(delta);

        //handlePosiitonInterpolation(delta);
        Vector2 previousPosition = new Vector2(position);
        position.add(new Vector2(MathUtils.cos(MathUtils.degreesToRadians * rotation) * velocity, MathUtils.sin(MathUtils.degreesToRadians * rotation) * velocity).scl(delta));
        collider.setPosition(position.x, position.y);
        collider.setOrigin(32 / 2f , 32 / 2f);
        collider.setRotation(rotation - 90);

        if(position.x > World.WIDTH || position.y > World.HEIGHT || position.x < 0 || position.y < 0) {
            position = previousPosition;
        }

        if(rotationState == TankRotationState.CCW) {
            rotation += delta * ROTATION_SPEED;
        } else if(rotationState == TankRotationState.CW) {
            rotation -= delta * ROTATION_SPEED;
        }
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

        float diffTheta = remoteRotation - rotation;
        rotation += diffTheta * delta * INTERPOLATION_CONSTANT;
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

    public void onProjectileCollison(UUID projectile, UUID firedBy) {
        if(!firedBy.equals(this.uuid)) {
            getTankDetails().setHp(getTankDetails().getHp() - 10);
        }
    }

    public void setVelocity(float magnitude) {
        this.velocity = magnitude;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public float getVelocity() {
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

    public TankDriveState getDriveState() {
        return driveState;
    }

    public void setDriveState(TankDriveState driveState) {
        this.driveState = driveState;
    }

    public TankRotationState getRotationState() {
        return rotationState;
    }

    public void setRotationState(TankRotationState rotationState) {
        this.rotationState = rotationState;
    }

    public Vector2 getRemotePosition() {
        return remotePosition;
    }

    public void setRemotePosition(Vector2 remotePosition) {
        this.remotePosition = remotePosition;
    }

    public float getRemoteRotation() {
        return remoteRotation;
    }

    public void setRemoteRotation(float remoteRotation) {
        this.remoteRotation = remoteRotation;
    }

    public String getName() {
        return name;
    }

    public TankDetails getTankDetails() {
        return tankDetails;
    }

    public void setTankDetails(TankDetails tankDetails) {
        this.tankDetails = tankDetails;
    }

    public Polygon getCollider() {
        return collider;
    }
}
