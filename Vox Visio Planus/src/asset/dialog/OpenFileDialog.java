/**
 * 
 */
package asset.dialog;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Docter60
 *
 */
public class OpenFileDialog {

	public static final int OPEN_AUDIO_FILE = 0;
	public static final int OPEN_PLAYLIST_FILE = 1;

	private String pathToFile = null;
	private String currentDirectory = null;
	private Stage parentStage;
	private FileChooser fileChooser;

	public OpenFileDialog(Stage parentStage) {
		this.fileChooser = new FileChooser();
		this.parentStage = parentStage;
	}
	
	public void showDialog(int type){
		this.fileChooser.getExtensionFilters().clear();
		switch (type) {
		case OPEN_AUDIO_FILE:
			this.fileChooser.setTitle("Open Audio File");
			this.fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("All Supported Audio Files", "*.wav; *.mp3; *.m4a"),
					new FileChooser.ExtensionFilter("M4A", "*.m4a"),
					new FileChooser.ExtensionFilter("MP3", "*.mp3"),
					new FileChooser.ExtensionFilter("WAV", "*.wav"));
			break;
		case OPEN_PLAYLIST_FILE:
			this.fileChooser.setTitle("Open Playlist File");
			break;
		}
		
		if(this.currentDirectory == null)
			this.fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		else
			this.fileChooser.setInitialDirectory(new File(currentDirectory));
		
		File file = fileChooser.showOpenDialog(parentStage);
		if(file != null){
			this.currentDirectory = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\") + 1);
			this.pathToFile = file.getAbsolutePath();
		}
	}
	
	public void clearPathToFile(){
		this.pathToFile = null;
	}

	public String getPathToFile() {
		return pathToFile;
	}

}
