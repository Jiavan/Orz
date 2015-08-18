package com.jiavan.libgdx.orz.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * ��Ϸ������
 * @author Jia Van
 *
 */
public class Constant {
	public static final float Screen_Height = 480f;//��Ļ�߶�
	public static final float Screen_Width = 320f;//��Ļ���
	public static final float World_Width = 10f;//��Ϸ������
	public static final float World_Height = 6f;//��Ϸ����߶�
	public static final Vector2 Gravity = new Vector2(0, -9.8f);//����
	public static final Vector3 Viewport = new Vector3(5, 3, 0);//�ӿڴ�С
	public static final float Screen_Scale = 10/480f;//��Ļ���ű���
	public static final int Velocity_Iterations = 6;//�ٶȵ�������
	public static final int Position_Iterations = 3;//λ�õ�������
	public static final float Map_Cell_WIDTH = 32;//��ͼ��Ԫ����
	public static enum STATE {UP, LEFT, RIGHT, IDELR, IDELL};//����ö��
}
