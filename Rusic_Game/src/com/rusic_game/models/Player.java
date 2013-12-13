package com.rusic_game.models;
//change
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
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
import com.rusic_game.controllers.PlayerController;
import com.rusic_game.models.helper.CustomUserData;

public class Player extends PlayerController {

	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private Vector2 velocity = new Vector2();
	//private float movementForce, airResistance, jumpPower;
	private boolean hitBoundary;
	private boolean invincible = false;
	private boolean collisionPause = false;
	private int collisionTimer = 0;
	private int invincibleTimer = 0;
	public Sprite playerSprite;

	public Sprite playerInvSprite;

	public Player(World world, float x, float y, float width) {
		
		

		WIDTH = width;
		HEIGHT = width * 2;
		velocity = super.velocity;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
		
		playerSprite = new Sprite(new Texture(Gdx.files.internal("images/musicNoteCharacter5.png")));
		playerSprite.setSize(2, 2);
		
		playerInvSprite = new Sprite(new Texture(Gdx.files.internal("images/musicNoteCharacter_Muscles.png")));
		playerInvSprite.setSize(2, 2);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, HEIGHT / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = .2f;
		fixtureDef.friction = .5f;
		fixtureDef.density = 3;

		body = world.createBody(bodyDef);
		body.setUserData(new CustomUserData("player", playerSprite));
		fixture = body.createFixture(fixtureDef);
		shape.dispose();

	}

	public void update() {
		if (hitBoundary) {
			body.setTransform(-10, 1, 0);
			body.setLinearVelocity(0, 0);
			body.setUserData(new CustomUserData("player",playerSprite));
			playerSprite.setPosition(body.getPosition().x - playerSprite.getWidth() / 2, body.getPosition().y - playerSprite.getHeight() / 2);
			playerSprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
			playerSprite.setSize(2, 2);

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
				body.setUserData(new CustomUserData("player",playerSprite));
				playerSprite.setPosition(body.getPosition().x - playerSprite.getWidth() / 2, body.getPosition().y - playerSprite.getHeight() / 2);
				playerSprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				playerSprite.setSize(2, 2);

				
				collisionPause = false;
				collisionTimer = 0;
				movementForce = 400;
				airResistance = 5;
			}
		}
		if (invincible) {
			body.setUserData(new CustomUserData("invulnerable",playerInvSprite));
			invincibleTimer++;
			if (invincibleTimer > 5 * 60) {
				invincible = false;
				//body.setUserData(new CustomUserData("player",playerSprite));
				playerSprite.setPosition(body.getPosition().x - playerSprite.getWidth() / 2, body.getPosition().y - playerSprite.getHeight() / 2);
				playerSprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				playerSprite.setSize(2, 2);

				body.setUserData(new CustomUserData("player", playerSprite));
				invincibleTimer = 0;
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return super.keyUp(keycode);
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		return super.touchDown(screenX, screenY, pointer, button, body);
	}
	
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return super.touchDragged(screenX, screenY, pointer, body);
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
