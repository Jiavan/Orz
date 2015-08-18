package com.jiavan.libgdx.orz.box2dmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jiavan.libgdx.orz.common.Constant;

public class BoxHelloWorld {

	public static float size = Constant.Map_Cell_WIDTH*Constant.Screen_Scale;
	public static Body create(World world) {
		BodyDef def = new BodyDef();
		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
		EdgeShape shape = new EdgeShape();
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		
		shape.set(size*1, size*2, size*5, size*2);
		body.createFixture(fixture);
		shape.set(size*6.5f, size*4, size*20, size*4);
		body.createFixture(fixture);
		
		return body;
	}
}
