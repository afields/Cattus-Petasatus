package com.team.catswithhats.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.GPS;
import com.team.catswithhats.GameFile;
import com.team.catswithhats.HatDatabase;
import com.team.catswithhats.HatDatabase.HatType;
import com.team.catswithhats.SoundFile;
import com.team.catswithhats.Objects.Map;

public class MapScreen implements Screen {

	// decide whether to display the quick heal button or not
	boolean enableQuickHeal = true;

	CatsWithHats game;
	Map map;
	TextButton back;
	TextButton quickheal;
	Stage stage;
	Label maplabel;
	SpriteBatch batch;
	BitmapFont white;
	Skin skin;
	BitmapFont black;
	TextureAtlas atlas;
	Texture mapTexture;
	Image mapImage;
	Sprite mapSprite;
	TextButton fightButton;
	TextButton ok;
	GPS gps;
	Pixmap pixmap;
	Dialog dialog;
	TextButtonStyle style;
	HatType curPicture = HatType.DEFAULT;
	public double longi;
	public double lati;
	public double mappedLongi;
	public double mappedLati;
	double zeroX = 28.583240;
	double zeroY = -81.209350;
	double scaleY;
	double scaleX;
	TextureRegion[] tuts;

	public MapScreen(CatsWithHats game) {
		this.game = game;
		this.gps = CatsWithHats.gps;
		
		tuts = new TextureRegion[5];
		tuts[0] = new TextureRegion(new Texture("data/ucf_map_water.png"), 0, 0, 512, 512);
		tuts[1] = new TextureRegion(new Texture("data/ucf_map_fire.png"), 0, 0, 512, 512);
		tuts[2] = new TextureRegion(new Texture("data/ucf_map_grass.png"), 0, 0, 512, 512);
		tuts[3] = new TextureRegion(new Texture("data/ucf_map_other.png"), 0, 0, 512, 512);
		tuts[4] = new TextureRegion(new Texture("data/ucf_map.png"), 0, 0, 1024, 1024);
		
		//GameFile.getCat().setCurrentHP(5);
	}

	@Override
	public void render(float delta) {
		scaleY = (double)Gdx.graphics.getHeight() / (double)mapTexture.getHeight();
		scaleX = (double)Gdx.graphics.getHeight() / (double)mapTexture.getWidth();
		update();
		Gdx.gl.glClearColor(255, 255, 255, .5f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		int select=4;
		HatType picture = HatDatabase.getHatArea((float) lati, (float) longi);
		if (curPicture != picture) {
			if (picture == HatType.WATER)
				select = 0;
			else if (picture == HatType.FIRE)
				select = 1;
			else if (picture == HatType.GRASS)
				select = 2;
			else if (picture == HatType.ELECTRIC || picture == HatType.EARTH)
				select = 3;
			else
				select = 4;
		}
		batch.begin();
		batch.draw(tuts[select], Gdx.graphics.getWidth() / 2 - Gdx.graphics.getHeight() / 2, 0, Gdx.graphics.getHeight(), Gdx.graphics.getHeight());
		batch.end();
		
		stage.act(delta);
		stage.draw();
	}

	public void update() {
		
		mappedLongi = (zeroY - gps.getLongitude()) * -1 * 40172;
		mappedLati = (gps.getLatitude() - zeroX) * 40172;
		
		mappedLongi = mappedLongi * scaleX + (Gdx.graphics.getWidth() / 2 - Gdx.graphics.getHeight() / 2);
		mappedLati = mappedLati * scaleY ;
		
		longi = gps.getLongitude();
		lati = gps.getLatitude();

		// Checks to see if in hospital range
		if (hospitalRange()
				&& (GameFile.getCat().getBaseHP() != GameFile.getCat()
						.getCurrentHP())) {
			GameFile.getCat().setCurrentHP(GameFile.getCat().getBaseHP());
			dialog = new Dialog("FULLY HEALED", new Window.WindowStyle(white,
					Color.RED, null));

			dialog.add(ok);
			Dialog.fadeDuration = 2f;
			dialog.show(stage);
		}
//		System.out.println("LONG:" + mappedLongi);
//		System.out.println("LAT:" + mappedLati);
	}

	@Override
	public void resize(int width, int height) {

		
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		// OK Image for healing dialog
		ok = new TextButton("OK", style);
		ok.setWidth(100);
		ok.setHeight(100);
		ok.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(ok.isPressed(), "regButton"); // play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("YUP");
				dialog.hide();
			}
		});

		pixmap = new Pixmap(32, 32, Pixmap.Format.RGB565);
		pixmap.setColor(Color.RED);
		pixmap.fill();
		// Texture pixText = new Texture(pixmap);
		// Image pixImage = new Image(pixText);

		final Table scrollTable = new Table();
		scrollTable.add(mapImage);

		final ScrollPane scroller = new ScrollPane(scrollTable);

		final Table table = new Table();
		table.setFillParent(true);
		table.add(scroller).fill().expand();

		// this.stage.addActor(table);
		// end

		// Start map label
