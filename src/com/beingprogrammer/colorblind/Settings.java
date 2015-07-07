package com.beingprogrammer.colorblind;

import android.app.Activity;
import android.content.SharedPreferences;

public class Settings {
private Activity context;
private SharedPreferences set;

	public Settings(Activity context){
		this.context = context;
		set = this.context.getSharedPreferences("data", 0);
		
	}
	
	public int getData(String key){
		int x =  set.getInt(key, 0);
		return x;
	}
	
	public int getData(String key,int i){
		int x =  set.getInt(key, i);
		return x;
	}
	
	public float getFloat(String key, float f){
		float x =  set.getFloat(key, f);
		return x;
		
	}
	//sounds for sounds and musics for music
	public void setData(String key,String val){
		SharedPreferences.Editor edit = set.edit();
		edit.putInt(key, Integer.parseInt(val));
		edit.commit();
	}
	
	public void setFloat(String key,String val){
		SharedPreferences.Editor edit = set.edit();
		edit.putFloat(key, Float.parseFloat(val));
		edit.commit();
	}
}
