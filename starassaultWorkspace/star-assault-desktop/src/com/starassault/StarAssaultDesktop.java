package com.starassault;

import com.badlogic.gdx.backends.jglfw.JglfwApplication;

public class StarAssaultDesktop {
	public static void main(String[] args) {
		new JglfwApplication(new StarAssault(), "Star Assault", 480, 320, true);
	}
}
