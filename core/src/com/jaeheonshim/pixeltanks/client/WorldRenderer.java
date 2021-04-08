package com.jaeheonshim.pixeltanks.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.jaeheonshim.pixeltanks.AssetHandler;
import com.jaeheonshim.pixeltanks.client.util.AnimationCreator;
import com.jaeheonshim.pixeltanks.core.Bullet;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.World;

import java.util.UUID;

public class WorldRenderer implements Disposable {
    private TextureRegion tankTexture = AssetHandler.getInstance().getAtlasTexture("Tank");
    private TextureRegion bulletTexture = AssetHandler.getInstance().getAtlasTexture("Bullet");
    private BitmapFont size16Font = AssetHandler.getInstance().getAssetManager().get("size16.ttf");
    private TextureRegion nametagBackground = AssetHandler.getInstance().getAtlasTexture("NametagBackground");

    private Animation<TextureRegion> bulletDespawnAnimation;

    private GlyphLayout glyphLayout = new GlyphLayout();

    public static final float TANK_SCALE = 4;
    public static final Vector2 NAMETAG_OFFSET = new Vector2(-20, 80);

    private GameScreen gameScreen;
    private World world;

    private ShapeRenderer shapeRenderer;

    private boolean debug = false;

    public WorldRenderer(World world, GameScreen gameScreen) {
        this.world = world;
        this.gameScreen = gameScreen;

        AnimationCreator creator = new AnimationCreator(AssetHandler.getInstance().getAtlasTexture("BulletDeath"));
        bulletDespawnAnimation = creator.create(1, 5, 0.02f);

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
                    tankTexture.getRegionWidth() / 2f,
                    tankTexture.getRegionHeight() / 2f,
                    tankTexture.getRegionWidth(),
                    tankTexture.getRegionHeight(),
                    TANK_SCALE, TANK_SCALE,
                    tank.getRotation() - 90);

            spriteBatch.setColor(Color.WHITE);

            glyphLayout.setText(size16Font, tank.getName());
            spriteBatch.draw(nametagBackground, tank.getPosition().x + NAMETAG_OFFSET.x, ((tank.getPosition().y + NAMETAG_OFFSET.y) - glyphLayout.height / 2f) - (nametagBackground.getRegionHeight() / 2f), glyphLayout.width + 20, nametagBackground.getRegionHeight());
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
            if(bullet.isDying()) {
                TextureRegion frame = bulletDespawnAnimation.getKeyFrame(bullet.getStateTime());
                spriteBatch.draw(frame, bullet.getPosition().x, bullet.getPosition().y);

                if(bulletDespawnAnimation.isAnimationFinished(bullet.getStateTime())) {
                    bullet.setDead(true);
                }
            } else {
                spriteBatch.draw(bulletTexture, bullet.getPosition().x, bullet.getPosition().y);
            }
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

    }
}
