/**
 * 
 */
package audio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @author Docter60
 *
 */
public class AudioPlayer implements Runnable{

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
	
	public void setVolume(float volume){
		Info source = Port.Info.SPEAKER;
		
		if(AudioSystem.isLineSupported(source)){
			try{
				Port outline = (Port) AudioSystem.getLine(source);
				outline.open();
				FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
				volumeControl.setValue(volume);
			}catch(LineUnavailableException e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean isPlayerReady(){
		if(clip.getAudioFile() == null)
			return false;
		return true;
	}
	
	public void setClip(AudioClip clip){
		this.clip = clip;
	}
	
	@Override
	public void run() {
		try {
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
