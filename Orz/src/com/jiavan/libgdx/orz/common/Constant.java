package com.jiavan.libgdx.orz.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * 游戏常量类
 * @author Jia Van
 *
 */
public class Constant {
	public static final float Screen_Height = 480f;//屏幕高度
	public static final float Screen_Width = 320f;//屏幕宽度
	public static final float World_Width = 10f;//游戏世界宽度
	public static final float World_Height = 6f;//游戏世界高度
	public static final Vector2 Gravity = new Vector2(0, -9.8f);//重力
	public static final Vector3 Viewport = new Vector3(5, 3, 0);//视口大小
	public static final float Screen_Scale = 10/480f;//屏幕缩放比例
	public static final int Velocity_Iterations = 6;//速度迭代次数
	public static final int Position_Iterations = 3;//位置迭代次数
	public static final float Map_Cell_WIDTH = 32;//地图单元格宽高
	public static enum STATE {UP, LEFT, RIGHT, IDELR, IDELL};//按键枚举
}
