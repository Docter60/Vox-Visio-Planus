/**
 * 
 */
package ui.pane;

import audio.SongInfoListener;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Docter60
 *
 */
public class SongInfoPane extends VerticalHotSpotPane implements SongInfoListener {
	public static final String AUDIO_CONTROL_RES = "file:res/texture/infoPane/";
	public static final double WIDTH = 300;
	public static final double HEIGHT = 100;

	public static final Image defaultCover = new Image(AUDIO_CONTROL_RES + "defaultCover.png");

	public static String notApp = "Unknown";

	private Label title;
	private Label artist;
	private Label album;
	private Label year;
	private ImageView albumCover;

	public SongInfoPane(Scene primaryScene) {
		super("", (primaryScene.getWidth() - WIDTH) / 2.0, primaryScene.getHeight() - HEIGHT, WIDTH, HEIGHT);
		this.setId("songInfoPane");
		this.relocate((primaryScene.getWidth() - WIDTH) / 2.0, primaryScene.getHeight() - HEIGHT);
		this.setMovesUp(true);

		this.title = new Label();
		this.artist = new Label();
		this.album = new Label();
		this.year = new Label();
		this.albumCover = new ImageView(new Image(AUDIO_CONTROL_RES + "defaultCover.png"));
		this.albumCover.setFitWidth(100);
		this.albumCover.setFitHeight(100);
		this.albumCover.setScaleX(0.8);
		this.albumCover.setScaleY(0.8);

		this.title.setId("songInfoLabel");
		this.artist.setId("songInfoLabel");
		this.album.setId("songInfoLabel");
		this.year.setId("songInfoLabel");

		this.getChildren().add(title);
		this.getChildren().add(artist);
		this.getChildren().add(album);
		this.getChildren().add(year);
		this.getChildren().add(albumCover);
	}

	@Override
	public void initializeElements() {
		super.initializeElements();
		this.albumCover.relocate(10, 0);
		this.title.relocate(110, 10);
		this.artist.relocate(110, 22);
		this.album.relocate(110, 34);
		this.year.relocate(110, 46);
	}

	@Override
	public void resizeUpdate(double newWidth, double newHeight) {
		double newX = (newWidth - WIDTH) / 2.0;
		this.relocate(newX, newHeight);
		this.hotSpot.relocate(newX, newHeight - HEIGHT);
	}

	@Override
	public void onMediaInfoUpdate(String[] info, Image albumCover) {
		if (info[0] != null)
			this.title.setText(info[0]);
		else
			this.title.setText(notApp);

		if (info[1] != null)
			this.artist.setText(info[1]);
		else
			this.artist.setText(notApp);

		if (info[2] != null)
			this.album.setText(info[2]);
		else
			this.album.setText(notApp);

		if (info[3] != null)
			this.year.setText(info[3]);
		else
			this.year.setText(notApp);

		this.albumCover.setImage(albumCover);
	}

}
