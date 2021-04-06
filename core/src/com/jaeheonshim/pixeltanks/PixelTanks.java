package com.jaeheonshim.pixeltanks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jaeheonshim.pixeltanks.client.GameScreen;

public class PixelTanks extends Game {
	private GameScreen gameScreen;
	
	@Override
	public void create () {
		gameScreen = new GameScreen();

		setScreen(gameScreen);
	}
	
	@Override
	public void dispose () {

	}
}
