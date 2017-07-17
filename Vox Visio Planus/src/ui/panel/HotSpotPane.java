/**
 * 
 */
package ui.panel;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public abstract class HotSpotPane extends VoxPane {

	protected HotSpot hotSpot;
	protected boolean showPane;
	protected boolean isHorizontal;
	
	public HotSpotPane(String title, double x, double y, double width, double height, boolean isHorizontal) {
		super(title, x, y, width, height);
		this.hotSpot = new HotSpot(x, y, width, height);
		this.showPane = false;
		this.isHorizontal = isHorizontal;
		this.setHotSpotProperties(this.hotSpot);
		this.setHotSpotProperties(this);
	}
	
	public void hotSpotUpdate() {
		double oldW;
		double newW;
		double showW;
		
		if(isHorizontal) {
			oldW = this.getTranslateX();
			showW = this.getWidth();
		} else {
			oldW = this.getTranslateY();
			showW = this.getHeight();
		}
		
		if(showPane)
			newW = Mathg.lerp(oldW, showW, 0.07);
		else
			newW = Mathg.lerp(oldW, 0, 0.07);
		
		if(isHorizontal)
			this.setTranslateX(newW);
		else
			this.setTranslateY(newW);
	}
	
	@Override
	public void addToScene(Group group) {
		group.getChildren().add(this.hotSpot);
		group.getChildren().add(this);
	}
	
	public void setDebugMode(boolean isDebug) {
		if(isDebug) {
			this.hotSpot.setStroke(Color.WHITE);
		} else {
			this.hotSpot.setStroke(Color.TRANSPARENT);
		}
	}
	
	public void setShowPane(boolean show){
		this.showPane = show;
	}
	
	protected class HotSpot extends Rectangle {
		public HotSpot(double x, double y, double width, double height) {
			super(x, y, width, height);
			this.setFill(Color.TRANSPARENT);
			this.setStroke(Color.TRANSPARENT);
		}
	}
	
	private void setHotSpotProperties(Node node) {
		node.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				HotSpotPane.this.setShowPane(true);
			}
		});

		node.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				HotSpotPane.this.setShowPane(false);
			}
		});
	}

}
