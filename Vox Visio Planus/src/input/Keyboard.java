package input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class Keyboard extends Component implements KeyListener {

	private static Map<Integer, Key> keyMap = new KeyMap();
	private static Set<Key> currentKeys = new HashSet<Key>();
	private static Set<Key> currentKeyPulse = new HashSet<Key>();
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		Key key = keyMap.get(keyCode);
		currentKeys.add(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		Key key = keyMap.get(keyCode);
		currentKeys.remove(key);
		currentKeyPulse.add(key);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public static void clearkeyPulse(){
		currentKeyPulse.clear();
	}
	
	public static Set<Key> getCurrentKeys() {
		return currentKeys;
	}
	
	public static boolean keyIsPressed(Key k) {
		return currentKeys.contains(k);
	}

	public static boolean keyIsPulsed(Key k){
		return currentKeyPulse.contains(k);
	}
	
}
