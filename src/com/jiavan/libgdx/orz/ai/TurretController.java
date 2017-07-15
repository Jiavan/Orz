package com.jiavan.libgdx.orz.ai;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.jiavan.libgdx.orz.BodyFactory;
import com.jiavan.libgdx.orz.common.Constant;
/**
 * 炮塔AI算法以及发射子弹
 * @author Jia Van
 *
 */

public class TurretController {
	private World world;
	private Body turret;
	
	public TurretController(World world, Body turret) {
		this.world = world;
		this.turret = turret;
	}
	
	public void moveTurret() {
		//while(flag) {
		if(turret.getLinearVelocity().y == 0) {
			if(Constant.stateTime * 1000 % 4 == 0) {
				turret.setLinearVelocity(-0.2f, 0);
			}else{
				turret.setLinearVelocity(0.2f, 0);
			}
		}
		
		System.out.println(Constant.stateTime);
	}
	
	public void pushBoom() {
		if(System.currentTimeMillis() % 120 == 0) {
			BodyFactory.createBoom(world, turret).setLinearVelocity(-10, 0);
		}
		//System.out.println(System.currentTimeMillis());
	}
}
