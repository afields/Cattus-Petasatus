package com.team.catswithhats;

import com.badlogic.gdx.Game;
import com.team.catswithhats.screens.SplashScreen;

public class CatsWithHats extends Game {
	
	public static final String VERSION = "0.0.0.01 Pre-alpha";
	public static final String LOG = "CatsWithHats";
	
	   public static GPS gps;

	   public CatsWithHats(GPS gps) {
	      CatsWithHats.gps = gps;
	   }
	
	@Override
	public void create() {
		// initialize hat db
		HatDatabase.init();
		
		// initialize game file
		GameFile.init();
		
		// initialize sound file
		SoundFile.init();
		
		// load main screen
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
	super.resume();
	}
}
