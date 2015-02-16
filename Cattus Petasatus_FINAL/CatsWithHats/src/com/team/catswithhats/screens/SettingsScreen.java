package com.team.catswithhats.screens;

/*
 * Developer: Graham Nicholson
 * Last Modified On: 3/15/2013
 * 
 * Comments: 
 * 		3/15/2013: Added basic functionality to Settings Screen.
 * 		Includes:
 * 			Functioning Back Button
 * 			Toggle Sound Buttons with accompanying text
 * 			Toggle Notifications with accompanying text
 * 		Would like:
 * 			Red + Green Font for "On" and "Off"
 */

//import java.io.Reader;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.GameFile;
import com.team.catswithhats.SoundFile;

public class SettingsScreen implements Screen {

	CatsWithHats game;
	Screen MainMenu;
	Skin skin;
	Stage stage;
	Files files;
	Texture bg;
	Texture title;

	// instantiate default button values
	// True = On
	// False = Off
	// public boolean SoundValue;
	// public boolean NotifValue;

	// instantiate three buttons to be displayed on the screen.
	TextButton sound; // button used to toggle sounds
	TextButton notify; // button used to toggle notifications
	TextButton delete; // button used to delete profile
	TextButton back; // button to navigate back to the Main Menu Screen
	TextureAtlas atlas;
	FileHandle handle;

	BitmapFont black;
	BitmapFont white;

	SpriteBatch batch;
	Label label;

