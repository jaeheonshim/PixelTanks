package com.jaeheonshim.pixeltanks.client.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jaeheonshim.pixeltanks.AssetHandler;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.World;

public class Minimap extends Widget {
    private World world;
    private Drawable background;
    private Texture minimapTexture = AssetHandler.getInstance().getAssetManager().get("Minimap.png");
    private final float border = 4;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Minimap(World world) {
        this.world = world;

        background = new TextureRegionDrawable(minimapTexture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        getStage().getViewport().apply();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        float x = getX();
        float y = getY();

        float scaleX = getScaleX();
        float scaleY = getScaleY();

        batch.draw(minimapTexture, x, y, minimapTexture.getWidth(), minimapTexture.getHeight());

        batch.end();

        shapeRenderer.setProjectionMatrix(getStage().getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(Tank tank : world.getTanks()) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(x + border + (tank.getPosition().x / World.WIDTH) * (getWidth() - border * 2),
                    y + border + (tank.getPosition().y / World.HEIGHT) * (getHeight() - border * 2),
                    8, 8);
        }
        shapeRenderer.end();

        batch.begin();
    }

    @Override
    public float getPrefHeight() {
        return minimapTexture.getHeight();
    }

    @Override
    public float getPrefWidth() {
        return minimapTexture.getWidth();
    }
}
