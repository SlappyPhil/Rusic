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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.rusic_game.controllers.PlayerController;
//import com.rusic_game.models.World;
import com.rusic_game.view.WorldRenderer;
import com.rusic_game.Rusic_Game;

public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private PlayerController controller;
	private int width, height;
	private SpriteBatch spriteBatch;
	private final float TIMESTEP = 1/60f;
	private final int VELOCITYITERATIONS = 9, POSITIONITERATIONS = 3;
	public GameScreen(Rusic_Game game, SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}
	

	@Override
	public void show() {
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		//renderer = new WorldRenderer(world, spriteBatch, false);
		controller = new PlayerController(world);
		
		Gdx.input.setInputProcessor(this);
		
		//body definition of character
		BodyDef charDef = new BodyDef();
		charDef.type = BodyType.DynamicBody;
		charDef.position.set(0,1);
		
		//create shape
		CircleShape shape = new CircleShape();
		shape.setRadius(0.5f);
		
		//fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 2.5f; 		//how much mass per 1 square meter
		fixtureDef.friction = 0.25f; 	//goes from 0-1;
		fixtureDef.restitution = 0.75f; //elasticity 0-1
		
		world.createBody(charDef).createFixture(fixtureDef);
		
		shape.dispose();
		
		//GROUND BOUNDS
		//body definition
		charDef.type = BodyType.StaticBody;
		charDef.position.set(0,0);
		
		//ground shape
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] {new Vector2(-50, 0), new Vector2(50, 0)});
		
		//fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		
		world.createBody(charDef).createFixture(fixtureDef);
		groundShape.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		debugRenderer.render(world, camera.combined);
		
		//how much computing power goes towards physics engine
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
		//controller.update(delta);
		//renderer.render();
		
		
	}

	@Override
	public void resize(int width, int height) {
		//renderer.setSize(width, height);
		this.width = width;
		this.height = height;
		
		camera.viewportWidth = width/25;
		camera.viewportHeight = height/25;
		camera.update();
	}

	@Override
	public void hide() {
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
		world.dispose();
		debugRenderer.dispose();
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
		if (x < width / 2 && y > height / 2) {
			controller.leftPressed();
		}
		if (x > width / 2 && y > height / 2) {
			controller.rightPressed();
		}
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (x < width / 2 && y > height / 2) {
			controller.leftReleased();
		}
		if (x > width / 2 && y > height / 2) {
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