package com.rusic_game.models;

import com.badlogic.gdx.math.Vector2;

public class World {

	/** Our player controlled hero **/
	Player player;

	public Player getPlayer() {
		return player;
	}

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		player = new Player(new Vector2(1, 3));
	}
}
