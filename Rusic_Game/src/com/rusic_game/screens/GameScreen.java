/* 
 * This class manages the Map class, and rendering of the map, in whatever class that might end up in
 * 
 * 
 */

package com.rusic_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.rusic_game.audio.AudioAnalyzer;
import com.rusic_game.controllers.PlayerController;
import com.rusic_game.models.World;
import com.rusic_game.view.WorldRenderer;
import com.rusic_game.Rusic_Game;

public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private PlayerController controller;
	private AudioAnalyzer analyzer;
	private int width, height;
	private SpriteBatch spriteBatch;
	public GameScreen(Rusic_Game game, SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}

	@Override
	public void show() {
		analyzer = new AudioAnalyzer("audio/tester.mp3");
		world = new World();
		renderer = new WorldRenderer(world, spriteBatch, false);
		controller = new PlayerController(world);
		analyzer.play();
		Gdx.input.setInputProcessor(this);
		
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		analyzer.stop();
		Gdx.input.setInputProcessor(null);
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
		analyzer.dispose();
		Gdx.input.setInputProcessor(null);
	}

	// * InputProcessor methods ***************************//

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftPressed();
		if (keycode == Keys.RIGHT)
			controller.rightPressed();
		if (keycode == Keys.UP)
			controller.upPressed();
		if (keycode == Keys.DOWN)
			controller.downPressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftReleased();
		if (keycode == Keys.RIGHT)
			controller.rightReleased();
		if (keycode == Keys.UP)
			controller.upReleased();
		if (keycode == Keys.DOWN)
			controller.downReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (x > 0 &&  x < width/12 && y > 10*height/12 && y < 11*height/12) {
			controller.leftPressed();
		}
		if (x > width/12 && x < 2*width/12 && y > 9*height /12 && y <10*height/12) {
			controller.upPressed();
		}
		if (x > width/12 && x < 2*width/12 && y > 11* height / 12 && y < height) {
			controller.downPressed();
		}
		if (x > 2*width/12 && x < 3*width/12 && y > 10* height / 12 && y < 11*height/12) {
			controller.rightPressed();
		}
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (x > 0 &&  x < width/12 && y > 10*height/12 && y < 11*height/12) {
			controller.leftReleased();
		}
		if (x > width/12 && x < 2*width/12 && y > 9*height /12 && y <10*height/12) {
			controller.upReleased();
		}
		if (x > width/12 && x < 2*width/12 && y > 11* height / 12 && y < height) {
			controller.downReleased();
		}
		if (x > 2*width/12 && x < 3*width/12 && y > 10* height / 12 && y < 11*height/12) {
			controller.rightReleased();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}