package com.jiavan.libgdx.orz.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BoomsComponent extends Group {
	private long boomCount;
	private long timer;
	private Vector2 position;
	private float width;
	private float height;
	
	public BoomsComponent(Vector2 position){
		this.setBoomCount(0);
		this.timer = 0;
		this.position = position;
		this.width = 0.4f;
		this.height = 0.4f;
	}
	
	public void draw() {
		if(timer % 100 == 0) {
			Image image = new Image(new Texture(Gdx.files.internal("data/bullet.png")));
			image.setPosition(position.x, position.y);
			image.setSize(width, height);
			
			image.addAction(Actions.moveTo(0, position.y, 1f));
			this.addActor(image);
			setBoomCount(getBoomCount() + 1);
		}
		timer++;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public long getBoomCount() {
		return boomCount;
	}

	public void setBoomCount(long boomCount) {
		this.boomCount = boomCount;
	}

}
