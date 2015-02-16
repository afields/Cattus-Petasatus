package com.team.catswithhats.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.GameFile;
import com.team.catswithhats.HatDatabase;
import com.team.catswithhats.HatDatabase.HatType;
import com.team.catswithhats.SoundFile;
import com.team.catswithhats.Objects.Hat;

public class HaberdasherHatScreen implements Screen {

	CatsWithHats game;
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
	TextButton Previous;
	TextButton Next;
	TextButton Equip;
	Label curHat;
	Label ifEquip;
	ArrayList<Hat> hatList;
	
	HatType[] dbKeys;
	int index;
	int subIndex;
	
	Hat currentHat;
	Texture bg;
	Texture title;
	Texture frame;

	public HaberdasherHatScreen(CatsWithHats game) {
		this.game = game;
		currentHat = GameFile.getCat().getHat();
		dbKeys = HatType.values();
		index = 0;
		subIndex = 0;
		hatList = HatDatabase.getCaughtList();
		System.out.print(hatList.size());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		curHat = new Label(currentHat.getName(), ls);
		curHat.setX(Gdx.graphics.getWidth()/2);
		curHat.setY(Gdx.graphics.getHeight() - 150);
		
		if(isEquipped(currentHat)){
		ifEquip = new Label("EQUIPPED", ls);
		ifEquip.setX(Gdx.graphics.getWidth()/2+75);
		ifEquip.setY(150);
		}
		else
		{
			ifEquip = new Label("NOT EQUIPPED", ls);
			ifEquip.setX(Gdx.graphics.getWidth()/2+75);
			ifEquip.setY(150);
		}
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(title, Gdx.graphics.getWidth() / 2 - title.getWidth() / 2, Gdx.graphics.getHeight() * .9f - title.getHeight() / 2);
		batch.draw(frame, Gdx.graphics.getWidth()/2 - 70, Gdx.graphics.getHeight()/2 - 70, currentHat.getHat().getWidth() + 140, currentHat.getHat().getWidth() + 140);
		curHat.draw(batch, 1);
		ifEquip.draw(batch, 1);
		batch.draw(currentHat.getHat(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, currentHat.getHat().getWidth(), 
				currentHat.getHat().getWidth());
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
				SoundFile.soundCheck(back.isPressed(), "regButton"); // play
																		// sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new HaberdasherMenuScreen(game));
				System.out.println("back pressed");
			}
		});

		stage.addActor(back);
		// end back logic

		// Next Button
		Next = new TextButton("Next", style);
		Next.setWidth(200);
		Next.setHeight(100);
		Next.setX(Gdx.graphics.getWidth() - Next.getWidth());
		Next.setY(0);

		Next.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(Next.isPressed(), "regButton"); // play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				nextPressed();
				System.out.println("next pressed");
			}
		});

		stage.addActor(Next);

		// Previous button
		Previous = new TextButton("Previous", style);
		Previous.setWidth(200);
		Previous.setHeight(100);
		Previous.setX(0);
		Previous.setY(0);

		Previous.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(Previous.isPressed(), "regButton"); // play
																			// sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				prevPressed();
				System.out.println("prev pressed1");
			}
		});

		stage.addActor(Previous);
		
		//Equip Button
		Equip = new TextButton("Equip", style);
		Equip.setWidth(200);
		Equip.setHeight(100);
		Equip.setX(Gdx.graphics.getWidth()/2-Equip.getWidth()/2);
		Equip.setY(0);

		Equip.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(Equip.isPressed(), "regButton"); // play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				GameFile.getCat().setHat(currentHat);
				GameFile.saveCat(GameFile.getCat());
			}
		});

		stage.addActor(Equip);

		// Top Label
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
//		label = new Label("HAT SCREEN", ls);
//		label.setX(Gdx.graphics.getWidth() / 2 - 160);
//		label.setY(Gdx.graphics.getHeight() - 50);
//		label.setWidth(width);
//		label.setAlignment(Align.center);
//		stage.addActor(label);
		
		//Equipped
		Label labelEquip = new Label("Equipped: ", ls);
		labelEquip.setX(Gdx.graphics.getWidth()/2-100);
		labelEquip.setY(150);
		labelEquip.setWidth(width);
		
		stage.addActor(labelEquip);
		
		//Hat Label
		Label labelHat = new Label("Hat: ", ls);
		labelHat.setX(Gdx.graphics.getWidth()/2-100);
		labelHat.setY(Gdx.graphics.getHeight()-150);
		labelHat.setWidth(width);
		
		stage.addActor(labelHat);
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
		title = new Texture("Screens/HHatScreen.png");
		frame = new Texture("Screens/frame.png");
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
		frame.dispose();
	}

	public boolean isEquipped(Hat hat){
		if(hat.getName().equals(GameFile.getCat().getHat().getName())){
			return true;
		}
		return false;
	}
	
	public void nextPressed(){
		if(hatList.size()<=1){
			return;
		}
		int position = hatList.indexOf(currentHat);
		if(position==hatList.size()-1){
			currentHat = hatList.get(0);
		}
		else
		currentHat = hatList.get(position+1);
	}
	
	public void prevPressed(){
		if(hatList.size()<=1){
			return;
		}
		int position = hatList.indexOf(currentHat);
		if(position==0){
			currentHat = hatList.get(hatList.size()-1);
		}
		else
		currentHat = hatList.get(position-1);

	}

}
