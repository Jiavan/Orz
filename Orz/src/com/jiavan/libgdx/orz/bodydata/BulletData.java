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
	 * ��ʼ������
	 */
	public BulletData() {
		this.bullet = new Sprite(new Texture(Gdx.files.internal("data/bullet.png")));
	}
	
	/**
	 * ���ƾ���
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		bullet.setPosition(position.x - BodyConfig.bulletRadius, 
				position.y - BodyConfig.bulletRadius);//�����ӵ���λ��
		bullet.setSize(BodyConfig.bulletRadius*2, BodyConfig.bulletRadius*2);//�����ӵ��Ĵ�С
		bullet.draw(batch);//����̨�л���
	}
	
	/**
	 * ��ȡ�ӵ���λ��
	 * @return
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * �����ӵ���λ��
	 * @param position
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

}
