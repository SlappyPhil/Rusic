package com.starassault;

import com.badlogic.gdx.Game;
import com.starassault.screens.GameScreen;

public class StarAssault extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}


}
