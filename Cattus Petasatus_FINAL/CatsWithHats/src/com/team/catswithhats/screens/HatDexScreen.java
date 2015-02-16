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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import java.util.*;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.GameFile;
import com.team.catswithhats.HatDatabase;
import com.team.catswithhats.HatDatabase.HatType;
import com.team.catswithhats.SoundFile;
import com.team.catswithhats.Objects.Hat;

public class HatDexScreen implements Screen {
	
	static Label nameLabel;
	static Label typeLabel;
	static Label foundLabel;
	static Label descriptionLabel;
	static boolean caught = false;
	
	boolean start = true;
	CatsWithHats game;
	Screen MainMenu;
	Skin skin;
	Stage stage;
	
	Texture textureFile;
	
	HatType[] dbKeys;
	int index;
	int subIndex;
	
	BitmapFont black;
	BitmapFont white;
	BitmapFont green;
	BitmapFont red;
	
	// Buttons
	TextButton back;
	TextButton prev;
	TextButton next;
	TextureAtlas atlas;
	
	SpriteBatch batch;
	private Label nameHeader;
	private Label typeHeader;
	private Label foundHeader;
	private Label descriptionHeader;
	private Label caughtHeader;
	private Image hatImage;
	
	Texture bg;
	Texture title;
	Texture frame;
	
	private Table mainTable;
	private Table hatInfoTable;
	private Table hatTable;
	private Table buttonTable;
	
	private ArrayList<Hat> hatList;
	private int currentIndex;
	
