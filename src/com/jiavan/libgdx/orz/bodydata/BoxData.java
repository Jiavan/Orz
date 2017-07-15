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
	 * ��ʼ������
	 */
	public BoxData() {
		this.box = new Sprite(new Texture(Gdx.files.internal("data/box.png")));
	}
	
	/**
	 * ���ƾ���
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		box.setPosition(position.x - BodyConfig.boxHalfWidth, 
				position.y - BodyConfig.boxHalfWidth);//�����ӵ���λ��
		box.setSize(BodyConfig.boxHalfWidth*2, BodyConfig.boxHalfWidth*2);//�����ӵ��Ĵ�С
		box.setOrigin(box.getWidth() / 2, box.getHeight() / 2);
		box.setRotation((float) (angle*180/Math.PI));
		box.draw(batch);//����̨�л���
	}
	
	/**
	 * ��ȡ��ת�Ƕ�
	 * @return
	 */
	public float getAngle() {
		return angle;
	}

	/**
	 * ������ת�Ƕ�
	 * @param angle
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}

	/**
	 * ��ȡ���ӵ�λ��
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
