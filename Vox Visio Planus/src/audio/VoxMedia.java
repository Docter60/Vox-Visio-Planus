/**
 * 
 */
package audio;

import java.io.File;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import ui.pane.SongInfoPane;

/**
 * @author Docter60
 *
 */
public class VoxMedia {
	public static final Image defaultCover = SongInfoPane.defaultCover;
	
	private Media media;

	private String path;
	private String title;
	private String artist;
	private String album;
	private String year;

	private Image albumCover;

	public VoxMedia(String filePath) {
		this.path = filePath;
		this.media = new Media(new File(filePath).toURI().toString());
		
		this.albumCover = defaultCover;
		
		this.media.getMetadata().addListener(new MetaDataListener());
	}

	private class MetaDataListener implements MapChangeListener<String, Object> {
		@Override
		public void onChanged(javafx.collections.MapChangeListener.Change<? extends String, ? extends Object> ch) {
			if (ch.wasAdded()) {
				String key = ch.getKey();
				Object value = ch.getValueAdded();
				if (key.equals("album"))
					VoxMedia.this.album = value.toString();
				else if (key.equals("artist"))
					VoxMedia.this.artist = value.toString();
				if (key.equals("title"))
					VoxMedia.this.title = value.toString();
				if (key.equals("year"))
					VoxMedia.this.year = value.toString();
				if (key.equals("image"))
					VoxMedia.this.albumCover = (Image) value;
			}
		}
	}

	public Media getMedia() {
		return media;
	}

	public String getPath() {
		return path;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public String getYear() {
		return year;
	}

	public Image getAlbumCover() {
		return albumCover;
	}
	
	public String[] getInfo() {
		String[] info = {this.getTitle(),
						 this.getArtist(),
						 this.getAlbum(),
						 this.getYear()};
		return info;
	}
}
