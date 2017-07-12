/**
 * 
 */
package core;

import audio.VoxPlayer;
import input.Key;
import input.Keyboard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import object.visualSpectrum.BarSpectrum;
import object.visualSpectrum.LinearSpectrum;

/**
 * @author Docter60
 *
 */
public class VoxVisioPlanus extends Application{
	public static final String DESKTOP_SAMPLE = "C:\\Users\\Docte\\Music\\iTunes\\iTunes Media\\Music\\Laszlo\\Closer EP\\05 Law of the Jungle.m4a";
	public static final String DESKTOP_SAMPLE2 = "C:\\Users\\Docte\\Music\\iTunes\\iTunes Media\\Music\\The Chainsmokers\\Memories...Do Not Open\\05 Something Just Like This.m4a";
	public static final String LAPTOP_SAMPLE = "C:\\Users\\Docter60\\Music\\iTunes\\iTunes Media\\Music\\Laszlo\\Closer EP\\05 Law of the Jungle.m4a";
	public static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	public static final int STAGE_WIDTH = (int) SCREEN_BOUNDS.getWidth() / 2;
	public static final int STAGE_HEIGHT = (int) SCREEN_BOUNDS.getHeight() / 2;
	
	private VoxPlayer voxPlayer;
	private BarSpectrum barSpectrum;
	private LinearSpectrum linearSpectrum;
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		////////////////////////////////////////////////////
		//           Setting up the foundation            //
		////////////////////////////////////////////////////
		
		primaryStage.setTitle("Vox Visio Planus");
		primaryStage.setX(STAGE_WIDTH / 2);
		primaryStage.setY(STAGE_HEIGHT / 2);
		Group root = new Group();
		Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, Color.BLACK);
		primaryStage.setScene(scene);
		
		voxPlayer = new VoxPlayer();
		voxPlayer.load(DESKTOP_SAMPLE);
		voxPlayer.setVolume(0.4);
		voxPlayer.play();
		
		barSpectrum = new BarSpectrum(128, scene, voxPlayer.getSpectrumData());
		root.getChildren().add(barSpectrum.getElements());
		
		linearSpectrum = new LinearSpectrum(scene.getWidth(), scene.getHeight(), 128);
		root.getChildren().add(linearSpectrum.getLines());
		
		//Renderer renderer = new Renderer(root);  // Renderer class isn't needed right now
		
		//Listening to window resize events
		primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {setWidth(newVal.doubleValue());});
		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {setHeight(newVal.doubleValue());});
		
		////////////////////////////////////////////////////
		//                     Loop                       //
		////////////////////////////////////////////////////
		Timeline loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);
		
		//final long timeStart = System.currentTimeMillis();
		
		KeyFrame kf = new KeyFrame(
				Duration.seconds(0.01), 
				new EventHandler<ActionEvent>()
				{
					public void handle(ActionEvent ae)
					{
						barSpectrum.updateNodes();
						
						if(Keyboard.keyIsPressed(Key.ESC)) System.exit(0); // Doesn't work.  Need to use javafx methods
					}
				});
		
		////////////////////////////////////////////////////
		
		loop.getKeyFrames().add(kf);
		loop.play();
		
		primaryStage.show();
	}
	
	public void setWidth(double width){
		barSpectrum.setSceneWidth(width);
	}
	
	public void setHeight(double height){
		barSpectrum.setSceneHeight(height);
	}
	
}
