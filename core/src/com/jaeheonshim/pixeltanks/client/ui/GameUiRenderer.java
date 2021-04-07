package com.jaeheonshim.pixeltanks.client.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.pixeltanks.AssetHandler;
import com.jaeheonshim.pixeltanks.client.ui.widgets.Minimap;
import com.jaeheonshim.pixeltanks.core.ClientState;
import com.jaeheonshim.pixeltanks.core.World;

public class GameUiRenderer {
    private Stage stage;
    private Table table;

    private ClientState clientState;
    private World world;

    private TextureRegionDrawable fullAuto = new TextureRegionDrawable((Texture) AssetHandler.getInstance().get("textures/ui/FullAuto.png"));
    private TextureRegionDrawable singleFire = new TextureRegionDrawable((Texture) AssetHandler.getInstance().get("textures/ui/SingleFire.png"));

    private ImageButton fireMode;
    private Minimap minimap;

    public GameUiRenderer(Viewport viewport, ClientState clientState, World world) {
        this.clientState = clientState;
        this.world = world;

        stage = new Stage(viewport);
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

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

        table.top().left();
        table.add(fireMode).expandX().left().top();

        table.right();
        minimap = new Minimap(world);
        table.add(minimap);

        stage.addActor(table);
    }

    public void update(float delta) {
        stage.act(delta);
        fireMode.setChecked(clientState.isFullAuto());
    }

    public Stage getStage() {
        return stage;
    }
}
