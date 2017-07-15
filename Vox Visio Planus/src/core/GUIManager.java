/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.List;

import asset.dialog.OpenFileDialog;
import audio.VoxPlayer;
import javafx.scene.Group;
import javafx.stage.Stage;
import ui.panel.AudioControlPane;
import ui.panel.HotSpotPane;
import ui.panel.SinglePlayPane;

/**
 * @author Docter60
 *
 */
public class GUIManager {

	private List<HotSpotPane> hotSpotPanes;
	
	private SinglePlayPane singlePlayPane;
	private AudioControlPane audioControlPane;
	// private PlaylistPane playlistPane;
	// private RecordPane recordPane

	private OpenFileDialog openFileDialog;

	public GUIManager(Stage primaryStage) {
		this.singlePlayPane = new SinglePlayPane(primaryStage);
		this.audioControlPane = new AudioControlPane(primaryStage);
		// this.playlistPane = new PlaylistPane();
		// this.recordPane = new RecordPane();
		
		this.hotSpotPanes = new ArrayList<HotSpotPane>();
		this.hotSpotPanes.add(singlePlayPane);
		this.hotSpotPanes.add(audioControlPane);

		this.openFileDialog = new OpenFileDialog(primaryStage);
	}

	public void addGUIToScene(Group root) {
		for(HotSpotPane hsp : hotSpotPanes)
			hsp.addToScene(root);
	}

	public void initializePanes() {
		for(HotSpotPane hsp : hotSpotPanes)
			hsp.initializeElements();
	}

	public void hotSpotUpdate() {
		for(HotSpotPane hsp : hotSpotPanes)
			hsp.hotSpotUpdate();
	}

	public void resizeUpdate(double newWidth, double newHeight) {
		for(HotSpotPane hsp : hotSpotPanes)
			hsp.resizeUpdate(newWidth, newHeight);
	}

	public void showOpenDialog(VisualSpectrumManager vsm, VoxPlayer vp) {
		openFileDialog.showDialog(OpenFileDialog.AUDIO_FILE);
		if (openFileDialog.getPathToFile() != null) {
			vp.load(openFileDialog.getPathToFile());
			openFileDialog.clearPathToFile();
			vsm.setDataReference(vp.getSpectrumData());
		}
	}

}
