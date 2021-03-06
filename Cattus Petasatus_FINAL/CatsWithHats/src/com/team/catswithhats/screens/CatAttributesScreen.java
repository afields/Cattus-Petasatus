package com.team.catswithhats.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
import com.team.catswithhats.Objects.Cat;

public class CatAttributesScreen implements Screen {

	private CatsWithHats game;
	private Cat cat;
	private Stage stage;
	private TextButton back, choose;
	private TextureAtlas atlas;
	private BitmapFont black, white;
	private SpriteBatch batch;
	private Skin skin;
	private Texture img;
	private Label catLabel, agilLabel, strLabel, defLabel;
	
	private String playerName, catName;
	private Texture bg;
	
	public CatAttributesScreen(CatsWithHats game, Cat cat, String playerName, String catName) {
		this.game = game;
		this.cat = cat;
		this.playerName = playerName;
		this.catName = catName;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(img, 50,defLabel.getY()-50, 256, 256);
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
		back.setX(325);
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
				game.setScreen(new ChooseCatScreen(game, playerName, catName));
				System.out.println("back pressed");
			}
		});

		stage.addActor(back);
		// end back logic
		
		// choose button logic
		choose = new TextButton("Choose Cat", style);
		choose.setWidth(300);
		choose.setHeight(50);
		choose.setX(450);
		choose.setY(50);
		
		choose.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(choose.isPressed(), "regButton");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				
				GameFile.createProfile(playerName, cat);
				System.out.println("choose pressed");
				game.setScreen(new MainMenu(game));
			}
		});
		
		stage.addActor(choose);
		// end choose button logic
		
		// Cats Name
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		catLabel = new Label("Name: " + catName, ls);
		catLabel.setX(325);
		catLabel.setY(Gdx.graphics.getHeight() - 100);
		catLabel.setWidth(0);
		catLabel.setAlignment(Align.left);
		stage.addActor(catLabel);
		
		// Cats Strength
		strLabel = new Label("Strength: " + cat.getStrength(), ls);
		strLabel.setX(325);
		strLabel.setY(Gdx.graphics.getHeight() - 150);
		strLabel.setWidth(0);
		strLabel.setAlignment(Align.left);
		stage.addActor(strLabel);
		
		// Cats Agility
		agilLabel = new Label("Agility: " + cat.getAgility(), ls);
		agilLabel.setX(325);
		agilLabel.setY(Gdx.graphics.getHeight() - 200);
		agilLabel.setWidth(0);
		agilLabel.setAlignment(Align.left);
		stage.addActor(agilLabel);
		
		// Cats Defense
		defLabel = new Label("Defense: " + cat.getDefense(), ls);
		defLabel.setX(325);
		defLabel.setY(Gdx.graphics.getHeight() - 250);
		defLabel.setWidth(0);
		defLabel.setAlignment(Align.left);
		stage.addActor(defLabel);

		// image
		img = new Texture("catdb/normal.jpg");
		
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

    public void createRectangle(int x, int y, int width, int height){
        ShapeRenderer shape = new ShapeRenderer();
        shape.begin(ShapeType.Line);
        shape.setColor(1, 1, 1, 1);
        shape.rect(x, y, width, height);
        shape.end();
        System.out.println("Created Rectangle");
    }

}
