/**
 * 
 */
package ui.pane;

import audio.SpectrumMediaPlayer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import ui.element.PlaylistControl;
import window.WindowPane;

/**
 * @author Docter60
 *
 */
public class PlaylistPane extends WindowPane {
	public static final double WIDTH = 300;
	public static final double HEIGHT = 200;
	
	private PlaylistControl playlistControl;
	
	public PlaylistPane(Scene primaryScene, SpectrumMediaPlayer spectrumMediaPlayer) {
		super(0, primaryScene.getHeight() - HEIGHT, WIDTH, HEIGHT, "Playlist");
		this.playlistControl = new PlaylistControl(spectrumMediaPlayer);
		this.playlistControl.initializeElements(WIDTH, HEIGHT);
		this.mainPane.setCenter(playlistControl);
		
		this.setRestoreShortcut(KeyCode.P);
		this.setResizeable(false);
		this.setSlideable(true);
	}
}
