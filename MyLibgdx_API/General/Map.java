package General;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import Box2D.Box;


public class Map {
	
	public int height;
	public int width;
	public int tilewidth;
	public int tileheight;
	
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;

	public Map(){
		
		
	}
	public void update_map_info(){
		 height=(Integer)tiledMap.getProperties().get("height");
		 width=(Integer)tiledMap.getProperties().get("width");
	     tilewidth=(Integer)tiledMap.getProperties().get("tilewidth");
	     tileheight=(Integer)tiledMap.getProperties().get("tileheight");     
	     Map_Info.set_map(tilewidth, tileheight, width, height);
	     
	}
	
	public void load(String path){
		tiledMap = new TmxMapLoader().load(path);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		update_map_info();
				 
	}
	public ArrayList<Box> create_map_box(){
				
		ArrayList<Box> list=new ArrayList<Box>();
		
		MapLayer layer=this.tiledMap.getLayers().get("box");
		MapObjects objects = layer.getObjects();
		int size=objects.getCount();
		int max_y=tileheight*height;
		
		for(int i=0;i<size;i++){
	
			 MapObject obj=objects.get(i);
			
			 float x=(Float)obj.getProperties().get("x");
			 float y=(Float)obj.getProperties().get("y");
			 float width=(Float)obj.getProperties().get("width");
			 float height=(Float)obj.getProperties().get("height");
			 
			 Box b=new Box((int)x,(int)y,(int)width,(int)height);
			 list.add(b);
		}
		return list;
		
	}
	
	public void render(OrthographicCamera cam){
		
		this.tiledMapRenderer.setView(cam);
		this.tiledMapRenderer.render();
	}
	
	public void dispose(){		
		this.tiledMap.dispose();
		
	}
}
