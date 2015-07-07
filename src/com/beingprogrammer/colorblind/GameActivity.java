package com.beingprogrammer.colorblind;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;
import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity {

	public static  int CAMERA_WIDTH = 400;
	public static  int CAMERA_HEIGHT = 800;
    public static  int fontSize  = 29;
    public static  float iconSize  = 32f;
    public static  float gapSize  = 10f;
    public static float heroWidth = 100f;
    public static float heroHeight = 200f;
	public static boolean isSmallMedia = false;
    @Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		final int dynamicHeight = dm.heightPixels;
	    if(dynamicHeight <= 400){
			  fontSize = 30;
			  iconSize = 40f;
		      gapSize = 15f;
		      heroWidth = 100f;
		      heroHeight = 200f;
		      isSmallMedia = true;
	  }
		
	 	Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		IResolutionPolicy resolutionPolicy = new FillResolutionPolicy();
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, resolutionPolicy, camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		// engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getRenderOptions().getConfigChooserOptions()
				.setRequestedAlphaSize(8);
		engineOptions.getRenderOptions().getConfigChooserOptions()
				.setRequestedRedSize(8);
		// Debug.i("engine configured");
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		// TODO Auto-generated method stub
		ResourceManager.getInstance().create(this, getEngine(),
				getEngine().getCamera(), getVertexBufferObjectManager());
		// ResourceManager.getInstance().loadFont();
		
		ResourceManager.getInstance().loadSplashFont();
		ResourceManager.getInstance().loadSplashGraphics();
		 // ResourceManager.getInstance().loadAudio();
		// ResourceManager.getInstance().loadGameGraphics();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		// TODO Auto-generated method stub
		// Scene scene = new GameScene();
		// scene.getBackground().setColor(Color.PINK);
		pOnCreateSceneCallback.onCreateSceneFinished(null);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		// TODO Auto-generated method stub
		 SceneManager.getInstance().showSplashAndMenuScene();
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
