package MyLib;
/*
  2D �Ϲ����F

*/
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Sprite {

 // �y��(x,y) shader�C�� �K�Ϯy�� sprite�z���� ���
 public static final int VERTEX_SIZE = 2 + 1 + 2 + 1 + 1;
 //public static final int VERTEX_SIZE = 2 + 1 + 2;
 public static final int SPRITE_SIZE = 4 * VERTEX_SIZE; // 4���I	
 
 public	float x;  //�y��X
 public float y;  //�y��Y
 public float z;  //z-order (���O3d��z�y��)
 public float scale_x; //x��V��j�v
 public float scale_y; //y��V��j�v
 public float ox;  //����x
 public float oy;  //����y
 public float alpha;  //Sprite���z����
 public float angle;   //¶z�b�����ਤ��
 public Color color; // shader�Ϊ��C��
 public Color tone;  // Sprite�����
 public Rectangle rect; //�ϧ���ܽd��

 public boolean flip_x; //����x
 public boolean flip_y; //����y
 public Texture texture; //�K��
 
 public Sprite(){
	 
	 init();//��Ϥ�
	 
 }
 //��Ϥ�
 private void init(){
	 this.x=0;
	 this.y=0;
	 this.scale_x=1;
	 this.scale_y=1;
	 this.ox=0;
	 this.oy=0;
	 this.alpha=1f;
	 this.color=new Color(0,0,0,0); 
	 this.rect=new Rectangle();
	 this.flip_x=false;
	 this.flip_y=false;
	 this.angle=0; 
	 this.texture=null;
 }

 

 
}
