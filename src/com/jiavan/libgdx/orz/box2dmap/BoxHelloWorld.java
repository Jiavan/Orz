package com.jiavan.libgdx.orz.box2dmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jiavan.libgdx.orz.bodydata.GroundData;
import com.jiavan.libgdx.orz.common.Constant;

/**
 * 创建一个物理世界的地图
 * @content 第一关 helloworld
 * @author Jia Van
 *
 */
public class BoxHelloWorld {
	//物理世界中的单位
	public static float size = Constant.Map_Cell_WIDTH*Constant.Screen_Scale;
	public static Body create(World world) {
		BodyDef def = new BodyDef();
		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
		body.setBullet(true);
		body.setUserData(new GroundData());
		EdgeShape shape = new EdgeShape();
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		
		shape.set(size*3, size*2, size*15f, size*2);
		body.createFixture(fixture);
		/*shape.set(size*15.5f, size*2, size*18, size*4);
		body.createFixture(fixture);*/
		shape.set(size*18f, size*4, size*24, size*4);
		body.createFixture(fixture);
		shape.set(size*26f, size*2, size*30, size*2);
		body.createFixture(fixture);
		shape.set(size*32f, size*2, size*36, size*2);
		body.createFixture(fixture);
		shape.set(size*38f, size*2, size*48, size*2);
		body.createFixture(fixture);
		/*shape.set(size*42f, size*4.5f, size*44, size*4.5f);
		body.createFixture(fixture);*/
		shape.set(size*48f, size*7f, size*50, size*7f);
		body.createFixture(fixture);
		
		shape.set(size*-5f, size*10f, size*55, size*10f);
		body.createFixture(fixture);
		shape.set(size*-5f, size*-2f, size*55, size*-2f);
		body.createFixture(fixture);
		shape.set(size*0f, size*-2f, size*0, size*10f);
		body.createFixture(fixture);
		shape.set(size*50f, size*-2f, size*50, size*10f);
		body.createFixture(fixture);
		
		return body;
	}
}
