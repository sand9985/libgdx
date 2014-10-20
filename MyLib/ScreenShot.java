package MyLib;

/*
 #  ScreenShot ver 1.0
 #  作者:sand9985
 #  轉載請保留此標籤
 */
import java.nio.ByteBuffer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;


public class ScreenShot {

	// 拍照儲存整個窗口
	public static void snap_to_file(String path) {

		snap_to_file(path, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

	}

	// 拍照儲存檔案 依座標位置
	public static void snap_to_file(String path, int x, int y, int width,
			int height) {

		FileHandle fh;
		try {
			fh = new FileHandle(path);
		} catch (Exception e) {

			return;
		}		
		byte[]pixel_data=ScreenUtils.getFrameBufferPixels(x, y, width, height, true);
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		//反轉y 否則是顛倒
		ByteBuffer pixels = pixmap.getPixels();
		pixels.clear();
		pixels.put(pixel_data);
		pixels.position(0);	
		PixmapIO.writePNG(fh, pixmap);
		pixmap.dispose(); //釋放資源

	}
	//拍照 傳回T
	public static Texture snap_to_texture(){
		
    return snap_to_texture(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}
	
	//拍照 傳回Texture
	public static Texture snap_to_texture(int x,int y,int width,int height){
		
		byte[]pixel_data=ScreenUtils.getFrameBufferPixels(x, y, width, height, true);
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		//反轉y 否則是顛倒
		ByteBuffer pixels = pixmap.getPixels();
		pixels.clear();
		pixels.put(pixel_data);
		pixels.position(0);			
		Texture texture=new Texture(pixmap);		
		return texture;
	}

}
