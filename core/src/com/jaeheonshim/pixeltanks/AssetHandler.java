package com.jaeheonshim.pixeltanks;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class AssetHandler {
    private static AssetHandler instance = new AssetHandler();
    private AssetManager assetManager = new AssetManager();

    public void loadAssets() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter size1Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size1Params.fontFileName = "fonts/Montserrat-SemiBold.ttf";
        size1Params.fontParameters.size = 16;
        size1Params.fontParameters.minFilter = Texture.TextureFilter.Nearest;
        size1Params.fontParameters.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        size1Params.fontParameters.color = Color.WHITE;

        FreetypeFontLoader.FreeTypeFontLoaderParameter size2Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size2Params.fontFileName = "fonts/orange_kid.ttf";
        size2Params.fontParameters.size = 38;
        size2Params.fontParameters.minFilter = Texture.TextureFilter.Nearest;
        size2Params.fontParameters.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        size2Params.fontParameters.color = Color.WHITE;

        assetManager.load("size16.ttf", BitmapFont.class, size1Params);
        assetManager.load("size38_pixel.ttf", BitmapFont.class, size2Params);
        assetManager.load("NametagBackground.png", Texture.class);
        assetManager.load("BackgroundTile.png", Texture.class);
        assetManager.load("Minimap.png", Texture.class);
        assetManager.load("Bullet.png", Texture.class);

        assetManager.load("textures/ui/FullAuto.png", Texture.class);
        assetManager.load("textures/ui/SingleFire.png", Texture.class);
        assetManager.load("textures/ui/AmmoCounter.png", Texture.class);
        assetManager.load("textures/ui/HealthBar.png", Texture.class);
    }

    public <T> T get(String s) {
        return assetManager.get(s);
    }

    public boolean update() {
        return assetManager.update();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public static AssetHandler getInstance() {
        return instance;
    }
}
