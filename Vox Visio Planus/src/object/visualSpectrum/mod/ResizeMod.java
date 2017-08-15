/**
 * 
 */
package object.visualSpectrum.mod;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * @author Docter60
 *
 */
public class ResizeMod {
	public interface OnResizeEventListener {
		void onResize(Node node, double x, double y, double h, double w);
	}

	private static final OnResizeEventListener defaultListener = new OnResizeEventListener() {
		@Override
		public void onResize(Node node, double x, double y, double h, double w) {
			setNodeSize(node, x, y, h, w);
		}

		private void setNodeSize(Node node, double x, double y, double h, double w) {
			node.setLayoutX(x);
			node.setLayoutY(y);
			if (node instanceof Rectangle) {
				Rectangle r = (Rectangle) node;
				r.setWidth(w);
				r.setHeight(h);
			}
		}
	};
	
	private static HashMap<Node, ResizeMod> listenerHandles = new HashMap<Node, ResizeMod>();

	public static enum S {
		DEFAULT, NW_RESIZE, SW_RESIZE, NE_RESIZE, SE_RESIZE, E_RESIZE, W_RESIZE, N_RESIZE, S_RESIZE;
	}

	private double nodeX, nodeY, nodeH, nodeW;

	private S state = S.DEFAULT;

	private Node node;
	private OnResizeEventListener listener = defaultListener;
	
	private EventHandler<MouseEvent> mousePressed;
	private EventHandler<MouseEvent> mouseDragged;
	private EventHandler<MouseEvent> mouseMoved;
	private EventHandler<MouseEvent> mouseReleased;

	private static final int MARGIN = 8;
	private static final double MIN_W = 30;
	private static final double MIN_H = 20;

