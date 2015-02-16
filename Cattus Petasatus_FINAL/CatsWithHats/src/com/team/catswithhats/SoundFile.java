package com.team.catswithhats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundFile {
	
	private static Sound button;
	private static Sound map;
	private static Sound settings;
	private static Sound hatdex;
	private static Sound cats;
	private static Sound hatsound;
	private static Sound fight;
	private static Sound fight_map;

	public static void init() {
		
		button = Gdx.audio.newSound(Gdx.files.internal("sounds/button_new.wav"));
		map = Gdx.audio.newSound(Gdx.files.internal("sounds/map.wav"));
		settings = Gdx.audio.newSound(Gdx.files.internal("sounds/settings.wav"));
		hatdex = Gdx.audio.newSound(Gdx.files.internal("sounds/hatdex.wav"));
		cats = Gdx.audio.newSound(Gdx.files.internal("sounds/cats.mp3"));
		hatsound = Gdx.audio.newSound(Gdx.files.internal("sounds/hatsound.mp3"));
		fight = Gdx.audio.newSound(Gdx.files.internal("sounds/fight.mp3"));
		fight_map = Gdx.audio.newSound(Gdx.files.internal("sounds/fight_map.wav"));
	}
	
	public static void soundCheck(boolean isPressed, String type) {
		
		if(isPressed) {
			if(type == "regButton") {
 				playSound(button);
 			}
			else if(type == "map") {
				playSound(map);
			}
  			else if (type == "settings") {
				playSound(settings);
			}
			else if(type == "hatdex") {
				playSound(hatdex);
			}
			else if(type == "cats") {
				playSound(cats);	
			}
			else if(type == "hatsound") {
				playSound(hatsound);
			}
			else if(type == "fight") {
				playSound(fight);
			}
			else if(type == "fight_map") {
				playSound(fight_map);
			}
		}
	}
	
	public static void playSound(Sound s) {
		
		if(GameFile.soundEnabled()) {
			s.play();
		}
	}

	public void dispose() {
		button.dispose();
		map.dispose();
		settings.dispose();
		hatdex.dispose();
		cats.dispose();
		hatsound.dispose();
		fight.dispose();
		fight_map.dispose();
	}
	
	
}
