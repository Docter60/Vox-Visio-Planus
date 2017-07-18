/**
 * 
 */
package core;

import java.io.File;

import audio.VoxMedia;
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
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Docter60
 *
 */
public class VoxVisioPlanus extends Application {
	public static final String INTRO = "./res/audio/VoxVisioPlanusTheme.mp3";
	public static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	public static final int STAGE_WIDTH = (int) SCREEN_BOUNDS.getWidth() / 2;
	public static final int STAGE_HEIGHT = (int) SCREEN_BOUNDS.getHeight() / 2;

	private VoxPlayer voxPlayer;
	private VisualSpectrumManager visualSpectrumManager;
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

		voxPlayer = new VoxPlayer();
		voxPlayer.load(new VoxMedia(new File(INTRO).toURI().toString()));
		voxPlayer.setVolume(0.2);

		visualSpectrumManager = new VisualSpectrumManager(this);
		visualSpectrumManager.addVisualSpectrumsToScene(root);

		guiManager = new GUIManager(this);
		guiManager.addGUIToScene(root);

		// Listening to window resize events
		primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
			sceneResizeUpdate(primaryStage);
		});
		primaryStage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			sceneResizeUpdate(primaryStage);
		});
		primaryStage.fullScreenProperty().addListener((obs, oldValue, iconified) -> { // Not getting new width and height values
			System.out.println("Hello");
			sceneResizeUpdate(primaryStage);
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
	
	private void sceneResizeUpdate(Stage primaryStage) {
		double width = primaryStage.getScene().getWidth();
		double height = primaryStage.getScene().getHeight();
		visualSpectrumManager.sceneResizeUpdate(width, height);
		guiManager.resizeUpdate(width, height);
	}

	private void configureMnemonics(Scene scene) {
		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN),
				new AcceleratorEventHandler() {
					@Override
					public void run() {
						VoxVisioPlanus.this.guiManager.showOpenDialog();
					}
				});
		
		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F3, KeyCombination.SHORTCUT_DOWN),
				new AcceleratorEventHandler() {
					@Override
					public void run() {
						guiManager.toggleDebug();
					}
				});
		
		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F4, KeyCombination.SHORTCUT_DOWN),
				new AcceleratorEventHandler() {
					@Override
					public void run() {
						Stage stage = VoxVisioPlanus.this.primaryStage;
						if(stage.isFullScreen())
							stage.setFullScreen(false);
						else
							stage.setFullScreen(true);
					}
				});
	}
	
	public VoxPlayer getVoxPlayer() {
		return this.voxPlayer;
	}
	
	public VisualSpectrumManager getVisualSpectrumManager() {
		return this.visualSpectrumManager;
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
