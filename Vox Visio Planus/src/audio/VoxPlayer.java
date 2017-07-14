/**
 * 
 */
package audio;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * @author Docter60
 *
 */
public class VoxPlayer {

	public static final int BANDS = 256;
	public static final int THRESHOLD = -60;
	public static final double INTERVAL = 0.01;
	
	private MediaPlayer player;
	private Media media;
	
	private AudioFormat format;
	private TargetDataLine microphone; // TODO: Microphone input
	
	private float[] spectrumData;
	
	public VoxPlayer(){
		this.format = new AudioFormat(8000.0f, 16, 1, true, true);
		try {
			this.microphone = AudioSystem.getTargetDataLine(format);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void load(String path){
		File file = new File(path);
		if(!file.isDirectory()){
			if(media != null)
				this.player.stop();
			
			this.media = new Media(file.toURI().toString());
			this.player = new MediaPlayer(media);
			
			this.player = new MediaPlayer(media);
			this.player.setAudioSpectrumNumBands(BANDS);
			this.player.setAudioSpectrumInterval(INTERVAL);
			this.player.setAudioSpectrumThreshold(THRESHOLD);
			
			this.spectrumData = new float[player.getAudioSpectrumNumBands()];
			
			this.player.setAudioSpectrumListener(new AudioSpectrumListener(){
				@Override
				public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
					for(int i = 0; i < spectrumData.length; i++){
						spectrumData[i] = magnitudes[i] - player.getAudioSpectrumThreshold();
					}
				}
			});
		}
	}
	
	public void play(){
		player.play();
	}
	
	public void pause(){
		player.pause();
	}
	
	public void stop(){
		player.stop();
	}
	
	public void setVolume(double volume){
		player.setVolume(volume);
	}
	
	public void quickSeek(boolean isForward){
		if(isForward)
			player.seek(player.getCurrentTime().add(new Duration(10000.0)));
		else
			player.seek(player.getCurrentTime().add(new Duration(-10000.0)));
	}
	
	public float[] getSpectrumData(){
		return spectrumData;
	}
	
	public Media getCurrentMedia(){
		return media;
	}
	
	public boolean isPlaying(){
		return player.getStatus().equals(Status.PLAYING);
	}
	
}
