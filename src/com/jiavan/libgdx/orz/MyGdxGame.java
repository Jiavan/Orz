package com.jiavan.libgdx.orz;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.jiavan.libgdx.orz.ai.BoxController;
import com.jiavan.libgdx.orz.ai.RocketController;
import com.jiavan.libgdx.orz.bodydata.BoomData;
import com.jiavan.libgdx.orz.bodydata.BoxData;
import com.jiavan.libgdx.orz.bodydata.BulletData;
import com.jiavan.libgdx.orz.bodydata.PlayerData;
import com.jiavan.libgdx.orz.box2dmap.BoxHelloWorld;
import com.jiavan.libgdx.orz.common.Constant;
import com.jiavan.libgdx.orz.common.Constant.STATE;
import com.jiavan.libgdx.orz.component.BoomsComponent;
import com.jiavan.libgdx.orz.component.PlayerComponent;
import com.jiavan.libgdx.orz.component.RocketComponent;
import com.jiavan.libgdx.orz.component.UFOComponent;

public class MyGdxGame extends BodyCollisionListener implements ApplicationListener {
	Stage stage;//��Ϸ��̨
	Stage stageUI;//UI��̨
	OrthographicCamera camera;//����ͶӰ���
	SpriteBatch batch;//����
	TextureAtlas atlas;//��ͼ��
	TiledMap map;//��ͼ
	OrthogonalTiledMapRenderer OTRender;//��ͼ��Ⱦ��
	Box2DDebugRenderer boxRender;//�������������Ⱦ��
	World world;//��������
	ImageButton[] controller;//��Ϸ��ť������
	Body bodyPlayer;//����body
	Vector2 velocity;
	public static STATE state;
	BoxController boxController;
	BoxController boxController2;
	RocketController rocketController;
	BoomsComponent boomsComponent;
	UFOComponent ufoComponent;
	PlayerComponent playerComponent;
	//Debug
	Label debugLabel;
	
	@Override
	public void create() {
		
		init();
		BoxHelloWorld.create(world);
		bodyPlayer = BodyFactory.createPlayer(world, new Vector2(3, 5));
		boxController = new BoxController(world, camera, 
				new Vector2(21f*Constant.Cell_Size, 5), 1.5f*Constant.Cell_Size);
		boxController2 = new BoxController(world, camera, 
				new Vector2(45f*Constant.Cell_Size, 5), 5f*Constant.Cell_Size);
		rocketController = new RocketController(camera, stage, new Vector2(17, 8));
		
		playerComponent = new PlayerComponent(new Vector2(3, 5));
		ufoComponent = new UFOComponent(new Vector2(8, 1.5f));
		boomsComponent = new BoomsComponent(new Vector2(ufoComponent.getX(), ufoComponent.getY()));
		
		stage.addActor(boomsComponent);
		stage.addActor(ufoComponent);
		stage.addActor(playerComponent);
		//controller = WorldFactory.createController(stage, atlas, new Vector2(1, 1), 2);
		
		//�������
		InputMultiplexer multiplexer = new InputMultiplexer();
		//multiplexer.addProcessor(this);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		moveCamera();
		camera.update();
		//boxRender.render(world, camera.combined);
		OTRender.setView(camera);
		OTRender.render();
		keyProcessor();
		boxController.begin();
		boxController2.begin();
		rocketController.begin();
		boomsComponent.setPosition(ufoComponent.getPosition());
		boomsComponent.draw();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		updateUI();
		checkCollision();
		batch.end();
		
		stage.act();
		stage.draw();
		
		stageUI.act();
		debugLabel.setPosition(0, Constant.Screen_Height - debugLabel.getTextBounds().height);
		debugLabel.setText(""
				+ "FPS " + Gdx.graphics.getFramesPerSecond() + ", "
				+ "Pos" + bodyPlayer.getPosition().toString()
				+ "\n"
				+ "body count " + world.getBodyCount() + ", "
				+ "Health " + PlayerData.health);
		stageUI.draw();
		
		world.step(Gdx.graphics.getDeltaTime(), Constant.Velocity_Iterations,
				Constant.Position_Iterations);
		destroyBody();
		Constant.stateTime += Gdx.graphics.getDeltaTime();
	}
	
