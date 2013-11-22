package com.rusic_game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rusic_game.Rusic_Game;
import com.rusic_game.audio.AudioAnalyzer;
import com.rusic_game.models.helper.CustomUserData;
import com.rusic_game.screens.GameScreen;

public class Visualizer {
	private World world;
	private AudioAnalyzer analyzer;
	private Rusic_Game game;
	private int numBars = 50;
	private float width = 52, height = 28.6f;
	private float[] spectrum;
	private Body[] groundBars;
	private Body[] ceilingBars;
	private Fixture[] groundBarFixtures;
	private Fixture[] ceilingBarFixtures;
	private FixtureDef fixtureDef;

	public Visualizer(World world, AudioAnalyzer analyzer, Rusic_Game game) {
		this.world = world;
		this.analyzer = analyzer;
		this.game = game;
		spectrum = new float[2048];
		groundBars = new Body[numBars];
		ceilingBars = new Body[numBars];
		groundBarFixtures = new Fixture[numBars];
		ceilingBarFixtures = new Fixture[numBars];
		fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0f;
		fixtureDef.friction = .5f;
		fixtureDef.density = 1000000;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width / numBars) / 2, 8);

		fixtureDef.shape = shape;

		for (int i = 0; i < numBars; i++) {
			BodyDef groundBarDef = new BodyDef();
			BodyDef ceilingBarDef = new BodyDef();
			groundBarDef.type = BodyType.StaticBody;
			ceilingBarDef.type = BodyType.StaticBody;
			if (i < numBars / 2) {
				groundBarDef.position.set(-(width * (numBars - i) / numBars - width / 2), -height);
				ceilingBarDef.position.set(-(width * (numBars - i) / numBars - width / 2), height);
			} else {
				groundBarDef.position.set(width * i / numBars - width / 2, -height);
				ceilingBarDef.position.set(width * i / numBars - width / 2, height);
			}
			 Sprite boundarySprite = new Sprite(new Texture(Gdx.files.internal("images/treeSoundWaveBlack.png")));
	    	    boundarySprite.setSize(5,20);

			groundBarDef.fixedRotation = true;
			ceilingBarDef.fixedRotation = true;
			groundBars[i] = world.createBody(groundBarDef);
			groundBars[i].setUserData(new CustomUserData("boundary", boundarySprite));
			ceilingBars[i] = world.createBody(ceilingBarDef);
			ceilingBars[i].setUserData(new CustomUserData("boundary", boundarySprite));

			groundBarFixtures[i] = groundBars[i].createFixture(fixtureDef);
			groundBarFixtures[i].setUserData(0f);
			ceilingBarFixtures[i] = ceilingBars[i].createFixture(fixtureDef);
		}
		shape.dispose();
	}

	public void update() {
		if (analyzer != null) {
			analyzer.getSpectrum(spectrum);
			int nb = (spectrum.length / numBars) / 2;

			for (int i = 0; i < numBars; i++) {
				// PolygonShape shape = new PolygonShape();

				if (i == numBars - 1) {
					groundBars[i].setTransform(new Vector2(-width / 2 + i * width / numBars, -height / 1.3f + scale(avg(0, nb)) / 2), 0);
					groundBarFixtures[i].setUserData(scale(avg(0, nb)) / 2);
					ceilingBars[i].setTransform(new Vector2(-width / 2 + i * width / numBars, +height / 1.3f - scale(avg(0, nb)) / 2), 0);
				} else {
					float h = (Float) groundBarFixtures[i + 1].getUserData();

					groundBars[i].setTransform(new Vector2(-width / 2 + i * width / numBars, -height / 1.3f + h), 0);
					groundBarFixtures[i].setUserData(h);
					ceilingBars[i].setTransform(new Vector2(-width / 2 + i * width / numBars, +height / 1.3f - h), 0);
				}
			}
		}
	}

	private float scale(float x) {
		if(game.gameScreen.difficulty.equals("Easy")){
			return x/256 * height * 1.0f;
		}
		else if(game.gameScreen.difficulty.equals("Normal")){
			return x/256 * height * 1.5f;
		}
		else{
			return x / 256 * height * 2.0f;
		}
	}

	private float avg(int pos, int nb) {
		int sum = 0;
		for (int i = 0; i < nb; i++) {
			sum += spectrum[pos + i];
		}

		return (float) (sum / nb);
	}
}
