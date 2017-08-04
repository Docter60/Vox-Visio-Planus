/**
 * 
 */
package window.mod;

import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import window.WindowPane;

/**
 * Needs to remember offset from mouse when the window is docked. When the
 * window has mouse down and is docked, record mouse movement. When window is in
 * docking range, dock and record mouse displacement from when first docked.
 * 
 * @author Docter60
 *
 */
public class SnapMod {
	public static final double MARGIN = 10.0;

	private static HashMap<WindowPane, SnapMod> listenerHandles = new HashMap<WindowPane, SnapMod>();

	public static enum Snap {
		DEFAULT, NW_SNAP, NE_SNAP, SW_SNAP, SE_SNAP, W_SNAP, N_SNAP, E_SNAP, S_SNAP
	};

	private Snap state = Snap.DEFAULT;

	private Scene scene;
	private WindowPane wp;

	private SceneChangeListener sceneChangeListener;
	private XListener xListener;
	private YListener yListener;

	private SnapMod(WindowPane wp) {
		this.wp = wp;

		this.sceneChangeListener = new SceneChangeListener();
		this.xListener = new XListener();
		this.yListener = new YListener();
		
		if(this.wp.getScene() != null) {
			this.scene = wp.getScene();
			this.wp.getMainPane().layoutXProperty().addListener(SnapMod.this.xListener);
			this.wp.getMainPane().layoutYProperty().addListener(SnapMod.this.yListener);
		}

		wp.getMainPane().sceneProperty().addListener(this.sceneChangeListener);
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
	
	public static Snap getSnapState(WindowPane wp) {
		return listenerHandles.get(wp).state;
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

	private void handleWindowState() {
		switch (this.state) {
		case NW_SNAP:
			setSnapPosition(0, 0);
			break;
		case SW_SNAP:
			setSnapPosition(0, wp.getScene().getHeight() - wp.getMainPane().getPrefHeight());
			break;
		case NE_SNAP:
			setSnapPosition(wp.getScene().getWidth() - wp.getMainPane().getPrefWidth(), 0);
			break;
		case SE_SNAP:
			setSnapPosition(wp.getScene().getWidth() - wp.getMainPane().getPrefWidth(), wp.getScene().getHeight() - wp.getMainPane().getPrefHeight());
			break;
		case E_SNAP:
			wp.getMainPane().setLayoutX(wp.getScene().getWidth() - wp.getMainPane().getPrefWidth());
			break;
		case W_SNAP:
			wp.getMainPane().setLayoutX(0);
			break;
		case N_SNAP:
			wp.getMainPane().setLayoutY(0);
			break;
		case S_SNAP:
			wp.getMainPane().setLayoutY(wp.getScene().getHeight() - wp.getMainPane().getPrefHeight());
			break;
		default:
			break;
		}
	}
	
	private void setSnapPosition(double x, double y) {
		this.wp.getMainPane().setLayoutX(x);
		this.wp.getMainPane().setLayoutY(y);
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
		return side + MARGIN > point && side - MARGIN < point;
	}
	
	private class SceneChangeListener implements ChangeListener<Scene> {
		@Override
		public void changed(ObservableValue<? extends Scene> obs, Scene oldVal, Scene newVal) {
			SnapMod.this.scene = newVal;
			SnapMod.this.wp.getMainPane().layoutXProperty().addListener(SnapMod.this.xListener);
			SnapMod.this.wp.getMainPane().layoutYProperty().addListener(SnapMod.this.yListener);
		}
	}

	private class XListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			SnapMod.this.currentWindowState();
			SnapMod.this.handleWindowState();
		}
	}

	private class YListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			SnapMod.this.currentWindowState();
			SnapMod.this.handleWindowState();
		}
	}

}
