/*
 *  This class manages the various screens, textures, sounds, user input, etc.
 */

package com.rusic_game.controllers;

import com.badlogic.gdx.InputProcessor;

public class ScreenController implements InputProcessor {

	@Override
	public boolean keyDown(int keycode) {
		// IGNORE THIS METHOD, FOR PC ONLY
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// IGNORE THIS METHOD, FOR PC ONLY
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// IGNORE THIS METHOD, FOR KEYBOARDS ONLY
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Reaction on touching the screen
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Reaction on releasing 
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Reaction on dragging around the screen
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// IGNORE THIS METHOD, FOR PC ONLY
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// IGNORE THIS METHOD, FOR PC ONLY
		return false;
	}

}
