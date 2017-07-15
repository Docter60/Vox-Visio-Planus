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
public abstract class FileDialog {
	public static final int AUDIO_FILE = 0;
	public static final int PLAYLIST_FILE = 1;
	
	protected FileChooser fileChooser;
	protected Stage primaryStage;
	protected String pathToFile = null;
	protected String currentDirectory = null;
	
	public FileDialog(Stage primaryStage){
		this.fileChooser = new FileChooser();
		this.primaryStage = primaryStage;
	}
	
	public void showDialog(int type){
		this.fileChooser.getExtensionFilters().clear();
		switch (type) {
		case AUDIO_FILE:
			this.fileChooser.setTitle("Open Audio File");
			this.fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("All Supported Audio Files", "*.wav; *.mp3; *.m4a"),
					new FileChooser.ExtensionFilter("M4A", "*.m4a"),
					new FileChooser.ExtensionFilter("MP3", "*.mp3"),
					new FileChooser.ExtensionFilter("WAV", "*.wav"));
			break;
		case PLAYLIST_FILE:
			this.fileChooser.setTitle("Open Playlist File");
			// TODO Add playlist filters
			break;
		}
		
		if(this.currentDirectory == null)
			this.fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		else
			this.fileChooser.setInitialDirectory(new File(currentDirectory));
	}
	
	public void clearPathToFile(){
		this.pathToFile = null;
	}

	public String getPathToFile() {
		return pathToFile;
	}
	
}
