package com.beingprogrammer.colorblind;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Color;

public class ResourceManager {
public GameActivity activity;
public Engine engine;
public Camera camera;
public VertexBufferObjectManager vbom;
public ITextureRegion sun;
public ITextureRegion bricks,about,developer;
public ITiledTextureRegion musics,smokes,coins,pr,sparkle;
public ITextureRegion splashTextureRegion;
private BitmapTextureAtlas splashTextureAtlas;
public int highScore;
public int isSoundEnabled;
public static final float GOONS_ORGINAL_SPEED = 5.0f;
public static final int GOONS_ORGINAL_CREATE_SPEED = 1500;
public float goonsSpeed;
public int goonsCreateSpeed;
public int currentScore;
public int isMusicEnabled;
public int points;
public Settings set;
private BuildableBitmapTextureAtlas gameTextureAtlas;
public ITextureRegion logo;
private static final ResourceManager INSTANCE = new ResourceManager();
	 
	 private ResourceManager(){}
	 
	 public static ResourceManager getInstance(){
		 return INSTANCE;
		 //hello
	 }
	 public void create(GameActivity activity, Engine engine, Camera camera, VertexBufferObjectManager vbom){
		 this.activity = activity;
		 this.camera = camera;
		 this.vbom = vbom;
		 this.engine = engine;
		
		 getSharedData();
		 
	 }
	 
	 public void getSharedData(){
		 set =  new Settings(this.activity);
		 goonsSpeed = set.getFloat("goons_speed", GOONS_ORGINAL_SPEED);
		 goonsCreateSpeed = set.getData("goons_create_speed",GOONS_ORGINAL_CREATE_SPEED);
		 currentScore = set.getData("current_score");
		 highScore  = set.getData("high_score");
		 isSoundEnabled = set.getData("sound");
		 points = set.getData("points");
		 isMusicEnabled =  set.getData("music");
	 }
	 public void setData(String key, String val){
		 set.setData(key, val);
	 }
	 
	 public void loadSplashGraphics(){
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		 splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),600,600,BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity.getAssets(),"bp-hair.jpg",0,0);
		 logo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity.getAssets(),"logo.png",300,300);
		splashTextureAtlas.load();
	 }
	 
	 public void unloadSplash(){
		 splashTextureAtlas.unload();
	 }
	 public void loadGameGraphics(){
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		 gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(),1500,1500,BitmapTextureFormat.RGBA_8888,TextureOptions.NEAREST_PREMULTIPLYALPHA);
		 //sun = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity.getAssets(), "apple.png");
		 bricks = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity.getAssets(), "brick.jpg");
		 musics = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity.getAssets(), "musics.png", 4, 1);
		 
		  smokes = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity.getAssets(), "smoke.png", 8, 5); 
		 sparkle = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity.getAssets(), "sparkle.png", 13, 1); 
			 
		  coins = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity.getAssets(), "coins.png", 9, 1); 
		  pr = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity.getAssets(), "pr.png", 2, 1); 
		 about = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity.getAssets(), "about.png");  	
		 developer = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity.getAssets(), "developer.png");  	
		  
		 try {
				 gameTextureAtlas.build(new
				 BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
				 BitmapTextureAtlas>(2, 0, 2)); 
				 gameTextureAtlas.load();
		 }catch(final TextureAtlasBuilderException e){
			 throw new RuntimeException("Errir while loading game texture",e);
			 }
		 }
	 //sounds
	 public Music gameMusic,menuMusic;
	 public Sound die,coin,newobject,play,speedup,levelup,click,swipe,sparkleAudio;
	 public Sound[] sounds = {die,coin,newobject,play,speedup,levelup,click,swipe,sparkleAudio};
	 public String[] soundName = {"die.wav","coin.wav","new-object.mp3","play.wav","speed-up.wav","levelup.wav","click.wav","perfect_shot.ogg","sparkle.wav"};
	  public void loadGameAudio(){
		 try{
			 for(int i = 0; i < soundName.length ; i++){
			 SoundFactory.setAssetBasePath("sfx/");
			 sounds[i] = SoundFactory.createSoundFromAsset(activity.
			 getSoundManager(), activity, soundName[i]);
			  MusicFactory.setAssetBasePath("sfx/");
				
			 gameMusic =  MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, "background.ogg");
		     menuMusic = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, "menu.ogg");
			 }
		  }catch(Exception e){
			System.out.println(e.getMessage());
		 }
	 } 
	 
	 //fonts
	 public Font font;
	 public Font font2,info;
	 public void loadFont(){
	 	 FontFactory.setAssetBasePath("fonts/");
		 final ITexture fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		 final ITexture fontTexture1 = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		 final ITexture fontTexture2 = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		 	
		 font = FontFactory.createFromAsset(activity.getFontManager(), fontTexture, activity.getAssets(),
			      "ARIAL.TTF", GameActivity.fontSize, true, android.graphics.Color.WHITE);
				 font.load();
				//scale desired size 25 by density
				
				 //define texture for font with BILINEAR scaling
				 font2 = FontFactory.createFromAsset(activity.getFontManager(), fontTexture1, activity.getAssets(),
				      "font.OTF", GameActivity.fontSize+30, true, android.graphics.Color.WHITE);
				
				 info = FontFactory.createFromAsset(activity.getFontManager(), fontTexture2, activity.getAssets(),
					      "font.OTF", (GameActivity.isSmallMedia) ? GameActivity.fontSize  : 25f , true, Color.WHITE);
				 info.load();
		       font2.load();
				// font.prepareLetters("asdfghjklqweerkegvnjvnfdksnfewopferignrenbgjbjgnbvjsnfioerngirebgjbvjbnj".toCharArray());
	 
	 }
	 public void loadSplashFont(){
		  FontFactory.setAssetBasePath("fonts/");
		 final ITexture fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
	 	 font = FontFactory.createFromAsset(activity.getFontManager(), fontTexture, activity.getAssets(),
			      "font.OTF", GameActivity.fontSize, true, android.graphics.Color.WHITE);
				 font.load();
				//scale desired size 25 by density
				
				 //define texture for font with BILINEAR scaling
				 font2 = FontFactory.createFromAsset(activity.getFontManager(), fontTexture, activity.getAssets(),
				      "font.OTF", GameActivity.fontSize + 5, true, android.graphics.Color.WHITE);
				 
		       font2.load();
				// font.prepareLetters("asdfghjklqweerkegvnjvnfdksnfewopferignrenbgjbjgnbvjsnfioerngirebgjbvjbnj".toCharArray());
	 
	 }
}
