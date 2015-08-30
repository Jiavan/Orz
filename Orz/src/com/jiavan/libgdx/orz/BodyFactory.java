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
 * Body������
 * ���𴴽����������еı߽硢�ӵ�����ײ�����Ϣ
 * @author Jia Van
 *
 */
public class BodyFactory {
	
	/**
	 * ����һ������
	 * @param World world ��������
	 * @param Vector2 position box������λ��
	 * @return Body body
	 */
	public static Body createBox(World world, Vector2 position) {
		//����һ��body�����ó�ʼλ��
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(position);
		bodyDef.type = BodyType.DynamicBody;//����bodyΪ��̬����
		Body body = world.createBody(bodyDef);
		
		//Ϊbody����shape
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(BodyConfig.boxHalfWidth, BodyConfig.boxHalfWidth);
		
		//Ϊbody���üо�
		FixtureDef fixture = new FixtureDef();
		fixture.density = 1f;
		fixture.friction = 0.7f;
		fixture.restitution = 0.1f;
		fixture.shape = shape;
		
		/*
		 * ����userdata�ʹ������body�������shape����
		 * �˴�Ӧע��shape������ɺ�����٣���fixture�����
		 * shape���ʱӦ�����ٽ�ʡ��Դ
		 */
		body.setUserData(new BoxData());
		body.createFixture(fixture);
		shape.dispose();
		
		return body;
	}
	
	/**
	 * ��������
	 * @param World world
	 * @param Vector startPoint �����ߵ���ʼλ��
	 * @param Vector endPoint �����ߵĽ���λ��
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
	 * �����������������
	 * @param World world ��������
	 * @param Vector2 position ��ʼλ��
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
	 * ����һ������
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
	 * ����һ���ӵ�
	 * @param World world
	 * @param Body player ��Ϸ����
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
	 * ����һ��ը��
	 * @param world
	 * @param turret ����
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
	 * ����һ�ܷɻ�
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
