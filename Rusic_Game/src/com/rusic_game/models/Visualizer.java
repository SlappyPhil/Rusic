package com.rusic_game.models;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rusic_game.audio.AudioAnalyzer;

public class Visualizer {
	private World world;
	private AudioAnalyzer analyzer;
	private int numBars = 50;
	private float width = 52, height = 28.6f;
	private float[] spectrum;
	private Body[] groundBars;
	private Body[] ceilingBars;
	private Fixture[] groundBarFixtures;
	private Fixture[] ceilingBarFixtures;
	private FixtureDef fixtureDef;
	private int delay;

	public Visualizer(World world, AudioAnalyzer analyzer) {
		this.world = world;
		this.analyzer = analyzer;
		spectrum = new float[2048];
		groundBars = new Body[numBars];
		ceilingBars = new Body[numBars];
		groundBarFixtures = new Fixture[numBars];
		ceilingBarFixtures = new Fixture[numBars];
		fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0f;
		fixtureDef.friction = .5f;
		fixtureDef.density = 1000000;
		delay = 0;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width / numBars) / 2, 0);

		fixtureDef.shape = shape;

		for (int i = 0; i < numBars; i++) {
			BodyDef groundBarDef = new BodyDef();
			BodyDef ceilingBarDef = new BodyDef();
			groundBarDef.type = BodyType.StaticBody;
			ceilingBarDef.type = BodyType.StaticBody;
			if (i < numBars / 2) {
				groundBarDef.position.set(
						-(width * (numBars - i) / numBars - width / 2),
						-height / 2);
				ceilingBarDef.position.set(
						-(width * (numBars - i) / numBars - width / 2),
						height / 2);
			} else {
				groundBarDef.position.set(width * i / numBars - width / 2,
						-height / 2);
				ceilingBarDef.position.set(width * i / numBars - width / 2,
						height / 2);
			}
			groundBarDef.fixedRotation = true;
			ceilingBarDef.fixedRotation = true;
			groundBars[i] = world.createBody(groundBarDef);
			ceilingBars[i] = world.createBody(ceilingBarDef);

			groundBarFixtures[i] = groundBars[i].createFixture(fixtureDef);
			groundBarFixtures[i].setUserData(0f);
			ceilingBarFixtures[i] = ceilingBars[i].createFixture(fixtureDef);
		}
		shape.dispose();
	}

	public void update() {
		if (analyzer != null) {
			if (delay > 6) {
				analyzer.getSpectrum(spectrum);
				int nb = (spectrum.length / numBars) / 2;

				for (int i = 0; i < numBars; i++) {
					PolygonShape shape = new PolygonShape();

					if (i == numBars - 1) {
						groundBars[i].destroyFixture(groundBarFixtures[i]);
						ceilingBars[i].destroyFixture(ceilingBarFixtures[i]);
						shape.setAsBox((width / numBars) / 2,
								scale(avg(0, nb)) / 2);

						fixtureDef.shape = shape;

						groundBarFixtures[i] = groundBars[i]
								.createFixture(fixtureDef);
						groundBarFixtures[i].setUserData(scale(avg(0, nb)) / 2);
						ceilingBarFixtures[i] = ceilingBars[i]
								.createFixture(fixtureDef);
						shape.dispose();
					} else {
						float h = (Float) groundBarFixtures[i + 1]
								.getUserData();
						groundBars[i].destroyFixture(groundBarFixtures[i]);
						ceilingBars[i].destroyFixture(ceilingBarFixtures[i]);
						shape = new PolygonShape();
						shape.setAsBox((width / numBars) / 2, h);

						fixtureDef.shape = shape;

						groundBarFixtures[i] = groundBars[i]
								.createFixture(fixtureDef);
						groundBarFixtures[i].setUserData(h);
						ceilingBarFixtures[i] = ceilingBars[i]
								.createFixture(fixtureDef);
						shape.dispose();
					}
				}
			} else {
				delay++;
			}
		}
		// if (delay > 6) {
		// analyzer.getSpectrum(spectrum);
		// for (int i = 0; i < numBars; i++) {
		// int histoX = 0;
		// if (i < numBars / 2) {
		// histoX = numBars / 2 - i;
		// } else {
		// histoX = i - numBars / 2;
		// }
		// int nb = (spectrum.length / numBars) / 2;
		// groundBars[i].destroyFixture(groundBarFixtures[i]);
		// ceilingBars[i].destroyFixture(ceilingBarFixtures[i]);
		//
		// PolygonShape shape = new PolygonShape();
		// shape.setAsBox((width / numBars) / 2,
		// scale(avg(histoX, nb)) / 2);
		//
		// fixtureDef.shape = shape;
		//
		// groundBarFixtures[i] = groundBars[i].createFixture(fixtureDef);
		// ceilingBarFixtures[i] = ceilingBars[i]
		// .createFixture(fixtureDef);
		// shape.dispose();
		// }
		// delay = 0;
		// }else{
		// delay++;
		// }
	}

	private float scale(float x) {
		return x / 256 * height * 2.0f;
	}

	private float avg(int pos, int nb) {
		int sum = 0;
		for (int i = 0; i < nb; i++) {
			sum += spectrum[pos + i];
		}

		return (float) (sum / nb);
	}
}
