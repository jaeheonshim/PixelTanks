package com.jaeheonshim.pixeltanks.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.client.listener.ConnectionResponseListener;
import com.jaeheonshim.pixeltanks.client.listener.TankInformationListener;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.TankDriveState;
import com.jaeheonshim.pixeltanks.core.World;
import com.jaeheonshim.pixeltanks.server.TankServer;
import com.jaeheonshim.pixeltanks.server.dto.ConnectionResponse;
import com.jaeheonshim.pixeltanks.server.dto.TankDrivePacket;
import com.jaeheonshim.pixeltanks.server.dto.TankInformationPacket;

import java.io.IOException;
import java.util.UUID;

public class GameScreen implements Screen {
    private World world;
    private WorldRenderer worldRenderer;

    private Viewport viewport;
    private SpriteBatch spriteBatch;

    private Client client = new Client();
    private Tank controllingTank;

    public GameScreen() {
        viewport = new FitViewport(300, 200);
        spriteBatch = new SpriteBatch();

        world = new World();
        worldRenderer = new WorldRenderer(world);

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

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        worldRenderer.render(spriteBatch);
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
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
