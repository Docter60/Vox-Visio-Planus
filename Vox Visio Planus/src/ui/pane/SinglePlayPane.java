/**
 * 
 */
package ui.pane;

import core.GUIManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import window.WindowPane;
import window.mod.SlideMod;

/**
 * @author Docter60
 *
 */
public class SinglePlayPane extends WindowPane {
	public static final double WIDTH = 100;
	public static final double HEIGHT = 50;
	
	private GUIManager guiManager;
	
	private Button openButton;

	public SinglePlayPane(GUIManager guiManager) {
		super(0, 0, WIDTH, HEIGHT, "Single Play");
		this.guiManager = guiManager;
		this.openButton = new Button("Open");
		this.openButton.setOnAction(new OpenButtonEventHandler());
		this.openButton.setLayoutX(this.mainPane.getWidth() / 2.0 - this.openButton.getWidth() / 2.0);
		this.openButton.setLayoutY(this.mainPane.getHeight() - this.openButton.getHeight() - 3.0);
		this.mainPane.setCenter(openButton);
		
		this.setSlideable(true);
		
		double initX = SlideMod.getListenerHandles().get(this).getHideX();
		double initY = SlideMod.getListenerHandles().get(this).getHideY();
		this.setLayoutX(initX);
		this.setLayoutY(initY);
	}
	
	private class OpenButtonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			SinglePlayPane.this.guiManager.showOpenDialog();
		}
	}
}
