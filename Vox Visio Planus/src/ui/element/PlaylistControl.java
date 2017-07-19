package ui.element;

import audio.VoxPlayer;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;

public class PlaylistControl extends Group {

	public static final String PLAYLIST_CONTROL_RES = "file:res/texture/playlistControl/";
	public static final double BUTTON_FIT_WIDTH = 30.0;
	public static final double BUTTON_FIT_HEIGHT = 20.0;
	
	private Button openButton;
	private Button closeButton;
	private Button AddButton;
	private Button removeButton;
	private Button addFolderButton;
	private Button swapButton;
	private Toggle shuffleToggle;
	
	private VoxPlayer voxPlayer;
	
	public PlaylistControl(VoxPlayer voxPlayer) {
		super();
		this.voxPlayer = voxPlayer;
	}
	
}
