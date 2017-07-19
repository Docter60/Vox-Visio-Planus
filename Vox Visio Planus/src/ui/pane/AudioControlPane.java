/**
 * 
 */
package ui.pane;

import audio.VoxPlayer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import ui.element.MediaControl;

/**
 * @author Docter60
 *
 */
public class AudioControlPane extends VerticalHotSpotPane {
	public static final double WIDTH = 500;
	public static final double HEIGHT = 50;
	
	private MediaControl mediaControl;
	
	public AudioControlPane(Scene primaryScene, VoxPlayer voxPlayer) {
		super("", (primaryScene.getWidth() - WIDTH) / 2.0, 0, WIDTH, HEIGHT);
		this.relocate((primaryScene.getWidth() - WIDTH) / 2.0, -HEIGHT);
		this.mediaControl = new MediaControl(voxPlayer);
		this.getChildren().add(mediaControl);
		this.getChildren().add(this.mediaControl.getVolumeSlider());
	}

	@Override
	public void initializeElements() {
		super.initializeElements();
		
		double buttonWidth = this.mediaControl.getMediaControlButtonWidth();
		double buttonHeight = this.mediaControl.getMediaControlButtonHeight();
		double mediaControlSize = this.mediaControl.getChildren().size();
		ObservableList<Node> children = this.mediaControl.getChildren();
		
		double mainControlsStart = (this.getWidth() - mediaControlSize * buttonWidth) / 2.0;
		double y = this.getHeight() - buttonHeight - 5.0;
		for(int i = 0; i < mediaControlSize; i++) {
			double x = i * buttonWidth + mainControlsStart;
			children.get(i).relocate(x, y);
		}
		
		Slider volumeSlider = this.mediaControl.getVolumeSlider();
		volumeSlider.relocate(WIDTH - volumeSlider.getWidth(), (HEIGHT - volumeSlider.getHeight()) / 2.0);
	}

	@Override
	public void resizeUpdate(double newSceneWidth, double newSceneHeight) {
		double newX = (newSceneWidth - WIDTH) / 2.0;
		this.relocate(newX, -HEIGHT);
		this.hotSpot.relocate(newX, 0);
	}
}
