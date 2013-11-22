/* 
 * This class manages the Map class, and rendering of the map, in whatever class that might end up in
 * 
 * 
 */

package com.rusic_game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rusic_game.Rusic_Game;
import com.rusic_game.models.Player;
import com.rusic_game.models.Visualizer;
import com.rusic_game.models.helper.CustomUserData;
import com.rusic_game.projectiles.BombPowerUp;
import com.rusic_game.projectiles.Circles;
import com.rusic_game.projectiles.InvincibilityPowerUp;
import com.rusic_game.projectiles.Squares;
import com.rusic_game.projectiles.Triangles;
import com.rusic_game.audio.AudioAnalyzer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Random;

public class GameScreen implements Screen {

	private World world;
	private Body body;
	private Fixture fixture;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private List<Body> bodiesToRemove;

	static public int score;
	private int currentScore;
	private float decreaseScore = 0.9f;
	private int diffiltyMultiplier = 1;
	private String scoreName;
	BitmapFont bitmapFontName;
	private Timer timer;
	private int width, height;
	private SpriteBatch spriteBatch;
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	private Player player;
	private Visualizer visualizer;
	private Vector3 bottomLeft, bottomRight;
	private Rusic_Game game;
	private AudioAnalyzer analyzer;
	public String musicPath;

	public String difficulty;

	float score_Difficulty_Modifier= 1;
	
	private Circles circle;
	private Squares square;
	private Triangles triangle;
	private InvincibilityPowerUp iPowerUp;
	private BombPowerUp bPowerUp;
	
	public Texture backgroundTexture = new Texture(Gdx.files.internal("images/backgroud_New.png"));
	private Array<Body> tmpBodies = new Array<Body>();


	private float circleSize = 5;

	private long projectileTimer1 = 0;
	private long projectileTimer2 = 0;

	public GameScreen(Rusic_Game game, SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
		this.game = game;
		
		bitmapFontName = new BitmapFont(Gdx.files.internal("font/white32.fnt.txt"));
		timer = new Timer();
		timer.schedule(new Timer.Task() {

			public void run() {
				score += 10;
				System.out.println(score);
			}
		}, 0, 0.5f);
	}

	public void score() {

	}

