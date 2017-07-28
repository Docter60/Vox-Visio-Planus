/**
 * 
 */
package window.mod;

import java.util.EventListener;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import window.WindowPane;
import window.mod.ResizeMod.S;

/**Needs to remember offset from mouse when the window is docked.
 * When the window has mouse down and is docked, record mouse movement.
 * When window is in docking range, dock and record mouse displacement from when first docked.
 * 
 * @author Docter60
 *
 */
public class SnapMod {
	public static final double MARGIN = 10.0;
	
	private static HashMap<WindowPane, SnapMod> listenerHandles = new HashMap<WindowPane, SnapMod>();
	
	private static enum Snap {DEFAULT, NW_SNAP, NE_SNAP, SW_SNAP, SE_SNAP, W_SNAP, N_SNAP, E_SNAP, S_SNAP};

	private Snap state = Snap.DEFAULT;
	
	private Scene scene;
	private WindowPane wp;
	
	private double mouseX;
	private double mouseY;
	
	private XListener xListener;
	private YListener yListener;
	
	private SnapMod(WindowPane wp) {
		this.wp = wp;
		this.scene = wp.getScene();
		
		this.xListener = new XListener();
		this.yListener = new YListener();
		
		wp.getMainPane().layoutXProperty().addListener(this.xListener);
		wp.getMainPane().layoutYProperty().addListener(this.yListener);
	}
	
	public static void setSnappable(WindowPane wp) {
		final SnapMod snapMod = new SnapMod(wp);
		listenerHandles.put(wp, snapMod);
	}
	
	public static void setUnsnappable(WindowPane wp) {
		wp.getMainPane().layoutXProperty().removeListener(listenerHandles.get(wp).xListener);
		wp.getMainPane().layoutYProperty().removeListener(listenerHandles.get(wp).yListener);
		listenerHandles.remove(wp);
	}
	
	private void currentWindowState() {
		Snap state = Snap.DEFAULT;
		
		boolean left = isLeftSnapZone();
		boolean right = isRightSnapZone();
		boolean top = isTopSnapZone();
		boolean bottom = isBottomSnapZone();

		if (left && top)
			state = Snap.NW_SNAP;
		else if (left && bottom)
			state = Snap.SW_SNAP;
		else if (right && top)
			state = Snap.NE_SNAP;
		else if (right && bottom)
			state = Snap.SE_SNAP;
		else if (right)
			state = Snap.E_SNAP;
		else if (left)
			state = Snap.W_SNAP;
		else if (top)
			state = Snap.N_SNAP;
		else if (bottom)
			state = Snap.S_SNAP;
		this.state = state;
	}
	
	private void recordMousePosition(MouseEvent e) {
		this.mouseX = e.getSceneX();
		this.mouseY = e.getSceneY();
	}

	private boolean isLeftSnapZone() {
		return intersect(0, wp.getMainPane().getLayoutX());
	}

	private boolean isRightSnapZone() {
		return intersect(scene.getWidth(), wp.getMainPane().getLayoutX() + wp.getMainPane().getPrefWidth());
	}

	private boolean isTopSnapZone() {
		return intersect(0, wp.getMainPane().getLayoutY());
	}

	private boolean isBottomSnapZone() {
		return intersect(scene.getHeight(), wp.getMainPane().getLayoutY() + wp.getMainPane().getPrefHeight());
	}

	private boolean intersect(double side, double point) {
		return side + MARGIN > point && side - MARGIN < point; // May need to alter this
	}
	
	private class XListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			SnapMod.this.currentWindowState();
		}
	}
	
	private class YListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			SnapMod.this.currentWindowState();
		}
	}
	
	private class MouseListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			boolean inMargin = SnapMod.this.state == Snap.DEFAULT;
			if(e.isPrimaryButtonDown() && inMargin)
				SnapMod.this.recordMousePosition(e); // hmm...
		}
		
	}
	
}
