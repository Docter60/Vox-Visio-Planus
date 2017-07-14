/**
 * 
 */
package ui.panel;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author Docter60
 *
 */
public abstract class VoxPane {

	protected Pane pane;
	protected Label label;
	
	public VoxPane(String title){
		this.pane = new Pane();
		this.pane.setId("pane");
		this.pane.setPrefSize(100, 100);
		this.setPosition(0, -this.pane.getPrefHeight());
		
		this.label = new Label(title);
		this.label.setId("text");
		this.pane.getChildren().add(label);
	}
	
	public void setPosition(double x, double y){
		this.pane.relocate(x, y);
	}
	
	public void setSize(double width, double height){
		this.pane.resize(width, height);
	}
	
	public Pane getPane(){
		return this.pane;
	}
	
	public void updateElements() {
		this.label.relocate(this.pane.getWidth() / 2.0 - this.label.getWidth() / 2.0, 3.0);
	}
	
}
