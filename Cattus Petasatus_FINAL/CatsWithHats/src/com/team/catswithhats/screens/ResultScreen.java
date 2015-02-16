package com.team.catswithhats.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.GameFile;
import com.team.catswithhats.HatDatabase;
import com.team.catswithhats.SoundFile;
import com.team.catswithhats.Objects.Cat;
import com.team.catswithhats.Objects.Hat;

public class ResultScreen implements Screen 
{
	private CatsWithHats game;
	private Stage stage;
	private Skin skin;
	private BitmapFont blackFont;
	private BitmapFont whiteFont;
	TextButton continueButton;
	private TextureAtlas buttonAtlas;
	private Table theTable;
	private Table captureTable;
	private Table catTable;
	
	private Cat playerCat;
	private Texture playerCatAvatarTexture;
	private Hat capturedHat;
	private Texture capturedHatTexture;
	private int xpGained;
	private boolean leveledUp;
	
	SpriteBatch batch;
	Texture bg;
	
	public ResultScreen(CatsWithHats game, Cat cat)
	{
		this.game = game;
		this.playerCat = cat;
		this.xpGained = Integer.MIN_VALUE;
		this.capturedHat = null;
	}
	
	public ResultScreen(CatsWithHats game, Cat cat, Hat hat)
	{
		this.game = game;
		this.playerCat = cat;
		this.xpGained = Integer.MIN_VALUE;
		this.capturedHat = hat;
	}
	
	public ResultScreen(CatsWithHats game, Cat cat, int xpGained)
	{
		this.game = game;
		this.playerCat = cat;
		this.xpGained = xpGained;
		this.capturedHat = null;
	}
	
	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		//Table.drawDebug(stage);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) 
	{
		if (stage == null)
		{
			stage = new Stage(width, height, true);
		}
		stage.clear();

		Gdx.input.setInputProcessor(stage);
		
		LabelStyle labelStyle = new LabelStyle(whiteFont, Color.WHITE);
		LabelStyle levelUpStyle = new LabelStyle(whiteFont, Color.YELLOW);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("buttonnormal");
		buttonStyle.down = skin.getDrawable("buttonpressed");
		buttonStyle.font = blackFont;
		
		Label titleLabel = new Label("Results", labelStyle);
		
		continueButton = new TextButton("Continue", buttonStyle);
		continueButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("down");
				SoundFile.soundCheck(continueButton.isPressed(), "regButton"); // play sound
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				GameFile.saveCat(playerCat);
				game.setScreen(new MapScreen(game));
			}
		});
		
		Label currentXpLabel = new Label("Current XP:", labelStyle);
		Label currentXpAmount = new Label(String.valueOf(playerCat.getXPCurrent()), labelStyle);
		currentXpAmount.setAlignment(Align.right);
		
		Label toNextLevelLabel = new Label("XP to next level:", labelStyle);
		Label toNextLevelAmount = new Label(String.valueOf(playerCat.getXPToNextLevel()), labelStyle);
		toNextLevelAmount.setAlignment(Align.right);
		
		Label catNameLabel = new Label(playerCat.getName(), labelStyle);
		
		Label leveledUpLabel = new Label("Leveled up!!", levelUpStyle);
		
		Label captureLabel = new Label("Captured:", labelStyle);
		Label capturedNoneLabel = new Label("NONE", labelStyle);
		
		catTable = new Table();
		catTable.debug();
		catTable.add(new Image(playerCatAvatarTexture));
		catTable.row();
		catTable.add(catNameLabel);
		
		captureTable = new Table();
		captureTable.debug();
		captureTable.add(captureLabel);
		captureTable.row();
		if (capturedHat != null)
		{
			captureTable.add(new Image(capturedHatTexture));
		}
		else
		{
			captureTable.add(capturedNoneLabel).pad(20);
		}
		
		theTable = new Table();
		theTable.setSize(width, height);
		theTable.setPosition(0, 0);
		theTable.top();
		theTable.debug();
		
		theTable.add(titleLabel).top().expandX().colspan(3).pad(25);
		theTable.row();
		theTable.add(catTable).left().top().padLeft(40).expandX();
		theTable.add(captureTable).colspan(2).expandY().top();
		theTable.row();
		theTable.add();
		theTable.add(currentXpLabel).right();
		theTable.add(currentXpAmount).width(200).right().padRight(20);
		theTable.row();
		theTable.add();
		theTable.add(toNextLevelLabel).right();
		theTable.add(toNextLevelAmount).width(200).right().padRight(20);
		theTable.row();
		theTable.add(continueButton).left().prefSize(200, 100);
		if (leveledUp)
		{
			theTable.add(leveledUpLabel).colspan(2).right().top().padTop(5).padRight(20);
		}
		
		stage.addActor(theTable);
	}

	@Override
	public void show() 
	{
		batch = new SpriteBatch();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("data/button.pack"));
		skin = new Skin();
		skin.addRegions(buttonAtlas);
		whiteFont = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		blackFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		bg = new Texture("Screens/default.jpg");
		bg.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

		playerCatAvatarTexture = playerCat.getPicture();
		
		if (xpGained > 0)
		{
			leveledUp = playerCat.increaseXP(xpGained);
		}
		else if (xpGained == -1)
		{
			playerCat.setCurrentHP(1);
		}
		
		if (capturedHat != null)
		{
			capturedHatTexture = capturedHat.getHat();
			HatDatabase.markAsCaught(capturedHat);
		}
		
		GameFile.saveCat(playerCat);
	}

	@Override
	public void hide() 
	{
		
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public void dispose() 
	{
		GameFile.saveCat(playerCat);	
		stage.dispose();
		batch.dispose();
		skin.dispose();
		playerCatAvatarTexture.dispose();
		capturedHatTexture.dispose();
		bg.dispose();
	}

}
