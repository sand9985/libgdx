package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import Scene.B2D_Scene;
import Scene.Scene_Base;
import General.MyInputProcessor;

public class MyGdxGame extends ApplicationAdapter {
	
	
	private Scene_Base scene;
	public static final float STEP = 1 / 60f;
	private float accum;
	
	@Override
	public void create () {
		
		scene=new B2D_Scene();		
		Gdx.input.setInputProcessor(new MyInputProcessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -= STEP;
			scene.update(STEP);
			scene.render();
		}
		
		
		
		
	}
}
