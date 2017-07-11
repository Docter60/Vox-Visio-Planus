/**
 * 
 */
package audio;

import java.io.File;

import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
	
	private float[] spectrumData;
	
	public VoxPlayer(){}
	
	public void load(String path){
		File file = new File(path);
		if(!file.isDirectory()){
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
	
	public float[] getSpectrumData(){
		return spectrumData;
	}
	
	public Media getCurrentMedia(){
		return media;
	}
	
}
