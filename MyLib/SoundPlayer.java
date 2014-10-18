package MyLib;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundPlayer {

	private static Music BGM = null; // �I������
	private static Map<String, Sound> SEs = new HashMap<String, Sound>(); // ����
	
	// �]�wBGM
	public static void set_BGM(String path) {
		if (BGM != null) {
			BGM.dispose();
			BGM = Gdx.audio.newMusic(Gdx.files.internal(path));
		} else {
			BGM = Gdx.audio.newMusic(Gdx.files.internal(path));
		}
	}

	// ����BGM
	public static void play_BGM(float volume) {

		if (BGM != null) {
			BGM.setLooping(true);
			BGM.setVolume(volume);
			BGM.play();
		}
	}

	// �Ȱ�BGM
	public static void pause_BGM() {
		if (BGM != null)
			BGM.pause();
	}

	// ���s�}�l����BGM
	public static void resume_BGM() {
		if (BGM != null)
			BGM.play();
	}

	// ����BGM
	public static void stop_BGM() {
		if (BGM != null)
			BGM.stop();
	}

	// ����BGM
	public static void free_BGM() {
		if (BGM != null)
			BGM.dispose();
	}

	// �]�w����
	public static void set_SE(String key, String path) {
		Sound SE = Gdx.audio.newSound(Gdx.files.internal(path));
		SEs.put(key, SE);

	}
	// ����SE
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
	// �^�_����
	public static void resume_SE(String key) {
		Sound SE = SEs.get(key);
		if (SE != null) {
			SE.resume();
		}
	}
	// ����BGM
	public static void stop_SE(String key) {
		Sound SE = SEs.get(key);
		if (SE != null) {
			SE.stop();
		}

	}
	// ���񭵮�
	public static void free_SE() {
		for (String key : SEs.keySet()) {
			SEs.get(key).dispose();
		}
		SEs.clear();

	}
	// ����ϥΪ��귽
	public static void free_resource() {

		free_BGM();
		free_SE();
	}

}