package com.rusic_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rusic_game.screens.GameScreen;
import com.rusic_game.screens.MainScreen;
import com.rusic_game.screens.MusicSelectScreen;
import com.rusic_game.screens.SettingScreen;
import com.rusic_game.screens.SplashScreen;


public class Rusic_Game extends Game {

	private SpriteBatch spriteBatch;
	public MainScreen mainScreen;
	public GameScreen gameScreen;
	public SplashScreen splashScreen;
	public SettingScreen settingsScreen;
	public MusicSelectScreen musicSelectScreen;
	public String[] musicinfo;
	public String[] musicpath;
	public boolean isAndroid;

	@Override
	public void create() {
		
		Texture.setEnforcePotImages(false);
		spriteBatch = new SpriteBatch();
		splashScreen = new SplashScreen(this, spriteBatch);
		mainScreen = new MainScreen(this, spriteBatch);
		gameScreen = new GameScreen(this, spriteBatch);
		settingsScreen = new SettingScreen(this, spriteBatch);
		musicSelectScreen = new MusicSelectScreen(this, spriteBatch);
		setScreen(splashScreen);
	}
	
	public Rusic_Game(String[] musicpath, String[] musicinfo,boolean isAndroid){
		this.musicinfo=musicinfo;
		this.musicpath=musicpath;
		this.isAndroid=isAndroid;
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