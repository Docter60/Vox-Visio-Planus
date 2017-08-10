/**
 * 
 */
package window.mod;

import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import window.WindowPane;

/**
 * @author Docter60
 *
 */
public class RestoreMod {
	
	private static HashMap<WindowPane, RestoreMod> listenerHandles = new HashMap<WindowPane, RestoreMod>();
	
	private WindowPane wp;
	private KeyCode kc;
	private KeyCodeCombination keyCodeCombination;
	private SceneChangeListener sceneChangeListener;
	
	public RestoreMod(WindowPane wp, KeyCode kc) {
		this.wp = wp;
		this.kc = kc;
		this.sceneChangeListener = new SceneChangeListener();
		wp.getMainPane().sceneProperty().addListener(this.sceneChangeListener);
	}
	
	public static void createRestoreMod(WindowPane wp, KeyCode kc) {
		listenerHandles.put(wp, new RestoreMod(wp, kc));
	}
	
	public static void removeRestoreMod(WindowPane wp) {
		if(listenerHandles.containsKey(wp)){
			wp.getScene().getAccelerators().remove(listenerHandles.get(wp).keyCodeCombination);
			listenerHandles.remove(wp);
		}
	}
	
	private void createAccelerator() {
		this.keyCodeCombination = new KeyCodeCombination(this.kc, KeyCombination.SHORTCUT_DOWN);
		this.wp.getScene().getAccelerators().put(keyCodeCombination, new Runner());
	}
	
	private class Runner implements Runnable {
		@Override
		public void run() {
			if(RestoreMod.this.wp.isVisible()) {
				RestoreMod.this.wp.setVisible(false);
			} else {
				RestoreMod.this.wp.setVisible(true);
				RestoreMod.this.wp.toFront();
			}
		}
	}
	
	private class SceneChangeListener implements ChangeListener<Scene> {
		@Override
		public void changed(ObservableValue<? extends Scene> obs, Scene oldVal, Scene newVal) {
			RestoreMod.this.createAccelerator();
		}
	}
}
