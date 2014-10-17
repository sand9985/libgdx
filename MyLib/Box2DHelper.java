package MyLib;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

/*
 * 
 * bool collide =
 (filterA.maskBits & filterB.categoryBits) != 0 &&
 (filterA.categoryBits & filterB.maskBits) != 0;
 */

public class Box2DHelper {

	public static World p_world = null; // 指定哪一個world
	public static final float PPM = 100; //一公尺
	
	// 設定圖層碰撞範圍
	public static void set_bound_box(Tilemap map, String layer_name) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		TiledMapTileLayer layer = map.get_layer(layer_name); //取得layer
		float tileheight = layer.getTileHeight(); //取得方格大小
		float tilewidth = layer.getTileWidth();
		float half_width = tilewidth / 2.0f; 
		float half_height = tileheight / 2.0f;
		//優化
        float ppm_half1=half_width/PPM;
        float ppm_half2=half_height/PPM;
        
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				Cell cell = layer.getCell(col, row);

				if (cell == null)
					continue;
				if (cell.getTile() == null)
					continue;
				//靜態物件
				bdef.type = BodyType.StaticBody;
				bdef.position.x = (col * tilewidth + half_width)/PPM;
				bdef.position.y = (row * tileheight + half_height)/PPM;			
				
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(- ppm_half1, -ppm_half2);
				v[1] = new Vector2(- ppm_half1, ppm_half2);
				v[2] = new Vector2(ppm_half1, ppm_half2);
				v[3] = new Vector2(ppm_half1, -ppm_half2);
				v[4]=v[0];
				cs.createChain(v);
				fdef.shape =cs;
				fdef.friction = 0; //摩擦力
				p_world.createBody(bdef).createFixture(fdef);
                cs.dispose();
			
			}

		}

	} //end
	

}
