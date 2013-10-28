package com.rusic_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.rusic_game.Rusic_Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainScreen implements Screen, InputProcessor {
	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;
	private Rusic_Game game;
	private SpriteBatch spriteBatch;
	private Texture mainScreenTexture;
	private int width, height;
	private TextButton buttonPlay,buttonSettings;
	private BitmapFont white;
	private Label heading;

	
	//CONSTRUCTOR
	public MainScreen(Rusic_Game game, SpriteBatch spriteBatch) {
		this.game = game;
		this.spriteBatch = spriteBatch;
	}

	@Override
	public void show() {
		mainScreenTexture = new Texture(Gdx.files.internal("images/MainScreen.png"));
		stage = new Stage();
		
		//INPUT PROCESSOR
		Gdx.input.setInputProcessor(stage);
		
		//CREATE ATLAS, TABLE, SKIN
		atlas = new TextureAtlas("UI/atlas.pack.txt");
		skin = new Skin(atlas);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		white = new BitmapFont(Gdx.files.internal("font/white32.fnt.txt"), false);
		
		//CREATE BUTTONS, BUTTONSTYLES
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.BLACK;
		buttonPlay = new TextButton("PLAY", textButtonStyle);
		buttonSettings = new TextButton("SETTINGS", textButtonStyle);
		
		
		//PLAY BUTTON FUNCTIONALITY
		buttonPlay.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            
            return true;
    }
    
    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(game.musicSelectScreen);
            
    }
});
		//SETTINGS BUTTON FUNCTIONALITY
		buttonSettings.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            
            return true;
    }
    
    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            
    	game.setScreen(game.settingsScreen);
            
            
    }
});
		//PAD MARGINS BUTTONS
		buttonPlay.pad(20);
		buttonSettings.pad(20);
		
		
		/*HEADING IF NEEDED******
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		
		heading = new Label("Rusic", headingStyle);
		*/
		
		
		//*****ADD EVERYTHING TO TABLE******
		table.add(heading).spaceBottom(15).row();
		table.add(buttonPlay).spaceBottom(15).row();
		table.add(buttonSettings).spaceBottom(15).row();
		
		table.debug();
		
		//ADD TABLE (aka ACTOR) TO STAGE
		stage.addActor(table);
		
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		spriteBatch.draw(mainScreenTexture, 0, 0, width, height);
		spriteBatch.end();
		
		//RENDER STAGE
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}