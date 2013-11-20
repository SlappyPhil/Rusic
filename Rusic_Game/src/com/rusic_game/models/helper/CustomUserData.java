package com.rusic_game.models.helper;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class CustomUserData {
	private String userDef;
	private Sprite sprite;
	
	public CustomUserData(String userDef, Sprite sprite) {
		super();
		this.userDef = userDef;
		this.sprite = sprite;
	}
	
	public String getUserDef() {
		return userDef;
	}
	public void setUserDef(String userDef) {
		this.userDef = userDef;
	}
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
}
