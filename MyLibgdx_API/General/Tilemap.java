package General;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import Box2D.Box;
import Box2D.Box2D_Setting;



public class Tilemap   {

    public static Drawer drawer=null; //指標
	
	
	private TiledMap map; //地圖
	private TmxMapLoader loader; //讀取器
    private Integer width; //地圖寬度
    private Integer height; //地圖高度
    private Integer tilewidth; //地圖格子寬
    private Integer tileheight; //地圖格子寬
    private Sprite temp; //繪圖用
    

	public  float ox; //偏移x
    public  float oy; //偏移y
    public  Tone  tone; //色調   
    public  float scale_x; //放大率x
    public  float scale_y; //放大率y
    
 	public Tilemap(){
		map=null;
		temp=new Sprite();
		loader=new TmxMapLoader();
		//取得視窗大小
		

		this.tone=new Tone();
		this.scale_x=1;
		this.scale_y=1;

		
	}
	public ArrayList<Box> create_map_box(){
		
		ArrayList<Box> list=new ArrayList<Box>();
		
		MapLayer layer=this.map.getLayers().get("box");
		MapObjects objects = layer.getObjects();
		int size=objects.getCount();
		
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
	public void update_map_info(){
		 height=(Integer)map.getProperties().get("height");
		 width=(Integer)map.getProperties().get("width");
	     tilewidth=(Integer)map.getProperties().get("tilewidth");
	     tileheight=(Integer)map.getProperties().get("tileheight");     
	     Map_Info.set_map(tilewidth, tileheight, width, height);
	     
	}
 	//取得layer
 	public TiledMapTileLayer get_layer(String name){
 		
 	TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(name);
 		
 	return layer;
 	}
	//讀取地圖 (tmx格式)
	public void load(String file_name){
		
		if(map!=null){
			map.dispose();
			map=loader.load(file_name);						
		}else{
			map=loader.load(file_name);
		}
         //取得資訊
	     height=(Integer)map.getProperties().get("height");
		 width=(Integer)map.getProperties().get("width");
	     tilewidth=(Integer)map.getProperties().get("tilewidth");
	     tileheight=(Integer)map.getProperties().get("tileheight");
	     
	     update_map_info();
	     create_map_box();
	     
	}

	
	//繪製地圖
	public void render () {
		
	
		
		temp.tone=tone;
		for (MapLayer layer : map.getLayers()) {
			if (layer.isVisible()) {
				if (layer instanceof TiledMapTileLayer) {
					renderTileLayer((TiledMapTileLayer)layer);
				}
			}
		}
		
	}
	
    
	 //繪製圖層
	private void renderTileLayer (TiledMapTileLayer layer) {
		
		
	
		final float layerTileWidth = tilewidth*this.scale_x;
		final float layerTileHeight = tileheight*this.scale_y;
		//減少不用畫的範圍
		float left=Map_Info.camera_x-Map_Info.Window_width/2;
	    left*=Box2D_Setting.PPM;		
	   final int col1 = Math.max(0, (int)(left/ layerTileWidth));
		
	   
	   //right
		float right=Map_Info.camera_x+Map_Info.Window_width/2;
		right*=Box2D_Setting.PPM;
		right+=layerTileWidth ;
		
		
		final int col2 = Math.min(width, (int)(( right ) / layerTileWidth));		
		float bottom=Map_Info.camera_y-Map_Info.Window_height/2;
		bottom*=Box2D_Setting.PPM;
		final int row1 = Math.max(0, (int)(bottom / layerTileWidth));
		
		
		float top=Map_Info.camera_y+Map_Info.Window_height/2;
		top*=Box2D_Setting.PPM;
	    top+=layerTileHeight;	    
		final int row2 = Math.min(height, (int)(top / layerTileHeight));
	
		float xStart = col1 * layerTileWidth;
		float y = row2 * layerTileHeight;
		
		temp.alpha=layer.getOpacity(); //取得透明度		
		for (int row = row2; row >= row1; row--) {
			float x = xStart;
			for (int col = col1; col < col2; col++) {
				final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
				if (cell == null) {
					x += layerTileWidth;
					continue;
				}
				final TiledMapTile tile = cell.getTile();
				
				
				temp.flip_x = cell.getFlipHorizontally();
				temp.flip_y = cell.getFlipVertically();
				temp.angle = cell.getRotation();
              
				TextureRegion region = tile.getTextureRegion();

				temp.x= x +ox*scale_x;
				temp.y= y +oy*scale_y;
               
				temp.rect.x=region.getRegionX();
				temp.rect.height=region.getRegionHeight();
				temp.rect.width=region.getRegionWidth();
				temp.rect.y = region.getRegionY();
				
				temp.scale_x=1;
				temp.scale_y=1;
				temp.texture=region.getTexture();								
				drawer.draw(temp);				
				x += layerTileWidth;
			}
			y -= layerTileHeight;
		}
		

		
		
	}
	
	//釋放
	public void dispose(){
		
		if(map!=null)
		 map.dispose();
	}
}