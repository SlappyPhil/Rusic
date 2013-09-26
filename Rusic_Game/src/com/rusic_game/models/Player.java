package com.rusic_game.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

		public enum State {
			IDLE, MOVING, POWEREDUP1, POWEREDUP2, POWEREDUP3, DYING
		}

		public static final float SPEED = 4f; // unit per second
		private static final float SIZE = 0.5f; // half a unit

		private Vector2 position = new Vector2();
		private Vector2 acceleration = new Vector2();
		private Vector2 velocity = new Vector2();
		private Rectangle bounds = new Rectangle();
		
		State state = State.IDLE;
		float stateTime = 0;

		public Player(Vector2 position) {
			this.position = position;
			this.bounds.height = SIZE;
			this.bounds.width = SIZE;
		}

		public void setState(State newState) {
			this.state = newState;
		}

		public void update(float delta) {
			stateTime += delta;
			position.add(velocity.cpy().mul(delta));
		}

		public static float getSpeed() {
			return SPEED;
		}

		public static float getSize() {
			return SIZE;
		}

		public Vector2 getPosition() {
			return position;
		}

		public Vector2 getAcceleration() {
			return acceleration;
		}

		public Vector2 getVelocity() {
			return velocity;
		}

		public Rectangle getBounds() {
			return bounds;
		}

		public State getState() {
			return state;
		}

		public float getStateTime() {
			return stateTime;
		}

	
}
