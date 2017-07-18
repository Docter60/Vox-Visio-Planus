/**
 * 
 */
package ui.pane;

import core.GUIManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * @author Docter60
 *
 */
public class SinglePlayPane extends VerticalHotSpotPane {
	public static final double WIDTH = 80;
	public static final double HEIGHT = 50;
	
	private GUIManager guiManager;
	
	private Button openButton;

	public SinglePlayPane(Scene primaryScene, GUIManager guiManager) {
		super("Single Play", 0, 0, WIDTH, HEIGHT);
		this.guiManager = guiManager;
		
		this.relocate(0, -HEIGHT);

		this.openButton = new Button("Open");
		this.openButton.setOnAction(new OpenButtonEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				SinglePlayPane.this.guiManager.showOpenDialog();
			}
		});
		this.getChildren().add(openButton);
	}

	@Override
	public void initializeElements() {
		super.initializeElements();
		this.openButton.relocate(this.getPrefWidth() / 2.0 - this.openButton.getWidth() / 2.0,
				this.getHeight() - this.openButton.getHeight() - 3.0);
	}

	@Override
	public void resizeUpdate(double newSceneWidth, double newSceneHeight) {
		// No relocation needed since this is in the upper left corner
	}
	
	private class OpenButtonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {}
	}
}
