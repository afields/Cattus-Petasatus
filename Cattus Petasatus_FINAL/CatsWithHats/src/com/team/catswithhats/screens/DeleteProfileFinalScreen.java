package com.team.catswithhats.screens;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.GameFile;
import com.team.catswithhats.SoundFile;

public class DeleteProfileFinalScreen implements Screen {

	CatsWithHats game;
	Screen MainMenu;
	Skin skin;
	Stage stage;
	Files files;

	// instantiate three buttons to be displayed on the screen.
	TextButton yes; // button used to confirm profile deleting
	TextButton no; // button used to avoid profile deletion
	TextButton back; // button to navigate back to the Main Menu Screen
	TextureAtlas atlas;
	FileHandle handle;

	BitmapFont black;
	BitmapFont white;

	SpriteBatch batch;
	Label label1, label2;
	Texture bg;

	public DeleteProfileFinalScreen(CatsWithHats game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

		// begin yes button logic
		yes = new TextButton("Yes", style);
		yes.setWidth(400);
		yes.setHeight(100);
		yes.setX(Gdx.graphics.getWidth() / 2 - yes.getWidth() / 2);
		yes.setY(Gdx.graphics.getHeight() / 2 - yes.getHeight() / 2);

		yes.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(yes.isPressed(), "regButton"); // play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// To Aaron: Delete archived data here
				GameFile.reset();	// deletes all info but sound and notify settings in gamefile.txt and sets createdProfile to false
				game.setScreen(new DeleteProfileResultsScreen(game));
				System.out.println("Yes pressed");
			}

		});

		stage.addActor(yes);
		// end yes logic

		// begin no button logic
		no = new TextButton("No", style);
		no.setWidth(400);
		no.setHeight(100);
		no.setX(Gdx.graphics.getWidth() / 2 - yes.getWidth() / 2);
		no.setY(Gdx.graphics.getHeight() / 4 - yes.getHeight() / 4);

		no.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(no.isPressed(), "regButton"); // play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("No pressed");
				game.setScreen(new SettingsScreen(game));
			}

		});

		stage.addActor(no);
		// end no logic

		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		label1 = new Label("Are you sure you would like", ls);
		label1.setX(0);
		label1.setY(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4);
		label1.setWidth(width);
		label1.setAlignment(Align.center);
		
		stage.addActor(label1);
		
		label2 = new Label("to delete your profile?", ls);
		label2.setX(0);
		label2.setY(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3);
		label2.setWidth(width);
		label2.setAlignment(Align.center);
		
		stage.addActor(label2);
		
		
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
	}

}
