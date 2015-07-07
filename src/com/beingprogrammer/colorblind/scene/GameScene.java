package com.beingprogrammer.colorblind.scene;

import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier;

import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.beingprogrammer.colorblind.GameActivity;
import com.beingprogrammer.colorblind.ResourceManager;
import com.beingprogrammer.colorblind.SceneManager;

public class GameScene extends AbstractScene implements IUpdateHandler {
	private Text scoreText;
	private PhysicsWorld mPhysicsWorld;
	TiledSprite s;// ,a,b,c;
	private Rectangle hero;
	FixtureDef BoxBodyFixtureDef;
	private float x, y;
	private Rectangle staticRectangle;
	private Rectangle ene;
	private Body enemyBody;
	private Rectangle staticRectangle1;
	private Entity goonsHolder;
	private Rectangle goons;
	int colorCode;
	private Text gameOverText;
	private Text collisionTxt;
	private Text highScoreText;
	private Text highScore;
	private int current_score;
	private int goonsCreateSpeed;
	private float goonsSpeed;
	SharedPreferences data;
	protected Timer timer;
	Body highScoreAnimationBody;
	Color masterColor;
	private int levelCounter = 1;
	private int level = 1;
	private float goonsOrginalSpeed;
	private int goonsOrginalCreateSpeed;
	protected AnimatedSprite smokes;
	Entity popmeup;
	protected Entity smokesEntity;
	private TiledSprite pr;
	private Text colorChangeText;
	private Text sizeAddText;
	private Text sizeSubText;
	Text colorChange;
	private Rectangle sizeAdd, sizeMinus;
	private int colorChangeTag, sizeAddTag, sizeMinusTag;
	private boolean toggler = false;
	Text gameOverHead;
	Text restartThis;
	Text continueThis;
	Text exit;
	Rectangle popup;
	private TiledSprite points;
	private Entity coinIconEntity;
	protected boolean coinIconEntityAnimation;
	private AnimatedSprite coinsIconAnimation;
	private static int pointsCounter;

