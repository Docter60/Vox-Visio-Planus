/**
 * 
 */

package object.visualSpectrum.mod;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author Docter60
 *
 */
public class DragMod {
	public interface OnDragEventListener {
		void onDrag(Node node, double x, double y, double h, double w);
	}

	private static final OnDragEventListener defaultListener = new OnDragEventListener() {
		@Override
		public void onDrag(Node node, double x, double y, double h, double w) {
			setNodeSize(node, x, y, h, w);
		}

		private void setNodeSize(Node node, double x, double y, double h, double w) {
			node.setLayoutX(x);
			node.setLayoutY(y);
		}
	};

	private static final double MARGIN = 8.0;
	public static enum S {
		DEFAULT, DRAG;
	}

	private double clickX, clickY, nodeH, nodeW;

	private S state = S.DEFAULT;

	private Node node;
	private OnDragEventListener listener = defaultListener;

	private DragMod(Node node, OnDragEventListener listener) {
		this.node = node;
		if (listener != null)
			this.listener = listener;
	}

	public static void makeDraggable(Node node) {
		makeDraggable(node, null);
	}

	public static void makeDraggable(Node node, OnDragEventListener listener) {
		final DragMod resizer = new DragMod(node, listener);

		node.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resizer.mousePressed(event);
			}
		});
		node.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resizer.mouseDragged(event);
			}
		});
		node.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resizer.mouseReleased(event);
			}
		});
	}
	
	public static void makeUndraggable(Node node) {
		node.setOnMousePressed(null);
		node.setOnMouseDragged(null);
		node.setOnMouseReleased(null);
	}

	protected void mouseReleased(MouseEvent event) {
		node.setCursor(Cursor.DEFAULT);
		state = S.DEFAULT;
	}

	protected void mouseDragged(MouseEvent event) {

		if (listener != null) {
			double mouseX = parentX(event.getX());
			double mouseY = parentY(event.getY());
			if (state == S.DRAG) {
				listener.onDrag(node, mouseX - clickX, mouseY - clickY, nodeH, nodeW);
			}
		}
	}

	protected void mousePressed(MouseEvent event) {

		if (isInDragZone(event)) {
			setNewInitialEventCoordinates(event);
			state = S.DRAG;
		} else {
			state = S.DEFAULT;
		}
	}

	private void setNewInitialEventCoordinates(MouseEvent event) {
		nodeH = nodeH();
		nodeW = nodeW();
		clickX = event.getX();
		clickY = event.getY();
	}

	private boolean isInDragZone(MouseEvent event) {
		double xPos = parentX(event.getX());
		double yPos = parentY(event.getY());
		double nodeX = nodeX() + MARGIN;
		double nodeY = nodeY() + MARGIN;
		double nodeX0 = nodeX() + nodeW() - MARGIN;
		double nodeY0 = nodeY() + nodeH() - MARGIN;

		return (xPos > nodeX && xPos < nodeX0) && (yPos > nodeY && yPos < nodeY0);
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
