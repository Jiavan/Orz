package com.jiavan.libgdx.orz.bodydata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jiavan.libgdx.orz.common.BodyConfig;

public class BulletData {
	public static String name = "bullet";
	private Vector2 position;
	private Sprite bullet;
	
	/**
	 * 初始化精灵
	 */
	public BulletData() {
		this.bullet = new Sprite(new Texture(Gdx.files.internal("data/bullet.png")));
	}
	
	/**
	 * 绘制精灵
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		bullet.setPosition(position.x - BodyConfig.bulletRadius, 
				position.y - BodyConfig.bulletRadius);//设置子弹的位置
		bullet.setSize(BodyConfig.bulletRadius*2, BodyConfig.bulletRadius*2);//设置子弹的大小
		bullet.draw(batch);//在舞台中绘制
	}
	
	/**
	 * 获取子弹的位置
	 * @return
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * 设置子弹的位置
	 * @param position
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

}
