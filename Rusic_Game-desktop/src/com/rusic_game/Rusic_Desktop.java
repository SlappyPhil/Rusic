package com.rusic_game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Rusic_Desktop {
	public static void main(String[] args) {
		new LwjglApplication(new Rusic_Game(), "Rusic", 1024, 512, true);
	}
}