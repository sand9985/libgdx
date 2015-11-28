package General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import Box2D.Box2D_Setting;
public class Map_Info {

	public static final float Window_width=Gdx.graphics.getWidth()/Box2D_Setting.PPM;;
	public static final float Window_height=Gdx.graphics.getHeight()/Box2D_Setting.PPM;
	public static float max_width;
	public static float max_height;
	private static float camera_x=Window_width/2;
	private static float camera_y=Window_height/2;
	
	
	public static void reset(){
		 camera_x=Window_width/2;
	     camera_y=Window_height/2;
	}
 
	public static void set_map(int grid_width,int grid_height,int w,int h){
		
		float width=(grid_width*w)/Box2D_Setting.PPM;
		float height=(grid_height*h)/Box2D_Setting.PPM;
		max_height=height;
		max_width=width;
	}
	
	public static Vector2 center(Body body){
         Vector2 p= body.getPosition();
		   
		   float dx=Map_Info.camera_x-p.x;
		   float dy=Map_Info.camera_y-p.y;
		   float half_w_w=( Window_width/2);
		   float half_h_h=( Window_height/2); 
		 
		   Map_Info.camera_x=p.x;
		   Map_Info.camera_y=p.y;
		   
		   float left=p.x-half_w_w;
		   float right=p.x+half_w_w;		   
		   if(left<0){dx+=left; Map_Info.camera_x-=left;}
		   if(right>max_width){ dx+=(right-max_width); Map_Info.camera_x-=(right- Map_Info.max_width); }
		          
             
		   float bottom=p.y-half_h_h;
		   float top=p.y+half_h_h;
		   if(bottom<0){ dy+=bottom; Map_Info.camera_y-=bottom;}
	       if(top>max_height){ dy+=(top-max_height); Map_Info.camera_y-=(top-Map_Info.max_height); } 
           
	       return new  Vector2(dx,dy);
	       
	}
}
