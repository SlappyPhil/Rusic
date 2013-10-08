/*
 *  This class manages the various screens, textures, sounds, user input, etc.
 */

package com.rusic_game.controllers;

import java.util.HashMap;
import java.util.Map;

import com.rusic_game.models.Player;
import com.rusic_game.models.Player.State;
import com.rusic_game.models.World;

public class PlayerController {

	enum Keys {
		LEFT, RIGHT, UP, DOWN
	}

	private Player player;

	static Map<Keys, Boolean> keys = new HashMap<PlayerController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	};

	public PlayerController(World world) {
		this.player = world.getPlayer();
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}

	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
	}

	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
	}

	/** The main update method **/
	public void update(float delta) {
		processInput();
		player.update(delta);
	}

	/** Change player's state and parameters based on input controls **/
	private void processInput() {
		if (keys.get(Keys.LEFT)) {
			// left is pressed
			player.setState(State.MOVING);
			player.getVelocity().x = -Player.SPEED;
		}
		if (keys.get(Keys.RIGHT)) {
			// right is pressed
			player.setState(State.MOVING);
			player.getVelocity().x = Player.SPEED;
		}
		if (keys.get(Keys.UP)) {
			// up is pressed
			player.setState(State.MOVING);
			player.getVelocity().y = Player.SPEED;
		}
		if (keys.get(Keys.DOWN)) {
			// down is pressed
			player.setState(State.MOVING);
			player.getVelocity().y = -Player.SPEED;
		}
		//check if both directions are pressed
		if (keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) {
			// acceleration is 0 on the x
			player.getAcceleration().x = 0;
			// horizontal speed is 0
			player.getVelocity().x = 0;
		}
		if (keys.get(Keys.UP) && keys.get(Keys.DOWN)) {
			//acceleration is 0 on the y
			player.getAcceleration().y = 0;
			// vertical speed is 0
			player.getVelocity().y = 0;
		}
		//check if no keys are pressed, or both directions are pressed and the other 2 aren't
		if ((!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT) && !keys.get(Keys.UP) && !keys.get(Keys.DOWN)) ||
			(keys.get(Keys.LEFT) && keys.get(Keys.RIGHT) && !keys.get(Keys.UP) && !keys.get(Keys.DOWN)) ||
			(keys.get(Keys.UP) && keys.get(Keys.DOWN) && !keys.get(Keys.RIGHT) && !keys.get(Keys.LEFT))) {
			player.setState(State.IDLE);
			// acceleration is 0 on the x
			player.getAcceleration().x = 0;
			// horizontal speed is 0
			player.getVelocity().x = 0;
			//acceleration is 0 on the y
			player.getAcceleration().y = 0;
			// vertical speed is 0
			player.getVelocity().y = 0;
		}
	}

}
