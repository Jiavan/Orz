package com.jiavan.libgdx.orz;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jiavan.libgdx.orz.common.Constant;

/**
 * 世界工厂类，创建配置物理世界信息
 * @author Jia Van
 *
 */
public class WorldFactory {
	/**
	 * 创建一个物理游戏世界
	 * @return
	 */
	public static World createWorld() {
		World world = new World(Constant.Gravity, true);//设置重力
		return world;
	}
	
	/**
	 * 创建游戏控制器
	 * 游戏控制器由三个imagebutton组成，按顺时针构成返回imagebutton数组
	 * @param Stage stage 游戏舞台
	 * @param TextureAtlas atlas组成imagebutton需要的drawable游戏资源 
	 * @param Vector position 控制器的中心位置
	 * @param float szie 整个控制器的大小
	 * @return
	 */
	public static ImageButton[] createController(Stage stage, TextureAtlas atlas, 
			Vector2 position, float size) {
		//分别生成imagebutton的上右左的按钮drawable
		TextureRegionDrawable upDrawable = new TextureRegionDrawable(
				new TextureRegion(atlas.findRegion("controlup")));
		TextureRegionDrawable rightDrawable = new TextureRegionDrawable(
				new TextureRegion(atlas.findRegion("controlright")));
		TextureRegionDrawable leftDrawable = new TextureRegionDrawable(
				new TextureRegion(atlas.findRegion("controlleft")));
		
		ImageButton[] buttons = new ImageButton[3];
		buttons[0] = new ImageButton(upDrawable, upDrawable);
		buttons[0].setPosition(position.x - size / 4, position.y);
		buttons[0].setSize(size/2, size/2);
		
		buttons[1] = new ImageButton(rightDrawable, rightDrawable);
		buttons[1].setPosition(position.x, position.y - size / 2);
		buttons[1].setSize(size/2, size/2);
		
		buttons[2] = new ImageButton(leftDrawable, leftDrawable);
		buttons[2].setPosition(position.x - size / 2, position.y - size / 2);
		buttons[2].setSize(size/2, size/2);
		
		stage.addActor(buttons[0]);
		stage.addActor(buttons[1]);
		stage.addActor(buttons[2]);
		
		return buttons;
	}
}
