package General;
import com.badlogic.gdx.Input.Keys;
public class Input_Event {

	
	public  final static int X=0;
	public  final static int Z=1;	
	public  final static int UP=2;
	public  final static int DOWN=3;
	public  final static int LEFT=4;
	public  final static int RIGHT=5;
	
	public final static  int SIZE=6;
	public static boolean []keys_state=new boolean[SIZE];
	public static boolean []keys_buffer=new boolean[SIZE]; //previous state
	
	public static boolean trigger(int key){
		
		if(keys_state[key]==true && keys_buffer[key]==false) return true;
		
		return false;
		
	}	
	public static boolean repeat(int key){
				
		return keys_state[key];
	}
	public static void update(){
		
		for(int i=0 ; i<SIZE ; i++)
		    keys_buffer[i]=keys_state[i];				
	}
	
}
