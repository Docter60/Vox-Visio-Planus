/**
 * 
 */
package core;

import asset.dialog.OpenFileDialog;
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
import object.visualSpectrum.BarSpectrum;
import object.visualSpectrum.LinearSpectrum;
import ui.panel.SinglePlayPane;

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
	private BarSpectrum barSpectrum;
	private LinearSpectrum linearSpectrum;
	
	private OpenFileDialog openFileDialog;
	
	private SinglePlayPane singlePlayPane;;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		////////////////////////////////////////////////////
		// Setting up the foundation //
		////////////////////////////////////////////////////
		primaryStage.setTitle("Vox Visio Planus");
		primaryStage.setX(STAGE_WIDTH / 2);
		primaryStage.setY(STAGE_HEIGHT / 2);
		Group root = new Group();
		Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, Color.BLACK);
		scene.getStylesheets().add((VoxVisioPlanus.class.getResource("VoxVisioPlanus.css").toExternalForm()));
		scene.setOnKeyPressed(this);

		primaryStage.setScene(scene);
		
		openFileDialog = new OpenFileDialog(primaryStage);
		
		voxPlayer = new VoxPlayer();
		voxPlayer.load(INTRO);
		voxPlayer.setVolume(0.5);

		barSpectrum = new BarSpectrum(128, scene, voxPlayer.getSpectrumData());
		barSpectrum.getEffectsKit().setFillRainbow();
		barSpectrum.getEffectsKit().setGlow(1.0);

		linearSpectrum = new LinearSpectrum(128, scene, voxPlayer.getSpectrumData());
		linearSpectrum.getEffectsKit().setStrokeRainbow();
		linearSpectrum.getEffectsKit().setGlow(1.0);

		singlePlayPane = new SinglePlayPane(root);
		
		root.getChildren().add(linearSpectrum.getElements());
		root.getChildren().add(barSpectrum.getElements());
		singlePlayPane.addToScene();

		// Listening to window resize events
		primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
			setWidth(newVal.doubleValue());
		});
		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			setHeight(newVal.doubleValue());
		});

		////////////////////////////////////////////////////
		// Loop //
		////////////////////////////////////////////////////
		Timeline loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);

		KeyFrame kf = new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				barSpectrum.updateNodes();
				linearSpectrum.updateNodes();
				singlePlayPane.updatePanel();
			}
		});

		////////////////////////////////////////////////////

		loop.getKeyFrames().add(kf);
		loop.play();

		primaryStage.show();
		
		singlePlayPane.updateElements();
	}

	public void setWidth(double width) {
		barSpectrum.setSceneWidth(width);
		linearSpectrum.setSceneWidth(width);
	}

	public void setHeight(double height) {
		barSpectrum.setSceneHeight(height);
		linearSpectrum.setSceneHeight(height);
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
			openFileDialog.showDialog(OpenFileDialog.OPEN_AUDIO_FILE);
			if(openFileDialog.getPathToFile() != null){
				voxPlayer.load(openFileDialog.getPathToFile());
				openFileDialog.clearPathToFile();
				linearSpectrum.setDataReference(voxPlayer.getSpectrumData());
				barSpectrum.setDataReference(voxPlayer.getSpectrumData());
			}
			break;
		default:
			break;
		}
	}

}
