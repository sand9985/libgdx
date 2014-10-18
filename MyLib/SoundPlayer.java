package MyLib;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundPlayer {

	private static Music BGM = null; // 背景音樂
	private static Map<String, Sound> SEs = new HashMap<String, Sound>(); // 音效
	
	// 設定BGM
	public static void set_BGM(String path) {
		if (BGM != null) {
			BGM.dispose();
			BGM = Gdx.audio.newMusic(Gdx.files.internal(path));
		} else {
			BGM = Gdx.audio.newMusic(Gdx.files.internal(path));
		}
	}

	// 播放BGM
	public static void play_BGM(float volume) {

		if (BGM != null) {
			BGM.setLooping(true);
			BGM.setVolume(volume);
			BGM.play();
		}
	}

	// 暫停BGM
	public static void pause_BGM() {
		if (BGM != null)
			BGM.pause();
	}

	// 重新開始播放BGM
	public static void resume_BGM() {
		if (BGM != null)
			BGM.play();
	}

	// 停止BGM
	public static void stop_BGM() {
		if (BGM != null)
			BGM.stop();
	}

	// 釋放BGM
	public static void free_BGM() {
		if (BGM != null)
			BGM.dispose();
	}

	// 設定音效
	public static void set_SE(String key, String path) {
		Sound SE = Gdx.audio.newSound(Gdx.files.internal(path));
		SEs.put(key, SE);

	}
	// 撥放SE
	public static void play_SE(String key, float volume) {

		Sound SE = SEs.get(key);
		if (SE != null) {
			SE.play(volume);
		}
	}

	public static void pause_SE(String key) {
		Sound SE = SEs.get(key);
		if (SE != null) {
			SE.pause();
		}
	}
	// 回復音效
	public static void resume_SE(String key) {
		Sound SE = SEs.get(key);
		if (SE != null) {
			SE.resume();
		}
	}
	// 停止BGM
	public static void stop_SE(String key) {
		Sound SE = SEs.get(key);
		if (SE != null) {
			SE.stop();
		}

	}
	// 釋放音效
	public static void free_SE() {
		for (String key : SEs.keySet()) {
			SEs.get(key).dispose();
		}
		SEs.clear();

	}
	// 釋放使用的資源
	public static void free_resource() {

		free_BGM();
		free_SE();
	}

}