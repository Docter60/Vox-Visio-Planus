/**
 * 
 */
package ui.pane;

import audio.VoxPlayer;
import javafx.scene.Scene;
import ui.element.PlaylistControl;

/**
 * @author Docter60
 *
 */
public class PlaylistPane extends HorizontalHotSpotPane {
	public static final double WIDTH = 300;
	public static final double HEIGHT = 200;
	
	private PlaylistControl playlistControl;
	
	public PlaylistPane(Scene primaryScene, VoxPlayer voxPlayer) {
		super("Playlist", 0, primaryScene.getHeight() - HEIGHT, WIDTH, HEIGHT);
		this.relocate(-WIDTH, primaryScene.getHeight() - HEIGHT);
		this.playlistControl = new PlaylistControl(voxPlayer);
		this.getChildren().add(playlistControl);
	}

	@Override
	public void initializeElements() {
		super.initializeElements();
		this.playlistControl.initializeElements(WIDTH, HEIGHT);
	}

	@Override
	public void resizeUpdate(double newSceneWidth, double newSceneHeight) {
		double newY = newSceneHeight - HEIGHT;
		this.relocate(-WIDTH, newY);
		this.hotSpot.relocate(0, newY);
	}

}
