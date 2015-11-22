package Box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
public class Map_Info {

	public static final float Window_width=Gdx.graphics.getWidth()/Box2D_Setting.PPM;;
	public static final float Window_height=Gdx.graphics.getHeight()/Box2D_Setting.PPM;
	public static float max_width=7.36f;
	public static float max_height=4.8f;
	public static float camera_x=3.2f;
	public static float camera_y=2.4f;
	
  
	
	public static Vector2 center(Body body){
         Vector2 p= body.getPosition();
		   
		   float dx=Map_Info.camera_x-p.x;
		   float dy=Map_Info.camera_y-p.y;
		   float half_w_w=( Map_Info.Window_width/2);
		   float half_h_h=( Map_Info.Window_height/2); 
		 
		   Map_Info.camera_x=p.x;
		   Map_Info.camera_y=p.y;
		   
		   float left=p.x-half_w_w;
		   float right=p.x+half_w_w;		   
		   if(left<0){dx+=left; Map_Info.camera_x-=left;}
		   if(right>Map_Info.max_width){ dx+=(right-Map_Info.max_width); Map_Info.camera_x-=(right- Map_Info.max_width); }
		   
		   float bottom=p.y-half_h_h;
		   float top=p.y+half_h_h;
		   if(bottom<0){ dy+=bottom; Map_Info.camera_y-=bottom;}
	       if(top>Map_Info.max_height){ dy+=(top-Map_Info.max_height); Map_Info.camera_y-=(top-Map_Info.max_height); } 
           
	       return new  Vector2(dx,dy);
	       
	}
}
