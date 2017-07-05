/**
 * 
 */
package audio;

import asset.AudioSpectrum;
import javafx.scene.media.MediaPlayer;
import javafx.animation.Timeline;

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
		data = new float[128];
		mediaPlayer = new MediaPlayer(clip.getMedia());
		mediaPlayer.setAudioSpectrumListener((double d, double d1, float[] magnitudes,
				float[] phases) -> {
					for(int i = 0; i < data.length; i++){
						data[i] = (float) (magnitudes[i] + 60);
					}
				});
		mediaPlayer.setAudioSpectrumInterval(0.01);
		mediaPlayer.setAudioSpectrumNumBands(128);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
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
	
	public float[] getData(){
		return data;
	}

}
