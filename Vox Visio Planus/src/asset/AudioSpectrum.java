/**
 * 
 */
package asset;

import audio.AudioPlayer;
import javafx.scene.media.AudioSpectrumListener;

/**
 * @author Docter60
 *
 */
public class AudioSpectrum implements AudioSpectrumListener{

	private float[] spectrumData;
	
	public AudioSpectrum(AudioPlayer player){
		spectrumData = new float[128];
	}
	
	public float[] getSpectrumData(){
		return spectrumData;
	}
	
	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		spectrumData = magnitudes;
	}

}
