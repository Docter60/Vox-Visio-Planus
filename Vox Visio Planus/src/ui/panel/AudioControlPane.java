/**
 * 
 */
package ui.panel;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class AudioControlPane extends HotSpotPane {
	public static final double WIDTH = 200;
	public static final double HEIGHT = 50;
	
	public static final String audioControlRes = "./res/texture/audioControl/";
	
//	public static final Image previousImage    = new Image(audioControlRes + "previous.png");
//	public static final Image rewindImage      = new Image(audioControlRes + "rewind.png");
//	public static final Image playImage        = new Image(audioControlRes + "play.png");
//	public static final Image pauseImage       = new Image(audioControlRes + "pause.png");
//	public static final Image stopImage        = new Image(audioControlRes + "stop.png");
//	public static final Image fastForwardImage = new Image(audioControlRes + "fastForward.png");
//	public static final Image skipImage        = new Image(audioControlRes + "skip.png");
	
	private Button previousButton;
	private Button rewindButton;
	private Button playPauseButton;
	private Button stopButton;
	private Button fastForwardButton;
	private Button skipButton;
	
	private List<Button> mainControls;
	
	public AudioControlPane(Stage primaryStage) {
		super("", (primaryStage.getScene().getWidth() - WIDTH) / 2.0, 0, WIDTH, HEIGHT);
		this.relocate((primaryStage.getScene().getWidth() - WIDTH) / 2.0, -HEIGHT);
		
//		this.previousButton    = new Button("", new ImageView(previousImage));
//		this.rewindButton      = new Button("", new ImageView(rewindImage));
//		this.playPauseButton   = new Button("", new ImageView(playImage));
//		this.stopButton        = new Button("", new ImageView(stopImage));
//		this.fastForwardButton = new Button("", new ImageView(fastForwardImage));
//		this.skipButton        = new Button("", new ImageView(skipImage));
//		
//		this.mainControls.add(previousButton);
//		this.mainControls.add(rewindButton);
//		this.mainControls.add(playPauseButton);
//		this.mainControls.add(stopButton);
//		this.mainControls.add(fastForwardButton);
//		this.mainControls.add(skipButton);
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
