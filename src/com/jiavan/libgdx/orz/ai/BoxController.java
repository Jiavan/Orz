package com.jiavan.libgdx.orz.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jiavan.libgdx.orz.BodyFactory;
import com.jiavan.libgdx.orz.common.AIController;
import com.jiavan.libgdx.orz.common.Constant;

/**
 * Ïä×Ó¿ØÖÆaiËã·¨
 * @author Jia Van
 *
 */
public class BoxController implements AIController{
	private World world;
	private Vector2 position;
	private Camera camera;
	private float size;
	private long timer;
	private int boxCount;
	
	public BoxController(World world, Camera camera, Vector2 position, float size) {
		this.world = world;
		this.position = position;
		this.camera = camera;
		this.size = size;
		this.timer = 0;
		this.boxCount = 0;
	}
	
	@Override
	public void begin() {
		if(Math.abs(position.x - camera.position.x) < Constant.Screen_Width / 2) {
			int random = MathUtils.random(2, 4)*100;
			if(timer % random == 0 && boxCount < 8) {
				float x = MathUtils.random(position.x - size, position.x + size);
				position.x = x;
				BodyFactory.createBox(world, position);
				timer = 0;
				boxCount++;
			}
		}
		timer++;
	}
}
