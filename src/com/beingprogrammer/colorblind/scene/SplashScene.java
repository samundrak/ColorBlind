package com.beingprogrammer.colorblind.scene;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier;

import android.graphics.Color;

import com.beingprogrammer.colorblind.SceneManager;

public class SplashScene extends AbstractScene {
	Text being, programmer;
	Sprite bg;

	@Override
	public void populate() {

		// TODO Auto-generated method stub
		final Entity background = new Entity();
		bg = new Sprite(0, 0, res.splashTextureRegion, vbom) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if (pSceneTouchEvent.isActionUp()) {
					SceneManager.getInstance().showMenuScene();
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}

		};
		bg.setWidth(200f);
		bg.setHeight(300f);
		bg.setAnchorCenter(0.5f, 0.5f);
		bg.setPosition(res.camera.getWidth() / 2, res.camera.getHeight() / 2);
		being = new Text(res.camera.getWidth() / 2, bg.getY()
				- (bg.getHeight() / 2 + 5f), res.font2, "Being", vbom);
		programmer = new Text(res.camera.getWidth() / 2, being.getY()
				- (being.getHeight() / 2 + 7f), res.font2, "Programmer's", vbom);
		final Sprite s = new Sprite(bg.getX(), bg.getY(), res.logo, vbom);
		s.setWidth(bg.getWidth());
		s.setHeight(bg.getHeight());
		s.setAlpha(0f);
		s.setVisible(false);

		being.setColor(Color.BLACK);
		programmer.setColor(Color.BLACK);
		registerUpdateHandler(bg);
		registerUpdateHandler(s);
		registerUpdateHandler(being);
		registerUpdateHandler(programmer);
		background.attachChild(bg);
		background.attachChild(being);
		background.attachChild(programmer);
		background.attachChild(s);
		bg.setAlpha(0f);
		being.setAlpha(0f);
		programmer.setAlpha(0f);
		IEntityModifierListener iem = new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,
					IEntity pItem) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				// TODO Auto-generated method stub
				DelayModifier dm = new DelayModifier(0.1f,
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
								IEntityModifierListener iem = new IEntityModifierListener() {

									@Override
									public void onModifierStarted(
											IModifier<IEntity> pModifier,
											IEntity pItem) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onModifierFinished(
											IModifier<IEntity> pModifier,
											IEntity pItem) {
										// TODO Auto-generated method stub
										s.setVisible(true);
										AlphaModifier am = new AlphaModifier(
												1f, 0f, 1f,
												new IEntityModifierListener() {

													@Override
													public void onModifierStarted(
															IModifier<IEntity> pModifier,
															IEntity pItem) {
														// TODO Auto-generated
														// method stub

													}

													@Override
													public void onModifierFinished(
															IModifier<IEntity> pModifier,
															IEntity pItem) {
														// TODO Auto-generated
														// method stub
														AlphaModifier am = new AlphaModifier(
																1.5f, 1f, 0f);
														s.registerEntityModifier(am);
													}
												});
										s.registerEntityModifier(am);
									}
								};
								AlphaModifier am = new AlphaModifier(1f, 1f,
										0f, iem);
								bg.registerEntityModifier(am);
								AlphaModifier am1 = new AlphaModifier(1f, 1f,
										0f);
								being.registerEntityModifier(am1);
								AlphaModifier am2 = new AlphaModifier(1f, 1f,
										0f);
								programmer.registerEntityModifier(am2);
							}
						});
				bg.registerEntityModifier(dm);
			}
		};
		AlphaModifier am = new AlphaModifier(2f, 0f, 1f, iem);
		bg.registerEntityModifier(am);
		AlphaModifier am1 = new AlphaModifier(2f, 0f, 1f);
		being.registerEntityModifier(am1);
		AlphaModifier am2 = new AlphaModifier(2f, 0f, 1f);
		programmer.registerEntityModifier(am2);
		setBackground(new EntityBackground(255 / 255f, 255 / 255f, 255 / 255f,
				background));
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

}
