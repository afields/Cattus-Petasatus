package com.team.catswithhats;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
//import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CatsWithHats";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;
		// Settings settings = new Settings();
		// settings.maxHeight = 2048;
		// settings.maxWidth = 2048;
		// TexturePacker2.process(settings, "../images",
		// "../CatsWithHats-android/assets", "game");

		new LwjglApplication(new CatsWithHats(new DesktopGPS()), cfg);
	}
}
