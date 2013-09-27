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

	private World world;
	private Player player;

	static Map<Keys, Boolean> keys = new HashMap<PlayerController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	};

	public PlayerController(World world) {
		this.world = world;
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
		keys.get(keys.put(Keys.DOWN, false));
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
			// left is pressed
			player.setState(State.MOVING);
			player.getVelocity().x = Player.SPEED;
		}
		// need to check if both or none direction are pressed, then player is idle
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
				(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			player.setState(State.IDLE);
			// acceleration is 0 on the x
			player.getAcceleration().x = 0;
			// horizontal speed is 0
			player.getVelocity().x = 0;
		}
	}

}
