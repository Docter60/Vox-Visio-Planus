/**
 * 
 */
package ui.element;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ui.panel.HotSpotPane;

/**
 * @author Docter60
 *
 */
public class HotSpot extends Rectangle{

	private HotSpotPane hsp;
	
	public HotSpot(double x, double y, double width, double height, HotSpotPane hsp){
		super(x, y, width, height);
		this.setFill(Color.TRANSPARENT);
		this.setStroke(Color.WHITE);
		this.hsp = hsp;
		setHotSpotProperties();
	}
	
	public HotSpotPane getHotSpotPane(){
		return hsp;
	}
	
	private void setHotSpotProperties() {
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getSource() instanceof HotSpot){
					HotSpotPane hsp = ((HotSpot) e.getSource()).getHotSpotPane();
					hsp.setShowPane(true);
				}
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getSource() instanceof HotSpot){
					HotSpotPane hsp = ((HotSpot) e.getSource()).getHotSpotPane();
					hsp.setShowPane(false);
				}
			}
		});
	}
	
}
