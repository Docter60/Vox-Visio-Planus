/**
 * 
 */
package audio;

import java.io.File;

import javafx.scene.media.Media;

/**
 * @author Docter60
 *
 */
public class AudioClip {

	private File audioFile;
	private Media media;
	
	public AudioClip(String filePath){
		
		File temporaryFile = new File(filePath);
		if(temporaryFile.exists() && !temporaryFile.isDirectory())
			this.audioFile = temporaryFile;
		this.media = new Media(audioFile.toURI().toString());
		//eventually check if the file ends in a supported format
	}
	
	public File getFile(){
		return this.audioFile;
	}
	
	public Media getMedia(){
		return this.media;
	}
	
}
