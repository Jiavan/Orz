package com.jiavan.libgdx.orz.bodydata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jiavan.libgdx.orz.common.BodyConfig;

/**
 * Box UserData
 * @author Jia Van
 *
 */
public class BoxData {
	public static String name = "box";
	private Vector2 position;
	private float angle;
	private Sprite box;
	
	/**
	 * 初始化精灵
	 */
	public BoxData() {
		this.box = new Sprite(new Texture(Gdx.files.internal("data/box.png")));
	}
	
	/**
	 * 绘制精灵
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		box.setPosition(position.x - BodyConfig.boxHalfWidth, 
				position.y - BodyConfig.boxHalfWidth);//设置子弹的位置
		box.setSize(BodyConfig.boxHalfWidth*2, BodyConfig.boxHalfWidth*2);//设置子弹的大小
		box.setOrigin(box.getWidth() / 2, box.getHeight() / 2);
		box.setRotation((float) (angle*180/Math.PI));
		box.draw(batch);//在舞台中绘制
	}
	
	/**
	 * 获取旋转角度
	 * @return
	 */
	public float getAngle() {
		return angle;
	}

	/**
	 * 设置旋转角度
	 * @param angle
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}

	/**
	 * 获取箱子的位置
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
