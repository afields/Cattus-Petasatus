package com.team.catswithhats.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.SoundFile;

public class HaberdasherCreate implements Screen {
	
	public static final String defaultCatName = "Enter Cat Name";
	public static final String defaultPlayerName = "Enter Player Name";
	
	CatsWithHats game;
	Stage stage;
	SpriteBatch batch;
	TextureAtlas atlas;
	TextButton back, next;
	TextField playerName, catName;
	BitmapFont black, white;
	Skin skin;
	Texture bg;
	
	public HaberdasherCreate(CatsWithHats game){
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
		
		// grahams back button logic
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;

		back = new TextButton("Back", style);
		back.setWidth(100);
		back.setHeight(50);
		back.setX(50);
		back.setY(50);

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
		
		NinePatch np = new NinePatch(new Texture("data/txtbg.png"));
		NinePatchDrawable npd = new NinePatchDrawable(np);
		
		TextFieldStyle tfstyle = new TextFieldStyle();
		tfstyle.background = npd;
		tfstyle.font = white;
		tfstyle.messageFont = white;
		tfstyle.fontColor = Color.WHITE;
		
		playerName = new TextField(defaultPlayerName, tfstyle);
		playerName.setX(100);
		playerName.setY(500);
		playerName.setWidth(600);
		playerName.setHeight(50);
		
		stage.addActor(playerName);
		playerName.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				playerName.setText("");
			}
		});
		
		/*
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
		 */
		
		catName = new TextField(defaultCatName, tfstyle);
		catName.setX(100);
		catName.setY(300);
		catName.setWidth(600);
		catName.setHeight(50);
		stage.addActor(catName);
		catName.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				catName.setText("");
			}
		});
		
		// next button
		next = new TextButton("Next", style);
		next.setWidth(100);
		next.setHeight(50);
		next.setX(Gdx.graphics.getWidth() - 150);
		next.setY(50);

		next.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				SoundFile.soundCheck(next.isPressed(), "regButton");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				String pName = playerName.getText();
				String cName = catName.getText();
				if (pName.compareTo(defaultPlayerName) != 0 && pName.compareTo("") != 0
						&& cName.compareTo(defaultCatName) != 0 && cName.compareTo("") != 0) {
						game.setScreen(new ChooseCatScreen(game, pName, cName));
				}
			}
		});

		stage.addActor(next);
		// end next button
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		// green = new BitmapFont(Gdx.files.internal("data/redfont.fnt"));
		// red = new BitmapFont(Gdx.files.internal("data/redfont.fnt"));
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
		skin.dispose();
		bg.dispose();
	}
	
	public void setStats(int [] stats){
		
	}

}
