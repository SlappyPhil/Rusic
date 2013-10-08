package com.rusic_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rusic_game.Rusic_Game;

public class SettingScreen implements Screen{

	private Rusic_Game game;
	private SpriteBatch spriteBatch;
	private Texture settingsScreenTexture;
	private int width, height;
	
	//CONSTRUCTOR
	public SettingScreen(Rusic_Game game, SpriteBatch spriteBatch) {
		this.game = game;
		this.spriteBatch = spriteBatch;
	}
	@Override
	public void render(float delta) {
		//CLEARS SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//CHANGES TO MAINSCREEN WHEN TOUCHED
		if(Gdx.input.justTouched()){
			game.setScreen(game.mainScreen);
		}	
		spriteBatch.begin();
		spriteBatch.draw(settingsScreenTexture, 0, 0, width, height);
		spriteBatch.end();
	}
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height= height;
		
		
	}
	@Override
	public void show() {
		settingsScreenTexture = new Texture(Gdx.files.internal("images/SettingsScreen.png"));
		
	}
	@Override
	public void hide() {
		
	}
	@Override
	public void pause() {
		
	}
	@Override
	public void resume() {
		
	}
	@Override
	public void dispose() {
		
	}
}
