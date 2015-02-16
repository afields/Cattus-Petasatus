package com.team.catswithhats.screens;

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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.team.catswithhats.BattleSystem;
import com.team.catswithhats.CatsWithHats;
import com.team.catswithhats.GPS;
import com.team.catswithhats.GameFile;
import com.team.catswithhats.HatDatabase;
import com.team.catswithhats.HatDatabase.HatType;
import com.team.catswithhats.SoundFile;
import com.team.catswithhats.Objects.Cat;
import com.team.catswithhats.Objects.Hat;

public class BattleScreen implements Screen {

	private SpriteBatch batch;
	private CatsWithHats game;
	private TextButton fightButton;
	private TextButton captureButton1;
	private TextButton captureButton2;
	private TextButton attackButton1;
	private TextButton attackButton2;
	private TextButton attackButton3;
	private TextButton attackButton4;
	private TextButton battleOutcomeButton;
	private Skin skin;
	private BitmapFont blackFont;
	private BitmapFont whiteFont;
	private Stage stage;
	private Texture playerCatAvatarTexture;
	private Texture opponentCatAvatarTexture;
	private TextureAtlas atlas;
	private Label messageBoxFirstMessage;
	private Label messageBoxSecondMessage;
	private Label playerHealth;
	private Label opponentHealth;
	private Cat playerCat;
	private Cat opponentCat;
	private Table attacksTable;
	private Table attacksCaptureTable;
	private Table fightCaptureTable;
	private Table messageFirstTable;
	private Table messageSecondTable;
	private Table battleAreaTable;
	private Table battleOutcomeTable;
	private Table opponentCatInfoTable;
	private Table playerCatInfoTable;
	private GPS gps;
	private double centerLongi;
	private double centerLati;
	private double longi;
	private double lati;
	private int awardedXp;
	private boolean successfulHatGrab = false;
	private Texture bg;
	Dialog dialog;
	TextButton ok;
	Table fleeTable;

	private static float heightOfBottomButtons = 150;
	private static double distanceOfFlee = .000001;

	public BattleScreen(CatsWithHats game) {
		this.game = game;
		this.gps = CatsWithHats.gps;
		this.centerLati = gps.getLatitude();
		this.centerLongi = gps.getLongitude();
	}

	public BattleScreen(CatsWithHats game, double longi, double lati) {
		this.game = game;
		this.gps = CatsWithHats.gps;
		this.centerLati = lati;
		this.centerLongi = longi;
		System.out.println("LONG:" + centerLati);
		System.out.println("LATI:" + centerLongi);
	}

	@Override
	public void render(float delta) {
		update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		batch.draw(userFrame, -65, 85);
//		batch.draw(enemyFrame, Gdx.graphics.getWidth()/2+209, 407);
		// batch.draw(bg, 0, heightOfBottomButtons, Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight() - heightOfBottomButtons);
		// batch.draw(playerCatAvatarTexture, 0, heightOfBottomButtons);
		// batch.draw(opponentCatAvatarTexture, Gdx.graphics.getWidth() -
		// opponentCatAvatarTexture.getWidth(), Gdx.graphics.getHeight() -
		// opponentCatAvatarTexture.getHeight());
		batch.end();

		if (!withinFightingDistance()) {
			fleeTable.setVisible(true);
			attacksTable.setTouchable(Touchable.disabled);
			attacksCaptureTable.setTouchable(Touchable.disabled);
			fightCaptureTable.setTouchable(Touchable.disabled);
			messageFirstTable.setTouchable(Touchable.disabled);
			messageSecondTable.setTouchable(Touchable.disabled);
			battleAreaTable.setTouchable(Touchable.disabled);
			battleOutcomeTable.setTouchable(Touchable.disabled);
		}

		stage.act(delta);
		stage.draw();

		// Table.drawDebug(stage);
	}

	public void update() {
		longi = gps.getLongitude();
		lati = gps.getLatitude();
		//System.out.println("LONG:" + longi);
		//System.out.println("LATI:" + lati);
	}

