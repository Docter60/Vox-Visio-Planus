/**
 * 
 */
package audio;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * @author Docter60
 *
 */
public class VoxPlayer {

	public static final int BANDS = 512;
	public static final int THRESHOLD = -70;
	public static final double INTERVAL = 0.01;
	
	private List<VoxMediaInfoListener> infoListeners;

	private MediaPlayer player;
	private VoxMedia media;

	private String[] currentSongInfo;
	private Image currentAlbumCover;
	
	private float[] spectrumData;
	private double volume;

	public VoxPlayer() {
		this.infoListeners = new ArrayList<VoxMediaInfoListener>();
		this.currentSongInfo = new String[4];
		this.volume = 0.5;
	}
	
	public void addMediaInfoListener(VoxMediaInfoListener vmil) {
		this.infoListeners.add(vmil);
	}

	public void load(VoxMedia voxMedia) {
		if (media != null)
			this.player.stop();

		this.media = voxMedia;
		this.player = new MediaPlayer(media.getMedia());
		this.player.setVolume(this.volume);

		this.player.setAudioSpectrumNumBands(BANDS);
		this.player.setAudioSpectrumInterval(INTERVAL);
		this.player.setAudioSpectrumThreshold(THRESHOLD);

		this.spectrumData = new float[player.getAudioSpectrumNumBands()];

		this.player.setAudioSpectrumListener(new AudioSpectrumListener() {
			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
				for (int i = 0; i < spectrumData.length; i++) {
					spectrumData[i] = magnitudes[i] - player.getAudioSpectrumThreshold();
					// TODO figure out window
				}
			}
		});
		
		this.player.setOnReady(new OnReadyListener());
	}

	public void play() {
		player.play();
	}

	public void pause() {
		player.pause();
	}

	public void stop() {
		player.stop();
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public void quickSeek(boolean isForward) {
		if (isForward)
			player.seek(player.getCurrentTime().add(new Duration(10000.0)));
		else
			player.seek(player.getCurrentTime().add(new Duration(-10000.0)));
	}

	public float[] getSpectrumData() {
		return spectrumData;
	}

	public VoxMedia getVoxMedia() {
		return media;
	}

	public boolean isPlaying() {
		return player.getStatus().equals(Status.PLAYING);
	}
	
	public String[] getCurrentMediaInfo() {
		return this.currentSongInfo;
	}
	
	public Image getcurrentAlbumCover() {
		return this.currentAlbumCover;
	}
	
	private class OnReadyListener implements Runnable {
		@Override
		public void run() {
			VoxMedia vm = VoxPlayer.this.media;
			String[] info = VoxPlayer.this.currentSongInfo;
			info[0] = vm.getTitle();
			info[1] = vm.getArtist();
			info[2] = vm.getAlbum();
			info[3] = vm.getYear();
			
			VoxPlayer.this.currentAlbumCover = vm.getAlbumCover();
			
			for(VoxMediaInfoListener vmil : infoListeners)
				vmil.onMediaInfoUpdate(info, vm.getAlbumCover());
		}
		
	}

}
