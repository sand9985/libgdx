package MyLib;
/*
#===================================
#
#  Drawer ver 1.0
#  �@��:sand9985
#  ����ЫO�d������
#==================================== 
 */

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;



public class Tilemap   {

    public static Drawer drawer=null; //����
	public static int view_width;  //�����e��
	public static int view_height; //��������
	
	private TiledMap map; //�a��
	private TmxMapLoader loader; //Ū����
    private Integer width; //�a�ϼe��
    private Integer height; //�a�ϰ���
    private Integer tilewidth; //�a�Ϯ�l�e
    private Integer tileheight; //�a�Ϯ�l�e
    private Sprite temp; //ø�ϥ�
    
    public  boolean visible; //�O�_�i�� 
	public  float ox; //����x
    public  float oy; //����y
    public  Tone  tone; //���   
    public  float scale_x; //��j�vx
    public  float scale_y; //��j�vy
    
 	public Tilemap(){
		map=null;
		temp=new Sprite();
		loader=new TmxMapLoader();
		//���o�����j�p
		
		this.ox=0;
		this.oy=0;
		this.tone=new Tone();
		this.scale_x=1;
		this.scale_y=1;
		this.visible=true;
		
	}
 	//���olayer
 	public TiledMapTileLayer get_layer(String name){
 		
 	TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(name);
 		
 	return layer;
 	}
	//Ū���a�� (tmx�榡)
	public void load(String file_name){
		
		if(map!=null){
			map.dispose();
			map=loader.load(file_name);						
		}else{
			map=loader.load(file_name);
		}
         //���o��T
	     height=(Integer)map.getProperties().get("height");
		 width=(Integer)map.getProperties().get("width");
	     tilewidth=(Integer)map.getProperties().get("tilewidth");
	     tileheight=(Integer)map.getProperties().get("tileheight");
	     
	    
	     
	}

	
	//ø�s�a��
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
	
    
	 //ø�s�ϼh
	private void renderTileLayer (TiledMapTileLayer layer) {
		
	
		final float layerTileWidth = tilewidth * this.scale_x;
		final float layerTileHeight = tileheight *this.scale_y;
		
		//��֤��εe���d��
		final int col1 = Math.max(0, (int)(-this.ox / layerTileWidth));
		final int col2 = Math.min(width, (int)(( -this.ox+view_width + layerTileWidth) / layerTileWidth));
		final int row1 = Math.max(0, (int)(-this.oy / layerTileWidth));
		final int row2 = Math.min(height, (int)((-this.oy+ view_height + layerTileHeight) / layerTileHeight));
	
		float xStart = col1 * layerTileWidth;
		float y = row2 * layerTileHeight;
		
		temp.alpha=layer.getOpacity(); //���o�z����
		
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

				temp.x= x + tile.getOffsetX() * this.scale_x+ox*scale_x;
				temp.y= y + tile.getOffsetY() * this.scale_y+oy*scale_y;

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
	
	//����
	public void dispose(){
		
		if(map!=null)
		 map.dispose();
	}
}