	private void updateUI() {
		playerComponent.setPosition(bodyPlayer.getPosition());
		
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for(Body body : bodies) {
			if(body.getUserData() instanceof BulletData) {
				BulletData bulletData = (BulletData) body.getUserData();
				bulletData.setPosition(body.getPosition());
				bulletData.draw(batch);
			} else if (body.getUserData() instanceof BoxData) {
				BoxData boxData = (BoxData) body.getUserData();
				boxData.setAngle(body.getAngle());
				boxData.setPosition(body.getPosition());
				boxData.draw(batch);
			}
		}
		
		Actor[] actors = boomsComponent.getChildren().begin();
		for(Actor actor : actors) {
			if(actor != null && actor.getX() == 0) {
				boomsComponent.removeActor(actor);
			}
		}
	}

	private void checkCollision() {
		Actor[] actorsBoom = boomsComponent.getChildren().begin();
		for(Actor actor : actorsBoom) {
			if(actor != null) {
				Rectangle playerRect = new Rectangle(playerComponent.getPosition().x - 0.5f,
						playerComponent.getPosition().y - 0.5f, 1, 1);
				Rectangle boomRect = new Rectangle(actor.getX(), actor.getY(), 
						actor.getWidth(), actor.getHeight());
				
				if(playerRect.overlaps(boomRect)) {
					boomsComponent.removeActor(actor);
					PlayerData.health--;
				}
			}
		}
		
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for(Body body : bodies) {
			if(body.getUserData() instanceof BulletData) {
				Rectangle ufoRect = new Rectangle(ufoComponent.getRectangle());
				if(ufoRect.contains(body.getPosition())) {
					try {
						ufoComponent.setHealth(ufoComponent.getHealth() - 1);
						world.destroyBody(body);
					} catch (Exception e) {
						// TODO: handle exception
					}    
				}
			} else if (body.getUserData() instanceof PlayerData) {
				Rectangle ufoRect = new Rectangle(ufoComponent.getRectangle());
				Rectangle playerRect = new Rectangle(body.getPosition().x - 0.5f,
						body.getPosition().y - 0.5f, 1, 1);
				
				Array<Actor> actors = stage.getActors();
				for(Actor actor : actors) {
					if(actor instanceof RocketComponent) {
						Rectangle rocketRect = ((RocketComponent) actor).getRectangle();
						if(rocketRect.overlaps(playerRect)) {
							((RocketComponent) actor).setSprite(null);
							actor.clear();
						}
					}
				}
				if(ufoRect.overlaps(playerRect)) {
					//��Ϸ����
				}
			}
		}
		
	}
	
