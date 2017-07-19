/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.List;

import asset.dialog.OpenFileDialog;
import audio.VoxMedia;
import audio.VoxPlayer;
import javafx.scene.Group;
import javafx.scene.Scene;
import ui.pane.AudioControlPane;
import ui.pane.HotSpotPane;
import ui.pane.PlaylistPane;
import ui.pane.SinglePlayPane;
import ui.pane.SongInfoPane;

/**
 * @author Docter60
 *
 */
public class GUIManager {

	private List<HotSpotPane> hotSpotPanes;

	private VoxVisioPlanus voxVisioPlanus;

	private SinglePlayPane singlePlayPane;
	private AudioControlPane audioControlPane;
	private PlaylistPane playlistPane;
	// private RecordPane recordPane
	private SongInfoPane songInfoPane;

	private OpenFileDialog openFileDialog;

	private boolean isDebugMode;

	public GUIManager(VoxVisioPlanus voxVisioPlanus) {
		this.voxVisioPlanus = voxVisioPlanus;

		Scene primaryScene = voxVisioPlanus.getPrimaryStage().getScene();
		
		this.singlePlayPane = new SinglePlayPane(primaryScene, this);
		this.audioControlPane = new AudioControlPane(primaryScene, voxVisioPlanus.getVoxPlayer());
		this.playlistPane = new PlaylistPane(primaryScene, voxVisioPlanus.getVoxPlayer());
		// this.recordPane = new RecordPane();
		this.songInfoPane = new SongInfoPane(primaryScene);
		
		voxVisioPlanus.getVoxPlayer().addMediaInfoListener(songInfoPane);

		this.hotSpotPanes = new ArrayList<HotSpotPane>();
		this.hotSpotPanes.add(singlePlayPane);
		this.hotSpotPanes.add(audioControlPane);
		this.hotSpotPanes.add(playlistPane);
		
		this.hotSpotPanes.add(songInfoPane);
		
		voxVisioPlanus.addResizeListener(singlePlayPane);
		voxVisioPlanus.addResizeListener(audioControlPane);
		voxVisioPlanus.addResizeListener(playlistPane);
		voxVisioPlanus.addResizeListener(songInfoPane);

		this.openFileDialog = new OpenFileDialog(voxVisioPlanus.getPrimaryStage());

		this.isDebugMode = false;
	}

	public void addGUIToScene(Group root) {
		for (HotSpotPane hsp : hotSpotPanes)
			hsp.addToScene(root);
	}

	public void initializePanes() {
		for (HotSpotPane hsp : hotSpotPanes)
			hsp.initializeElements();
	}

	public void hotSpotUpdate() {
		for (HotSpotPane hsp : hotSpotPanes)
			hsp.hotSpotUpdate();
	}

	public void showOpenDialog() {
		openFileDialog.showDialog(OpenFileDialog.AUDIO_FILE);
		if (openFileDialog.getPathToFile() != null) {
			VoxPlayer vp = this.voxVisioPlanus.getVoxPlayer();
			vp.load(new VoxMedia(openFileDialog.getPathToFile()));
			openFileDialog.clearPathToFile();
		}
	}

	public void toggleDebug() {
		this.isDebugMode = !this.isDebugMode;
		for (HotSpotPane hsp : hotSpotPanes)
			hsp.setDebugMode(isDebugMode);
	}

}
