package com.rusic_game.models;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rusic_game.models.helper.CustomUserData;

public class Player extends InputAdapter {

	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private Vector2 velocity = new Vector2();
	private float movementForce = 400, airResistance = 5, jumpPower = 40;
	private boolean hitBoundary;
	private boolean invincible = false;
	private boolean collisionPause = false;
	private int collisionTimer = 0;
	private int invincibleTimer = 0;

	public Player(World world, float x, float y, float width) {
		WIDTH = width;
		HEIGHT = width * 2;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, HEIGHT / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = .2f;
		fixtureDef.friction = .5f;
		fixtureDef.density = 3;

		body = world.createBody(bodyDef);
		body.setUserData(new CustomUserData("player"));
		fixture = body.createFixture(fixtureDef);
		shape.dispose();

	}

	public void update() {
		if (hitBoundary) {
			body.setTransform(-10, 1, 0);
			body.setLinearVelocity(0, 0);
			velocity.x = 0;
			velocity.y = 0;
			hitBoundary = false;
			movementForce = 0;
			airResistance = 0;
			collisionPause = true;
		} else {
			body.applyForceToCenter(velocity, true);
		}
		if (collisionPause) {
			body.setUserData(new CustomUserData("invulnerable"));
			collisionTimer++;
			if (collisionTimer > 1 * 60) {
				body.setUserData(new CustomUserData("player"));
				collisionPause = false;
				collisionTimer = 0;
				movementForce = 400;
				airResistance = 5;
			}
		}
		if (invincible) {
			body.setUserData(new CustomUserData("invulnerable"));
			invincibleTimer++;
			if (invincibleTimer > 5 * 60) {
				invincible = false;
				body.setUserData(new CustomUserData("player"));
				invincibleTimer = 0;
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			velocity.x = -movementForce;
			break;
		case Keys.RIGHT:
			velocity.x = movementForce;
			break;
		case Keys.UP:
			velocity.y = movementForce;
			break;
		case Keys.DOWN:
			velocity.y = -movementForce;
			break;

		// TODO remove this case

		default:
			return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			velocity.x = airResistance;
			break;
		case Keys.RIGHT:
			velocity.x = airResistance;
			break;
		case Keys.UP:
			velocity.y = airResistance;
			break;
		case Keys.DOWN:
			velocity.y = airResistance;
			break;

		// TODO remove this case

		default:
			return false;
		}
		return true;
	}

	public float getRestitution() {
		return fixture.getRestitution();
	}

	public void setRestitution(float restitution) {
		fixture.setRestitution(restitution);
	}

	public Body getBody() {
		return body;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public boolean isHitBoundary() {
		return hitBoundary;
	}

	public void setHitBoundary(boolean hitBoundary) {
		this.hitBoundary = hitBoundary;
	}

	public boolean getInvincible() {
		return invincible;
	}

	public void setInvincible(boolean b) {
		invincible = b;
	}

}
