package com.team.catswithhats.screens;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.SoundFile;

public class TutorialScreen implements Screen {

	CatsWithHats game;
	Screen MainMenu;
	Skin skin;
	Stage stage;
	Files files;

	public static final int NUM_TUT = 4;
	public static final int TUT_WIDTH = 250;
	public static final int TUT_HEIGHT = 250;

	int curTut = 0;

	// instantiate tutorial slides
	Texture tutorial;
	TextureRegion[] tuts;

	// instantiate three buttons to be displayed on the screen.
	TextButton prev; // button used to move to previous tip
	TextButton next; // button used to return to next tip
	TextButton back; // button to navigate back to the Main Menu Screen
	TextureAtlas atlas;

	BitmapFont black;
	BitmapFont white;

	SpriteBatch batch;
	Label label;
	Texture bg;
	Texture title;

	public TutorialScreen(CatsWithHats game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, -112, -210);
		batch.draw(title, 300, 300);
		batch.draw(
				tuts[curTut],
				Gdx.graphics.getWidth() / 2 - tuts[curTut].getRegionWidth() / 2,
				Gdx.graphics.getHeight() / 2 - tuts[curTut].getRegionHeight()
						/ 2);
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

		// set the style
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;

		tutorial = new Texture(Gdx.files.internal("data/Tutorial.png"));
		tuts = new TextureRegion[NUM_TUT];
		tuts[0] = new TextureRegion(tutorial, 0, 0, TUT_WIDTH, TUT_HEIGHT);
		tuts[1] = new TextureRegion(tutorial, 250, 0, TUT_WIDTH, TUT_HEIGHT);
		tuts[2] = new TextureRegion(tutorial, 0, 250, TUT_WIDTH, TUT_HEIGHT);
		tuts[3] = new TextureRegion(tutorial, 250, 250, TUT_WIDTH, TUT_HEIGHT);

		// begin prev logic
		prev = new TextButton("Previous", style);
		prev.setWidth(200);
		prev.setHeight(100);
		prev.setX(0);
		prev.setY(0);

		prev.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(prev.isPressed(), "regButton");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(curTut != 0) {
					curTut = curTut - 1;
					System.out.println("prev pressed");
				}
			}
		});

		stage.addActor(prev);
		// end prev logic
		
		// begin next logic
		next = new TextButton("Next", style);
		next.setWidth(200);
		next.setHeight(100);
		next.setX(Gdx.graphics.getWidth() - next.getWidth());
		next.setY(0);

		next.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(next.isPressed(), "regButton");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(curTut != NUM_TUT-1) {
					curTut = curTut + 1;
				}
				System.out.println("next pressed");
			}
		});

		stage.addActor(next);
		// end next logic

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

//		LabelStyle ls = new LabelStyle(white, Color.WHITE);
//		label = new Label("TUTORIAL", ls);
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
		title = new Texture("Screens/Tutorial.png");
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
		tutorial.dispose();
	}

	public void toScreen() {

	}

}
