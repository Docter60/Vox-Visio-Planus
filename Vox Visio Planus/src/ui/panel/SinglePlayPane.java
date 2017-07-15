/**
 * 
 */
package ui.panel;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class SinglePlayPane extends HotSpotPane {
	public static final double WIDTH = 80;
	public static final double HEIGHT = 50;

	private Button openButton;

	public SinglePlayPane(Stage primaryStage) {
		super("Single Play", 0, 0, WIDTH, HEIGHT);
		this.relocate(0, -HEIGHT);

		this.openButton = new Button("Open");
		this.openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				openButtonPressed();
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
	public void hotSpotUpdate() {
		double oldY = this.getTranslateY();
		double newY;
		double showY = -this.getPrefHeight() / 2.0; // Idk why this has to be
													// halved, but it do

		if (showPane)
			newY = Mathg.lerp(oldY, 0, 0.07);
		else
			newY = Mathg.lerp(oldY, showY, 0.07);

		this.setTranslateY(newY);
		this.layoutYProperty().set(newY);
	}

	@Override
	public void resizeUpdate(double newSceneWidth, double newSceneHeight) {
		// No relocation needed since this is in the upper left corner
	}

	private void openButtonPressed() {
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_O);
			r.keyRelease(KeyEvent.VK_O);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
