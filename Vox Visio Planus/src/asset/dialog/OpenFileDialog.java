/**
 * 
 */
package asset.dialog;

import java.io.File;

import javafx.stage.Stage;

/**
 * @author Docter60
 *
 */
public class OpenFileDialog extends FileDialog{

	public OpenFileDialog(Stage parentStage) {
		super(parentStage);
	}
	
	public void showDialog(int type){
		super.showDialog(type);
		File file = fileChooser.showOpenDialog(primaryStage);
		if(file != null){
			this.currentDirectory = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\") + 1);
			this.pathToFile = file.getAbsolutePath();
		}
	}
}
