package com.beingprogrammer.colorblind;

import org.andengine.audio.sound.Sound;
import org.andengine.util.debug.Debug;

import android.os.AsyncTask;

import com.beingprogrammer.colorblind.scene.AbstractScene;
import com.beingprogrammer.colorblind.scene.GameScene;
import com.beingprogrammer.colorblind.scene.LoadingScene;
import com.beingprogrammer.colorblind.scene.MenuSceneWrapper;
import com.beingprogrammer.colorblind.scene.SplashScene;

public class SceneManager {

	// single instance is created only
	private static final SceneManager INSTANCE = new SceneManager();
	public static final long SPLASH_DURATION = 4500;
	private ResourceManager res = ResourceManager.getInstance();
	private AbstractScene currentScene;
	private LoadingScene loadingScene = null;

	private SceneManager() {
	}

	public static SceneManager getInstance() {
		return INSTANCE;
	}

	public AbstractScene getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(AbstractScene currentScene) {
		this.currentScene = currentScene;
		res.engine.setScene(currentScene);
	//	Debug.i("Current scene: " + currentScene.getClass().getName());
	}
	AbstractScene nextScene;
	 boolean b = false;
	public AbstractScene showSplashAndMenuScene() {
		final SplashScene splashScene = new SplashScene();
		splashScene.populate();
		setCurrentScene(splashScene);
		nextScene = new MenuSceneWrapper();
		if(GameActivity.fontSize <= 35){
		Thread t = new Thread(){
			public void run(){
				try{
				res.loadFont();
				res.loadGameAudio();
				res.loadGameGraphics();
				loadingScene = new LoadingScene();
				loadingScene.populate();
					sleep(4500);
				}
				catch(InterruptedException e){}
				finally{
					nextScene.populate();
				setCurrentScene(nextScene);
				splashScene.destroy();
				res.loadSplashGraphics();
					//validation();
				}
			}
		};
		t.start();
		}else{
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				long timestamp = System.currentTimeMillis();
				
				res.loadFont();
				res.loadGameAudio();
				res.loadGameGraphics();
				loadingScene = new LoadingScene();
				loadingScene.populate();
				
			
				
				if (System.currentTimeMillis() - timestamp < SPLASH_DURATION) {
					try {
						Thread.sleep(SPLASH_DURATION
								- (System.currentTimeMillis() - timestamp));
					} catch (InterruptedException e) {
						Debug.e("Interrupted", e);
					}
				}
				nextScene.populate();
				setCurrentScene(nextScene);
				splashScene.destroy();
				res.loadSplashGraphics();
				return null;
			}
		}.execute();
	}
		return splashScene;
	}

	public void showGameScene() {
		final AbstractScene previousScene = getCurrentScene();
		setCurrentScene(loadingScene);
	 if(GameActivity.fontSize <= 35){
		 GameScene gameScene = new GameScene();
			gameScene.populate();
			setCurrentScene(gameScene);
			previousScene.destroy();
	 } else{
	 
			new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				GameScene gameScene = new GameScene();
				gameScene.populate();
				setCurrentScene(gameScene);
				previousScene.destroy();
				return null;
			 }
	 	}.execute();
	}
	 }

	public void showMenuScene() {
		final AbstractScene previousScene = getCurrentScene();
		setCurrentScene(loadingScene);
		if(GameActivity.fontSize <= 35){
			for(Sound x: res.sounds){
				x.stop();
			}
			
			MenuSceneWrapper menuSceneWrapper = new MenuSceneWrapper();
			menuSceneWrapper.populate();
			setCurrentScene(menuSceneWrapper);
			
			previousScene.destroy();
		}else{
			
		 
	   new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				for(Sound x: res.sounds){
					x.stop();
				}
				
				MenuSceneWrapper menuSceneWrapper = new MenuSceneWrapper();
				menuSceneWrapper.populate();
				setCurrentScene(menuSceneWrapper);
				
				previousScene.destroy();
			 	return null;
			 }
		}.execute();
	  }
	}
}
