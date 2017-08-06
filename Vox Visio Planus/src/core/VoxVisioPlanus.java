/**
 * 
 */
package core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import audio.SpectrumMediaPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import object.visualSpectrum.AudioBarVisualizer;

/**
 * TODO : GUI Revamp, Debug mode
 * 
 * @author Docter60
 *
 */
public class VoxVisioPlanus extends Application {
	public static final String INTRO = "./res/audio/VoxVisioPlanusTheme.mp3";
	public static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	public static final int STAGE_WIDTH = (int) SCREEN_BOUNDS.getWidth() / 2;
	public static final int STAGE_HEIGHT = (int) SCREEN_BOUNDS.getHeight() / 2;

	private List<ResizeListener> resizeListeners;

	private SpectrumMediaPlayer spectrumMediaPlayer;
	private GUIManager guiManager;

	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.resizeListeners = new ArrayList<ResizeListener>();
		primaryStage.setTitle("Vox Visio Planus");
		primaryStage.setX(STAGE_WIDTH / 2);
		primaryStage.setY(STAGE_HEIGHT / 2);

		Group root = new Group();

		Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, Color.BLACK);
		scene.getStylesheets().add((VoxVisioPlanus.class.getResource("VoxVisioPlanus.css").toExternalForm()));
		configureMnemonics(scene);

		primaryStage.setScene(scene);

		spectrumMediaPlayer = new SpectrumMediaPlayer();
		spectrumMediaPlayer.setMedia(new Media(new File(INTRO).toURI().toString()));
		spectrumMediaPlayer.setVolume(0.8);

		guiManager = new GUIManager(this);
		guiManager.addGUIToScene(root);

		// main loop
		Timeline loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);

		KeyFrame kf = new KeyFrame(Duration.seconds(0.005), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				guiManager.hotSpotUpdate();
			}
		});

		loop.getKeyFrames().add(kf);
		loop.play();

		AudioBarVisualizer audioBarVisualizer = new AudioBarVisualizer(scene, spectrumMediaPlayer, 128);
		audioBarVisualizer.getEffectsKit().setFillRainbow();
		audioBarVisualizer.getEffectsKit().setGlow(1.0);

		primaryStage.show();

		guiManager.initializePanes();
	}

	public SpectrumMediaPlayer getSpectrumMediaPlayer() {
		return spectrumMediaPlayer;
	}

	private void toggleFullscreen() {
		if (primaryStage.isFullScreen()) {
			primaryStage.setFullScreen(false);
		} else {
			primaryStage.setFullScreen(true);
		}
	}

	private void configureMnemonics(Scene scene) {
		setMnemonic(scene, KeyCode.O, new AcceleratorEventHandler() {
			@Override
			public void run() { VoxVisioPlanus.this.guiManager.showOpenDialog(); }
			});
		
		setMnemonic(scene, KeyCode.F3, new AcceleratorEventHandler() {
			@Override
			public void run() { VoxVisioPlanus.this.guiManager.toggleDebug(); }
			});

		setMnemonic(scene, KeyCode.F4, new AcceleratorEventHandler() {
			@Override
			public void run() { toggleFullscreen(); }
			});
	}

	private void setMnemonic(Scene scene, KeyCode keyCode, AcceleratorEventHandler aev) {
		scene.getAccelerators().put(new KeyCodeCombination(keyCode, KeyCombination.SHORTCUT_DOWN), aev);
	}

	public void addResizeListener(ResizeListener rl) {
		resizeListeners.add(rl);
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	private class AcceleratorEventHandler implements Runnable {
		@Override
		public void run() {
		}
	}

}
