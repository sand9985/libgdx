package MyLib;
/*
  
#===================================
#
#  MyInputProcessor ver 1.0
#  作者:sand9985
#  轉載請保留此標籤
#==================================== 

  
 */
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {

	

	public boolean mouseMoved(int x, int y) {
		 Input.mouse_x=x;
		 Input.mouse_y=y;
		 
		return true;
	}
	
	public boolean touchDragged(int x, int y, int pointer) {
		Input.mouse_x=x;
		Input.mouse_y=y;
		
		return true;
	}
	
	public boolean touchDown(int x, int y, int pointer, int button) {
		Input.mouse_x=x;
		Input.mouse_y=y;
		
		
		return true;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button) {
		Input.mouse_x=x;
		Input.mouse_y=y;
		
		return true;
	}
	
	public boolean keyDown(int k) {
		if(k == Keys.Z) Input.setKey(Input.Z, true);
		else if(k == Keys.X) Input.setKey(Input.X, true); 
		else if(k==Keys.LEFT)Input.setKey(Input.LEFT, true);
		else if(k==Keys.RIGHT)Input.setKey(Input.RIGHT, true);
		else if(k==Keys.UP)Input.setKey(Input.UP, true);
		else if(k==Keys.DOWN)Input.setKey(Input.DOWN, true);
		
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.Z) Input.setKey(Input.Z, false);
		else if(k == Keys.X) Input.setKey(Input.X, false);
		else if(k==Keys.LEFT)Input.setKey(Input.LEFT, false);
		else if(k==Keys.RIGHT)Input.setKey(Input.RIGHT, false);
		else if(k==Keys.UP)Input.setKey(Input.UP, false);
		else if(k==Keys.DOWN)Input.setKey(Input.DOWN, false);
		
	
		return true;
	}
	
}
