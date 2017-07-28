/**
 * 
 */
package window.mod;

import java.util.HashMap;

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
	
	public RestoreMod(WindowPane wp, KeyCode kc) {
		this.wp = wp;
		this.kc = kc;
		createAccelerator();
	}
	
	public static void createRestoreMod(WindowPane wp, KeyCode kc) {
		listenerHandles.put(wp, new RestoreMod(wp, kc));
	}
	
	public static void removeRestoreMod(WindowPane wp) {
		wp.getScene().getAccelerators().remove(listenerHandles.get(wp).keyCodeCombination);
		listenerHandles.remove(wp);
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
			}
		}
	}
	
}
