/* 
 * This class manages the Map class, and rendering of the map, in whatever class that might end up in
 * 
 * 
 */

package com.rusic_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rusic_game.Rusic_Game;
import com.rusic_game.models.Player;
import com.rusic_game.models.Visualizer;
import com.rusic_game.projectiles.Circles;
import com.rusic_game.projectiles.Squares;
import com.rusic_game.projectiles.Triangles;
import com.rusic_game.audio.AudioAnalyzer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;



public class GameScreen implements Screen {

	

	private World world;
	private Body body;
	private Fixture fixture;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;

	private int width, height;
	private SpriteBatch spriteBatch;
	private final float TIMESTEP = 1/60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	private Player player;
	private Visualizer visualizer;
	private Vector3 bottomLeft, bottomRight;
	private Rusic_Game game;
	private AudioAnalyzer analyzer;
	public String musicPath; 
	
	private Array<Circles> circles = new Array<Circles>();
	private Circles circle;
	private Squares square;
	private Triangles triangle;
	
	
	private float circleSize = 5;
	
	public GameScreen(Rusic_Game game, SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
		this.game = game;
	}
	

	@Override
	public void show() {
		if(musicPath != null)
			analyzer = new AudioAnalyzer(musicPath);
		else
			analyzer = null;
		world = new World(new Vector2(0, -19), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		//renderer = new WorldRenderer(world, spriteBatch, false);
		
		 player = new Player(world, 0, 1, 1);
         visualizer = new Visualizer(world, analyzer);
         
         spriteBatch = new SpriteBatch();
         Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {
        	 @Override
             public boolean keyDown(int keycode) {
                     switch(keycode) {
                     case Keys.ESCAPE:
                             game.setScreen(new MainScreen(game, spriteBatch));
                             break;
                     case Keys.C:
                    	 circle = new Circles(world, 1);
            			 circle.update();
            			 break;
                     case Keys.S:
                    	 square = new Squares(world);
                    	 square.update();
                    	 break;
                     /*case Keys.T:
                    	 triangle = new Triangles(world);
                    	 triangle.update();
                    	 break;*/
                     }
                     return false;
             }

         }, player));
		
		//body definition of character
		BodyDef charDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();


		
		//BOTTOM GROUND BOUNDS
		//body definition

        //GROUND BOUNDS
        //body definition
        charDef.type = BodyType.StaticBody;
        charDef.position.set(0,0);
        
        //ground shape
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] {new Vector2(-50, -14.3f), new Vector2(50, -14.3f)});
        
        //fixture definition
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(charDef).createFixture(fixtureDef);
        groundShape.dispose();
		
		//TOP GROUND BOUNDS
        
      

        //body definition
        charDef.type = BodyType.StaticBody;
        charDef.position.set(0,0);
        
     
        ChainShape topShape = new ChainShape();
        topShape.createChain(new Vector2[] {new Vector2(-50, 14.3f), new Vector2(50, 14.3f)});
        
        //fixture definition
        fixtureDef.shape = topShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(charDef).createFixture(fixtureDef);
        topShape.dispose();
		
		//LEFT SIDE BOUNDS
        


      
        //body definition
        charDef.type = BodyType.StaticBody;
        charDef.position.set(0,0);
        
     
        ChainShape leftShape = new ChainShape();
        leftShape.createChain(new Vector2[] {new Vector2(-26, 50), new Vector2(-26, -50)});
        
        //fixture definition
        fixtureDef.shape = leftShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(charDef).createFixture(fixtureDef);
        leftShape.dispose();
        
        //RIGHT SIDE BOUNDS
        charDef.type = BodyType.StaticBody;
        charDef.position.set(0,0);
        
     
        ChainShape rightShape = new ChainShape();
        rightShape.createChain(new Vector2[] {new Vector2(26, 50), new Vector2(26, -50)});
        
        //fixture definition
        fixtureDef.shape = rightShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(charDef).createFixture(fixtureDef);
        rightShape.dispose();
        /*
		charDef.type = BodyType.StaticBody;
		charDef.position.set(0,8);
		
		//ground shape
		ChainShape leftShape = new ChainShape();
		leftShape.createChain(new Vector2[] {new Vector2(-20, 50), new Vector2(-20, -50)});
		
		//fixture definition
		
		fixtureDef.shape = leftShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		
		world.createBody(charDef).createFixture(fixtureDef);
		leftShape.dispose();
		*/
        if(analyzer != null)
        	analyzer.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		visualizer.update();
		player.update();
     
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
		if(analyzer != null)
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
		world.dispose();
		debugRenderer.dispose();
		Gdx.input.setInputProcessor(null);
	}

	
}