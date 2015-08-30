package com.jiavan.libgdx.orz;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.jiavan.libgdx.orz.bodydata.BoomData;
import com.jiavan.libgdx.orz.bodydata.BoxData;
import com.jiavan.libgdx.orz.bodydata.BulletData;
import com.jiavan.libgdx.orz.bodydata.GroundData;
import com.jiavan.libgdx.orz.bodydata.PlayerData;
import com.jiavan.libgdx.orz.bodydata.TurretData;
import com.jiavan.libgdx.orz.common.BodyConfig;

/**
 * Body工厂类
 * 负责创建物理世界中的边界、子弹、碰撞物等信息
 * @author Jia Van
 *
 */
public class BodyFactory {
	
	/**
	 * 创建一个箱子
	 * @param World world 物理世界
	 * @param Vector2 position box产生的位置
	 * @return Body body
	 */
	public static Body createBox(World world, Vector2 position) {
		//定义一个body并设置初始位置
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(position);
		bodyDef.type = BodyType.DynamicBody;//设置body为动态物体
		Body body = world.createBody(bodyDef);
		
		//为body设置shape
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(BodyConfig.boxHalfWidth, BodyConfig.boxHalfWidth);
		
		//为body设置夹具
		FixtureDef fixture = new FixtureDef();
		fixture.density = 1f;
		fixture.friction = 0.7f;
		fixture.restitution = 0.1f;
		fixture.shape = shape;
		
		/*
		 * 设置userdata和打包生成body最后销毁shape对象
		 * 此处应注意shape创建完成后的销毁，当fixture打包完
		 * shape后此时应当销毁节省资源
		 */
		body.setUserData(new BoxData());
		body.createFixture(fixture);
		shape.dispose();
		
		return body;
	}
	
	/**
	 * 创建地面
	 * @param World world
	 * @param Vector startPoint 地面线的起始位置
	 * @param Vector endPoint 地面线的结束位置
	 * @return
	 */
	public static Body createGroundLine(World world, Vector2 startPoint, Vector2 endPoint) {
		BodyDef def = new BodyDef();
		def.type = BodyType.StaticBody;
		Body body = world.createBody(def);
		
		EdgeShape shape = new EdgeShape();
		shape.set(startPoint, endPoint);

		FixtureDef fixture = new FixtureDef();
		fixture.density = 0;
		fixture.friction = 0.5f;
		fixture.restitution = 0;
		fixture.shape = shape;
		
		body.setUserData(new GroundData());
		body.createFixture(fixture);
		shape.dispose();
		
		return body;
	}
	
	/**
	 * 创建物理世界的主角
	 * @param World world 物理世界
	 * @param Vector2 position 初始位置
	 * @return
	 */
	public static Body createPlayer(World world, Vector2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(position);
		Body body = world.createBody(def);
		body.setUserData(new PlayerData());
		
		CircleShape shape = new CircleShape();
		shape.setRadius(0.5f);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.friction = 0.5f;
		fixture.restitution = 0;
		body.createFixture(fixture);
		
		return body;
	}
	
	/**
	 * 创建一个炮塔
	 * @param World world
	 * @param Vector2 position
	 * @return
	 */
	public static Body createTurret(World world, Vector2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(position);
		Body body = world.createBody(def);
		body.setUserData(new TurretData());
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.6f, 0.45f);
		
		FixtureDef fixture = new FixtureDef();
		fixture.density = 50f;
		fixture.friction = 0.5f;
		fixture.restitution = 0f;
		fixture.shape = shape;
		body.createFixture(fixture);
		
		return body;
	}
	
	/**
	 * 创建一颗子弹
	 * @param World world
	 * @param Body player 游戏主角
	 * @return
	 */
	public static Body createBullet(World world, Body player, Vector2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(position);
		Body body = world.createBody(def);
		body.setBullet(true);
		body.setUserData(new BulletData());
		
		CircleShape shape = new CircleShape();
		shape.setRadius(BodyConfig.bulletRadius);
		
		FixtureDef fixture = new FixtureDef();
		fixture.density = 1f;
		fixture.friction = 0.5f;
		fixture.restitution = 0f;
		fixture.shape = shape;
		body.createFixture(fixture);
		
		return body;
	}
	
	/**
	 * 创建一颗炸弹
	 * @param world
	 * @param turret 炮塔
	 * @return
	 */
	public static Body createBoom(World world, Body turret) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(turret.getPosition().x - 0.6f, turret.getPosition().y);
		Body body = world.createBody(def);
		body.setGravityScale(0);
		body.setUserData(new BoomData());
		
		CircleShape shape = new CircleShape();
		shape.setRadius(0.2f);
		
		FixtureDef fixture = new FixtureDef();
		fixture.density = 1;
		fixture.restitution = 0.1f;
		fixture.shape = shape;
		body.createFixture(fixture);
		
		return body;
	}
	
	/***
	 * 创建一架飞机
	 * @param world
	 * @param position
	 * @return
	 */
	public static Body createPlane(World world, Vector2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(position);
		Body body = world.createBody(def);
		body.setUserData(new PlayerData());
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.3f, 0.3f);
		
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;
		fixture.density = 0.5f;
		fixture.restitution = 0;
		fixture.friction = 0;
		body.createFixture(fixture);
		
		return body;
	}
}
