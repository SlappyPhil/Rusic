package com.rusic_game.controllers;

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

public class PlayerController extends InputAdapter{
	
	public float movementForce = 400, airResistance = 5, jumpPower = 40;
	public Vector2 velocity = new Vector2();
	
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
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button, Body body){
		System.out.println("Screen X: " + screenX + " and Screen Y: " + screenY);
		System.out.println("Blody X: " + body.getPosition().x + " and Body Y: " + body.getPosition().y);
		System.out.println("New Screen X: " + fromPTM(screenX, screenY).x + " Y: " + fromPTM(screenX, screenY).y);
		
		body.setTransform(fromPTM(screenX, screenY), 0);
		
		return true;
	}
	
	private static Vector2 fromPTM(int screenX, int screenY){
		//1280, 720
		//25.5, 13.3 (CENTER OF THE SCREEN)
		screenY = 720-screenY;
		
		
		Vector2 newCord = new Vector2();
		
		newCord.x = (float) (screenX*0.03984375);
		newCord.y = (float) (screenY*0.03694444);
		
		if(newCord.x > 25.3)
			newCord.x = -(float) (25.3-newCord.x);
		else
			newCord.x = (float)(newCord.x-25.3);
		
		if(newCord.y > 13.3)
			newCord.y = (float)(newCord.y-13.3);
		else
			newCord.y = -(float)(13.3-newCord.y);
		
		return newCord;
	}
	
}
