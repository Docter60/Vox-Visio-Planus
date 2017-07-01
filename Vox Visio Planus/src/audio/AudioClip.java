/**
 * 
 */
package audio;

import java.io.File;

/**
 * @author Docter60
 *
 */
public class AudioClip {

	private File audioFile;
	
	public AudioClip(String filePath){
		
		File temporaryFile = new File(filePath);
		if(temporaryFile.exists() && !temporaryFile.isDirectory())
			this.audioFile = temporaryFile;
		//eventually check if the file ends in a supported format
	}
	
	public File getAudioFile(){
		return this.audioFile;
	}
	
}
