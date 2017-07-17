/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.List;

import asset.dialog.OpenFileDialog;
import audio.VoxPlayer;
import javafx.scene.Group;
import javafx.scene.Scene;
import ui.panel.AudioControlPane;
import ui.panel.HotSpotPane;
import ui.panel.PlaylistPane;
import ui.panel.SinglePlayPane;

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

	private OpenFileDialog openFileDialog;

	private boolean isDebugMode;

	public GUIManager(VoxVisioPlanus voxVisioPlanus) {
		this.voxVisioPlanus = voxVisioPlanus;

		Scene primaryScene = voxVisioPlanus.getPrimaryStage().getScene();
		
		this.singlePlayPane = new SinglePlayPane(primaryScene, this);
		this.audioControlPane = new AudioControlPane(primaryScene, voxVisioPlanus.getVoxPlayer());
		this.playlistPane = new PlaylistPane(primaryScene);
		// this.recordPane = new RecordPane();

		this.hotSpotPanes = new ArrayList<HotSpotPane>();
		this.hotSpotPanes.add(singlePlayPane);
		this.hotSpotPanes.add(audioControlPane);
		this.hotSpotPanes.add(playlistPane);

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

	public void resizeUpdate(double newWidth, double newHeight) {
		for (HotSpotPane hsp : hotSpotPanes)
			hsp.resizeUpdate(newWidth, newHeight);
	}

	public void showOpenDialog() {
		openFileDialog.showDialog(OpenFileDialog.AUDIO_FILE);
		if (openFileDialog.getPathToFile() != null) {
			VoxPlayer vp = this.voxVisioPlanus.getVoxPlayer();
			VisualSpectrumManager vsm = this.voxVisioPlanus.getVisualSpectrumManager();
			vp.load(openFileDialog.getPathToFile());
			openFileDialog.clearPathToFile();
			vsm.setDataReference(vp.getSpectrumData());
		}
	}

	public void toggleDebug() {
		this.isDebugMode = !this.isDebugMode;
		for (HotSpotPane hsp : hotSpotPanes)
			hsp.setDebugMode(isDebugMode);
	}

}
