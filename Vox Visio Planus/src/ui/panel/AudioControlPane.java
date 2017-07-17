/**
 * 
 */
package ui.panel;

import audio.VoxPlayer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import ui.element.MediaControl;

/**
 * @author Docter60
 *
 */
public class AudioControlPane extends HotSpotPane {
	public static final double WIDTH = 300;
	public static final double HEIGHT = 50;
	
	private MediaControl mediaControl;
	
	public AudioControlPane(Scene primaryScene, VoxPlayer voxPlayer) {
		super("", (primaryScene.getWidth() - WIDTH) / 2.0, 0, WIDTH, HEIGHT, false);
		this.relocate((primaryScene.getWidth() - WIDTH) / 2.0, -HEIGHT);
		this.mediaControl = new MediaControl(voxPlayer);
		this.getChildren().add(mediaControl);
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
	}

	@Override
	public void resizeUpdate(double newSceneWidth, double newSceneHeight) {
		double newX = (newSceneWidth - WIDTH) / 2.0;
		this.relocate(newX, -HEIGHT);
		this.hotSpot.relocate(newX, 0);
	}
}
