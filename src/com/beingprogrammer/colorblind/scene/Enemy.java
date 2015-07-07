package com.beingprogrammer.colorblind.scene;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Enemy extends Rectangle{
private PhysicsWorld pw;
public static boolean health = true;
Body enemyBody;
private FixtureDef BoxBodyFixtureDef;
private Entity[] collider;
	public Enemy(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager,PhysicsWorld pw) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		this.pw = pw;
		BoxBodyFixtureDef = PhysicsFactory.createFixtureDef(1f, 0.5f, 1f);
		// TODO Auto-generated constructor stub
	}

	
	public void design(){
		 setColor(Color.BLACK);
	}
	
	public void physics(){
	 	enemyBody = PhysicsFactory.createBoxBody(pw,
		this, BodyType.DynamicBody, BoxBodyFixtureDef);
		pw.registerPhysicsConnector(new PhysicsConnector(
		this, enemyBody));
	}

	public void setCollider(Entity... name){
		collider = new Entity[name.length];
		for (int i = 0; i < name.length; i++) {
			collider[i] = name[i];
		}
		System.out.println(name.length);
	}

	

	
}
