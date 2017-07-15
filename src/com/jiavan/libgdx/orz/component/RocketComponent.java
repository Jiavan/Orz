package com.jiavan.libgdx.orz.component;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 火箭组件
 * @author Jia Van
 *
 */
public class RocketComponent extends Actor {
	private Sprite sprite;//火箭精灵
	private Vector2 size;//精灵的大小
	private float angle;//精灵旋转的角度
	
	public RocketComponent(Vector2 position, Sprite sprite) {
		this.sprite = sprite;
		this.angle = -90;
		this.size = new Vector2(0.5f, 1f);//精灵宽高
		
		/**
		 * 组件采用了精灵类绘制，设置actor的位置和精灵的旋转中心
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
			sprite.setPosition(this.getX(), this.getY());//绘制精灵的坐标通过actor的位置得到
			sprite.draw(batch);
		}
	}
	
	/**
	 * 获取火箭矩形区域
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
