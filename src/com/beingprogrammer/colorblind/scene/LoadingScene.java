package com.beingprogrammer.colorblind.scene;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.text.Text;

import com.beingprogrammer.colorblind.GameActivity;

public class LoadingScene extends AbstractScene {

	@Override
	public void populate() {
		
		CameraScene cameraScene = new CameraScene(camera);
		Text text = new Text(GameActivity.CAMERA_WIDTH / 2,
				GameActivity.CAMERA_HEIGHT / 2, res.font, "LOADING...", vbom);
	
		cameraScene.attachChild(text);
		setChildScene(cameraScene);
		
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onResume() {
	}
}