	private ResizeMod(Node node, OnResizeEventListener listener) {
		this.node = node;
		if (listener != null)
			this.listener = listener;
		
		mousePressed = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mousePressed(event);
			}
		};
		
		mouseDragged = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseDragged(event);
			}
		};
		
		mouseMoved = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseOver(event);
			}
		};
		
		mouseReleased = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseReleased(event);
			}
		};
	}

	public static void makeResizable(Node node) {
		makeResizable(node, null);
	}

	public static void makeResizable(Node node, OnResizeEventListener listener) {
		final ResizeMod resizer = new ResizeMod(node, listener);
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, resizer.mousePressed);
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, resizer.mouseDragged);
		node.addEventHandler(MouseEvent.MOUSE_MOVED, resizer.mouseMoved);
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, resizer.mouseReleased);
		listenerHandles.put(node, resizer);
	}

	public static void makeUnresizeable(Node node) {
		if(listenerHandles.containsKey(node)) {
			node.setCursor(Cursor.DEFAULT);
			node.removeEventHandler(MouseEvent.MOUSE_PRESSED, listenerHandles.get(node).mousePressed);
			node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, listenerHandles.get(node).mouseDragged);
			node.removeEventHandler(MouseEvent.MOUSE_MOVED, listenerHandles.get(node).mouseMoved);
			node.removeEventHandler(MouseEvent.MOUSE_RELEASED, listenerHandles.get(node).mouseReleased);
			listenerHandles.remove(node);
		}
	}

	protected void mouseReleased(MouseEvent event) {
		node.setCursor(Cursor.DEFAULT);
		state = S.DEFAULT;
	}

	protected void mouseOver(MouseEvent event) {
		S state = currentMouseState(event);
		Cursor cursor = getCursorForState(state);
		node.setCursor(cursor);
	}

	private S currentMouseState(MouseEvent event) {
		S state = S.DEFAULT;
		boolean left = isLeftResizeZone(event);
		boolean right = isRightResizeZone(event);
		boolean top = isTopResizeZone(event);
		boolean bottom = isBottomResizeZone(event);

		if (left && top)
			state = S.NW_RESIZE;
		else if (left && bottom)
			state = S.SW_RESIZE;
		else if (right && top)
			state = S.NE_RESIZE;
		else if (right && bottom)
			state = S.SE_RESIZE;
		else if (right)
			state = S.E_RESIZE;
		else if (left)
			state = S.W_RESIZE;
		else if (top)
			state = S.N_RESIZE;
		else if (bottom)
			state = S.S_RESIZE;

		return state;
	}

	private static Cursor getCursorForState(S state) {
		switch (state) {
		case NW_RESIZE:
			return Cursor.NW_RESIZE;
		case SW_RESIZE:
			return Cursor.SW_RESIZE;
		case NE_RESIZE:
			return Cursor.NE_RESIZE;
		case SE_RESIZE:
			return Cursor.SE_RESIZE;
		case E_RESIZE:
			return Cursor.E_RESIZE;
		case W_RESIZE:
			return Cursor.W_RESIZE;
		case N_RESIZE:
			return Cursor.N_RESIZE;
		case S_RESIZE:
			return Cursor.S_RESIZE;
		default:
			return Cursor.DEFAULT;
		}
	}

	protected void mouseDragged(MouseEvent event) {

		if (listener != null) {
			double mouseX = parentX(event.getX());
			double mouseY = parentY(event.getY());
			if (state != S.DEFAULT) {
				// Resizing
				double newX = nodeX;
				double newY = nodeY;
				double newH = nodeH;
				double newW = nodeW;

				// Right Resize
				if (state == S.E_RESIZE || state == S.NE_RESIZE || state == S.SE_RESIZE) {
					newW = mouseX - nodeX;
				}
				// Left Resize
				if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE) {
					newX = mouseX;
					newW = nodeW + nodeX - newX;
				}

				// Bottom Resize
				if (state == S.S_RESIZE || state == S.SE_RESIZE || state == S.SW_RESIZE) {
					newH = mouseY - nodeY;
				}
				// Top Resize
				if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE) {
					newY = mouseY;
					newH = nodeH + nodeY - newY;
				}

				// Minimum valid rectangle size Check
				if (newW < MIN_W) {
					if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE)
						newX = newX - MIN_W + newW;
					newW = MIN_W;
				}

				if (newH < MIN_H) {
					if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE)
						newY = newY + newH - MIN_H;
					newH = MIN_H;
				}

				listener.onResize(node, newX, newY, newH, newW);
			}
		}
	}

	protected void mousePressed(MouseEvent event) {

		if (isInResizeZone(event)) {
			setNewInitialEventCoordinates(event);
			state = currentMouseState(event);
		} else {
			state = S.DEFAULT;
		}
	}

	private void setNewInitialEventCoordinates(MouseEvent event) {
		nodeX = nodeX();
		nodeY = nodeY();
		nodeH = nodeH();
		nodeW = nodeW();
	}

	private boolean isInResizeZone(MouseEvent event) {
		return isLeftResizeZone(event) || isRightResizeZone(event) || isBottomResizeZone(event)
				|| isTopResizeZone(event);
	}

	private boolean isLeftResizeZone(MouseEvent event) {
		return intersect(0, event.getX());
	}

	private boolean isRightResizeZone(MouseEvent event) {
		return intersect(nodeW(), event.getX());
	}

	private boolean isTopResizeZone(MouseEvent event) {
		return intersect(0, event.getY());
	}

	private boolean isBottomResizeZone(MouseEvent event) {
		return intersect(nodeH(), event.getY());
	}

	private boolean intersect(double side, double point) {
		return side + MARGIN > point && side - MARGIN < point;
	}

	private double parentX(double localX) {
		return nodeX() + localX;
	}

	private double parentY(double localY) {
		return nodeY() + localY;
	}

	private double nodeX() {
		return node.getBoundsInParent().getMinX();
	}

	private double nodeY() {
		return node.getBoundsInParent().getMinY();
	}

	private double nodeW() {
		return node.getBoundsInParent().getWidth();
	}

	private double nodeH() {
		return node.getBoundsInParent().getHeight();
	}

}
