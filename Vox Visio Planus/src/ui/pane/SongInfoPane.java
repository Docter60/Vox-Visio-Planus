/**
 * 
 */
package ui.pane;

import audio.MediaChangedListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import window.WindowPane;

/**
 * @author Docter60
 *
 */
public class SongInfoPane extends WindowPane implements MediaChangedListener {
	public static final String AUDIO_CONTROL_RES = "file:res/texture/infoPane/";
	public static final double WIDTH = 300;
	public static final double HEIGHT = 100;

	public static final Image defaultCover = new Image(AUDIO_CONTROL_RES + "defaultCover.png");

	public static String notApp = "Unknown";

	private HBox infoBox;
	private VBox textInfoBox;
	
	private Label title;
	private Label artist;
	private Label album;
	private Label year;
	private ImageView albumCover;

	public SongInfoPane(Scene primaryScene) {
		super((primaryScene.getWidth() - WIDTH) / 2.0, primaryScene.getHeight() - HEIGHT, WIDTH, HEIGHT);
		this.mainPane.setId("songInfoPane");
		this.relocate((primaryScene.getWidth() - WIDTH) / 2.0, primaryScene.getHeight() - HEIGHT);

		this.infoBox = new HBox();
		
		this.textInfoBox = new VBox();
		this.textInfoBox.setTranslateY(20.0);
		
		this.title = new Label();
		this.artist = new Label();
		this.album = new Label();
		this.year = new Label();
		this.albumCover = new ImageView(new Image(AUDIO_CONTROL_RES + "defaultCover.png"));
		this.albumCover.setFitWidth(100);
		this.albumCover.setFitHeight(100);
		this.albumCover.setScaleX(0.8);
		this.albumCover.setScaleY(0.8);
		this.albumCover.setTranslateY(10.0);

		this.title.setId("songInfoLabel");
		this.artist.setId("songInfoLabel");
		this.album.setId("songInfoLabel");
		this.year.setId("songInfoLabel");

		textInfoBox.getChildren().add(title);
		textInfoBox.getChildren().add(artist);
		textInfoBox.getChildren().add(album);
		textInfoBox.getChildren().add(year);
		
		infoBox.getChildren().add(albumCover);
		infoBox.getChildren().add(textInfoBox);
		infoBox.setTranslateX(30.0);
		
		this.mainPane.setCenter(infoBox);
		
		this.setRestoreShortcut(KeyCode.I);
		this.setResizeable(false);
		this.setSlideable(true);
	}

	@Override
	public void changed(String[] info, Image albumCover) {
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
