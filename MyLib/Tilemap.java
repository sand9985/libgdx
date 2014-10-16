package MyLib;
/*
#===================================
#
#  Drawer ver 1.0
#  作者:sand9985
#  轉載請保留此標籤
#==================================== 
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Rectangle;

public class Tilemap   {

    public static Drawer drawer=null; //指標
	private TiledMap map; //地圖
	private TmxMapLoader loader; //讀取器
    private Integer width; //地圖寬度
    private Integer height; //地圖高度
    private Integer tilewidth; //地圖格子寬
    private Integer tileheight; //地圖格子寬
	private int view_width;  //視窗寬度
	private int view_height; //視窗高度
    private Sprite temp;
    
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
		this.view_width=Gdx.graphics.getWidth();
		this.view_height=Gdx.graphics.getHeight();
		this.ox=0;
		this.oy=0;
		this.tone=new Tone();
		this.scale_x=1;
		this.scale_y=1;
		
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
	     
	    
	     
	}

	
	//繪製地圖
	public void render () {
		
		AnimatedTiledMapTile.updateAnimationBaseTime();
		temp.tone=tone;
		for (MapLayer layer : map.getLayers()) {
			if (layer.isVisible()) {
				if (layer instanceof TiledMapTileLayer) {
					renderTileLayer((TiledMapTileLayer)layer);
				}
			}
		}
		
	}
	
    
	
	private void renderTileLayer (TiledMapTileLayer layer) {
		
	
		final float layerTileWidth = tilewidth * this.scale_x;
		final float layerTileHeight = tileheight *this.scale_y;
		
		//減少不用畫的範圍
		final int col1 = Math.max(0, (int)(this.ox / layerTileWidth));
		final int col2 = Math.min(width, (int)(( this.ox+view_width + layerTileWidth) / layerTileWidth));
		final int row1 = Math.max(0, (int)(this.oy / layerTileWidth));
		final int row2 = Math.min(height, (int)((this.oy+ view_height + layerTileHeight) / layerTileHeight));
		
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

				temp.x= x + tile.getOffsetX() * this.scale_x-ox*scale_x;
				temp.y= y + tile.getOffsetY() * this.scale_y-oy*scale_y;
			
				temp.rect.x=region.getRegionX();
				temp.rect.height=region.getRegionHeight();
				temp.rect.width=region.getRegionWidth();
				temp.rect.y = region.getRegionY();
				
				temp.scale_x=this.scale_x;
				temp.scale_y=this.scale_y;
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
