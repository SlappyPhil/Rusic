package com.rusic_game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rusic_game.Rusic_Game;

public class MusicSelectScreen implements Screen{

	private Rusic_Game game;
	private SpriteBatch spriteBatch;
	private Texture mainScreenTexture;
	private int width, height;
	private TextButton buttonPlay;
	private SelectBox musicSelectBox;
	private BitmapFont white;
	private Skin skin;
	private Table table;
	private Stage stage;
	private TextureAtlas atlas;
	private Label heading;
	private SelectBox.SelectBoxStyle style;
	private ScrollPane.ScrollPaneStyle scrollStyle;
	
	private ArrayList<String> musicpath;
	private ArrayList<String> musicinfo;
	
	//difficulty addition
	private int dif_width, dif_height;
	private TextButton dif_buttonPlay;
	private BitmapFont dif_white;
	private Skin dif_skin;
	private Table dif_table;
	private Stage dif_stage;
	private TextureAtlas dif_atlas;
	private Label dif_heading;
	private SelectBox.SelectBoxStyle dif_style;
	private ScrollPane.ScrollPaneStyle dif_scrollStyle;
	private SelectBox dif_SelectBox;
	private Table dif_Table;
	private ArrayList<String> difficulty_choices;
	//end difficulty addition
	
	//CONSTRUCTOR
	public MusicSelectScreen(Rusic_Game game, SpriteBatch spriteBatch) {
		this.game = game;
		this.spriteBatch = spriteBatch;
		// difficulty addition
		difficulty_choices = new ArrayList<String>();
		difficulty_choices.add("Easy");
		difficulty_choices.add("Normal");
		difficulty_choices.add("Hard");
		// end difficulty addition
		if(game.isAndroid==true){
			musicpath = game.musicpath;
            musicinfo = game.musicinfo;
            musicinfo.add(0, "NONE");
		}
		else{
			musicpath = new ArrayList<String>();
			musicinfo = new ArrayList<String>();
			musicpath.add("audio/tester.mp3");
			musicpath.add("audio/tester1.mp3");
			musicinfo.add("NONE");
			musicinfo.add("Test Song 1");
			musicinfo.add("Test Song 2");
		}
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
		this.height= height;
		
		
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
		buttonPlay = new TextButton("START", textButtonStyle);
		ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle(skin.getDrawable("button.up"), skin.getDrawable("button.up"), skin.getDrawable("button.up"), skin.getDrawable("button.up"), skin.getDrawable("button.up"));
		List.ListStyle listStyle = new List.ListStyle(white, Color.BLACK, Color.GRAY, skin.getDrawable("button.up"));
		style = new SelectBox.SelectBoxStyle(white, Color.BLACK, skin.getDrawable("button.up"), scrollStyle, listStyle);
		
		musicSelectBox = new SelectBox(musicinfo.toArray(), style);

		//difficulty addition
				//ScrollPane.ScrollPaneStyle dif_scrollStyle = new ScrollPane.ScrollPaneStyle(skin.getDrawable("button.up"), skin.getDrawable("button.up"), skin.getDrawable("button.up"),skin.getDrawable("button.up"), skin.getDrawable("button.up"));
				//List.ListStyle dif_listStyle = new List.ListStyle(dif_white, Color.BLACK, Color.GRAY, dif_skin.getDrawable("button.up"));
				//dif_style = new SelectBox.SelectBoxStyle(dif_white, Color.BLACK, dif_skin.getDrawable("button.up"), dif_scrollStyle, dif_listStyle);
				
				dif_SelectBox = new SelectBox(difficulty_choices.toArray(), style);
		//end difficulty addition
		
		//PLAY BUTTON FUNCTIONALITY
		buttonPlay.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
    
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(musicSelectBox.getSelectionIndex() > 0) {
					game.gameScreen.musicPath = musicpath.get(musicSelectBox.getSelectionIndex()-1)	;
				}
				else{
					game.gameScreen.musicPath = null;
				}
				game.gameScreen.difficulty = difficulty_choices.get(dif_SelectBox.getSelectionIndex());
				System.out.println(game.gameScreen.difficulty);
				game.setScreen(game.gameScreen);        
		}
});

		
		//PAD MARGINS BUTTONS
		buttonPlay.pad(20);

		
		
		/*HEADING IF NEEDED******
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		
		heading = new Label("Rusic", headingStyle);
		*/
		
		
		//*****ADD EVERYTHING TO TABLE******
		table.add(heading).spaceBottom(15).row();
		table.add(buttonPlay).spaceBottom(15).row();
		table.add(musicSelectBox).spaceBottom(15).row();
		table.add(dif_SelectBox).spaceBottom(15).row();
		
		table.debug();
		
		//ADD TABLE (aka ACTOR) TO STAGE
		stage.addActor(table);
		
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
