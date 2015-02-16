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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.HatDatabase;
import com.team.catswithhats.HatDatabase.HatType;
import com.team.catswithhats.SoundFile;
import com.team.catswithhats.Objects.Cat;
import com.team.catswithhats.Objects.Hat;

public class ChooseCatScreen implements Screen {

	private CatsWithHats game;
	private Stage stage;
	private TextButton back, cat1button, cat2button, cat3button;
	private TextureAtlas atlas;
	private BitmapFont black;
	private SpriteBatch batch;
	private Skin skin;
	private Cat cat1, cat2, cat3;
	private Hat nohat;
	private Texture img;
	
	private String playerName, catName;
	private Texture bg;
	
	public ChooseCatScreen(CatsWithHats game, String playerName, String catName) {
		this.game = game;
		this.playerName = playerName;
		this.catName = catName;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(img, Gdx.graphics.getWidth()/2-img.getWidth()/2, Gdx.graphics.getHeight()/2-img.getHeight()/2+100, 480, 400);
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
				game.setScreen(new HaberdasherCreate(game));
				System.out.println("back pressed");
			}
		});

		stage.addActor(back);
		// end back logic
		
		// image
		img = new Texture("data/kittenscup.png");
		
		// cat 1
		nohat = HatDatabase.getHat(HatType.DEFAULT, "No Hat");
		cat1 = new Cat(catName, 10, 5, 5, 50, nohat, 1);
		HatDatabase.markAsCaught(nohat);
		HatDatabase.markAsSeen(nohat);
		cat1button = new TextButton("Cat 1", style);
		cat1button.setWidth(200);
		cat1button.setHeight(100);
		
		cat1button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(cat1button.isPressed(), "cats");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new CatAttributesScreen(game, cat1, playerName, catName));
				System.out.println("cat1 pressed");
			}
		});
		
		// cat 2
		cat2 = new Cat(catName, 5, 10, 5, 50, nohat, 1);
		cat2button = new TextButton("Cat 2", style);
		cat2button.setWidth(200);
		cat2button.setHeight(100);
		
		cat2button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(cat2button.isPressed(), "cats");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new CatAttributesScreen(game, cat2, playerName, catName));
				System.out.println("cat2 pressed");
			}
		});
		
		// cat 3
		cat3 = new Cat(catName, 5, 5, 10, 50, nohat, 1);
		cat3button = new TextButton("Cat 3", style);
		cat3button.setWidth(200);
		cat3button.setHeight(100);

		cat3button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(cat3button.isPressed(), "cats");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new CatAttributesScreen(game, cat3, playerName, catName));
				System.out.println("cat3 pressed");
			}
		});
		
		
		// button placements
		int spaceSize = 20;
		int heightFromBottom = 30;
		
		float cat1pos = (Gdx.graphics.getWidth() 
			- cat1button.getWidth() 
			- cat2button.getWidth() 
			- cat3button.getWidth() 
			- 2*spaceSize)
			/2;
		cat1button.setX(cat1pos);
		cat1button.setY(heightFromBottom);
		
		float cat2pos = cat1pos + cat1button.getWidth() + spaceSize;
		cat2button.setX(cat2pos);
		cat2button.setY(heightFromBottom);
		
		float cat3pos = cat2pos + cat2button.getWidth() + spaceSize;
		cat3button.setX(cat3pos);
		cat3button.setY(heightFromBottom);
		
		// add the buttons
		stage.addActor(cat1button);
		stage.addActor(cat2button);
		stage.addActor(cat3button);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		//white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		bg = new Texture("Screens/default.jpg");
		// green = new BitmapFont(Gdx.files.internal("data/redfont.fnt"));
		// red = new BitmapFont(Gdx.files.internal("data/redfont.fnt"));
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
		img.dispose();
	}

}
