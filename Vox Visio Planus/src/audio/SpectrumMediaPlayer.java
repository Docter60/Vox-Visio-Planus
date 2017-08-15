/**
 * 
 */
package audio;

import java.util.Arrays;

import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
/**
 * The SpectrumMediaPlayer extends the AdvancedMediaPlayer and 
 * reveals the spectrum data reference.
 * 
 * @author Docter60
 */
public class SpectrumMediaPlayer extends AdvancedMediaPlayer {
	public static final int BANDS = 512;
	public static final int THRESHOLD = -70;
	public static final double INTERVAL = 0.01;
	
	private static float[] hammingWindow;
	
	private float[] spectrumData;
	
	static {
		hammingWindow = new float[BANDS];
		for(int i = 0; i < BANDS; i++) {
			hammingWindow[i] = (float) (0.54 - 0.46 * Math.cos(2 * Math.PI * (i / BANDS)));
		}
	}
	
	public SpectrumMediaPlayer() {
		super();
		spectrumData = new float[BANDS];
	}
	
	@Override
	public void setMedia(Media media) {
		super.setMedia(media);
		mediaPlayer.setAudioSpectrumListener(new VoxAudioSpectrumListener());
		mediaPlayer.setAudioSpectrumInterval(INTERVAL);
		mediaPlayer.setAudioSpectrumNumBands(BANDS);
		mediaPlayer.setAudioSpectrumThreshold(THRESHOLD);
	}
	
	@Override
	public void stop() {
		super.stop();
		Arrays.fill(spectrumData, 0);
	}
	
	public float[] getSpectrumData() {
		return spectrumData;
	}
	
	private class VoxAudioSpectrumListener implements AudioSpectrumListener {
		@Override
		public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
			float[] spectrumData = SpectrumMediaPlayer.this.spectrumData;
			int threshold = SpectrumMediaPlayer.this.mediaPlayer.getAudioSpectrumThreshold();
			for (int i = 0; i < spectrumData.length; i++) {
				//spectrumData[i] = (magnitudes[i] - threshold) * hammingWindow[i] * 10f;
				spectrumData[i] = magnitudes[i] - threshold;
			}
		}
	}
}
