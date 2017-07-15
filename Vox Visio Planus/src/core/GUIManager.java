/**
 * 
 */
package core;

import asset.dialog.OpenFileDialog;
import audio.VoxPlayer;
import javafx.scene.Group;
import javafx.stage.Stage;
import ui.panel.AudioControlPane;
import ui.panel.SinglePlayPane;

/**
 * @author Docter60
 *
 */
public class GUIManager {

	private SinglePlayPane singlePlayPane;
	private AudioControlPane audioControlPane;
	// private PlaylistPane playlistPane;
	// private RecordPane recordPane

	private OpenFileDialog openFileDialog;

	public GUIManager(Stage primaryStage) {
		this.singlePlayPane = new SinglePlayPane(primaryStage);
		// this.audioControlPane = new AudioControlPane(primaryStage);
		// this.playlistPane = new PlaylistPane();
		// this.recordPane = new RecordPane();

		this.openFileDialog = new OpenFileDialog(primaryStage);
	}

	public void addGUIToScene(Group root) {
		this.singlePlayPane.addToScene(root);
		// this.audioControlPane.addToScene(root);
		// this.playlistPane.addToScene(root);
		// this.recordPane.addToScene(root);
	}

	public void initializePanes() {
		this.singlePlayPane.initializeElements();
		// this.audioControlPane.initializeElements();
	}

	public void hotSpotUpdate() {
		this.singlePlayPane.hotSpotUpdate();
		// this.audioControlPane.hotSpotUpdate();
	}

	public void resizeUpdate(double newWidth, double newHeight) {
		this.singlePlayPane.resizeUpdate(newWidth, newHeight);
		// this.audioControlPane.resizeUpdate(newWidth, newHeight);
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
