package MyLib;

/*
 #  ScreenShot ver 1.0
 #  �@��:sand9985
 #  ����ЫO�d������
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

	// ����x�s��ӵ��f
	public static void snap_to_file(String path) {

		snap_to_file(path, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

	}

	// ����x�s�ɮ� �̮y�Ц�m
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
		//����y �_�h�O�A��
		ByteBuffer pixels = pixmap.getPixels();
		pixels.clear();
		pixels.put(pixel_data);
		pixels.position(0);	
		PixmapIO.writePNG(fh, pixmap);
		pixmap.dispose(); //����귽

	}
	//��� �Ǧ^T
	public static Texture snap_to_texture(){
		
    return snap_to_texture(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}
	
	//��� �Ǧ^Texture
	public static Texture snap_to_texture(int x,int y,int width,int height){
		
		byte[]pixel_data=ScreenUtils.getFrameBufferPixels(x, y, width, height, true);
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		//����y �_�h�O�A��
		ByteBuffer pixels = pixmap.getPixels();
		pixels.clear();
		pixels.put(pixel_data);
		pixels.position(0);			
		Texture texture=new Texture(pixmap);		
		return texture;
	}

}
