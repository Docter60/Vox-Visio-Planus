/**
 * 
 */
package ui.panel;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import ui.element.HotSpot;

/**
 * @author Docter60
 *
 */
public abstract class HotSpotPane extends VoxPane {

	protected HotSpot hotSpot;
	protected boolean showPane;
	
	public HotSpotPane(String title, double x, double y, double width, double height) {
		super(title, x, y, width, height);
		this.hotSpot = new HotSpot(x, y, width, height, this);
		this.showPane = false;
		setHotSpotProperties();
	}
	
	public abstract void hotSpotUpdate();
	
	@Override
	public void addToScene(Group group) {
		group.getChildren().add(this.hotSpot);
		group.getChildren().add(this);
	}
	
	private void setHotSpotProperties() {
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getSource() instanceof HotSpotPane){
					HotSpotPane hsp = ((HotSpotPane) e.getSource());
					hsp.setShowPane(true);
				}
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getSource() instanceof HotSpotPane){
					HotSpotPane hsp = ((HotSpotPane) e.getSource());
					hsp.setShowPane(false);
				}
			}
		});
	}
	
	public void setShowPane(boolean show){
		this.showPane = show;
	}

}
