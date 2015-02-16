package com.team.catswithhats.screens;

/*
 * Developer: Graham Nicholson
 * Last Modified On: 3/22/2013
 * 
 * Comments: 
 * 		3/22/2013: Added basic functionality to Haberdasher Menu Screen.
 * 		Includes:
 * 			Functioning Back Button
 * 		Needs:
 * 			Cats button requires CatsAttributesScreen
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.team.catswithhats.SoundFile;

public class HaberdasherMenuScreen implements Screen {

	CatsWithHats game;
	Screen MainMenu;
	Skin skin;
	Stage stage;

	// instantiate three buttons to be displayed on the screen.
	TextButton Cats; // button used to observe your Cat's information
	TextButton Hats; // button used to fuse Hats
	TextButton back; // button to navigate back to the Main Menu Screen
	TextureAtlas atlas;

	BitmapFont black;
	BitmapFont white;

	SpriteBatch batch;
	Label label;
	Texture bg;
	Texture title;

	public HaberdasherMenuScreen(CatsWithHats game) {
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
				SoundFile.soundCheck(back.isPressed(), "regButton");	// play sound
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

		// begin Hats button logic
		Hats = new TextButton("Hats", style);
		Hats.setWidth(400);
		Hats.setHeight(100);
		Hats.setX(Gdx.graphics.getWidth() / 2 - Hats.getWidth() / 2);
		Hats.setY(Gdx.graphics.getHeight() / 2 - Hats.getHeight() / 2);

		Hats.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(Hats.isPressed(), "hatsound");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new HaberdasherHatScreen(game));
				System.out.println("Hats pressed");
			}
		});

		stage.addActor(Hats);
		// end Hats logic

		// begin Cats button logic
		Cats = new TextButton("Cats", style);
		Cats.setWidth(400);
		Cats.setHeight(100);
		Cats.setX(Gdx.graphics.getWidth() / 2 - Cats.getWidth() / 2);
		Cats.setY(Gdx.graphics.getHeight() / 4 - Cats.getHeight() / 4);

		Cats.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(Cats.isPressed(), "cats");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new HaberdasherCatScreen(game));
				System.out.println("Cats pressed");
			}
		});

		stage.addActor(Cats);
		// end Cats logic

//		LabelStyle ls = new LabelStyle(white, Color.WHITE);
//		label = new Label("HABERDASHER MENU", ls);
//		label.setX(Gdx.graphics.getWidth() / 2 - 200);
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
		title = new Texture("Screens/HaberdasherMenu.png");
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
		skin.dispose();
		bg.dispose();
		title.dispose();
	}

	public void setSound(int value) {

	}

	public void setNotifications(int value) {

	}

	public void setReset(int value) {

	}

	public void setMainMenu() {

	}

}