	//�¼�����
	private void keyProcessor() {
		if(Gdx.input.isKeyPressed(Keys.UP) && Math.abs(bodyPlayer.getLinearVelocity().y) <= 0.005f) {
			velocity = new Vector2(bodyPlayer.getLinearVelocity().x, 5);
			bodyPlayer.setLinearVelocity(velocity);
		}else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			state = STATE.RIGHT;
			velocity = new Vector2(3, bodyPlayer.getLinearVelocity().y);
			bodyPlayer.setLinearVelocity(velocity);
		}else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			state = STATE.LEFT;
			velocity = new Vector2(-3, bodyPlayer.getLinearVelocity().y);
			bodyPlayer.setLinearVelocity(velocity);
		}else{
			//���û���¼�����ʹx������ٶ�Ϊ0��y���򱣳�ԭ�е��ٶȣ�Ϊ�˷�ֹ������x�������󲻻ᾲֹ
			velocity = new Vector2(0, bodyPlayer.getLinearVelocity().y);
			bodyPlayer.setLinearVelocity(velocity);
		}
		
		/**
		 * ͨ��space���������ӵ�
		 * ����state�ж��ӵ��ķ��䷽��
		 */
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			Body bullet = null;;
			Vector2 position = null;
			if(state == STATE.RIGHT) {
				position = new Vector2(bodyPlayer.getPosition().x + 0.6f, 
						bodyPlayer.getPosition().y);
				bullet = BodyFactory.createBullet(world, bodyPlayer, position);
				bullet.applyForce(new Vector2(20f, 0), bullet.getWorldCenter(), false);
				
			}else if(state == STATE.LEFT) {
				position = new Vector2(bodyPlayer.getPosition().x - 0.6f, 
						bodyPlayer.getPosition().y);
				bullet = BodyFactory.createBullet(world, bodyPlayer, position);
				bullet.applyForce(new Vector2(-20f, 0), bullet.getWorldCenter(), false);
			}
		}
	}
	
	//�������������ƶ�������÷���Ӧ��camera update֮ǰ
	private void moveCamera() {
		if((bodyPlayer.getWorldCenter().x + 1) >= (50*BoxHelloWorld.size - Constant.World_Width/2)) {
			camera.position.set(50*BoxHelloWorld.size - Constant.World_Width/2, 
					camera.position.y, 0);
		}else {
			camera.position.set(bodyPlayer.getWorldCenter().x + 1, camera.position.y, 0);
		}
	}
	
	//�����ӵ����˷���Ӧ��world step�����
	private void destroyBody() {
		Array<Body> bodies = new Array<Body>();//����������������飬�˲�����ռ䣬û��body
		world.getBodies(bodies);//�˲���ȡ�����е�body��������
		
		try {
			for(Body body : bodies) {
				//�������齫userDataΪBullet��body����
				if(body.getUserData() instanceof BulletData) {
					/**
					 * һ���Ǽ��ÿһ��bullet��xy�ϵ��ٶȣ���ĳЩ����²��������ӵ����ٶ����ն���Ϊ0
					 * ���Լ��ֵ����ֵx����С��1����y�������5�ͽ������٣���Ϊ�ӵ�����������ٶ�x����
					 * �ϵ��ٶ�ԶԶ�����1��y��Ҳһ����������������ӵ��������������ײ������Ҫ��ʱ����
					 * ������������ģ�����������ռ�úܶ���Դ���¼����ٶȻ�����Ϸ����.
					 */
					if(Math.abs(body.getLinearVelocity().x) < 1
							|| Math.abs(body.getLinearVelocity().y) > 5) {
						world.destroyBody(body);
					}
				} else if (body.getUserData() instanceof BoomData) {
					if(Math.abs(body.getLinearVelocity().x) < 5f
							) {
						world.destroyBody(body);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//��ʼ�����ݣ��˷���Ӧ��create�е�һ������
	private void init() {
		//��ʼ��batch��stage�Լ��������������
		batch = new SpriteBatch();
		stage = new Stage();
		stageUI = new Stage();
		camera = (OrthographicCamera) stage.getCamera();
		camera.viewportHeight = Constant.World_Height;
		camera.viewportWidth = Constant.World_Width;
		camera.position.set(Constant.Viewport);
		world = WorldFactory.createWorld();
		world.setContactListener(this);
		boxRender = new Box2DDebugRenderer();
		atlas = new TextureAtlas(Gdx.files.internal("atlas/base/base"));
		velocity = new Vector2(0, 0);
		state = STATE.LEFT;
		
		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.RED);
		debugLabel = new Label("debug", labelStyle);
		stageUI.addActor(debugLabel);
		
		//���õ�ͼ��Ϣ�����õ�ͼ��Ⱦ��
		map = new TmxMapLoader().load("maps/map1_hello_world.tmx");
		OTRender = new OrthogonalTiledMapRenderer(map, Constant.Screen_Scale, batch);
	}
	
	@Override
	public void dispose() {
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