	public SettingsScreen(CatsWithHats game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(title, Gdx.graphics.getWidth() / 2 - title.getWidth() / 2, Gdx.graphics.getHeight() * .9f - title.getHeight() / 2);
		batch.end();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		// set style
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;

		// begin back button logic
		back = new TextButton("Back", style);
		back.setWidth(200);
		back.setHeight(100);
		back.setX(0);
		back.setY(Gdx.graphics.getHeight() - back.getHeight());

		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(back.isPressed(), "regButton");
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game));
				System.out.println("back pressed");
			}
		});

		stage.addActor(back);
		// end back logic

		// begin sound button logic

		// For GameFile Functionality
		if (GameFile.soundEnabled()) {
			sound = new TextButton("Sound: On", style);
		} else {
			sound = new TextButton("Sound: Off", style);
		}

		// WithOut GameFile Functionality
		// if (SoundValue == true) {
		// sound = new TextButton("Sound: On", style);
		// } else if (SoundValue == false) {
		// sound = new TextButton("Sound: Off", style);
		// }

		sound.setWidth(400);
		sound.setHeight(100);
		sound.setX(Gdx.graphics.getWidth() / 2 - sound.getWidth() / 2);
		sound.setY(Gdx.graphics.getHeight() / 2 - sound.getHeight() / 2);

		sound.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(sound.isPressed(), "regButton");
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("sound pressed");

				// For GameFile Functionality
				// switches Sound setting from "Sound: On" to "Sound: Off"
				if (GameFile.soundEnabled()) {
					GameFile.toggleSound();
					sound.setText("Sound: Off");
				}
				// switches Sound setting from "Sound: Off" to "Sound: On"
				else {
					GameFile.toggleSound();
					sound.setText("Sound: On");
				}

				// WithOut GameFile Functionality
				// switches Sound setting from "Sound: On" to "Sound: Off"
				// if (SoundValue == true) {
				// SoundValue = false;
				// sound.setText("Sound: Off");
				// handle.writeString("Sound:" + SoundValue
				// + "\nNotifications:" + NotifValue, false);
				// }
				// // switches Sound setting from "Sound: Off" to "Sound: On"
				// else if (SoundValue == false) {
				// SoundValue = true;
				// sound.setText("Sound: On");
				// handle.writeString("Sound:" + SoundValue
				// + "\nNotifications:" + NotifValue, false);
				// }
				//
				// System.out.println("SoundValue: " + SoundValue); // WithOut
				// // GameFile
				// // Functionality
				System.out.println("SoundValue: " + GameFile.soundEnabled());	// For GameFile Functionality
			}
		});

		stage.addActor(sound);
		// end sound logic

		// begin notifications button logic

		// For GameFile Functionality
		if (GameFile.notificationsEnabled()) {
			notify = new TextButton("Notifications: On", style);
		} else {
			notify = new TextButton("Notifications: Off", style);
		}

		// WithOut GameFile Functionality
		// if (NotifValue == true) {
		// notify = new TextButton("Notifications: On", style);
		// } else if (NotifValue == false) {
		// notify = new TextButton("Notifications: Off", style);
		// }

		notify.setWidth(400);
		notify.setHeight(100);
		notify.setX(Gdx.graphics.getWidth() / 2 - notify.getWidth() / 2);
		notify.setY(Gdx.graphics.getHeight() / 4 - notify.getHeight() / 4);

		notify.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(notify.isPressed(), "regButton");
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("notifications pressed");

				// For GameFile Functionality switches Sound setting from
				// "Notifications: On" to "Notifications: Off"
				if (GameFile.notificationsEnabled()) {
					GameFile.toggleNotifications();
					notify.setText("Notifications: Off");
				}
				// switches Sound setting from "Notifications: Off" to
				// "Notifications: On"
				else {
					GameFile.toggleNotifications();
					notify.setText("Notifications: On");
				}

				// WithOut GameFile Functionality switches Sound setting from
				// "Notifications: On" to "Notifications: Off"
				// if (NotifValue == true) {
				// NotifValue = false;
				// notify.setText("Notifications: Off");
				// handle.writeString("Sound:" + SoundValue
				// + "\nNotifications:" + NotifValue, false);
				// }
				// // switches Sound setting from "Notifications: Off" to
				// "Notifications: On"
				// else if (NotifValue == false) {
				// NotifValue = true;
				// notify.setText("Notifications: On");
				// handle.writeString("Sound:" + SoundValue
				// + "\nNotifications:" + NotifValue, false);
				// }

				// System.out.println("NotifValue: " + NotifValue); // WithOut
				// GameFile Functionality
				System.out.println("NotifValue: "+ GameFile.notificationsEnabled()); // For GameFile Functionality
			}
		});

		stage.addActor(notify);
		// end notifications logic

		if(GameFile.createdProfile()) {
			// begin delete button logic
			delete = new TextButton("Delete Profile", style);
			delete.setWidth(400);
			delete.setHeight(100);
			delete.setX(Gdx.graphics.getWidth() / 2 - notify.getWidth() / 2);
			delete.setY(notify.getHeight() - 100);

			delete.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					SoundFile.soundCheck(delete.isPressed(), "regButton");
					System.out.println("down");
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					System.out.println("delete pressed");
					game.setScreen(new DeleteProfileFinalScreen(game));
				}
			});

			stage.addActor(delete);
			// end delete logic
		}

//		LabelStyle ls = new LabelStyle(white, Color.WHITE);
//		label = new Label("SETTINGS", ls);
//		label.setX(Gdx.graphics.getWidth() / 2 - 160);
//		label.setY(Gdx.graphics.getHeight() - 50);
//		label.setWidth(width);
//		label.setAlignment(Align.center);
//
//		stage.addActor(label);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		bg = new Texture("Screens/default.jpg");
		title = new Texture("Screens/Settings.png");

		// GameFile.init(); // moved to CatsWithHats.java

		// WithOut GameFile Functionality
		// handle = Gdx.files.local("config.txt");
		// SoundValue = findSetting("Sound");
		// System.out.println("findSetting(Sound) = " + SoundValue);
		// NotifValue = findSetting("Notifications");
		// System.out.println("findSetting(Notifications) = " + NotifValue);

		// For GameFile Functionality
		System.out.println("GameFile.soundEnabled() = "+ GameFile.soundEnabled());
		System.out.println("GameFile.notificationsEnabled() = "+ GameFile.notificationsEnabled());
	}

	public boolean findSetting(String word) {

		String settings = handle.readString();

		if (settings.contains(word + ":true")) {
			return true;
		} else if (settings.contains(word + ":false")) {
			return false;
		} else {
			return false;
		}
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		bg.dispose();
		title.dispose();
		
	}
}
