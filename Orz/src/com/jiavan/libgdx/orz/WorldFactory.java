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
 * ���繤���࣬������������������Ϣ
 * @author Jia Van
 *
 */
public class WorldFactory {
	/**
	 * ����һ��������Ϸ����
	 * @return
	 */
	public static World createWorld() {
		World world = new World(Constant.Gravity, true);//��������
		return world;
	}
	
	/**
	 * ������Ϸ������
	 * ��Ϸ������������imagebutton��ɣ���˳ʱ�빹�ɷ���imagebutton����
	 * @param Stage stage ��Ϸ��̨
	 * @param TextureAtlas atlas���imagebutton��Ҫ��drawable��Ϸ��Դ 
	 * @param Vector position ������������λ��
	 * @param float szie �����������Ĵ�С
	 * @return
	 */
	public static ImageButton[] createController(Stage stage, TextureAtlas atlas, 
			Vector2 position, float size) {
		//�ֱ�����imagebutton��������İ�ťdrawable
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
