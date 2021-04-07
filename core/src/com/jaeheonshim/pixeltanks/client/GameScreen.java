package com.jaeheonshim.pixeltanks.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.jaeheonshim.pixeltanks.AssetHandler;
import com.jaeheonshim.pixeltanks.client.listener.*;
import com.jaeheonshim.pixeltanks.client.ui.GameUiRenderer;
import com.jaeheonshim.pixeltanks.core.*;
import com.jaeheonshim.pixeltanks.server.TankServer;
import com.jaeheonshim.pixeltanks.server.dto.*;

import java.io.IOException;

public class GameScreen implements Screen {
    private World world;
    private WorldRenderer worldRenderer;
    private GameUiRenderer gameUiRenderer;

    private Viewport viewport;
    private Viewport overlayViewport;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private Client client = new Client();
    private Tank controllingTank;
    private ClientState clientState = new ClientState();

    private Texture backgroundTile = AssetHandler.getInstance().getAssetManager().get("BackgroundTile.png");

    public GameScreen() {
        viewport = new FitViewport(1920, 1080);
        overlayViewport = new ExtendViewport(1000, 1000);
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        world = new World();
        worldRenderer = new WorldRenderer(world);
        gameUiRenderer = new GameUiRenderer(overlayViewport, clientState, world);

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
        client.addListener(new BulletListener(this));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        clientState.update(delta);

        world.clientCorrection(delta);
        world.update(delta);
        handleInput(delta);
        gameUiRenderer.update(delta);

        viewport.apply();

        if(controllingTank != null) {
            viewport.getCamera().position.set(controllingTank.getPosition(), viewport.getCamera().position.z);
            viewport.getCamera().update();
        }

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        drawBackground(spriteBatch);

        worldRenderer.render(spriteBatch);

        spriteBatch.setProjectionMatrix(overlayViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(overlayViewport.getCamera().combined);
        overlayViewport.apply();
        gameUiRenderer.getStage().draw();
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

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && clientState.isFullAuto() && clientState.getAutoTimer().isFinished()) {
            fireBullet();
            clientState.getAutoTimer().reset();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !clientState.isFullAuto()) {
            fireBullet();
        }
    }

    private void fireBullet() {
        Bullet bullet = world.spawnBullet(controllingTank.getPosition(), controllingTank.getRotation());
        bullet.setVelocity(new Vector2(bullet.getVelocity()).add(controllingTank.getVelocity() * MathUtils.cosDeg(controllingTank.getRotation()), controllingTank.getVelocity() * MathUtils.sinDeg(controllingTank.getRotation())));
        client.sendUDP(new BulletSpawnPacket(bullet.getUuid().toString(), controllingTank.getPosition(), bullet.getVelocity(), controllingTank.getRotation()));
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