	public GameScene() {

		popmeup = new Entity();
		coinIconEntity = new Entity();
		attachChild(coinIconEntity);
		pointsCounter = 0;

		mPhysicsWorld = new PhysicsWorld(new Vector2(0,
				-SensorManager.GRAVITY_EARTH * 4), true);
		hero = new Rectangle(200, 10f, GameActivity.heroWidth,
				GameActivity.heroHeight, vbom);
		BoxBodyFixtureDef = PhysicsFactory.createFixtureDef(20f, 0.5f, 0.5f);
		attachChild(hero);
		goonsHolder = new Entity();
		attachChild(goonsHolder);
		masterColor = new Color(204, 102, 255);
		x = 0;
		y = 0;
		// changeAddMinusTags();
		smokesEntity = new Entity();
		attachChild(smokesEntity);
		coinsEntity = new Entity();
		attachChild(coinsEntity);
		hero.setTag(5);
		current_score = 0;
		goonsSpeed = ResourceManager.GOONS_ORGINAL_SPEED;
		goonsOrginalSpeed = ResourceManager.GOONS_ORGINAL_SPEED;
		goonsCreateSpeed = ResourceManager.GOONS_ORGINAL_CREATE_SPEED;
		goonsOrginalCreateSpeed = ResourceManager.GOONS_ORGINAL_CREATE_SPEED;
		if (res.isSoundEnabled == 0) {
			for (Sound x : res.sounds) {
				x.setVolume(30f);
			}
		}
		// res.sounds[4].setLooping(true);
		res.activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						if (!toggler) {
							if (goonsSpeed >= 20) {

								goonsOrginalSpeed++;

								if (goonsOrginalSpeed > 25)
									goonsOrginalSpeed = 9;
								if (goonsOrginalCreateSpeed > 500)
									goonsOrginalCreateSpeed = 1000;

								goonsSpeed = goonsOrginalSpeed;
								goonsOrginalCreateSpeed -= 50;
								goonsCreateSpeed = goonsOrginalCreateSpeed;
								if (levelCounter > 6) {
									int x = getRandom(6);
									level = (x == 0) ? 1 : x;
									levelCounter = 0;
								} else {
									if (level >= 6) {
										level = 1;
									} else {
										level++;
									}

									int r = getRandom(getColors().length - 1);
									hero.setColor(getColors()[r]);
									hero.setTag(r);

								}

							}

							if (goonsSpeed <= 25) {
								goonsSpeed += 1f;
							}
							if (goonsCreateSpeed >= 500) {
								goonsCreateSpeed -= 100;
							}
						}
					}
				}, 0, 4000);

			}
		});
		gameState = true;
	}

	@SuppressWarnings("unused")
	private <T> void sop(T t) {
		// TODO Auto-generated method stub
		System.out.println(t);
	}

	@Override
	public void populate() {
		// TODO Auto-generated method stub
		createBackGround();
		res.getSharedData();
		// sop("Populated ... ");
		createWalls();
		createHUD();

		createEnemy();
		popmeup.setVisible(false);
		attachChild(popmeup);
		// if(res.set.getData("restart") == 1){
		// if(res.points >= 3) pauseGame();
		// res.setData("restart", 0+"");
		// }
		colorChange.setText(res.points + "");
		pr = new TiledSprite(res.camera.getWidth() - 50f,
				res.camera.getHeight() - 50f, res.pr, vbom) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.isActionUp()) {
					if (getCurrentTileIndex() == 0) {
						popmeup.setVisible(true);
						pauseGame();
						setCurrentTileIndex(1);
						toggler = true;
					} else {
						toggler = false;
						popmeup.setVisible(false);
						resumeGame();
						setCurrentTileIndex(0);
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}

		};
		registerTouchArea(pr);
		pr.setCurrentTileIndex(0);
		pr.setWidth(GameActivity.iconSize);
		pr.setHeight(GameActivity.iconSize);

		attachChild(pr);
		if (res.isMusicEnabled == 0) {
			res.gameMusic.seekTo(0);
			res.gameMusic.play();
			res.gameMusic.setVolume(10f);
		}
		registerTouchArea(gameOverText);
		registerUpdateHandler(mPhysicsWorld);
		setText(scoreText, current_score);
		setText(highScore, res.highScore);
		setOnSceneTouchListener(new IOnSceneTouchListener() {

			@Override
			public boolean onSceneTouchEvent(Scene pScene,
					TouchEvent pSceneTouchEvent) {
				// TODO Auto-generated method stub
				x = pSceneTouchEvent.getX();
				y = pSceneTouchEvent.getY();
				if (pSceneTouchEvent.isActionDown()) {
					// if (!gameState)
					// restartGame();

				} else if (pSceneTouchEvent.isActionMove()) {
					if (y <= hero.getHeight() && gameState && !toggler) {

						if (x <= hero.getX() + (hero.getWidth() / 2)
								&& x >= hero.getX() - (hero.getWidth() / 2)) {

							if (x <= hero.getWidth() / 2 + 5) {
								hero.setX(hero.getWidth() / 2);
							} else if (x > res.camera.getWidth()
									- (hero.getWidth() / 2)) {
								hero.setX(res.camera.getWidth()
										- (hero.getWidth() / 2));
							} else {
								hero.setX(x);
							}
							// enemyBody.setLinearVelocity(260f, 10f);
						}
					}

				} else {

				}
				return false;
			}
		});
	}

	private VertexBufferObjectManager getVertexBufferObjectManager() {
		// TODO Auto-generated method stub
		return vbom;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
	}

	private void createBackGround() {
		Entity background = new Entity();
		Sprite bg = new Sprite(0, 0, res.bricks, vbom);
		bg.setWidth(res.camera.getWidth());
		bg.setHeight(res.camera.getHeight());
		bg.setAnchorCenter(0f, 0f);
		background.attachChild(bg);
		setBackground(new EntityBackground(0.82f, 0.96f, 0.97f, background));
	}

	private void createHUD() {
		HUD hud = new HUD();
		float txtX = 5;
		float txtYGap = 15;
		float txtXXGap = 10;
		float th = res.camera.getHeight() - 30f;

		collisionTxt = new Text(txtX, th, res.font, "Matched:",
				new TextOptions(HorizontalAlign.LEFT), vbom);
		collisionTxt.setAnchorCenter(0, 0);
		scoreText = new Text(collisionTxt.getWidth() + txtXXGap
				+ GameActivity.gapSize, th, res.font, "0000", new TextOptions(
				HorizontalAlign.LEFT), vbom);
		scoreText.setAnchorCenter(0, 0);

		highScoreText = new Text(txtX, scoreText.getY() - (txtYGap + 10),
				res.font, "Highscore:", new TextOptions(HorizontalAlign.LEFT),
				vbom);
		highScoreText.setAnchorCenter(0, 0);

		points = new TiledSprite(txtX, (highScoreText.getY())
				- (txtYGap + txtYGap), res.coins, vbom);
		points.setAnchorCenter(0f, 0f);
		points.setCurrentTileIndex(0);

		colorChangeText = new Text(points.getX() + points.getWidth(),
				(highScoreText.getY()) - (txtYGap + txtYGap), res.font,
				"Coins:", new TextOptions(HorizontalAlign.LEFT), vbom);
		colorChangeText.setAnchorCenter(0, 0);

		colorChange = new Text(colorChangeText.getX()
				+ colorChangeText.getWidth() + txtXXGap, (highScoreText.getY())
				- (txtYGap + txtYGap), res.font, "00000", new TextOptions(
				HorizontalAlign.LEFT), vbom);
		colorChange.setAnchorCenter(0f, 0f);

		sizeAddText = new Text(txtX, (colorChangeText.getY())
				- (txtYGap + txtYGap), res.font, "+:", new TextOptions(
				HorizontalAlign.LEFT), vbom);
		sizeAdd = new Rectangle(colorChange.getX(), colorChangeText.getY()
				- (txtYGap + txtYGap), 20f, 20f, vbom);
		sizeAdd.setAnchorCenter(0f, 0f);
		sizeAddText.setAnchorCenter(0, 0);

		sizeSubText = new Text(txtX,
				(sizeAddText.getY()) - (txtYGap + txtYGap), res.font, "-:",
				new TextOptions(HorizontalAlign.LEFT), vbom);
		sizeMinus = new Rectangle(colorChange.getX(), (sizeAddText.getY())
				- (txtYGap + txtYGap), 20f, 20f, vbom);
		sizeMinus.setAnchorCenter(0f, 0f);
		sizeSubText.setAnchorCenter(0, 0);

		highScore = new Text(highScoreText.getWidth() + txtXXGap
				+ GameActivity.gapSize, scoreText.getY() - (txtYGap + 10f),
				res.font, "0000", new TextOptions(HorizontalAlign.LEFT), vbom);
		highScore.setAnchorCenter(0, 0);

		gameOverText = new Text(
				res.camera.getWidth() / 2,
				res.camera.getHeight() / 2,
				res.font,
				"Game Over! Tap To Play Again! abckfjviohfevihrfuvhurvhuhvufghvuhuv",
				new TextOptions(HorizontalAlign.LEFT), vbom);
		gameOverText.setAnchorCenter(0.5f, 0.5f);
		gameOverText.setVisible(false);

		colorChange.setColor(getColors()[colorChangeTag]);
		sizeAdd.setColor(getColors()[sizeAddTag]);
		sizeMinus.setColor(getColors()[sizeMinusTag]);

		attachChild(gameOverText);
		attachChild(collisionTxt);
		attachChild(scoreText);
		attachChild(highScoreText);
		attachChild(highScore);
		attachChild(colorChangeText);
		attachChild(points);
		attachChild(colorChange);

		coinsIconAnimation = new AnimatedSprite(txtX, (highScoreText.getY())
				- (txtYGap + txtYGap), res.coins, vbom);
		coinsIconAnimation.setAnchorCenter(0f, 0f);
		attachChild(coinsIconAnimation);
		coinsIconAnimation.setVisible(false);
		// attachChild(sizeAddText);
		// attachChild(sizeAdd);
		// attachChild(sizeSubText);
		// attachChild(sizeMinus);
		camera.setHUD(hud);
	}

	private void createGoons() {

		float dimension = getRandom(120);
		colorCode = getRandom(getColors().length);
		int xAxis = getRandom(((int) res.camera.getWidth() - 100));
		goons = new Rectangle(xAxis, res.camera.getHeight() + xAxis,
				(dimension < 50 ? 50 : dimension), (dimension < 50 ? 50
						: dimension), vbom) {

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				super.onManagedUpdate(pSecondsElapsed);
				// scoreText.setText("hero X: " + hero.getX() + ": HeroY : " +
				// hero.getY() + ": thisX: " + this.getX() +
				// ":+thisY: "+this.getY());
				setY(getY() - goonsSpeed);
				if (this.getY() < hero.getY() + (hero.getHeight() / 2)
						&& (this.getX() + this.getWidth()) > hero.getX()
								- (hero.getWidth() / 2)
						&& this.getX() < (hero.getX() + (hero.getWidth() / 2))) {
					// Collision with hero

					if (hero.getTag() == this.getTag()) {
						int heroTag = getRandom(getColors().length);
						// hero.setColor();
						hero.setTag(heroTag);
						highScoreSave();

						current_score++;
						if (pointsCounter >= 3) {
							coinsAnimation(this, res.coins);
							res.points++;
							points.setVisible(false);
							coinsIconAnimation.setColor(getColors()[this
									.getTag()]);
							coinsIconAnimation.setVisible(true);
							IAnimationListener ial = new IAnimationListener() {

								@Override
								public void onAnimationStarted(
										AnimatedSprite pAnimatedSprite,
										int pInitialLoopCount) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationLoopFinished(
										AnimatedSprite pAnimatedSprite,
										int pRemainingLoopCount,
										int pInitialLoopCount) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationFrameChanged(
										AnimatedSprite pAnimatedSprite,
										int pOldFrameIndex, int pNewFrameIndex) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationFinished(
										AnimatedSprite pAnimatedSprite) {
									// TODO Auto-generated method stub
									points.setVisible(true);
									coinsIconAnimation.setVisible(false);
								}
							};
							coinsIconAnimation.animate(60l, false, ial);

							colorChange.setText(res.points + "");
							pointsCounter = 0;
							res.setData("points", res.points + "");
							if (res.isSoundEnabled == 0)
								res.sounds[1].play();

						} else {
							pointsCounter++;

							coinsAnimation(this, res.sparkle);
							if (res.isSoundEnabled == 0)
								res.sounds[8].play();

						}
						changeSts(scoreText, Integer.toString(current_score));
						// setText(scoreText, current_score);
						ColorModifier cm = new ColorModifier(0.5f,
								hero.getColor(), getColors()[heroTag]);
						hero.registerEntityModifier(cm);
						removeGoons(this);

					} else {
						// if (this.getTag() == colorChangeTag) {
						// int heroTag = getRandom(getColors().length);
						// hero.setTag(heroTag);
						// ColorModifier cm = new ColorModifier(0.5f,
						// hero.getColor(), getColors()[heroTag]);
						// hero.registerEntityModifier(cm);
						// this.setIgnoreUpdate(true);
						// removeGoons(this);
						// } else if (this.getTag() == sizeAddTag) {
						// if (hero.getWidth() <= res.camera.getWidth() / 2) {
						// hero.setWidth(hero.getWidth() + 5f);
						//
						// }
						// this.setIgnoreUpdate(true);
						// removeGoons(this);
						// } else if (this.getTag() == sizeMinusTag) {
						// if (hero.getWidth() >= 50f) {
						// hero.setWidth(hero.getWidth() - 5f);
						// }
						// this.setIgnoreUpdate(true);
						// removeGoons(this);
						// } else {
						this.setIgnoreUpdate(true);
						removeGoons(this);
						gameState = false;
						// }
					}

				} else if (this.getY() < staticRectangle.getY()) {
					// Collision with bottom layer
					if (this.getTag() == hero.getTag()) {
						// Game Over
						gameState = false;
					}
					this.setIgnoreUpdate(true);
					removeGoons(this);
					smokesAnimation(this);
				}
			}
		};
		goons.setZIndex(0);
		goons.setTag(colorCode);
		goons.setAnchorCenter(0f, 0f);
		goons.setColor(getColors()[colorCode]);
		if (res.isSoundEnabled == 0)
			res.sounds[2].play();
		// sop("goons created");
		goonsHolder.attachChild(goons);

	}

	protected <T extends IEntity> void removeGoons(final T e) {
		// TODO Auto-generated method stub
		res.activity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// goonsHolder.detachChild(tag);
				e.detachSelf();
			}
		});
	}

	private void createWalls() {
		staticRectangle = new Rectangle(0f, 0f, 800f, 1f,
				this.getVertexBufferObjectManager());
		Rectangle staticRectangle0 = new Rectangle(0f, 0f, 800f, 1f,
				this.getVertexBufferObjectManager());
		staticRectangle1 = new Rectangle(0f, 800f, 800f, 1f,
				this.getVertexBufferObjectManager());
		Rectangle staticRectangle2 = new Rectangle(0f, 800f, 1f, 1800f,
				this.getVertexBufferObjectManager());
		Rectangle staticRectangle3 = new Rectangle(400f, 0f, 1f, 1800f,
				this.getVertexBufferObjectManager());
		staticRectangle.setColor(Color.TRANSPARENT);
		staticRectangle0.setColor(Color.TRANSPARENT);
		staticRectangle1.setColor(Color.TRANSPARENT);
		staticRectangle2.setColor(Color.TRANSPARENT);
		staticRectangle3.setColor(Color.TRANSPARENT);

		attachChild(staticRectangle0);
		attachChild(staticRectangle);
		PhysicsFactory.createBoxBody(mPhysicsWorld, staticRectangle0,
				BodyType.StaticBody, BoxBodyFixtureDef);

		attachChild(staticRectangle1);
		PhysicsFactory.createBoxBody(mPhysicsWorld, staticRectangle1,
				BodyType.StaticBody, BoxBodyFixtureDef);

		attachChild(staticRectangle2);
		PhysicsFactory.createBoxBody(mPhysicsWorld, staticRectangle2,
				BodyType.StaticBody, BoxBodyFixtureDef);

		attachChild(staticRectangle3);
		PhysicsFactory.createBoxBody(mPhysicsWorld, staticRectangle3,
				BodyType.StaticBody, BoxBodyFixtureDef);

	}

	private void createEnemy() {
		ene = new Rectangle(res.camera.getWidth(), 0, 140f, 140f, vbom);
		ene.setColor(Color.BLACK);
		ene.setAnchorCenter(0f, 0f);
		enemyBody = PhysicsFactory.createBoxBody(mPhysicsWorld, ene,
				BodyType.DynamicBody, BoxBodyFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(ene,
				enemyBody));
		attachChild(ene);

	}

	int time = 0;
	private boolean gameState;

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		super.onManagedUpdate(pSecondsElapsed);
		if (time >= goonsCreateSpeed) {
			createGoons();
			time = 0;
		} else {
			time += 10;
		}

		gameOver();
	}

	private void highScoreSave() {
		if (res.highScore <= current_score) {
			res.highScore++;
			highScoreAnimation();
			changeSts(highScore, Integer.toString(res.highScore));
			res.activity.runOnUiThread(new Runnable() {
				public void run() {
					res.setData("highscore", res.highScore + "");
				}
			});

		}
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		if (gameState) {
			if (toggler == true) {
				popmeup.setVisible(false);
				toggler = false;
				resumeGame();
				pr.setCurrentTileIndex(0);
			} else {
				popmeup.setVisible(true);
				toggler = true;
				pr.setCurrentTileIndex(1);
				popup(1);
				pauseGame();
			}
		}
	}

	private int getRandom(int range) {
		Random r = new Random();
		int queCode = r.nextInt(range);
		return queCode;
	}

	private void setText(Text t, int val) {
		try {
			String x = Integer.toString(val);
			t.setText(x);
		} catch (Exception e) {
		}
	}

	private void gameOver() {
		if (!gameState) {
			// if(isHighScore){
			// highScoreAnimation();
			// }
			saveDatas(current_score, res.highScore, (int) goonsSpeed,
					goonsCreateSpeed);
			pauseGame();

		}
	}

	int gameOverAnimationTimer = 0;
	Text highScoreAnimation;
	int c = 0;
	private AnimatedSprite coins;
	private Entity coinsEntity;
	private boolean gameInStart = true;

	private void highScoreAnimation() {

		highScoreAnimation = new Text(res.camera.getWidth() / 2,
				res.camera.getHeight() - 100, res.font2, res.highScore + "",
				new TextOptions(HorizontalAlign.LEFT), vbom) {

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				super.onManagedUpdate(pSecondsElapsed);
				if (c == 2000) {
					removeGoons(this);
					mPhysicsWorld
							.unregisterPhysicsConnector(new PhysicsConnector(
									highScoreAnimation, highScoreAnimationBody));
					c = 0;
				} else {
					c += 10;
				}
			}

		};
		if (res.highScore != 1) {
			highScoreAnimation.detachChild(res.highScore - 1);
		}
		highScoreAnimation.setAnchorCenter(0.5f, 0.5f);
		highScoreAnimation.setColor(getColors()[getRandom(getColors().length)]);
		highScoreAnimation.setTag(res.highScore);
		highScoreAnimationBody = PhysicsFactory.createBoxBody(mPhysicsWorld,
				highScoreAnimation, BodyType.DynamicBody, BoxBodyFixtureDef);
		highScoreAnimationBody
				.setAngularVelocity(getRandom(getColors().length));
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				highScoreAnimation, highScoreAnimationBody));
		attachChild(highScoreAnimation);
		registerUpdateHandler(highScoreAnimation);
	}

	private void restartGame() {

		this.setIgnoreUpdate(true);
		this.setChildrenIgnoreUpdate(true);
		this.detachChildren();
		unregisterTouchArea(hero);
		unregisterTouchArea(this);
		timer.cancel();
		if (res.gameMusic.isPlaying()) {
			res.gameMusic.pause();
		}

		try {
			for (Sound x : res.sounds) {
				x.stop();
			}
		} catch (Exception e) {

		}
		mPhysicsWorld.clearPhysicsConnectors();
		Iterator<Body> it = mPhysicsWorld.getBodies();
		while (it.hasNext()) {
			mPhysicsWorld.destroyBody(it.next());
		}
		mPhysicsWorld.clearForces();
		this.clearUpdateHandlers();
		this.unregisterUpdateHandler(this);
		this.unregisterUpdateHandler(goons);
		this.unregisterUpdateHandler(hero);
		this.destroy();
	}

	private Color[] getColors() {
		Color[] c = null;
		switch (level) {

		case 1:
			c = Colors.simple();
			break;
		case 2:
			c = Colors.more();
			break;
		case 3:
			c = Colors.confusion();
			break;
		default:
			c = Colors.simple();
			break;
		}
		return c;
	}

	private <T extends Entity> void smokesAnimation(T r) {
		if (smokesEntity.getChildCount() == 1)
			removeGoons(smokesEntity.getChildByTag(0));
		smokes = new AnimatedSprite(r.getX() + r.getWidth() / 2, r.getY()
				+ r.getHeight() / 2 + 5f, res.smokes, vbom);
		smokes.animate(40l, false);
		smokes.setTag(0);
		smokesEntity.attachChild(smokes);
		//

	}

	private <T extends Entity> void coinsAnimation(T r, ITiledTextureRegion itr) {
		if (coinsEntity.getChildCount() == 1)
			removeGoons(coinsEntity.getChildByTag(0));
		coins = new AnimatedSprite(r.getX() + r.getWidth() / 2, r.getY()
				+ r.getHeight() / 2 + 5f, itr, vbom);
		coins.animate(60l, false);
		coins.setTag(0);
		coins.setSize(coins.getWidth() + 20f, coins.getHeight() + 20f);
		coinsEntity.attachChild(coins);
		//

	}

	private void changeSts(final Text t, final String num) {
		IEntityModifierListener iem = new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,
					IEntity pItem) {
				// TODO Auto-generated method stub

				t.setText(num);
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				// TODO Auto-generated method stub
			}
		};
		RotationModifier rm = new RotationModifier(0.5f, 0.0f, 360f, iem);
		t.registerEntityModifier(rm);
	}

	private void pauseGame() {
		if (!gameState) {
			unregisterTouchArea(pr);
		}
		if (res.isMusicEnabled == 0) {
			if (res.gameMusic.isPlaying()) {
				res.gameMusic.pause();
			}
		}

		this.setIgnoreUpdate(true);

		if (!gameState) {

			popup(0);
			if (res.isSoundEnabled == 0)
				res.sounds[0].play();

		} else {
			if (gameInStart)
				popup(2);
			else
				popup(1);
			if (res.isSoundEnabled == 0)
				res.sounds[7].play();
		}
		toggler = true;
	}

	private void resumeGame() {
		if (res.isMusicEnabled == 0) {
			res.gameMusic.play();

		}
		if (!gameState) {
			registerTouchArea(pr);
		}
		toggler = false;
		gameState = true;
		popmeup.setVisible(false);
		this.setIgnoreUpdate(false);

		pr.setCurrentTileIndex(0);
	}

	private void popup(final int q) {
		toggler = true;
		popmeup.setVisible(true);
		if (popmeup.getChildCount() > 0) {
			for (int i = 0; i < popmeup.getChildCount(); i++) {
				removeGoons(popmeup.getChildByIndex(i));
			}
		}
		popup = new Rectangle(res.camera.getWidth() / 2,
				res.camera.getHeight() / 2, res.camera.getWidth(), 300f, vbom);

		popup.setColor(Color.BLACK);
		popup.setAlpha(0.8f);
		ScaleModifier sm = new ScaleModifier(1f, 0f, 1f);
		popmeup.attachChild(popup);
		registerUpdateHandler(popup);
		popup.registerEntityModifier(sm);
		String msg = null;
		switch (q) {
		case 0:
			msg = "Game Over";
			break;
		case 1:
			msg = "Paused";
			break;
		case 2:
			msg = "Start Game";
			break;
		}
		gameOverHead = new Text(popup.getX(), popup.getY()
				+ (popup.getHeight() / 2) - 20f, res.info, msg, vbom);

		restartThis = new Text(popup.getX(), popup.getY()
				+ gameOverHead.getHeight() + 10f, res.info, "Restart", vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub

				if (pSceneTouchEvent.isActionUp()) {
					res.setData("restart", 1 + "");
					res.goonsSpeed = ResourceManager.GOONS_ORGINAL_SPEED;
					res.goonsCreateSpeed = ResourceManager.GOONS_ORGINAL_CREATE_SPEED;
					res.currentScore = 0;
					restartGame();
					saveDatas(0, res.highScore,
							ResourceManager.GOONS_ORGINAL_SPEED,
							ResourceManager.GOONS_ORGINAL_CREATE_SPEED);
					SceneManager.getInstance().showGameScene();
				} else if (pSceneTouchEvent.isActionDown()) {
					this.setColor(Color.CYAN);
				} else {
					this.setColor(Color.WHITE);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		exit = new Text(popup.getX(), popup.getY() - (popup.getHeight() / 2)
				+ 20f, res.info, "Quit", vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.isActionUp()) {

					timer.cancel();
					if (res.isMusicEnabled == 0) {
						if (res.gameMusic.isPlaying()) {
							res.gameMusic.pause();
						}
					}
					restartGame();
					saveDatas(current_score, res.highScore, goonsSpeed,
							goonsCreateSpeed);
					SceneManager.getInstance().showMenuScene();
				} else if (pSceneTouchEvent.isActionDown()) {
					this.setColor(Color.CYAN);
				} else {
					this.setColor(Color.WHITE);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		continueThis = new Text(popup.getX(), restartThis.getY()
				- (restartThis.getHeight() / 2 + 30f), res.info, "Continue",
				vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.isActionUp()) {
					//

					if (q == 1) {
						if (gameState) {
							toggler = false;
							popmeup.setVisible(false);
							resumeGame();
						}
						// sop("Game is resumed");
					} else {
						if (!gameState) {
							if (res.points >= 3) {
								res.points -= 3;
								res.setData("points", res.points + "");
								colorChange.setText(res.points + "");
								resumeGame();
							} else {
								// sop("Not have money");
								res.activity.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										// sop("not enough money");
										// TODO Auto-generated method stub
										Toast.makeText(
												res.activity,
												"You must have more then 3 coins to continue game",
												Toast.LENGTH_SHORT).show();
									}
								});
							}
						}
					}
				} else if (pSceneTouchEvent.isActionDown()) {
					this.setColor(Color.CYAN);
				} else {
					this.setColor(Color.WHITE);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};

		popmeup.attachChild(exit);
		exit.setTag(0);
		registerTouchArea(exit);

		popmeup.attachChild(gameOverHead);
		popmeup.attachChild(restartThis);
		popmeup.attachChild(continueThis);
		popup.setTag(0);
		gameOverHead.setTag(0);
		restartThis.setTag(0);
		continueThis.setTag(0);

		gameOverHead.registerEntityModifier(sm);
		registerUpdateHandler(gameOverHead);
		restartThis.registerEntityModifier(sm);
		registerUpdateHandler(restartThis);
		continueThis.registerEntityModifier(sm);
		registerUpdateHandler(continueThis);
		registerUpdateHandler(exit);
		registerTouchArea(continueThis);
		registerTouchArea(restartThis);

	}

	private void saveDatas(final int curren_score, final int high_score,
			final float goons_speed, final int goonscs) {
		res.activity.runOnUiThread(new Runnable() {
			public void run() {
				res.setData("current_score", curren_score + "");
				res.set.setFloat("goons_speed", goons_speed + "");
				res.setData("goons_create_speed", goonscs + "");
				res.setData("high_score", res.highScore + "");
				res.setData("points", res.points + "");
			}
		});
	}
}
