package com.jaeheonshim.pixeltanks.client.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationCreator {
    private Animation<TextureRegion> animation;
    private TextureRegion spriteSheet;

    public AnimationCreator(TextureRegion spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public Animation<TextureRegion> create(int rows, int cols, float frameDuration) {
        TextureRegion[][] split = spriteSheet.split(spriteSheet.getRegionWidth() / cols, spriteSheet.getRegionHeight() / rows);
        TextureRegion[] frames = new TextureRegion[rows * cols];

        int cnt = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                frames[cnt++] = split[i][j];
            }
        }

        return new Animation<TextureRegion>(frameDuration, frames);
    }
}
