/**
 * 
 */
package core;

import audio.VoxPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Docter60
 *
 */
public class VoxVisioPlanus extends Application implements EventHandler<KeyEvent> {
	public static final String INTRO = "./res/audio/VoxVisioPlanusTheme.mp3";
	public static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	public static final int STAGE_WIDTH = (int) SCREEN_BOUNDS.getWidth() / 2;
	public static final int STAGE_HEIGHT = (int) SCREEN_BOUNDS.getHeight() / 2;

	private VoxPlayer voxPlayer;
	private VisualSpectrumManager visualSpectrumManager;
	private GUIManager guiManager;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Vox Visio Planus");
		primaryStage.setX(STAGE_WIDTH / 2);
		primaryStage.setY(STAGE_HEIGHT / 2);

		Group root = new Group();

		Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, Color.BLACK);
		scene.getStylesheets().add((VoxVisioPlanus.class.getResource("VoxVisioPlanus.css").toExternalForm()));
		scene.setOnKeyPressed(this);

		primaryStage.setScene(scene);

		voxPlayer = new VoxPlayer();
		voxPlayer.load(INTRO);
		voxPlayer.setVolume(0.5);

		visualSpectrumManager = new VisualSpectrumManager(primaryStage, voxPlayer);
		visualSpectrumManager.addVisualSpectrumsToScene(root);

		guiManager = new GUIManager(primaryStage);
		guiManager.addGUIToScene(root);

		// Listening to window resize events
		primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
			visualSpectrumManager.sceneResizeUpdate(newWidth.doubleValue(), primaryStage.getHeight());
		});
		primaryStage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			visualSpectrumManager.sceneResizeUpdate(primaryStage.getWidth(), newHeight.doubleValue());
		});

		// main loop
		Timeline loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);

		KeyFrame kf = new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				visualSpectrumManager.updateMagnitudeData();
				guiManager.hotSpotUpdate();
			}
		});

		loop.getKeyFrames().add(kf);
		loop.play();

		primaryStage.show();

		guiManager.initializePanes();
	}

	@Override
	public void handle(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();

		switch (keyCode) {
		case ESCAPE:
			// cleanup();
			System.exit(0);
			break;
		case K:
			if (voxPlayer.isPlaying())
				voxPlayer.pause();
			else
				voxPlayer.play();
			break;
		case L:
			voxPlayer.quickSeek(true);
			break;
		case J:
			voxPlayer.quickSeek(false);
			break;
		case O:
			guiManager.showOpenDialog(visualSpectrumManager, voxPlayer);
			break;
		default:
			break;
		}
	}

}
