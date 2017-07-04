/**
 * 
 */
package audio;

import asset.AudioSpectrum;
import javafx.scene.media.MediaPlayer;

/**
 * @author Docter60
 *
 */
public class AudioPlayer {

	private MediaPlayer mediaPlayer;
	private AudioSpectrum audioSpectrum;
	private AudioClip clip;
	
	public AudioPlayer(){}
	
	public void attachAudioClip(AudioClip clip){
		this.clip = clip;
		mediaPlayer = new MediaPlayer(clip.getMedia());
		audioSpectrum = new AudioSpectrum(this);
		mediaPlayer.setAudioSpectrumListener(audioSpectrum);
		mediaPlayer.setAudioSpectrumInterval(0.001);
		System.out.println(mediaPlayer.getAudioSpectrumNumBands());
		mediaPlayer.setAudioSpectrumThreshold(-70);
		mediaPlayer.setAudioSpectrumNumBands(1024);
	}
	
	public void play(){
		mediaPlayer.play();
	}
	
	public void pause(){
		mediaPlayer.pause();
	}
	
	public void setVolume(double volume){
		mediaPlayer.setVolume(volume);
	}
	
	public AudioClip getCurrentClip(){
		return clip;
	}
	
	public MediaPlayer getMediaPlayer(){
		return mediaPlayer;
	}
	
	public AudioSpectrum getAudioSpectrum(){
		return audioSpectrum;
	}

}
