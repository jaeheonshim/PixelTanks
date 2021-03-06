package com.jaeheonshim.pixeltanks.client.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.pixeltanks.AssetHandler;
import com.jaeheonshim.pixeltanks.client.GameScreen;
import com.jaeheonshim.pixeltanks.client.ui.widgets.HealthBar;
import com.jaeheonshim.pixeltanks.client.ui.widgets.Minimap;
import com.jaeheonshim.pixeltanks.core.ClientState;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.World;

public class GameUiRenderer {
    private Stage stage;
    private Table table;
    private VerticalGroup statsGroup;

    private ClientState clientState;
    private World world;
    private GameScreen gameScreen;

    private TextureRegionDrawable fullAuto = new TextureRegionDrawable((Texture) AssetHandler.getInstance().get("textures/ui/FullAuto.png"));
    private TextureRegionDrawable singleFire = new TextureRegionDrawable((Texture) AssetHandler.getInstance().get("textures/ui/SingleFire.png"));
    private NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(new NinePatch(((Texture) AssetHandler.getInstance().get("textures/ui/AmmoCounter.png")), 78, 14, 6, 6));

    private TextureRegionDrawable progressBarBackground = new TextureRegionDrawable((Texture) AssetHandler.getInstance().get("textures/ui/HealthBar.png"));

    private BitmapFont font = AssetHandler.getInstance().getAssetManager().get("size38_pixel.ttf");

    private ImageButton fireMode;
    private Minimap minimap;
    private HealthBar healthBar;

    private Label.LabelStyle ammoCounterLabelStyle = new Label.LabelStyle(font, Color.BLACK);
    private Label ammoCounter;

    public GameUiRenderer(Viewport viewport, ClientState clientState, GameScreen gameScreen) {
        this.clientState = clientState;
        this.world = gameScreen.getWorld();
        this.gameScreen = gameScreen;

        stage = new Stage(viewport);
        table = new Table();
        statsGroup = new VerticalGroup();

        table.setFillParent(true);

        Gdx.input.setInputProcessor(stage);

        setupElements();
    }

    private void setupElements() {
        TextureRegionDrawable drawable = singleFire;
        TextureRegionDrawable checkedDrawable = fullAuto;
        fireMode = new ImageButton(drawable, drawable, checkedDrawable);

        fireMode.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clientState.setFullAuto(!clientState.isFullAuto());
            }
        });

        ammoCounterLabelStyle.background = ninePatchDrawable;
        ammoCounter = new Label("0", ammoCounterLabelStyle);

        table.top().left();
        table.pad(16);

        healthBar = new HealthBar();
        statsGroup.left().top();
        statsGroup.columnAlign(Align.left);
        statsGroup.addActor(healthBar);
        statsGroup.addActor(ammoCounter);

        table.add(statsGroup).expandX().left().top();

        table.right();
        minimap = new Minimap(world);
        table.add(minimap);

        table.row().left().bottom();
        table.add(fireMode).expandY();

        stage.addActor(table);
    }

    public void update(float delta) {
        Tank controllingTank = this.gameScreen.getControllingTank();

        stage.act(delta);
        fireMode.setChecked(clientState.isFullAuto());
        if(controllingTank != null) {
            int ammo = controllingTank.getTankDetails().getAmmo();

            healthBar.setPercent(controllingTank.getTankDetails().getHp() / (float) controllingTank.getTankDetails().getAvailableHp());
            ammoCounter.setText(Integer.toString(ammo));
            if(ammo == 0) {
                ammoCounterLabelStyle.fontColor = Color.RED;
            } else {
                ammoCounterLabelStyle.fontColor = Color.BLACK;
            }
        }
    }

    public Stage getStage() {
        return stage;
    }
}
