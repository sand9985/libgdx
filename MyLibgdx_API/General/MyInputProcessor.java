package General;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {

	

	public boolean mouseMoved(int x, int y) {
		
		 
		return true;
	}
	
	public boolean touchDragged(int x, int y, int pointer) {
		
		
		return true;
	}
	
	public boolean touchDown(int x, int y, int pointer, int button) {
	
		
		return true;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button) {
		
		
		return true;
	}
	
	public boolean keyDown(int k) {
 
		if(k==Input.Keys.Z) Input_Event.keys_state[Input_Event.Z]=true;
	    if(k==Input.Keys.X) Input_Event.keys_state[Input_Event.X]=true;
		if(k==Input.Keys.LEFT) Input_Event.keys_state[Input_Event.LEFT]=true;
	    if(k==Input.Keys.RIGHT) Input_Event.keys_state[Input_Event.RIGHT]=true;
			
	    
	    
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k==Input.Keys.Z) Input_Event.keys_state[Input_Event.Z]=false;
	    if(k==Input.Keys.X) Input_Event.keys_state[Input_Event.X]=false;
		if(k==Input.Keys.LEFT) Input_Event.keys_state[Input_Event.LEFT]=false;
	    if(k==Input.Keys.RIGHT) Input_Event.keys_state[Input_Event.RIGHT]=false;
			
		return true;
	}
	
}