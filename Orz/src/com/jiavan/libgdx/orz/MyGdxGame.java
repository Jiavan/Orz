package com.jiavan.libgdx.orz;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.jiavan.libgdx.orz.bodydata.BoxData;
import com.jiavan.libgdx.orz.box2dmap.BoxHelloWorld;
import com.jiavan.libgdx.orz.common.Constant;
import com.jiavan.libgdx.orz.common.Constant.STATE;
import com.jiavan.libgdx.orz.common.EventParent;

public class MyGdxGame implements ApplicationListener {
	Stage stage;//舞台
	OrthographicCamera camera;//正交投影相机
	SpriteBatch batch;//画笔
	TextureAtlas atlas;//地图集
	TiledMap map;//地图
	OrthogonalTiledMapRenderer OTRender;//地图渲染器
	Box2DDebugRenderer boxRender;//物理引擎调试渲染器
	World world;//物理世界
	ImageButton[] controller;//游戏按钮控制器
	Body bodyPlayer;//主角body
	Body bodyGround;//地面body
	Vector2 force;//主角受到的力
	Vector2 velocity;//主角的速度
	
	@Override
	public void create() {
		//初始化batch，stage以及相机和物理世界
		batch = new SpriteBatch();
		stage = new Stage();
		camera = (OrthographicCamera) stage.getCamera();
		camera.viewportHeight = Constant.World_Height;
		camera.viewportWidth = Constant.World_Width;
		camera.position.set(Constant.Viewport);
		world = WorldFactory.createWorld();
		boxRender = new Box2DDebugRenderer();
		atlas = new TextureAtlas(Gdx.files.internal("atlas/base/base"));
		force = new Vector2(0, 0);
		velocity = new Vector2(0, 0);
		
		//配置地图信息和设置地图渲染器
		//map = new TmxMapLoader().load("maps/map1_helloworld.tmx");
		//OTRender = new OrthogonalTiledMapRenderer(map, Constant.Screen_Scale, batch);

		/******************test**********************/
		BodyFactory.createBox(world, new Vector2(5, 5));
		BodyFactory.createBox(world, new Vector2(5.5f, 5));
		BodyFactory.createBox(world, new Vector2(5, 5));
		/*bodyGround = BodyFactory.createGroundLine(world, new Vector2(
				Constant.Map_Cell_WIDTH*Constant.Screen_Scale*3,
				Constant.Map_Cell_WIDTH*Constant.Screen_Scale*2),
				new Vector2(Constant.Map_Cell_WIDTH*Constant.Screen_Scale*48, 
						Constant.Map_Cell_WIDTH*Constant.Screen_Scale*2));*/
		BoxHelloWorld.create(world);
		bodyPlayer = BodyFactory.createActor(world, new Vector2(3, 5));
		//controller = WorldFactory.createController(stage, atlas, new Vector2(1, 1), 2);
		
		//输入监听
		InputMultiplexer multiplexer = new InputMultiplexer();
		//multiplexer.addProcessor(this);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	//事件监听
	private void keyProcessor() {
		if(Gdx.input.isKeyPressed(Keys.UP) && Math.abs(bodyPlayer.getLinearVelocity().y) <= 0.005f ) {
			velocity = new Vector2(bodyPlayer.getLinearVelocity().x, 5);
			bodyPlayer.setLinearVelocity(velocity);
		}else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			velocity = new Vector2(3, bodyPlayer.getLinearVelocity().y);
			bodyPlayer.setLinearVelocity(velocity);
		}else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			velocity = new Vector2(-3, bodyPlayer.getLinearVelocity().y);
			bodyPlayer.setLinearVelocity(velocity);
		}else{
			velocity = new Vector2(0, bodyPlayer.getLinearVelocity().y);
			bodyPlayer.setLinearVelocity(velocity);
		}
	}
	
	@Override
	public void render() {		
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(bodyPlayer.getWorldCenter().x + 1, camera.position.y, 0);
		camera.update();
		boxRender.render(world, camera.combined);
		//OTRender.setView(camera);
		//OTRender.render();
		keyProcessor();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();
		
		stage.act();
		stage.draw();
		
		world.step(Gdx.graphics.getDeltaTime(), Constant.Velocity_Iterations,
				Constant.Position_Iterations);
		
		//output fps
		//System.out.println(Gdx.graphics.getFramesPerSecond());
		//System.out.println(bodyPlayer.getPosition().toString() + "," + camera.position.toString());
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
