package com.team.catswithhats.screens;

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
import com.team.catswithhats.GameFile;
import com.team.catswithhats.SoundFile;

public class MainMenu implements Screen {

	CatsWithHats game;
	Stage stage;
	Skin skin;

	TextButton play;
	TextButton hatdex;
	TextButton settings;
	TextButton habmenu;
	TextButton tut;
	TextureAtlas atlas;

	BitmapFont black;
	BitmapFont white;

	Screen MapScreen;
	Screen SettingsScreen;
	Screen HaberdasherMenuScreen;
	Screen HatDexScreen;

	SpriteBatch batch;
	Label label;
	
	Texture bg;
	Texture title;

	public MainMenu(CatsWithHats game) {
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
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		// Begin play button logic
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
		
		if (GameFile.createdProfile()) {
			play = new TextButton("Play!", style);
		} else {
			play = new TextButton("Create Profile", style);
		}
		
		//play = new TextButton("Play!", style);
		play.setWidth(400);
		play.setHeight(100);
		play.setX(Gdx.graphics.getWidth() / 2 - play.getWidth() / 2);
		play.setY(Gdx.graphics.getHeight() / 2 - play.getHeight() / 2);

		play.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(play.isPressed(), "map");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				
				if (GameFile.createdProfile())
					game.setScreen(new MapScreen(game));
				else
					game.setScreen(new HaberdasherCreate(game));
				System.out.println("play pressed");
			}

		});

		stage.addActor(play);
		// End begin button logic
		
		if(GameFile.createdProfile()) {
		// begin catdex button logic
		hatdex = new TextButton("Hatdex", style);
		hatdex.setWidth(200);
		hatdex.setHeight(100);
		hatdex.setX(play.getX());
		hatdex.setY(Gdx.graphics.getHeight() / 4 - hatdex.getHeight() / 4);

		hatdex.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(hatdex.isPressed(), "hatdex");	// play sound
				game.setScreen(new HatDexScreen(game));
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// game.setScreen(new Game(game));
				System.out.println("Hatdex pressed");
			}

		});

		stage.addActor(hatdex);
		// end catdex logic

		// begin settings button logic
		settings = new TextButton("Settings", style);
		settings.setWidth(200);
		settings.setHeight(100);
		settings.setX(play.getX() + 200);
		settings.setY(Gdx.graphics.getHeight() / 4 - play.getHeight() / 4);

		settings.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(settings.isPressed(), "settings");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new SettingsScreen(game));
				System.out.println("settings pressed");
			}

		});

		stage.addActor(settings);
		// end settings logic

		// start habmenu logic

		habmenu = new TextButton("User Menu", style);
		habmenu.setWidth(200);
		habmenu.setHeight(100);
		habmenu.setX(play.getX()+habmenu.getWidth()/2);
		habmenu.setY(settings.getY() - 100);

		habmenu.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(habmenu.isPressed(), "regButton");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new HaberdasherMenuScreen(game));
				System.out.println("habmenu pressed");
			}

		});

		stage.addActor(habmenu);

		// end habmenu logic

		// start tutorial logic

		/*tut = new TextButton("Tutorial", style);
		tut.setWidth(200);
		tut.setHeight(100);
		tut.setX(play.getX() + 200);
		tut.setY(settings.getY() - 100);

		tut.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(tut.isPressed(), "regButton");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new TutorialScreen(game));
				System.out.println("tutorial pressed");
			}
		});
		
		stage.addActor(tut);*/
		
		// end tutorial logic
		
		//END GAMEFILE CHECK LOGIC
		}
		
//		LabelStyle ls = new LabelStyle(white, Color.WHITE);
//		label = new Label("CATS WITH HATS", ls);
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
		bg = new Texture("Screens/MainMenu.jpg");
		title = new Texture("Screens/CatsWithHats.png");
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

	public void toPlay() {

	}

	public void toSettings() {

	}

	public void toHaberdasherMenu() {

	}

}