	public HatDexScreen(CatsWithHats game){
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if(start){
			start();
			start = false;
		}
		
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(title, Gdx.graphics.getWidth() / 2 - title.getWidth() / 2, Gdx.graphics.getHeight() * .9f - title.getHeight() / 2);
		
		// Description Box
//		createRectangle(50, Gdx.graphics.getHeight() - 320, 500, 100);
//		// Captured Box
//		createRectangle(700, Gdx.graphics.getHeight() - 90, 20, 20);
//		
//		// Captured Indication
//		if(caught){
//			createRectangle(705, Gdx.graphics.getHeight() - 85, 10, 10);
//		}
//		
//		// Picture Box
//		createRectangle(600, Gdx.graphics.getHeight() - 250, 128, 128);
		
		batch.end();
		
		stage.act(delta);
		stage.draw();
		
//		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();
		
		Gdx.input.setInputProcessor(stage);
		
		// begin back button logic
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;

		back = new TextButton("Back", style);
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
		
		// begin previous button logic
		prev = new TextButton("Previous", style);
		prev.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(prev.isPressed(), "hatdex");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				prevPressed();
				System.out.println("prev pressed");
			}
		});
		
		// begin next button logic
		next = new TextButton("Next", style);
		next.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(next.isPressed(), "hatdex");	// play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				nextPressed();
				System.out.println("next pressed");
			}
		});

		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		
		// Name Label Logic
		nameHeader = new Label("Name:", ls);
		nameHeader.setAlignment(Align.left);
		
		nameLabel = new Label("", ls);
		nameLabel.setAlignment(Align.left);
		// End Name Label Logic
		
		// Type Label Logic
		typeHeader = new Label("Type:", ls);
		typeHeader.setAlignment(Align.left);
		
		typeLabel = new Label("", ls);
		typeLabel.setAlignment(Align.left);
		// End Type Label Logic
		
		// Found Label Logic
		foundHeader = new Label("Found:", ls);
		foundHeader.setAlignment(Align.left);
		
		foundLabel = new Label("", ls);
		foundLabel.setAlignment(Align.left);
		// End Found Label Logic
		
		// Description Label Logic
		descriptionHeader = new Label("Description:", ls);
		descriptionHeader.setAlignment(Align.left);

		LabelStyle descBackg = new LabelStyle();

		NinePatch np = new NinePatch(new Texture("data/blackbg.png"));
		NinePatchDrawable npd = new NinePatchDrawable(np);
		
		descBackg.background = npd;
		descBackg.font = white;
		descBackg.fontColor = Color.WHITE;
		
		descriptionLabel = new Label("", descBackg);
		descriptionLabel.setAlignment(Align.top + Align.left);
		descriptionLabel.setWrap(true);
		// End Description
		
		// Caught Label
		caughtHeader = new Label("Caught:", ls);
		caughtHeader.setAlignment(Align.left);

		mainTable = new Table();
		
		stage.addActor(mainTable);
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
		title = new Texture("Screens/HatDex.png");
		frame = new Texture("Screens/frame.png");
		hatList = HatDatabase.getSeenList();
		Hat currentHat = GameFile.getCat().getHat();
		currentIndex = hatList.indexOf(currentHat);
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
		skin.dispose();
		stage.dispose();
		textureFile.dispose();
		batch.dispose();
		atlas.dispose();
		bg.dispose();
		title.dispose();
		frame.dispose();
		
	}	
	
	public void nextPressed()
	{
		currentIndex = (currentIndex + 1) % hatList.size();
		Hat hat = hatList.get(currentIndex);
		showHat(hat);
	}
	
	public void prevPressed()
	{
		currentIndex = (currentIndex - 1) % hatList.size();
		if (currentIndex < 0) currentIndex = hatList.size() -1;
		Hat hat = hatList.get(currentIndex);
		showHat(hat);
	}
	
	public void start()
	{
		showHat(hatList.get(currentIndex));
	}
	
	public void showHat(Hat hat)
	{
		mainTable.remove();
		
		nameLabel.setText(hat.getName());
		
		typeLabel.setText(hat.getType().toString());
		
		foundLabel.setText(hat.getFound());
		
		descriptionLabel.setText(hat.getDescription());
		
		caught = hat.getCaught();
		
//		NinePatch border = new NinePatch(gridAtlas.createPatch("gridLine"));
//		NinePatchDrawable bo = new NinePatchDrawable(border);
		hatInfoTable = new Table();
		hatInfoTable.debug();
		hatInfoTable.add(nameHeader).left();
		hatInfoTable.add(nameLabel).left();
		hatInfoTable.row();
		hatInfoTable.add(typeHeader).left();
		hatInfoTable.add(typeLabel).left();
		hatInfoTable.row();
		hatInfoTable.add(foundHeader).left();
		hatInfoTable.add(foundLabel).left();
		
		hatImage = new Image(hat.getHat());
		
		hatTable = new Table();
		hatTable.debug();
		hatTable.add(caughtHeader).uniformX();
		if (caught) hatTable.add(new Image(new NinePatch(new Texture("data/caught.png")))).uniformX().left();
		else hatTable.add().uniformX();
		hatTable.row();
		hatTable.add(hatImage).colspan(2);
		
		batch.begin();
//		batch.draw(frame, hatImage.localToStageCoordinates(new Vector2(hatImage.getX(), hatImage.getY())).x, hatImage.localToStageCoordinates(Vector2.Y).y, hatImage.getHeight()*2, hatImage.getWidth()*2);
		batch.draw(frame, hatImage.getX(), hatImage.getY(), hatImage.getHeight()*2, hatImage.getWidth()*2);
		batch.end();
		
		buttonTable = new Table();
		buttonTable.add(prev).width(300).height(100).uniformX().fill().expandX();
		buttonTable.add(next).width(300).height(100).uniformX().fill().expandX();
		
		mainTable = new Table();
		mainTable.size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainTable.debug();
		
		mainTable.add(back).expandX().top().left().colspan(2);
		mainTable.row();
		mainTable.add(hatInfoTable).left().padLeft(30).padTop(15).top().expandX();
		mainTable.add(hatTable).padTop(15);
		mainTable.row();
		mainTable.add(descriptionHeader).padLeft(30).left();
		mainTable.row();
		mainTable.add(descriptionLabel).padLeft(35).padRight(45).padBottom(15).expandY().fill().top().colspan(2);
		mainTable.row();
		mainTable.add(buttonTable).colspan(2).expandX().fill().padBottom(30);
		
		stage.addActor(mainTable);
	}
	
	public void createRectangle(int x, int y, int width, int height){
		ShapeRenderer shape = new ShapeRenderer();
		shape.begin(ShapeType.Line);
		shape.setColor(1, 1, 1, 1);
		shape.rect(x, y, width, height);
		shape.end();
	}

}