	@Override
	public void show() {
		if (musicPath != null) {
			analyzer = new AudioAnalyzer(musicPath, game.isAndroid);
		} else
			analyzer = null;
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		bodiesToRemove = new ArrayList<Body>();
		// renderer = new WorldRenderer(world, spriteBatch, false);

		player = new Player(world, 0, 1, 1);
		visualizer = new Visualizer(world, analyzer, game);

		score = 0;
		scoreName = "score: 0";

		spriteBatch = new SpriteBatch();
		Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Keys.ESCAPE:
					timer.clear();
					timer.stop();
					ScoreScreen.updateScore(score);
					ScoreScreen.exportData();
					game.setScreen(new MainScreen(game, spriteBatch));
					break;
				case Keys.C:
					circle = new Circles(world);
					circle.update();
					break;
				case Keys.S:
					square = new Squares(world);
					square.update();
					break;

				case Keys.T:
					triangle = new Triangles(world);
					triangle.update();
					break;

				case Keys.I:
					iPowerUp = new InvincibilityPowerUp(world, 0.2f);
					iPowerUp.update();
					break;

				case Keys.B:
					bPowerUp = new BombPowerUp(world, 0.2f);
					bPowerUp.update();
					break;
				}
				return false;
			}

		}, player));

		// body definition of character
		BodyDef charDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		// BOTTOM GROUND BOUNDS
		// body definition

		// GROUND BOUNDS
		// body definition
		charDef.type = BodyType.StaticBody;
		charDef.position.set(0, 0);

		// ground shape
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-50, -14.3f),
				new Vector2(50, -14.3f) });

		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 1;

		Body ground = world.createBody(charDef);
		ground.setUserData(new CustomUserData("ground"));
		ground.createFixture(fixtureDef);
		groundShape.dispose();

		// TOP GROUND BOUNDS

		// body definition
		charDef.type = BodyType.StaticBody;
		charDef.position.set(0, 0);

		ChainShape topShape = new ChainShape();
		topShape.createChain(new Vector2[] { new Vector2(-50, 14.3f),
				new Vector2(50, 14.3f) });

		// fixture definition
		fixtureDef.shape = topShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = .5f;

		Body top = world.createBody(charDef);
		top.setUserData(new CustomUserData("top"));
		top.createFixture(fixtureDef);
		topShape.dispose();

		// LEFT SIDE BOUNDS

		// body definition
		charDef.type = BodyType.StaticBody;
		charDef.position.set(0, 0);

		ChainShape leftShape = new ChainShape();
		leftShape.createChain(new Vector2[] { new Vector2(-26, 50),
				new Vector2(-26, -50) });

		// fixture definition
		fixtureDef.shape = leftShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;

		Body left = world.createBody(charDef);
		left.setUserData(new CustomUserData("left"));
		left.createFixture(fixtureDef);
		leftShape.dispose();

		// RIGHT SIDE BOUNDS
		charDef.type = BodyType.StaticBody;
		charDef.position.set(0, 0);

		ChainShape rightShape = new ChainShape();
		rightShape.createChain(new Vector2[] { new Vector2(26, 50),
				new Vector2(26, -50) });

		// fixture definition
		fixtureDef.shape = rightShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;

		Body right = world.createBody(charDef);
		right.setUserData(new CustomUserData("right"));
		right.createFixture(fixtureDef);
		rightShape.dispose();
		/*
		 * charDef.type = BodyType.StaticBody; charDef.position.set(0,8);
		 * 
		 * //ground shape ChainShape leftShape = new ChainShape();
		 * leftShape.createChain(new Vector2[] {new Vector2(-20, 50), new
		 * Vector2(-20, -50)});
		 * 
		 * //fixture definition
		 * 
		 * fixtureDef.shape = leftShape; fixtureDef.friction = 0;
		 * fixtureDef.restitution = 0;
		 * 
		 * world.createBody(charDef).createFixture(fixtureDef);
		 * leftShape.dispose();
		 */
		if (analyzer != null)
			analyzer.play();

		this.world.setContactListener(new ContactListener() {

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}

			@Override
			public void endContact(Contact contact) {

			}

			@Override
			public void beginContact(Contact contact) {
				final Body bodyA = contact.getFixtureA().getBody();
				final Body bodyB = contact.getFixtureB().getBody();
				final CustomUserData aData = (CustomUserData) bodyA
						.getUserData();
				final CustomUserData bData = (CustomUserData) bodyB
						.getUserData();
				
				if (difficulty.equals("Easy"))
					score_Difficulty_Modifier = 1.0f;
				if (difficulty.equals("Normal"))
					score_Difficulty_Modifier = 0.9f;
				if (difficulty.equals("Hard"))
					score_Difficulty_Modifier = 0.8f;
				
				// OBJECTS THAT HIT THE LEFT BOUNDARY
				if (aData.getUserDef().equals("left")
						&& bData.getUserDef().equals("projectile")) {
					bodiesToRemove.add(bodyB);
				} else if (aData.getUserDef().equals("projectile")
						&& bData.getUserDef().equals("left")) {
					bodiesToRemove.add(bodyA);
				}
				if (aData.getUserDef().equals("left")
						&& bData.getUserDef().equals("iPowerUp")) {
					bodiesToRemove.add(bodyB);
				} else if (aData.getUserDef().equals("iPowerUp")
						&& bData.getUserDef().equals("left")) {
					bodiesToRemove.add(bodyA);
				}
				if (aData.getUserDef().equals("left")
						&& bData.getUserDef().equals("bPowerUp")) {
					bodiesToRemove.add(bodyB);
				} else if (aData.getUserDef().equals("bPowerUp")
						&& bData.getUserDef().equals("left")) {
					bodiesToRemove.add(bodyA);
				}

				// OBJECTS THAT HIT THE PLAYER
				if ((aData.getUserDef().equals("player") && bData.getUserDef()
						.equals("boundary"))
						|| (aData.getUserDef().equals("boundary") && bData
								.getUserDef().equals("player"))) {
					// set variable in player to note a hit, update function
					// handles post events
					score =  (int) (score_Difficulty_Modifier * score * decreaseScore);
					ScoreScreen.deaths++;
					player.setHitBoundary(true);
				}
				if (aData.getUserDef().equals("player")
						&& bData.getUserDef().equals("projectile")) {
					score = (int) (score_Difficulty_Modifier *score * decreaseScore);
					bodiesToRemove.add(bodyB);
					ScoreScreen.deaths++;
				} else if (aData.getUserDef().equals("projectile")
						&& bData.getUserDef().equals("player")) {
					score = (int) (score_Difficulty_Modifier * score * decreaseScore);
					bodiesToRemove.add(bodyA);
					ScoreScreen.deaths++;
				}
				if (aData.getUserDef().equals("iPowerUp")
						&& bData.getUserDef().equals("player")) {
					player.setInvincible(true);
					ScoreScreen.powerups++;
					bodiesToRemove.add(bodyA);
				} else if (bData.getUserDef().equals("iPowerUp")
						&& aData.getUserDef().equals("player")) {
					// set player invincible to true
					player.setInvincible(true);
					ScoreScreen.powerups++;
					bodiesToRemove.add(bodyB);
				}
				if (aData.getUserDef().equals("bPowerUp")
						&& bData.getUserDef().equals("player")) {
					deleteAllProjectiles();
					ScoreScreen.powerups++;
					bodiesToRemove.add(bodyA);
				} else if (bData.getUserDef().equals("bPowerUp")
						&& aData.getUserDef().equals("player")) {
					// set player invincible to true
					deleteAllProjectiles();
					ScoreScreen.powerups++;
					bodiesToRemove.add(bodyB);
				}

				// OTHER
			}
		});
	}

	private void deleteAllProjectiles() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);

		for (Body body : bodies) {
			if (body.getUserData() instanceof CustomUserData) {
				CustomUserData cud = (CustomUserData) body.getUserData();
				if (cud.getUserDef().equals("projectile")) {
					bodiesToRemove.add(body);
				}
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		visualizer.update();
		player.update();
		// Time interval generation of enemies/power ups
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);
		int timeModifier = 2500;
		if (difficulty.equals("Easy"))
			timeModifier = 2000;
		if (difficulty.equals("Normal"))
			timeModifier = 1000;
		if (difficulty.equals("Hard"))
			timeModifier = 500;
		projectileTimer2 = TimeUtils.millis();
		if ((projectileTimer2 - projectileTimer1) >= timeModifier) {
			if ((randomInt >= 0) && (randomInt < 40)) {
				circle = new Circles(world);
				circle.update();
			} else if ((randomInt >= 40) && (randomInt < 80)) {
				square = new Squares(world);
				square.update();
			 }
			 else if((randomInt >= 80) && (randomInt< 90) ){
			 triangle = new Triangles(world);
			 triangle.update();
			}
			else if ((randomInt >= 90) && (randomInt < 95)) {
				iPowerUp = new InvincibilityPowerUp(world, 0.2f);
				iPowerUp.update();
			}
			else if ((randomInt >= 95) && (randomInt < 100)) {
				bPowerUp = new BombPowerUp(world, 0.3f);
				bPowerUp.update();
			}

			projectileTimer1 = projectileTimer2;
		}


		if (analyzer != null && Gdx.input.justTouched()) {
			//analyzer.togglePlay();
		}
		
		debugRenderer.render(world, camera.combined);

		// how much computing power goes towards physics engine
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

		if (!bodiesToRemove.isEmpty()) {
			for (Body b : bodiesToRemove) {
				world.destroyBody(b);
			}
			bodiesToRemove.clear();
		}
		// controller.update(delta);
		// renderer.render();

		
		
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(backgroundTexture, -25.5f, -14f, camera.viewportWidth, camera.viewportHeight);
		
		spriteBatch.end();
		
		
		
		spriteBatch.begin();
		
		world.getBodies(tmpBodies);
		for(Body body : tmpBodies)
			if(body.getUserData() instanceof CustomUserData) {
				CustomUserData data = (CustomUserData) body.getUserData();

				Sprite sprite = data.getSprite();
				try{
					sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
					sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
					
					sprite.draw(spriteBatch);
				}catch(NullPointerException e){}	
			}
		spriteBatch.end();
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());

		
		spriteBatch.setProjectionMatrix(normalProjection);
		if(analyzer != null)
		{
		spriteBatch.begin();
	
			bitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			bitmapFontName.draw(spriteBatch, Integer.toString(score), 25, 25);
		
		spriteBatch.end();
		}
		
		
		
		
		
		if (analyzer != null && analyzer.playing == false) {
			timer.clear();
			timer.stop();
			ScoreScreen.updateScore(score);
			ScoreScreen.exportData();
			game.setScreen(game.mainScreen);
		}

	}

	@Override
	public void resize(int width, int height) {
		// renderer.setSize(width, height);
		this.width = width;
		this.height = height;

		camera.viewportWidth = width / 25;
		camera.viewportHeight = height / 25;
		camera.update();
	}

	@Override
	public void hide() {
		if (analyzer != null)
			analyzer.stop();
		if (timer != null) {
			timer.clear();
			timer.stop();
		}
		//score = 0;
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