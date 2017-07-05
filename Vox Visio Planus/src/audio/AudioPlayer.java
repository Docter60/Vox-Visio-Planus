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
	private float[] data;
	
	public AudioPlayer(){}
	
	public void attachAudioClip(AudioClip clip){
		this.clip = clip;
		mediaPlayer = new MediaPlayer(clip.getMedia());
		this.data = new float[mediaPlayer.getAudioSpectrumNumBands()];
		audioSpectrum = new AudioSpectrum(mediaPlayer, data);
		mediaPlayer.setAudioSpectrumListener(audioSpectrum);
		mediaPlayer.setAudioSpectrumInterval(0.02);
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
	
	public float[] getSpectrumData(){
		return data;
	}

}
