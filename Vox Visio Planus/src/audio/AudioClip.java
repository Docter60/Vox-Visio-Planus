/**
 * 
 */
package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Docter60
 *
 */
public class AudioClip {

	private Clip clip;
	
	public AudioClip(String file){
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(file));
			this.clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
			System.err.println("Could not create AudioClip:" + file);
		}
	}
	
}
