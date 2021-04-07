package com.jaeheonshim.pixeltanks.client.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.jaeheonshim.pixeltanks.AssetHandler;

public class HealthBar extends Widget {
    private Texture background = AssetHandler.getInstance().get("textures/ui/HealthBar.png");
    private Texture remainingTexture;
    private Texture lostTexture;

    private float percent = 1f;
    private float maxWidth = 207;
    private final Pixmap lost;
    private final Pixmap remaining;

    public HealthBar() {
        remaining = new Pixmap((int) (this.percent * maxWidth), (int) (background.getHeight() * 1.3f), Pixmap.Format.RGBA8888);
        remaining.setColor(Color.GREEN);
        remaining.fill();

        remainingTexture = new Texture(remaining);

        remaining.dispose();

        lost = new Pixmap(10, (int) (background.getHeight() * 1.35f), Pixmap.Format.RGBA8888);
        lost.setColor(Color.RED);
        lost.fill();

        lostTexture = new Texture(lost);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(remainingTexture, getX() + 64, getY() + 30, (this.percent * maxWidth), background.getHeight() * 1.3f);
        batch.draw(lostTexture, getX() + 64 + (this.percent * maxWidth), getY() + 30, ((1 - this.percent) * maxWidth), background.getHeight() * 1.35f);
        batch.draw(background, getX(), getY(), getWidth(), getHeight());
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        if(percent >= 0 && percent <= 1)
            this.percent = percent;
    }

    @Override
    public float getPrefWidth() {
        return background.getWidth() * 5;
    }

    @Override
    public float getPrefHeight() {
        return background.getHeight() * 5;
    }
}
