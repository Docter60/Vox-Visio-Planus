/**
 * 
 */
package ui;

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

	private String pathToFile;
	private Stage parentStage;
	private FileChooser fileChooser;

	public OpenFileDialog(int type, Stage parentStage) {
		this.fileChooser = new FileChooser();
		this.pathToFile = null;

		switch (type) {
		case OPEN_AUDIO_FILE:
			this.fileChooser.setTitle("Open Audio File");
			break;
		case OPEN_PLAYLIST_FILE:
			this.fileChooser.setTitle("Open Playlist File");
			break;
		}
	}
	
	public void showDialog(){
		File file = fileChooser.showOpenDialog(parentStage);
		this.pathToFile = file.getAbsolutePath();
	}
	
	public void clearPathToFile(){
		this.pathToFile = null;
	}

	public String getPathToFile() {
		return pathToFile;
	}

}
