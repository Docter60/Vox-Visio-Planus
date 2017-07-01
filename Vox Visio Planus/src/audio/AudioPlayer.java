/**
 * 
 */
package audio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @author Docter60
 *
 */
public class AudioPlayer {

	private Player player;
	private AudioClip clip;
	//private AudioPlaylist playlist;
	
	public AudioPlayer(AudioClip clip){
		this.clip = clip;
		try {
			FileInputStream fis = new FileInputStream(clip.getAudioFile());
			this.player = new Player(fis);
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public void play(){
		if(isPlayerReady())
			try {
				player.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
	}
	
	//TODO: add all other functions
	
	public boolean isPlayerReady(){
		if(clip.getAudioFile() == null)
			return false;
		return true;
	}
	
	public void setClip(AudioClip clip){
		this.clip = clip;
	}
	
}
