package com.rusic_game;

import com.badlogic.gdx.Game;


public class Rusic_Game extends Game {
	
	@Override
	public void create() {		
		setScreen(new GameScreen()); // When the game is run, creates new GameScreen and displays it 
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		
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
