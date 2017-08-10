/**
 * 
 */
package core;

import java.io.File;

import audio.SpectrumMediaPlayer;
import javafx.application.Application;
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
import object.visualSpectrum.AudioBarVisualizer;
import object.visualSpectrum.AudioCircleVisualizer;
import object.visualSpectrum.AudioLinearVisualizer;
import object.visualSpectrum.AudioSpectrumVisualizer;

/**
 * 
 * 
 * @author Docter60
 */
public class VoxVisioPlanus extends Application {
	public static final String INTRO = "./res/audio/VoxVisioPlanusTheme.mp3";
	public static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	public static final double STAGE_WIDTH = SCREEN_BOUNDS.getWidth() / 2.0;
	public static final double STAGE_HEIGHT = SCREEN_BOUNDS.getHeight() / 2.0;

	private SpectrumMediaPlayer spectrumMediaPlayer;
	private GUIManager guiManager;

	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Vox Visio Planus");
		primaryStage.setX(STAGE_WIDTH / 2);
		primaryStage.setY(STAGE_HEIGHT / 2);

		Group root = new Group();

		Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, Color.BLACK);
		scene.getStylesheets().add((VoxVisioPlanus.class.getResource("VoxVisioPlanus.css").toExternalForm()));
		configureMnemonics(scene);

		primaryStage.setScene(scene);
		AudioSpectrumVisualizer.setScene(scene);

		spectrumMediaPlayer = new SpectrumMediaPlayer();
		spectrumMediaPlayer.setMedia(new Media(new File(INTRO).toURI().toString()));
		spectrumMediaPlayer.setVolume(0.8);

		guiManager = new GUIManager(this);
		
		AudioLinearVisualizer linearSpectrum = new AudioLinearVisualizer(100, 100, 500, 300, spectrumMediaPlayer, 128);
		linearSpectrum.getEffectsKit().setStrokeRainbow();
		
		AudioBarVisualizer audioBarVisualizer = new AudioBarVisualizer(100, 100, 500, 300, spectrumMediaPlayer, 128);
		audioBarVisualizer.getEffectsKit().setFillRainbow();
		
		AudioCircleVisualizer circleSpectrum = new AudioCircleVisualizer(100, 100, 500, 300, spectrumMediaPlayer, 128);
		circleSpectrum.getEffectsKit().setStrokeRainbow();

		primaryStage.show();
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
	
	private void toggleDebugMode() {
		// TODO Handling Toggle debug mode
	}

	private void configureMnemonics(Scene scene) {
		setMnemonic(scene, KeyCode.O, new Runnable() {
			@Override
			public void run() { VoxVisioPlanus.this.guiManager.showOpenDialog(); }
			});
		
		setMnemonic(scene, KeyCode.F3, new Runnable() {
			@Override
			public void run() { VoxVisioPlanus.this.toggleDebugMode(); }
			});

		setMnemonic(scene, KeyCode.F4, new Runnable() {
			@Override
			public void run() { VoxVisioPlanus.this.toggleFullscreen(); }
			});
	}

	private void setMnemonic(Scene scene, KeyCode keyCode, Runnable r) {
		scene.getAccelerators().put(new KeyCodeCombination(keyCode, KeyCombination.SHORTCUT_DOWN), r);
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
}
