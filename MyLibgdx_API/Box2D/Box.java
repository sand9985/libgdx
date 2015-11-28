package Box2D;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Box {

	public Body body;
	
	public Box(int x,int y,int width,int height){
								
		BodyDef bdef = new BodyDef();	    
		width/=2;
		height/=2;
		x+=width;
		y+=height;
		create_body(x,y);	
	    create_polygonShape(width,height);
	    
		
	}
     protected void create_body(int x,int y){
		
		BodyDef bdef = new BodyDef();
		//PPM =100.0f
    	bdef.position.set(x/ Box2D_Setting.PPM, y / Box2D_Setting.PPM);
    	bdef.type = BodyType.StaticBody;
        body = Box2D_Setting.p_world.createBody(bdef);//it is point to your box2d world.                        
        
	}
     protected void create_polygonShape(int width,int height){
 		
 	    PolygonShape shape = new PolygonShape();		
 	      FixtureDef fdef ;
 	    //it just need half width  and half height.
     	shape.setAsBox(width/Box2D_Setting.PPM, height/Box2D_Setting.PPM); 
     	fdef = new FixtureDef();
 		fdef.shape = shape;
 		body.createFixture(fdef); 	
 	    shape.dispose();
 		
 	}
	public void dispose(){
		Box2D_Setting.p_world.destroyBody(this.body);
		
	}
     
}
