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

public class DeleteProfileResultsScreen implements Screen {

		CatsWithHats game;
		Screen MainMenu;
		Skin skin;
		Stage stage;
		Files files;

		// instantiate three buttons to be displayed on the screen.
		TextButton returnMain; // button used to avoid profile deletion
		TextureAtlas atlas;
		FileHandle handle;

		BitmapFont black;
		BitmapFont white;

		SpriteBatch batch;
		Label label;
		Texture bg;

		public DeleteProfileResultsScreen(CatsWithHats game) {
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

			// begin return button logic
			returnMain = new TextButton("Main Menu", style);
			returnMain.setWidth(400);
			returnMain.setHeight(100);
			returnMain.setX(Gdx.graphics.getWidth() / 2 - returnMain.getWidth() / 2);
			returnMain.setY(Gdx.graphics.getHeight() / 2 - returnMain.getHeight() / 2);

			returnMain.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					SoundFile.soundCheck(returnMain.isPressed(), "regButton"); // play sound
					System.out.println("down");
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					GameFile.reset();
					game.setScreen(new MainMenu(game));
					System.out.println("Main Menu pressed");
				}

			});

			stage.addActor(returnMain);
			// end return logic

			LabelStyle ls = new LabelStyle(white, Color.WHITE);
			label = new Label("Your Profile has been deleted.", ls);
			label.setX(0);
			label.setY(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4);
			label.setWidth(width);
			label.setAlignment(Align.center);
			
			stage.addActor(label);
			
			
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
