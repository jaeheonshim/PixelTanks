package com.jaeheonshim.pixeltanks.client.listener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jaeheonshim.pixeltanks.AssetHandler;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.World;

public class MinimapRenderer {
    private World world;
    private Texture minimapTexture = AssetHandler.getInstance().getAssetManager().get("Minimap.png");
    private final float border = 4;

    public MinimapRenderer(World world) {
        this.world = world;
    }

    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, float x, float y) {
        spriteBatch.begin();

        spriteBatch.draw(minimapTexture, x, y);

        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(Tank tank : world.getTanks()) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(x + border + (tank.getPosition().x / World.WIDTH) * (minimapTexture.getWidth() - border * 2),
                    y + border + (tank.getPosition().y / World.HEIGHT) * (minimapTexture.getHeight() - border * 2),
                    8, 8);
        }
        shapeRenderer.end();
    }

    public Texture getMinimapTexture() {
        return minimapTexture;
    }
}
