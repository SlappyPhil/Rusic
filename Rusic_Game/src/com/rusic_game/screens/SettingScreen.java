package com.rusic_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rusic_game.Rusic_Game;

public class SettingScreen implements Screen{

	private Rusic_Game game;
	private SpriteBatch spriteBatch;
	private Texture settingsScreenTexture;
	private int width, height;
	private BitmapFont white;
	
	//CONSTRUCTOR
	public SettingScreen(Rusic_Game game, SpriteBatch spriteBatch) {
		this.game = game;
		this.spriteBatch = spriteBatch;
		this.white = new BitmapFont(Gdx.files.internal("font/yellowtailWhite.fnt"), false);
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
		white.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		int runner = height-15;
		int count = 0;
		for(String s : ScoreScreen.songs){
			white.draw(spriteBatch, (ScoreScreen.songs.get(count) + " - " + ScoreScreen.highscores.get(count)), 0, runner);
			count++;
			runner -= 30;
		}
		 runner = height-330;
		 white.draw(spriteBatch, ("Deaths - " + ScoreScreen.deaths), width/3-150, runner);
		 white.draw(spriteBatch, deathCalc(), width/3-150, runner-250);
		 white.draw(spriteBatch, ("Powerups - " + ScoreScreen.powerups), (2*width)/3-150, runner);
		 white.draw(spriteBatch, powerupCalc(), (2*width)/3-150, runner-250);
		 
		 spriteBatch.end();
	}
	
	public String deathCalc(){
		     String str = "";
		     if(ScoreScreen.deaths < 50) str = "Black and Blue";
		     else if(ScoreScreen.deaths < 100) str = "Hell's Citizen";
		     else if(ScoreScreen.deaths < 500) str = "Overseer";
		     else if(ScoreScreen.deaths < 1000) str = "Eviscerator";
		     else if(ScoreScreen.deaths < 10000) str = "Lord Death";
		     return str;
		   }
		   
		   public String powerupCalc(){
		     String str = "";
		     if(ScoreScreen.powerups < 30) str = "Average Joe";
		     else if(ScoreScreen.powerups < 60) str = "Powerup Junkie";
		     else if(ScoreScreen.powerups < 120) str = "Mutant";
		     else if(ScoreScreen.powerups < 500) str = "Superhuman";
		     else if(ScoreScreen.powerups < 1500) str = "Deity";
		     return str;
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
