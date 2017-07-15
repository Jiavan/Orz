package com.jiavan.libgdx.orz.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jiavan.libgdx.orz.MyGdxGame;
import com.jiavan.libgdx.orz.common.Constant;
import com.jiavan.libgdx.orz.common.Constant.STATE;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

public class PlayerComponent extends Actor {
	private Vector2 position;
	private Sprite sprite;
	public int health;
	private Animation animationR;
	private Animation animationL;
	private Animation animationIR;
	private Animation animationIL;
	private TextureRegion[][] regionPlayer;
	private TextureRegion[][] regionmirror;
	private TextureRegion currentRegion;
	
	public PlayerComponent(Vector2 position) {
		this.position = position;
		sprite = new Sprite();
		sprite.setSize(1, 1);
		this.health = 100;
		
		Texture texture = new Texture(Gdx.files.internal("data/player.png"));
		regionPlayer = TextureRegion.split(texture, 
				texture.getWidth() / 6, texture.getWidth() / 6);
		regionmirror = TextureRegion.split(texture, 
				texture.getWidth() / 6, texture.getWidth() / 6);
		for(TextureRegion[] regions : regionmirror) {
			for(TextureRegion region : regions) {
				region.flip(true, false);
			}
		}

		TextureRegion[] regionR = new TextureRegion[5];
		TextureRegion[] regionL = new TextureRegion[5];
		TextureRegion[] regionIR = new TextureRegion[1];
		TextureRegion[] regionIL = new TextureRegion[1];
		
		for (int i = 0; i < 5; i++) {
			regionR[i] = regionPlayer[0][i];
			regionL[i] = regionmirror[0][i];
		}
		regionIL[0] = regionmirror[0][0];
		regionIR[0] = regionPlayer[0][0];
		
		animationR = new Animation(0.1f, regionR);
		animationL = new Animation(0.1f, regionL);
		animationIR = new Animation(0.1f, regionIR);
		animationIL = new Animation(0.1f, regionIL);
		currentRegion = new TextureRegion(regionIR[0]);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		check();
		sprite.setRegion(currentRegion);
		sprite.setPosition(position.x - 0.5f, position.y - 0.5f);
		sprite.draw(batch);
	}
	
	private void check() {
		if(MyGdxGame.state == STATE.RIGHT) {
			currentRegion = animationR.getKeyFrame(Constant.stateTime, true);
		}else if(MyGdxGame.state == STATE.LEFT) {
			currentRegion = animationL.getKeyFrame(Constant.stateTime, true);
		}else if(MyGdxGame.state == STATE.IDELL) {
			currentRegion = animationIL.getKeyFrame(Constant.stateTime, true);
		}else if(MyGdxGame.state == STATE.IDELR){
			currentRegion = animationIR.getKeyFrame(Constant.stateTime, true);
		}
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

}
