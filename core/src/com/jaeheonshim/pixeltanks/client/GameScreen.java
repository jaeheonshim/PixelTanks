package com.jaeheonshim.pixeltanks.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.pixeltanks.core.World;

public class GameScreen implements Screen {
    private World world;
    private WorldRenderer worldRenderer;

    private Viewport viewport;
    private SpriteBatch spriteBatch;

    public GameScreen() {
        viewport = new FitViewport(300, 200);
        spriteBatch = new SpriteBatch();

        world = new World();
        worldRenderer = new WorldRenderer(world);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        worldRenderer.render(spriteBatch);
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
}