//		LabelStyle ls = new LabelStyle(black, Color.WHITE);
//		maplabel = new Label("MAP SCREEN", ls);
//		maplabel.setX(Gdx.graphics.getWidth() / 2 - 160);
//		maplabel.setY(Gdx.graphics.getHeight() - 50);
//		maplabel.setWidth(width);
//		maplabel.setAlignment(Align.center);
//
//		stage.addActor(maplabel);
		// End map label

		// Start back button

		back = new TextButton("Back", style);
		back.setWidth(200);
		back.setHeight(100);
		back.setX(0);
		back.setY(Gdx.graphics.getHeight() - back.getHeight());

		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(back.isPressed(), "regButton"); // play sound
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

		// end quick heal logic
		if (enableQuickHeal) {
			quickheal = new TextButton("Quick Heal", style);
			quickheal.setWidth(200);
			quickheal.setHeight(100);
			quickheal.setX(0);
			quickheal.setY(0);

			quickheal.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					SoundFile.soundCheck(quickheal.isPressed(), "regButton"); // play sound
					System.out.println("down");
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (GameFile.checkHeal(System.currentTimeMillis())==0)
					{
						GameFile.quickHeal();
						System.out.println("Quick Heal pressed");
						dialog = new Dialog("FULLY HEALED", new Window.WindowStyle(white,
								Color.MAGENTA, null));

						dialog.add(ok);
						Dialog.fadeDuration = 2f;
						dialog.show(stage);
					}
					else
					{
						System.out.println("No heal.");
						dialog = new Dialog("Wait "+GameFile.checkHeal(System.currentTimeMillis())+" seconds", new Window.WindowStyle(white,
								Color.DARK_GRAY, null));

						dialog.add(ok);
						Dialog.fadeDuration = 2f;
						dialog.show(stage);
					}
				}
			});

			stage.addActor(quickheal);
			// end quick heal logic
		}

		// Begin play button logic

		fightButton = new TextButton("Fight", style);
		fightButton.setWidth(200);
		fightButton.setHeight(100);
		fightButton.setX(Gdx.graphics.getWidth() - 200);
		fightButton.setY(0);

		fightButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(fightButton.isPressed(), "fight_map"); // play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (lati == 0 && longi == 0) {
					// dialog.setX(Gdx.graphics.getWidth()/2);
					// dialog.setY(Gdx.graphics.getHeight()/2);
					dialog = new Dialog("Please connect to location services",
							new Window.WindowStyle(black, Color.BLACK, null));
					dialog.add(back);
					Dialog.fadeDuration = 2f;
					dialog.show(stage);
				} else {
					game.setScreen(new BattleScreen(game,longi,lati));
					System.out.println("Battle pressed");
				}
			}

		});

		stage.addActor(fightButton);
		// End begin button logic
	}

	@Override
	public void show() {
		mapTexture = new Texture("data/ucf_map.png");
		mapImage = new Image(mapTexture);

		batch = new SpriteBatch();

		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);

		style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
	}

	public boolean hospitalRange() {
		if ((lati < 28.599731 && lati > 28.599067)
				&& (longi < -81.199002 && longi > -81.199806))
			return true;
		return false;

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
		batch.dispose();
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		mapTexture.dispose();
		pixmap.dispose();
		
	}

}
