package General;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Sprite {

 // 座標(x,y) shader顏色 貼圖座標 sprite透明度 色調
 public static final int VERTEX_SIZE = 2 + 1 + 2 + 1 + 4;
 //public static final int VERTEX_SIZE = 2 + 1 + 2;
 public static final int SPRITE_SIZE = 4 * VERTEX_SIZE; // 4個點	
 
 public	float x;  //座標X 
 public float y;  //座標Y 
 public float z;  //z-order (不是3d的z座標)
 public float scale_x; //x方向放大率 
 public float scale_y; //y方向放大率 
 public float ox;  //偏移x
 public float oy;  //偏移y 
 public float alpha;  //Sprite的透明度 
 public float angle;   //繞z軸的旋轉角度 
 public Color color; // shader用的顏色 
 public Tone tone;  // Sprite的色調  
 public Rectangle rect; //圖形顯示範圍 
 public int blend_mode;   //混色模式 0:一般 1:加法  
 public boolean flip_x; //反轉x 
 public boolean flip_y; //反轉y 
 public Texture texture; //貼圖 
 public boolean visible; //是否可見
 
 
 public Sprite(){	
	 
	 this.x=0;
	 this.y=0;
	 this.scale_x=1;
	 this.scale_y=1;
	 this.ox=0;
	 this.oy=0;
	 this.alpha=1f;
	 this.color=new Color(0,0,0,0); 
	 this.tone=new Tone();
	 this.rect=new Rectangle();
	 this.flip_x=false;
	 this.flip_y=false;
	 this.angle=0; 
	 this.texture=null;
	 this.blend_mode=0; 
	 this.visible=true;
	 
	 
 }
 

 
}