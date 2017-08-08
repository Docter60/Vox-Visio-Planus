/**
 * 
 */
package core;

import java.io.File;

import asset.dialog.OpenFileDialog;
import audio.SpectrumMediaPlayer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import ui.pane.AudioControlPane;
import ui.pane.PlaylistPane;
import ui.pane.SinglePlayPane;
import ui.pane.SongInfoPane;

/**
 * @author Docter60
 *
 */
public class GUIManager {

	private VoxVisioPlanus voxVisioPlanus;

	private SinglePlayPane singlePlayPane;
	private AudioControlPane audioControlPane;
	private PlaylistPane playlistPane;
	// private RecordPane recordPane
	private SongInfoPane songInfoPane;

	private OpenFileDialog openFileDialog;

	public GUIManager(VoxVisioPlanus voxVisioPlanus) {
		this.voxVisioPlanus = voxVisioPlanus;

		Scene primaryScene = voxVisioPlanus.getPrimaryStage().getScene();
		
		this.singlePlayPane = new SinglePlayPane(this);
		this.audioControlPane = new AudioControlPane(primaryScene, voxVisioPlanus.getSpectrumMediaPlayer());
		this.playlistPane = new PlaylistPane(primaryScene, voxVisioPlanus.getSpectrumMediaPlayer());
		// this.recordPane = new RecordPane();
		this.songInfoPane = new SongInfoPane(primaryScene);
		
		voxVisioPlanus.getSpectrumMediaPlayer().addMediaChangedListener(songInfoPane);

		((Group) primaryScene.getRoot()).getChildren().add(this.singlePlayPane);
		((Group) primaryScene.getRoot()).getChildren().add(this.audioControlPane);
		((Group) primaryScene.getRoot()).getChildren().add(this.playlistPane);
		((Group) primaryScene.getRoot()).getChildren().add(this.songInfoPane);

		this.openFileDialog = new OpenFileDialog(voxVisioPlanus.getPrimaryStage());
	}

	public void showOpenDialog() {
		openFileDialog.showDialog(OpenFileDialog.AUDIO_FILE);
		if (openFileDialog.getPathToFile() != null) {
			SpectrumMediaPlayer smp = voxVisioPlanus.getSpectrumMediaPlayer();
			String path = new File(openFileDialog.getPathToFile()).toURI().toString();
			smp.setMedia(new Media(path));
			openFileDialog.clearPathToFile();
		}
	}
}
