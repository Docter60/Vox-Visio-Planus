/**
 * 
 */
package ui.pane;

import core.ResizeListener;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author Docter60
 *
 */
public abstract class VoxPane extends Pane implements ResizeListener{

	protected Label label;

	public VoxPane(String title, double x, double y, double width, double height) {
		super();
		this.setId("pane");
		this.relocate(x, y);
		this.setPrefSize(width, height);
		this.label = new Label(title);
		this.label.setId("text");
		this.getChildren().add(label);
	}

	public void setPosition(double x, double y) {
		this.relocate(x, y);
	}

	public void setSize(double width, double height) {
		this.resizeUpdate(width, height);
	}

	public void initializeElements() {
		this.label.relocate(this.getWidth() / 2.0 - this.label.getWidth() / 2.0, 3.0);
	}

	public abstract void addToScene(Group group);

}
