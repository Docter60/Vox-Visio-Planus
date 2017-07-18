/**
 * 
 */
package ui.pane;

import audio.VoxPlayer;
import javafx.scene.Scene;

/**
 * @author Docter60
 *
 */
public class PlaylistPane extends HorizontalHotSpotPane {
	public static final double WIDTH = 200;
	public static final double HEIGHT = 300;
	
	public PlaylistPane(Scene primaryScene, VoxPlayer voxPlayer) {
		super("Playlist", 0, primaryScene.getHeight() - HEIGHT, WIDTH, HEIGHT);
		this.relocate(-WIDTH, primaryScene.getHeight() - HEIGHT);
	}

	@Override
	public void initializeElements() {
		super.initializeElements();
		// TODO
	}

	@Override
	public void resizeUpdate(double newSceneWidth, double newSceneHeight) {
		double newY = newSceneHeight - HEIGHT;
		this.relocate(-WIDTH, newY);
		this.hotSpot.relocate(0, newY);
	}

}
