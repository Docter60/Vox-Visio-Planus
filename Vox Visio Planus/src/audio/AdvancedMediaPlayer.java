/**
 * 
 */
package audio;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * The AdvancedMediaPlayer simply allows a version of MediaPlayer to exist without having a set Media.
 * Listeners can be added for when the media is ready.
 * 
 * @author Docter60
 */
public class AdvancedMediaPlayer {
	protected List<MediaChangedListener> mediaChangedListeners;
	protected MediaPlayer mediaPlayer;
	
	private MetaDataListener metaDataListener;
	private String[] songInfo;
	private Image albumCover;
	
	public AdvancedMediaPlayer() {
		mediaChangedListeners = new ArrayList<MediaChangedListener>();
		songInfo = new String[4];
		metaDataListener = new MetaDataListener();
	}
	
	public void setMedia(Media media) {
		if(mediaPlayer != null)
			mediaPlayer.stop();
		media.getMetadata().addListener(metaDataListener);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnReady(new OnReadyListener());
	}
	
	public void addMediaChangedListener(MediaChangedListener mediaChangedListener) {
		mediaChangedListeners.add(mediaChangedListener);
	}
	
	public void play() {
		if(mediaPlayer == null)
			System.err.println("Media has not been set!");
		else
			mediaPlayer.play();
	}
	
	public void pause() {
		if(mediaPlayer == null)
			System.err.println("Media has not been set!");
		else
			mediaPlayer.pause();
	}
	
	public void stop() {
		if(mediaPlayer == null)
			System.err.println("Media has not been set!");
		else
			mediaPlayer.stop();
	}
	
	public void setVolume(double volume) {
		if(mediaPlayer == null)
			System.err.println("Media has not been set!");
		else
			mediaPlayer.setVolume(volume);
	}
	
	public void quickSeek(boolean isForward) {
		Duration seekTime;
		if (isForward)
			seekTime = new Duration(10000.0);
		else
			seekTime = new Duration(-10000.0);
		mediaPlayer.seek(mediaPlayer.getCurrentTime().add(seekTime));
	}
	
	public void seek(double seekTime) {
		if(mediaPlayer == null)
			System.err.println("Media has not been set!");
		else
			mediaPlayer.seek(new Duration(seekTime));
	}
	
	public boolean isPlaying() {
		return mediaPlayer.getStatus().equals(Status.PLAYING);
	}
	
	public Media getMedia() {
		if(mediaPlayer == null)
			return null;
		else
			return mediaPlayer.getMedia();
	}
	
	private class OnReadyListener implements Runnable {
		@Override
		public void run() {
			for(MediaChangedListener mediaChangedListener : mediaChangedListeners)
				mediaChangedListener.changed(AdvancedMediaPlayer.this.songInfo, AdvancedMediaPlayer.this.albumCover);
		}
	}
	
	private class MetaDataListener implements MapChangeListener<String, Object> {
		@Override
		public void onChanged(javafx.collections.MapChangeListener.Change<? extends String, ? extends Object> ch) {
			if (ch.wasAdded()) {
				String key = ch.getKey();
				Object value = ch.getValueAdded();
				if (key.equals("album"))
					AdvancedMediaPlayer.this.songInfo[0] = value.toString();
				else if (key.equals("artist"))
					AdvancedMediaPlayer.this.songInfo[1] = value.toString();
				if (key.equals("title"))
					AdvancedMediaPlayer.this.songInfo[2] = value.toString();
				if (key.equals("year"))
					AdvancedMediaPlayer.this.songInfo[3] = value.toString();
				if (key.equals("image"))
					AdvancedMediaPlayer.this.albumCover = (Image) value;
			}
		}
	}
}
