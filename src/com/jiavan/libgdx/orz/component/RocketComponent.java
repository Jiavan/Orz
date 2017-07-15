package com.jiavan.libgdx.orz.component;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * ������
 * @author Jia Van
 *
 */
public class RocketComponent extends Actor {
	private Sprite sprite;//�������
	private Vector2 size;//����Ĵ�С
	private float angle;//������ת�ĽǶ�
	
	public RocketComponent(Vector2 position, Sprite sprite) {
		this.sprite = sprite;
		this.angle = -90;
		this.size = new Vector2(0.5f, 1f);//������
		
		/**
		 * ��������˾�������ƣ�����actor��λ�ú;������ת����
		 */
		sprite.setSize(size.x, size.y);
		sprite.setOrigin(size.x / 2, size.y / 2);
		this.setPosition(position.x, position.y);
		this.setSize(size.x, size.y);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(sprite != null) {
			sprite.setRotation(angle);
			sprite.setPosition(this.getX(), this.getY());//���ƾ��������ͨ��actor��λ�õõ�
			sprite.draw(batch);
		}
	}
	
	/**
	 * ��ȡ�����������
	 * @return
	 */
	public Rectangle getRectangle() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
}
