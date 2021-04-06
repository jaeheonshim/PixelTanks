package com.jaeheonshim.pixeltanks.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.World;

public class WorldRenderer implements Disposable {
    private Texture tankTexture = new Texture(Gdx.files.internal("Tank.png"));

    private World world;

    public WorldRenderer(World world) {
        this.world = world;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        drawTanks(spriteBatch);

        spriteBatch.end();
    }

    private void drawTanks(SpriteBatch spriteBatch) {
        for(Tank tank : world.getTanks()) {
            spriteBatch.draw(tankTexture, tank.getPosition().x, tank.getPosition().y);
        }
    }

    @Override
    public void dispose() {
        tankTexture.dispose();
    }
}
