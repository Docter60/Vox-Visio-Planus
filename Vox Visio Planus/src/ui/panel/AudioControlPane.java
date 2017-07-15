/**
 * 
 */
package ui.panel;

import javafx.stage.Stage;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class AudioControlPane extends HotSpotPane {
	public static final double WIDTH = 100;
	public static final double HEIGHT = 50;
	
	public AudioControlPane(Stage primaryStage) {
		super("", (primaryStage.getWidth() - WIDTH) / 2.0, 0, WIDTH, HEIGHT);
	}

	@Override
	public void initializeElements() {
		super.initializeElements();
		// TODO Add all audio control element placement here
	}

	@Override
	public void resizeUpdate(double newSceneWidth, double newSceneHeight) {
		double newX = (newSceneWidth + this.getPrefWidth()) / 2.0;
		this.setLayoutX(newX);
		this.hotSpot.setLayoutX(newX);
	}

	@Override
	public void hotSpotUpdate() {
		double oldY = this.getTranslateY();
		double newY;
		double showY = -this.getPrefHeight() / 2.0;

		if (showPane)
			newY = Mathg.lerp(oldY, 0, 0.07);
		else
			newY = Mathg.lerp(oldY, showY, 0.07);

		this.setTranslateY(newY);
		this.layoutYProperty().set(newY);
	}

}
