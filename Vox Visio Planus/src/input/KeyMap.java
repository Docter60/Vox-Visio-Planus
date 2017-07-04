package input;

import java.awt.event.KeyEvent;
import java.util.HashMap;

@SuppressWarnings("serial")
public class KeyMap extends HashMap<Integer, Key> {
	
	public KeyMap() {
		this.put(KeyEvent.VK_ESCAPE, Key.ESC);
		this.put(KeyEvent.VK_F4, Key.F4);
		this.put(KeyEvent.VK_L, Key.L);
		this.put(KeyEvent.VK_K, Key.K);
	}
}
