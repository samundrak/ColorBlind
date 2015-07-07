package com.beingprogrammer.colorblind.scene;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.beingprogrammer.colorblind.GameActivity;
import com.beingprogrammer.colorblind.SceneManager;

public class MenuSceneWrapper extends AbstractScene implements
		IOnMenuItemClickListener {
	private IMenuItem playMenuItem, highScore;
	Text highScoreTxt;
	private Rectangle box1, box2;
	PhysicsWorld pw;
	Color[] color;
	MenuScene menuScene;
	Entity e;
	Body bottom, middle, playBody, optionsBody, highScoreBody, soundsBody,
			soundsOfBody, musicsBody, musicsOfBody, highScoreTxtBody;
	Timer timer;
	FixtureDef fd;
	private Entity background;
	private boolean isHighScoreShown = false;
	private Entity popup;
	protected boolean isAboutShown = false;
	String infoTxt, devTxt;
	protected boolean isDevTxtShown = false;

	public MenuSceneWrapper() {
		pw = new PhysicsWorld(new Vector2(0, -SensorManager.GRAVITY_EARTH * 4),
				true);
		fd = PhysicsFactory.createFixtureDef(1f, 0.5f, 1f);
		infoTxt =  "Color Blind game is Played by matching color with the falling objects. Player have to collide with the same color they have and color that are falling. For every 3 matched color you will get 1 coin and to continue the game from same stage of game over 3 coin is deducted. ";
		devTxt = "This Game is Developed by Samundra Kc. Contact developer at samundrak@yahoo.com";
				 
		box1 = new Rectangle(0, res.camera.getHeight() / 2,
				res.camera.getWidth() + 100, 10f, vbom);
		box2 = new Rectangle(0, 0, res.camera.getWidth() + 100, 10f, vbom);
		box1.setAnchorCenter(0f, 0f);
		box2.setAnchorCenter(0f, 0f);
		box1.setColor(Color.TRANSPARENT);
		box2.setColor(Color.TRANSPARENT);
		PhysicsFactory.createBoxBody(pw, box2, BodyType.StaticBody, fd);
		PhysicsFactory.createBoxBody(pw, box1, BodyType.StaticBody, fd);
		Color[] c = { Color.RED, Color.WHITE, Color.YELLOW, Color.BLUE,
				Color.PINK, Color.CYAN };
		color = c;
		e = new Entity();

		menuScene = new MenuScene(camera);

		res.activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {

						// TODO Auto-generated method stub
						Rectangle r = new Rectangle(getRandom((int) res.camera
								.getWidth() - 100),
								res.camera.getHeight() + 100, 50, 50, vbom) {

							@Override
							protected void onManagedUpdate(float pSecondsElapsed) {
								// TODO Auto-generated method stub
								super.onManagedUpdate(pSecondsElapsed);
								setY(getY() - 5);
								if (this.getY() <= 0) {
									removeIt(this);
								}
							}

						};
						e.attachChild(r);
						r.setZIndex(0);
						r.setColor(color[getRandom(color.length)]);
					}
				}, 0, 1500);
			}
		});

		c = null;
	}

	public <T extends Entity> void removeIt(final T r) {
		res.activity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				r.detachSelf();
			}
		});
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {

		// TODO Auto-generated method stub
		switch (pMenuItem.getID()) {
		case 0:
			if (res.isSoundEnabled == 0) {
				res.sounds[3].play();
			}
			timer.cancel();
			this.setIgnoreUpdate(true);
			e.setIgnoreUpdate(true);
			if (res.isMusicEnabled == 0) {
				res.menuMusic.pause();
			}

			if (isHighScoreShown) {
				highScoreTxt.dispose();
				isHighScoreShown = false;
			}
			SceneManager.getInstance().showGameScene();
			return true;
		case 2:
			if (!isHighScoreShown) {
				if (res.isSoundEnabled == 0)
					res.sounds[1].play();
				highScoreTxt = new Text(res.camera.getWidth() / 2, 300,
						res.font2, Integer.toString(res.highScore), vbom);
				registerTouchArea(highScoreTxt);
				highScoreTxtBody = PhysicsFactory.createBoxBody(pw,
						highScoreTxt, BodyType.DynamicBody, fd);
				highScoreTxt.setY(box1.getY() + box1.getHeight() + 60f);
				pw.registerPhysicsConnector(new PhysicsConnector(highScoreTxt,
						highScoreTxtBody));
				background.attachChild(highScoreTxt);
				isHighScoreShown = true;
			}
			return true;
			// break;
		default:
			return false;
		}
	}

	@Override
	public void onBackKeyPressed() {
		res.activity.finish();
	}

	@Override
	public void populate() {
		// TODO Auto-generated method stub
		if (res.isMusicEnabled == 0) {

			res.menuMusic.play();
		}
		registerUpdateHandler(pw);
		background = new Entity();
		Sprite bg = new Sprite(0, 0, res.bricks, vbom);
		bg.setWidth(res.camera.getWidth());
		bg.setHeight(res.camera.getHeight());
		bg.setAnchorCenter(0f, 0f);
		background.attachChild(bg);
		background.attachChild(e);
		background.attachChild(box1);
		background.attachChild(box2);
		addSoundIcons(background);
		menuScene.attachChild(background);
		menuScene.getBackground().setColor(Color.WHITE);
		playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0, res.font,
				"Play", vbom), Color.CYAN, Color.WHITE);
		playMenuItem.setY(res.camera.getHeight() - 100);
		playMenuItem.setVisible(false);
		playMenuItem.setX(res.camera.getWidth() / 2 - 50f);
		playBody = PhysicsFactory.createBoxBody(pw, playMenuItem,
				BodyType.DynamicBody, fd);
		pw.registerPhysicsConnector(new PhysicsConnector(playMenuItem, playBody));

		highScore = new ColorMenuItemDecorator(new TextMenuItem(2, res.font,
				"High Score", vbom), Color.CYAN, Color.WHITE);
		highScore.setVisible(false);
		highScore.setY(res.camera.getHeight() - 100);
		highScore.setX(playMenuItem.getX() + 100f + GameActivity.gapSize);
		highScoreBody = PhysicsFactory.createBoxBody(pw, highScore,
				BodyType.DynamicBody, fd);
		pw.registerPhysicsConnector(new PhysicsConnector(highScore,
				highScoreBody));

		playMenuItem.setZIndex(1000);
		menuScene.addMenuItem(playMenuItem);
		menuScene.addMenuItem(highScore);
		popup = new Entity();
		menuScene.attachChild(popup);

		playMenuItem.setVisible(true);
		highScore.setVisible(true);
		// menuScene.addMenuItem(highScoreTxt);

		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(true);
		menuScene.setOnMenuItemClickListener(this);

		setChildScene(menuScene);
	}

	private int getRandom(int range) {
		Random rn = new Random();
		int rnn = rn.nextInt(range);
		return rnn;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

	Sprite about;
	Sprite developer;

	private void addSoundIcons(final Entity e) {
		TiledSprite sound = new TiledSprite(res.musics.getWidth() / 2,
				res.camera.getHeight() - 50f, res.musics, vbom) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.isActionUp()) {
					IEntityModifierListener iem = new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							if (res.isSoundEnabled == 0)
								res.sounds[7].play();

						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							if (res.isSoundEnabled == 0) {
								res.isSoundEnabled = 1;
								setCurrentTileIndex(0);
								res.setData("sound", "1");

							} else {
								res.isSoundEnabled = 0;
								setCurrentTileIndex(1);
								res.setData("sound", "0");

							}
						}
					};
					RotationModifier rm = new RotationModifier(0.2f, 0.0f,
							360f, iem);
					registerEntityModifier(rm);

				}
				return true;
			}

		};
		if (res.isSoundEnabled == 0)
			sound.setCurrentTileIndex(1);
		else
			sound.setCurrentTileIndex(0);

		sound.setWidth(GameActivity.iconSize);
		sound.setHeight(GameActivity.iconSize);
		e.attachChild(sound);
		registerTouchArea(sound);

		TiledSprite musics = new TiledSprite(sound.getX()
				+ res.musics.getWidth() / 2 + 10f + GameActivity.gapSize,
				res.camera.getHeight() - 50f, res.musics, vbom) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.isActionUp()) {
					IEntityModifierListener iem = new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							if (res.isSoundEnabled == 0)
								res.sounds[7].play();

						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							if (res.isMusicEnabled == 0) {
								setCurrentTileIndex(3);
								res.isMusicEnabled = 1;
								res.setData("music", "1");
								res.menuMusic.pause();
								// res.menuMusic.release();
							} else {
								setCurrentTileIndex(2);
								res.menuMusic.play();
								res.isMusicEnabled = 0;
								res.setData("music", "0");

							}
						}
					};
					RotationModifier rm = new RotationModifier(0.2f, 0.0f,
							360f, iem);
					registerEntityModifier(rm);

				}
				return true;
			}

		};
		if (res.isMusicEnabled == 0)
			musics.setCurrentTileIndex(2);
		else
			musics.setCurrentTileIndex(3);
		musics.setWidth(GameActivity.iconSize);
		musics.setHeight(GameActivity.iconSize);
		registerTouchArea(musics);

		e.attachChild(musics);

		about = new Sprite(musics.getX() + (res.musics.getWidth() / 2)
				 + 5f, res.camera.getHeight() - 50f,
				res.about, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.isActionUp()) {
					if (!isDevTxtShown) {
						if(res.isSoundEnabled == 0) res.sounds[7].play();
						RotationModifier rm = new RotationModifier(0.2f, 0f,
								360f);
						registerEntityModifier(rm);
						if (!isAboutShown) {
							popUp("show", popup, infoTxt);
							isAboutShown = true;
							this.setColor(Color.RED);
						} else {
							popUp("hide", popup, devTxt);
							isAboutShown = false;
							this.setColor(Color.WHITE);
						}
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		developer = new Sprite(about.getX() + (about.getWidth() / 2)
				+ 5f, res.camera.getHeight() - 50f,
				res.developer, vbom) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.isActionUp()) {
					if (!isAboutShown) {
						if(res.isSoundEnabled == 0) res.sounds[7].play();
						RotationModifier rm = new RotationModifier(0.2f, 0f,
								360f);
						 registerEntityModifier(rm);
						if (!isDevTxtShown) {
							popUp("show", popup, devTxt);
							isDevTxtShown = true;
							this.setColor(Color.RED);
						} else {
							popUp("hide", popup, devTxt);
							isDevTxtShown = false;
							this.setColor(Color.WHITE);
						}
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		about.setSize(GameActivity.iconSize, GameActivity.iconSize);
		developer.setSize(GameActivity.iconSize, GameActivity.iconSize);
		registerTouchArea(about);
		registerTouchArea(developer);
		e.attachChild(about);
		e.attachChild(developer);
	}

	Rectangle popUpBox;
	Text info;

	private void popUp(String s, Entity e, String txt) {
		ScaleModifier sm;
		if (s.equals("hide")) {
			sm = new ScaleModifier(0.25f, 1f, 0f,
					new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							removeIt(popUpBox);
							removeIt(info);
						}
					});
			popUpBox.registerEntityModifier(sm);
			info.registerEntityModifier(sm);
			registerUpdateHandler(popUpBox);
			registerUpdateHandler(info);
		} else {
			info = new Text(res.camera.getWidth() / 2,
					res.camera.getHeight() / 2, res.font, txt, vbom);
			info.setAutoWrap(AutoWrap.WORDS);
			info.setAutoWrapWidth(350);
			popUpBox = new Rectangle(res.camera.getWidth() / 2,
					res.camera.getHeight() / 2, res.camera.getWidth(),
					info.getHeight() + 100f, vbom);

			popUpBox.setColor(Color.BLACK);
			popUpBox.setAlpha(0.8f);
			sm = new ScaleModifier(0.4f, 0f, 1f);
			e.attachChild(popUpBox);
			e.attachChild(info);
			popUpBox.registerEntityModifier(sm);
			info.registerEntityModifier(sm);
			registerUpdateHandler(popUpBox);
			registerUpdateHandler(info);
			// Text t = new Text(pX, pY, pFont, pText,
			// pVertexBufferObjectManager)
		}

	}
}
