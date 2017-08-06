/**
 * 
 */
package ui.pane;

import audio.SpectrumMediaPlayer;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import ui.element.MediaControl;
import window.WindowPane;
import window.mod.SlideMod;

/**
 * @author Docter60
 *
 */
public class AudioControlPane extends WindowPane {
	public static final double WIDTH = 400;
	public static final double HEIGHT = 50;
	
	private MediaControl mediaControl;
	private HBox mediaContainer;
	
	public AudioControlPane(Scene primaryScene, SpectrumMediaPlayer spectrumMediaPlayer) {
		super((primaryScene.getWidth() - WIDTH) / 2.0, 0, WIDTH, HEIGHT, "Audio Controls");
		this.relocate((primaryScene.getWidth() - WIDTH) / 2.0, -HEIGHT);
		this.mediaControl = new MediaControl(spectrumMediaPlayer);
		this.mediaContainer = new HBox();
		this.initializeMediaControl();
		this.mainPane.setBottom(this.mediaContainer);
		
		this.setRestoreShortcut(KeyCode.A);
		this.setResizeable(false);
		this.setSlideable(true);
		
		double initX = SlideMod.getListenerHandles().get(this).getHideX();
		double initY = SlideMod.getListenerHandles().get(this).getHideY();
		this.setLayoutX(initX);
		this.setLayoutY(initY);
	}

	private void initializeMediaControl() {
		ObservableList<Node> children = this.mediaControl.getChildren();
		this.mediaContainer.getChildren().addAll(children);
		this.mediaContainer.setAlignment(Pos.CENTER);
	}
}
