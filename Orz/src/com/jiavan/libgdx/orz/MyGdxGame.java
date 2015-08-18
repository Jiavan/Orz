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
	Stage stage;//��̨
	OrthographicCamera camera;//����ͶӰ���
	SpriteBatch batch;//����
	TextureAtlas atlas;//��ͼ��
	TiledMap map;//��ͼ
	OrthogonalTiledMapRenderer OTRender;//��ͼ��Ⱦ��
	Box2DDebugRenderer boxRender;//�������������Ⱦ��
	World world;//��������
	ImageButton[] controller;//��Ϸ��ť������
	Body bodyPlayer;//����body
	Body bodyGround;//����body
	Vector2 force;//�����ܵ�����
	Vector2 velocity;//���ǵ��ٶ�
	
	@Override
	public void create() {
		//��ʼ��batch��stage�Լ��������������
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
		
		//���õ�ͼ��Ϣ�����õ�ͼ��Ⱦ��
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
		
		//�������
		InputMultiplexer multiplexer = new InputMultiplexer();
		//multiplexer.addProcessor(this);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	//�¼�����
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
