package com.jiavan.libgdx.orz.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.jiavan.libgdx.orz.common.Constant;

/**
 * UFO���
 * construct function
 * ���������λ��
 * @author Jia Van
 *
 */
public class UFOComponent extends Actor {
	private Sprite sprite;//UFO����
	private Vector2 size;//�����С
	private Vector2 position;//ufo��λ��
	private int health;//UFOѪ��
	private Sprite blood;//Ѫ������
	private Pixmap pixmap;//����Ѫ����pixmap
	private Texture texture;//����Ѫ����texture
	private TextureRegion region;//����Ѫ����region
	private ParticleEffect effect;
	
	/**
	 * Actor��λ���Լ������λ��
	 * @param position
	 */
	public UFOComponent(Vector2 position) {
		this.size = new Vector2(1.5f,  1f);//����Ĵ�С
		this.position = position;
		this.health = 62;
		sprite = new Sprite(new Texture(Gdx.files.internal("data/ufo.png")));
		
		//���þ����actor��λ��
		sprite.setPosition(position.x, position.y);
		sprite.setSize(size.x, size.y);
		this.setPosition(position.x, position.y);
		this.setSize(size.x, size.y);
		addAction();
		
		//Ѫ������
		pixmap = new Pixmap(64, 4, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.drawRectangle(0, 0, 64, 4);
		texture = new Texture(pixmap);
		region = new TextureRegion(texture, 64, 4);
		blood = new Sprite(region);
		blood.setSize(1f, 0.08f);
		
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("particle/fire.p"), Gdx.files.internal("particle/"));
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setPosition(this.getX(), this.getY());
		sprite.draw(batch);
		
		//����Ѫ��
		pixmap.setColor(Color.WHITE);//�����֮ǰ���Ƶ�Ѫ��
		pixmap.fillRectangle(1, 1, 62, 2);//������
		pixmap.setColor(Color.RED);
		pixmap.fillRectangle(1, 1, (int) (health), 2);
		texture.draw(pixmap, 0, 0);
		region.setTexture(texture);
		blood.setRegion(region);
		blood.setPosition(this.getX() + 0.25f, this.getY() + 1.1f);//����Ѫ����λ����ufo�Ϸ�
		blood.draw(batch);
		
		//effect.draw(batch, Constant.stateTime);
		//effect.setPosition(this.getX() + size.x / 2, this.getY());
	}
	
	/**
	 * ��Ӷ���Ч��
	 */
	public void addAction() {
		Action right = Actions.moveTo(position.x + 2, position.y, 3);
		Action left = Actions.moveTo(position.x - 2, position.y, 3);
		SequenceAction sequenceAction = Actions.sequence(left, right);
		RepeatAction repeatAction = Actions.forever(sequenceAction);
		
		this.addAction(repeatAction);
	}
	
	/**
	 * ��ȡActor�����ľ�������
	 * @return
	 */
	public Rectangle getRectangle() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	/**
	 * ��ȡActor�ĵ�ǰλ��
	 * @return
	 */
	public Vector2 getPosition() {
		return new Vector2(this.getX(), this.getY());
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
