/**
 * 
 */
package asset;

import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.MediaPlayer;

/**
 * @author Docter60
 *
 */
public class AudioSpectrum implements AudioSpectrumListener{

	private float[] data;
	private int minValue;
	
	public AudioSpectrum(MediaPlayer mp, float[] data){
		this.data = data;
		this.minValue = mp.getAudioSpectrumThreshold();
	}
	
	public float[] getSpectrumData(){
		return data;
	}
	
	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		for(int i = 0; i < data.length; i++){
			data[i] = magnitudes[i] + -minValue;
		}
	}

}
