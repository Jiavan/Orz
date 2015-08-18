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
import com.jiavan.libgdx.orz.bodydata.BoxData;
import com.jiavan.libgdx.orz.bodydata.GroundData;

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
		shape.setAsBox(0.5f, 0.5f);
		
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
	public static Body createActor(World world, Vector2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(position);
		Body body = world.createBody(def);
		
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
}
