package com.rusic_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rusic_game.screens.GameScreen;
import com.rusic_game.screens.MainScreen;

public class Rusic_Game extends Game {
	
	private SpriteBatch spriteBatch;
	public MainScreen mainScreen;
	public GameScreen gameScreen;
	
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		mainScreen = new MainScreen(this, spriteBatch);
		gameScreen = new GameScreen(this, spriteBatch);
		setScreen(mainScreen); // When the game is run, creates new MainScreen and displays it 
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