	public boolean withinFightingDistance() {
		double currentDistance = Math.sqrt(Math.pow(centerLati - lati, 2)
				+ Math.pow(centerLongi - longi, 2));

		return currentDistance <= distanceOfFlee;
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();

		heightOfBottomButtons = (int) (height * .25);

		Gdx.input.setInputProcessor(stage);

		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = blackFont;

		LabelStyle messageLabelStyle = new LabelStyle(blackFont, Color.BLACK);
		LabelStyle catNameLabelStyle = new LabelStyle(whiteFont, Color.WHITE);

		// OK button for fleeing dialog
		ok = new TextButton("YOU HAVE FLED THE BATTLE", style);
		ok.setWidth(400);
		ok.setHeight(200);
		ok.toFront();
		ok.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(ok.isPressed(), "regButton"); // play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MapScreen(game));
			}
		});

		// Flee table
		fleeTable = new Table();
		fleeTable.setSize(width, height - heightOfBottomButtons);
		fleeTable.add(ok);
		fleeTable.setVisible(false);
		stage.addActor(fleeTable);

		// Begin Battle area logic
		battleAreaTable = new Table();
		battleAreaTable.setSize(width, height - heightOfBottomButtons);
		battleAreaTable.setPosition(0, heightOfBottomButtons);
		battleAreaTable.debug();

		opponentHealth = new Label(String.valueOf(opponentCat.getCurrentHP()),
				catNameLabelStyle);
		playerHealth = new Label(String.valueOf(playerCat.getCurrentHP()),
				catNameLabelStyle);

		opponentCatInfoTable = new Table();
		opponentCatInfoTable
				.add(new Label(opponentCat.getName(), catNameLabelStyle))
				.left().colspan(2);
		opponentCatInfoTable.row();
		opponentCatInfoTable.add(
				new Label(opponentCat.getHat().getName(), new LabelStyle(
						whiteFont, colorBasedOnHatType(opponentCat.getHat()))))
				.left();
		opponentCatInfoTable.add(new Image(opponentCat.getHat().getHat()))
				.left().size(30);
		opponentCatInfoTable.row();
		opponentCatInfoTable.add(opponentHealth).left().colspan(2);

		playerCatInfoTable = new Table();
		playerCatInfoTable.add(playerHealth).right().colspan(2);
		playerCatInfoTable.row();
		playerCatInfoTable.add(new Image(playerCat.getHat().getHat())).right()
				.size(30);
		playerCatInfoTable.add(
				new Label(playerCat.getHat().getName(), new LabelStyle(
						whiteFont, colorBasedOnHatType(playerCat.getHat()))))
				.right();
		playerCatInfoTable.row();
		playerCatInfoTable
				.add(new Label(playerCat.getName(), catNameLabelStyle)).right()
				.colspan(2);

		battleAreaTable.add(opponentCatInfoTable).expand().top().left()
				.padTop(20).padLeft(20);
		battleAreaTable.add(new Image(opponentCatAvatarTexture)).top().right();
		battleAreaTable.row();
		battleAreaTable.add(new Image(playerCatAvatarTexture)).bottom().left();
		battleAreaTable.add(playerCatInfoTable).bottom().right().padBottom(20)
				.padRight(20);
		// End Battle area logic

		// Begin Fight! button logic
		fightButton = new TextButton("Fight!", style);

		fightButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(fightButton.isPressed(), "fight"); // play
																		// sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				fightCaptureTable.remove();
				stage.addActor(attacksCaptureTable);
				System.out.println("Fight! pressed");
			}

		});
		// End Fight! button logic

		// Begin <attackButton1> button logic
		attackButton1 = new TextButton(
				playerCat.getHat().getAttacks().get(0).attackName, style);
		attackButton1.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(attackButton1.isPressed(), "regButton"); // play
																				// sound
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				attackButtonPressed(0);
				System.out.println("<attackButton1> pressed");
			}

		});
		// End begin <attackButton1> logic

		// Begin <attackButton2> button logic
		attackButton2 = new TextButton(
				playerCat.getHat().getAttacks().get(1).attackName, style);

		attackButton2.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(attackButton2.isPressed(), "regButton"); // play
																				// sound
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				attackButtonPressed(1);
				System.out.println("<attackButton2> pressed");
			}

		});
		// End begin <attackButton2> logic

		// Begin <attackButton3> button logic
		attackButton3 = new TextButton(
				playerCat.getHat().getAttacks().get(2).attackName, style);
		attackButton3.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(attackButton3.isPressed(), "regButton"); // play
																				// sound
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				attackButtonPressed(2);
				System.out.println("<attackButton3> pressed");
			}

		});
		// End begin <attackButton3> logic

		// Begin <attackButton4> button logic
		attackButton4 = new TextButton(
				playerCat.getHat().getAttacks().get(3).attackName, style);

		attackButton4.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(attackButton4.isPressed(), "regButton"); // play
																				// sound
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				attackButtonPressed(3);
				System.out.println("<attackButton4> pressed");
			}

		});
		// End begin <attackButton4> logic

		// Begin Capture1 button logic
		captureButton1 = new TextButton("Capture", style);
		captureButton1.center();

		captureButton1.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(captureButton1.isPressed(), "hatsound"); // play
																				// sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				fightCaptureTable.remove();
				captureButtonPressed();
				System.out.println("Capture pressed");
			}

		});
		// End Capture1 button logic

		// Begin Capture2 button logic
		captureButton2 = new TextButton("Capture", style);
		captureButton2.center();

		captureButton2.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(captureButton2.isPressed(), "hatsound"); // play
																				// sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				attacksCaptureTable.remove();
				captureButtonPressed();
				System.out.println("Capture pressed");
			}

		});
		// End Capture2 button logic

		// Begin battle text first field logic
		messageFirstTable = new Table(skin);
		messageFirstTable.setSize(width, heightOfBottomButtons);
		messageFirstTable.setBackground("buttonnormal");
		messageFirstTable.setPosition(0, 0);
		messageFirstTable.debug();
		messageFirstTable.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (successfulHatGrab) {
					game.setScreen(new ResultScreen(game, playerCat,
							opponentCat.getHat()));
				} else {
					opponentTurn();
				}
			}

		});

		messageBoxFirstMessage = new Label("", messageLabelStyle);

		messageBoxFirstMessage.setWrap(true);

		messageFirstTable.add(messageBoxFirstMessage).expand().fillX().top()
				.pad(5, 18, 18, 18);
		// End battle text first field logic

		// Begin battle text second field logic
		messageSecondTable = new Table(skin);
		messageSecondTable.setSize(width, heightOfBottomButtons);
		messageSecondTable.setBackground("buttonnormal");
		messageSecondTable.setPosition(0, 0);
		messageSecondTable.debug();

		messageBoxSecondMessage = new Label("", messageLabelStyle);

		messageBoxSecondMessage.setWrap(true);
		messageBoxSecondMessage.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				messageSecondTable.remove();
				stage.addActor(fightCaptureTable);
				System.out.println("Capture pressed");
			}

		});

		messageSecondTable.add(messageBoxSecondMessage).expand().fillX().top()
				.pad(5, 18, 18, 18);
		// End battle text second field logic

		// Begin Fight/Capture table logic
		fightCaptureTable = new Table();
		fightCaptureTable.setSize(width, heightOfBottomButtons);
		fightCaptureTable.setPosition(0, 0);
		fightCaptureTable.debug();

		fightCaptureTable.add(fightButton).expand().fill();
		fightCaptureTable.add(captureButton1).expandY().fillY().width(200);
		// End Fight/Capture table logic

		// Begin Attacks table logic
		attacksTable = new Table();
		attacksTable.debug();

		attacksTable.add(attackButton1).expand().fill().uniform();
		attacksTable.add(attackButton2).expand().fill().uniform();
		attacksTable.row();
		attacksTable.add(attackButton3).expand().fill().uniform();
		attacksTable.add(attackButton4).expand().fill().uniform();
		// End Attacks table logic

		// Begin Fight/Capture table logic
		attacksCaptureTable = new Table();
		attacksCaptureTable.setSize(width, heightOfBottomButtons);
		attacksCaptureTable.setPosition(0, 0);
		attacksCaptureTable.debug();

		attacksCaptureTable.add(attacksTable).expand().fill();
		attacksCaptureTable.add(captureButton2).expandY().fillY().width(200);
		// End Fight/Capture table logic

		// Begin Battle Outcome logic
		battleOutcomeButton = new TextButton("", style);
		battleOutcomeButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				SoundFile.soundCheck(battleOutcomeButton.isPressed(),
						"regButton"); // play sound
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new ResultScreen(game, playerCat, awardedXp));
			}
		});

		battleOutcomeTable = new Table();
		battleOutcomeTable.setSize(width, height - heightOfBottomButtons);
		battleOutcomeTable.setPosition(0, heightOfBottomButtons);
		battleOutcomeTable.debug();

		battleOutcomeTable.add(battleOutcomeButton).center();
		// End Battle Outcome Button logic

		stage.addActor(fightCaptureTable);
		stage.addActor(battleAreaTable);
	}

	@Override
	public void show() {
		playerCat = GameFile.getCat();
		HatType areaHat = getHatArea();
		// The string for the picture can be pulled to, but b/c we dont have any
		// atm I didnt write in the funcitonality
		opponentCat = BattleSystem.generateAI(playerCat,
				HatDatabase.getRandomHat(areaHat));

		if (playerCat == null) {
			playerCat = GameFile.getCat();
		}

		playerCatAvatarTexture = playerCat.getPicture();
		opponentCatAvatarTexture = opponentCat.getPicture();
		HatDatabase.markAsSeen(opponentCat.getHat());

		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		whiteFont = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"),
				false);
		blackFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		bg = new Texture("Screens/battlescreen.jpg");
	}

	public HatType getHatArea() {
		// UPPER LEFT
		if ((centerLati < 28.611839 && centerLati > 28.602157)
				&& (centerLongi > -81.207633 && centerLongi < -81.197376))
			return HatType.WATER;
		// UPPER RIGHT
		else if ((centerLati < 28.611839 && centerLati > 28.602157)
				&& (centerLongi > -81.197376 && centerLongi < -81.187334))
			return HatType.FIRE;
		// BOTTOM LEFT
		else if ((centerLati < 28.602157 && centerLati > 28.590476)
				&& (centerLongi > -81.207633 && centerLongi < -81.197376))
			return HatType.GRASS;
		// BOTTOM RIGHT
		else if ((centerLati < 28.602157 && centerLati > 28.590476)
				&& (centerLongi > -81.197376 && centerLongi < -81.187334)) {
			if (centerLati % 2 == 0) {
				return HatType.EARTH;
			}
			return HatType.ELECTRIC;
		}
		// DEFAULT RETURN
		return HatType.DEFAULT;
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
		playerCatAvatarTexture.dispose();
		opponentCatAvatarTexture.dispose();
		bg.dispose();
	}

	private void attackButtonPressed(int attackNumber) {
		attacksCaptureTable.remove();
		messageBoxFirstMessage.setText("");
		stage.addActor(messageFirstTable);

		BattleSystem.sendAttack(playerCat, opponentCat, attackNumber);

		messageBoxFirstMessage.setText(BattleSystem.getSuccessfulAttackMsg(
				playerCat, opponentCat));
		opponentHealth.setText(String.valueOf(opponentCat.getCurrentHP()));

		if (hasBeenDefeated(opponentCat)) {
			messageFirstTable.setTouchable(Touchable.disabled);
			battleOutcomeButton.setText("Congratulations!\nYou Won!");
			stage.addActor(battleOutcomeTable);
			awardedXp = opponentCat.getXPGivenWhenBeat(playerCat);
		}
	}

	private void opponentTurn() {
		messageFirstTable.remove();
		messageBoxSecondMessage.setText("");
		stage.addActor(messageSecondTable);

		BattleSystem.simpleAI(opponentCat, playerCat);

		messageBoxSecondMessage.setText(BattleSystem.getSuccessfulAttackMsg(
				opponentCat, playerCat));
		playerHealth.setText(String.valueOf(playerCat.getCurrentHP()));

		if (hasBeenDefeated(playerCat)) {
			messageSecondTable.setTouchable(Touchable.disabled);
			battleOutcomeButton.setText("Shoot!\nYou lost...");
			stage.addActor(battleOutcomeTable);
			awardedXp = -1;
		}
	}

	private void captureButtonPressed() {
		successfulHatGrab = BattleSystem.attemptCapture(opponentCat);
		messageBoxFirstMessage.setText("");

		if (successfulHatGrab) {
			messageBoxFirstMessage
					.setText(String
							.format("HUZZAH! You grabbed the %s from %s!\nQuickly! Make a run for it!!",
									opponentCat.getHat().getName(),
									opponentCat.getName()));
		} else {
			messageBoxFirstMessage
					.setText(String
							.format("%s was able to keep the hat...\nThis doesn't look good...",
									opponentCat.getName()));
		}

		stage.addActor(messageFirstTable);
	}

	private boolean hasBeenDefeated(Cat cat) {
		return cat.getCurrentHP() == 0;
	}

	private Color colorBasedOnHatType(Hat hat) {
		switch (hat.getType()) {
		case WATER:
			return Color.BLUE;
		case GRASS:
			return Color.GREEN;
		case ELECTRIC:
			return Color.YELLOW;
		case FIRE:
			return Color.RED;
		case EARTH:
			return Color.valueOf("A67B5B");
		default:
			return Color.GRAY;
		}
	}
}
