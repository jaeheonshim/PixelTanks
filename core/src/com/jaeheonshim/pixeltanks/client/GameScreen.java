package com.jaeheonshim.pixeltanks.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.AssetHandler;
import com.jaeheonshim.pixeltanks.client.listener.ConnectionResponseListener;
import com.jaeheonshim.pixeltanks.client.listener.MinimapRenderer;
import com.jaeheonshim.pixeltanks.client.listener.TankConnectionListener;
import com.jaeheonshim.pixeltanks.client.listener.TankInformationListener;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.TankDriveState;
import com.jaeheonshim.pixeltanks.core.TankRotationState;
import com.jaeheonshim.pixeltanks.core.World;
import com.jaeheonshim.pixeltanks.server.TankServer;
import com.jaeheonshim.pixeltanks.server.dto.*;

import java.io.IOException;
import java.util.UUID;

public class GameScreen implements Screen {
    private World world;
    private WorldRenderer worldRenderer;
    private MinimapRenderer minimapRenderer;

    private Viewport viewport;
    private Viewport overlayViewport;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private Client client = new Client();
    private Tank controllingTank;

    private Texture backgroundTile = AssetHandler.getInstance().getAssetManager().get("BackgroundTile.png");

    public GameScreen() {
        viewport = new FitViewport(1920, 1080);
        overlayViewport = new ExtendViewport(1000, 1000);
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        world = new World();
        worldRenderer = new WorldRenderer(world);
        minimapRenderer = new MinimapRenderer(world);

        try {
            initNetworkClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initNetworkClient() throws IOException {
        TankServer.registerClasses(client.getKryo());

        client.start();
        client.connect(5000, "127.0.0.1", 4200, 4201);

        client.addListener(new ConnectionResponseListener(this));
        client.addListener(new TankInformationListener(this));
        client.addListener(new TankConnectionListener(this));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.clientCorrection(delta);
        world.update(delta);
        handleInput(delta);

        viewport.apply();

        viewport.getCamera().position.set(controllingTank.getPosition(), viewport.getCamera().position.z);
        viewport.getCamera().update();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        drawBackground(spriteBatch);

        worldRenderer.render(spriteBatch);

        spriteBatch.setProjectionMatrix(overlayViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(overlayViewport.getCamera().combined);
        overlayViewport.apply();
        minimapRenderer.draw(spriteBatch, shapeRenderer, overlayViewport.getWorldWidth() - minimapRenderer.getMinimapTexture().getWidth(), 0);
    }

    private void drawBackground(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for(int i = 0; i < World.WIDTH / backgroundTile.getWidth(); i++) {
            for(int j = 0; j < World.HEIGHT / backgroundTile.getHeight(); j++) {
                spriteBatch.draw(backgroundTile, i * backgroundTile.getWidth(), j * backgroundTile.getHeight());
            }
        }

        spriteBatch.end();
    }

    private void handleInput(float delta) {
        if(controllingTank == null) {
            return;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && controllingTank.getDriveState() != TankDriveState.FORWARD) {
            client.sendUDP(new TankDrivePacket(TankDriveState.FORWARD));
            controllingTank.setDriveState(TankDriveState.FORWARD);
            controllingTank.setVelocity(Tank.MOVEMENT_SPEED);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && controllingTank.getDriveState() != TankDriveState.REVERSE) {
            client.sendUDP(new TankDrivePacket(TankDriveState.REVERSE));
            controllingTank.setDriveState(TankDriveState.REVERSE);
            controllingTank.setVelocity(Tank.MOVEMENT_SPEED * -1);
        } else if(!(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) && controllingTank.getDriveState() != TankDriveState.STOP) {
            client.sendUDP(new TankDrivePacket(TankDriveState.STOP));
            controllingTank.setDriveState(TankDriveState.STOP);
            controllingTank.setVelocity(0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && controllingTank.getRotationState() != TankRotationState.CCW) {
            client.sendUDP(new TankRotationPacket(TankRotationState.CCW));
            controllingTank.setRotationState(TankRotationState.CCW);
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && controllingTank.getRotationState() != TankRotationState.CW) {
            client.sendUDP(new TankRotationPacket(TankRotationState.CW));
            controllingTank.setRotationState(TankRotationState.CW);
        } else if(!(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) && controllingTank.getRotationState() != TankRotationState.NONE) {
            client.sendUDP(new TankRotationPacket(TankRotationState.NONE));
            controllingTank.setRotationState(TankRotationState.NONE);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        overlayViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        worldRenderer.dispose();
    }

    public World getWorld() {
        return world;
    }

    public Tank getControllingTank() {
        return controllingTank;
    }

    public void setControllingTank(Tank controllingTank) {
        this.controllingTank = controllingTank;
    }
}
