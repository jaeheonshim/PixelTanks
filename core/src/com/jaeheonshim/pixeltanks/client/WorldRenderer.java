package com.jaeheonshim.pixeltanks.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.jaeheonshim.pixeltanks.AssetHandler;
import com.jaeheonshim.pixeltanks.core.Bullet;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.World;

public class WorldRenderer implements Disposable {
    private Texture tankTexture = new Texture(Gdx.files.internal("Tank.png"));
    private Texture bulletTexture = AssetHandler.getInstance().getAssetManager().get("Bullet.png");
    private BitmapFont size16Font = AssetHandler.getInstance().getAssetManager().get("size16.ttf");
    private Texture nametagBackground = AssetHandler.getInstance().getAssetManager().get("NametagBackground.png");

    private GlyphLayout glyphLayout = new GlyphLayout();

    public static final float TANK_SCALE = 4;
    public static final Vector2 NAMETAG_OFFSET = new Vector2(-20, 80);

    private GameScreen gameScreen;
    private World world;

    private ShapeRenderer shapeRenderer;

    private boolean debug = true;

    public WorldRenderer(World world, GameScreen gameScreen) {
        this.world = world;
        this.gameScreen = gameScreen;

        size16Font.setUseIntegerPositions(false);
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        drawTanks(spriteBatch);
        renderBullets(spriteBatch);

        spriteBatch.end();
    }

    private void drawTanks(SpriteBatch spriteBatch) {
        for (Tank tank : world.getTanks()) {
            if (tank.getTankDetails().isTakingDamage()) {
                spriteBatch.setColor(Color.valueOf("FF8484"));
            }

            spriteBatch.draw(
                    tankTexture,
                    tank.getPosition().x,
                    tank.getPosition().y,
                    tankTexture.getWidth() / 2f,
                    tankTexture.getHeight() / 2f,
                    tankTexture.getWidth(),
                    tankTexture.getHeight(),
                    TANK_SCALE, TANK_SCALE,
                    tank.getRotation() - 90,
                    0, 0,
                    tankTexture.getWidth(),
                    tankTexture.getHeight(),
                    false, false);

            spriteBatch.setColor(Color.WHITE);

            glyphLayout.setText(size16Font, tank.getName());
            spriteBatch.draw(nametagBackground, tank.getPosition().x + NAMETAG_OFFSET.x, ((tank.getPosition().y + NAMETAG_OFFSET.y) - glyphLayout.height / 2f) - (nametagBackground.getHeight() / 2f), glyphLayout.width + 20, nametagBackground.getHeight());
            size16Font.draw(spriteBatch, tank.getName(), tank.getPosition().x + NAMETAG_OFFSET.x + 10, tank.getPosition().y + NAMETAG_OFFSET.y);
        }

        spriteBatch.end();

        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        if (debug) {
            for (Tank tank : world.getTanks()) {
                shapeRenderer.polygon(tank.getCollider().getTransformedVertices());
            }
        }

        shapeRenderer.end();
        spriteBatch.begin();
    }

    private void renderBullets(SpriteBatch spriteBatch) {
        for (Bullet bullet : world.getBullets()) {
            spriteBatch.draw(bulletTexture, bullet.getPosition().x, bullet.getPosition().y);
        }

        spriteBatch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        if (debug) {
            for (Bullet bullet : world.getBullets()) {
                shapeRenderer.circle(bullet.getCollider().x, bullet.getCollider().y, bullet.getCollider().radius);
            }
        }

        shapeRenderer.end();

        spriteBatch.begin();
    }

    @Override
    public void dispose() {
        tankTexture.dispose();
    }
}
