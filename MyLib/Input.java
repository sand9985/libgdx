package MyLib;

public class Input {

	public static int mouse_x;
	public static int mouse_y;


	public static boolean[] keys;
	public static boolean[] pkeys;

	private static final int NUM_KEYS = 6; // 多少使用的鍵盤
	// 按鍵定義
	public static final int Z = 0;
	public static final int X = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int UP = 4;
	public static final int DOWN = 5;

	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];

	}

	// 更新鍵盤資訊
	public static void update() {
	
		for (int i = 0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}

	// 設定keys
	public static void setKey(int i, boolean b) {
		keys[i] = b;
		
	}

	// 鍵盤按一下
	public static boolean trigger(int i) {
		return keys[i] && !pkeys[i];
	}

	// 連續按下
	public static boolean repeat(int i) {
		return keys[i];
	}
}
