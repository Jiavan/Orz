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
	Stage stage;//游戏舞台
	Stage stageUI;//UI舞台
	OrthographicCamera camera;//正交投影相机
	SpriteBatch batch;//画笔
	TextureAtlas atlas;//地图集
	TiledMap map;//地图
	OrthogonalTiledMapRenderer OTRender;//地图渲染器
	Box2DDebugRenderer boxRender;//物理引擎调试渲染器
	World world;//物理世界
	ImageButton[] controller;//游戏按钮控制器
	Body bodyPlayer;//主角body
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
		
		//输入监听
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
					//游戏结束
				}
			}
		}
		
	}
	
	//事件监听
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
			//如果没有事件发生使x方向的速度为0，y方向保持原有的速度，为了防止物体在x上受力后不会静止
			velocity = new Vector2(0, bodyPlayer.getLinearVelocity().y);
			bodyPlayer.setLinearVelocity(velocity);
		}
		
		/**
		 * 通过space按键发射子弹
		 * 根据state判断子弹的发射方向
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
	
	//根据人物行走移动相机，该方法应在camera update之前
	private void moveCamera() {
		if((bodyPlayer.getWorldCenter().x + 1) >= (50*BoxHelloWorld.size - Constant.World_Width/2)) {
			camera.position.set(50*BoxHelloWorld.size - Constant.World_Width/2, 
					camera.position.y, 0);
		}else {
			camera.position.set(bodyPlayer.getWorldCenter().x + 1, camera.position.y, 0);
		}
	}
	
	//销毁子弹，此方法应在world step后调用
	private void destroyBody() {
		Array<Body> bodies = new Array<Body>();//整个物理世界的数组，此步申请空间，没有body
		world.getBodies(bodies);//此步获取世界中的body到数组中
		
		try {
			for(Body body : bodies) {
				//遍历数组将userData为Bullet的body销毁
				if(body.getUserData() instanceof BulletData) {
					/**
					 * 一下是检测每一个bullet的xy上的速度，在某些情况下不是所有子弹的速度最终都会为0
					 * 所以检测值绝对值x方向小于1或者y方向大于5就将其销毁，因为子弹发射出来的速度x方向
					 * 上的速度远远会大于1，y轴也一样，满足此条件的子弹基本上是完成碰撞了所以要及时销毁
					 * 否则物理世界模拟的物体过多会占用很多资源导致计算速度缓慢游戏卡顿.
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
	
	//初始化数据，此方法应在create中第一个调用
	private void init() {
		//初始化batch，stage以及相机和物理世界
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
		
		//配置地图信息和设置地图渲染器
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
