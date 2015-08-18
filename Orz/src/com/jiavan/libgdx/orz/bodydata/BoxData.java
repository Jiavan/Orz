package com.jiavan.libgdx.orz.bodydata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jiavan.libgdx.orz.BodyFactory;

/**
 * Box UserData
 * @author Jia Van
 *
 */
public class BoxData {
	public static String name = "box";
	private static Texture texture;
	public float x;
	public float y;
	public float angle;
	public Sprite sprite;
	
	public BoxData() {
		texture = new Texture(Gdx.files.internal("data/box.png"));
		sprite = new Sprite(texture);
	}
	
	/*public void draw(Stage stage, Body body, SpriteBatch batch) {
		sprite.setPosition(body.getPosition().x - BodyFactory.boxHalfWidth,
				body.getPosition().y - BodyFactory.boxHalfWidth);
		sprite.setOrigin(body.getPosition().x, body.getPosition().y);
		sprite.setSize(2*BodyFactory.boxHalfWidth, 2*BodyFactory.boxHalfWidth);
		sprite.draw(batch);
	}*/
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
}
