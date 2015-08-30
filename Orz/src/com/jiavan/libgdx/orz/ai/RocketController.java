package com.jiavan.libgdx.orz.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.jiavan.libgdx.orz.common.AIController;
import com.jiavan.libgdx.orz.common.Constant;
import com.jiavan.libgdx.orz.component.RocketComponent;

/**
 * 火箭发射控制器
 * @author Jia Van
 *
 */
public class RocketController implements AIController {
	private Camera camera;
	private Stage stage;
	private Vector2 posistion;
	private long timer;//计时器
	private Sprite sprite;
	
	public RocketController(Camera camera, Stage stage, Vector2 position) {
		this.camera = camera;
		this.stage = stage;
		this.posistion = position;
		this.timer = 0;
		this.sprite = new Sprite(new Texture(Gdx.files.internal("data/rocket.png")));
	}
	
	public void begin() {
		if(Math.abs(camera.position.x - posistion.x) <= Constant.World_Width) {
			if(timer % 50 == 0) {
				Vector2 pushPoint = new Vector2();
				pushPoint.x = MathUtils.random(posistion.x - 2, posistion.x + 2);
				pushPoint.y = posistion.y;
				
				RocketComponent component = new RocketComponent(pushPoint, sprite);
				component.addAction(Actions.moveTo(pushPoint.x + 6, -5, 2));
				stage.addActor(component);
			}
			timer++;
		}
	}
}
