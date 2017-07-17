/**
 * 
 */
package ui.element;

import audio.VoxPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * @author Docter60
 *
 */
public class MediaControl extends Group {

	public static final String AUDIO_CONTROL_RES = "file:res/texture/audioControl/";
	public static final double BUTTON_FIT_SIZE = 20;
	
	public ImageView previousImage;
	public ImageView rewindImage;
	public ImageView playImage;
	public ImageView pauseImage;
	public ImageView stopImage;
	public ImageView fastForwardImage;
	public ImageView skipImage;
	
	private Button previousButton;
	private Button rewindButton;
	private Button playPauseButton;
	private Button stopButton;
	private Button fastForwardButton;
	private Button skipButton;
	
	private VoxPlayer voxPlayer;
	
	public MediaControl(VoxPlayer voxPlayer) {
		super();
		this.voxPlayer = voxPlayer;
		initializeControls();
		configureControls();
	}
	
	public double getMediaControlButtonWidth() {
		return this.previousButton.getWidth();
	}
	
	public double getMediaControlButtonHeight() {
		return this.previousButton.getHeight();
	}
	
	private void configureControls() {
		setFitSize(pauseImage);
		configureButtonControl(this.previousButton, this.previousImage);
		configureButtonControl(this.rewindButton, this.rewindImage);
		configureButtonControl(this.playPauseButton, this.playImage);
		configureButtonControl(this.stopButton, this.stopImage);
		configureButtonControl(this.fastForwardButton, this.fastForwardImage);
		configureButtonControl(this.skipButton, this.skipImage);
		
		configureButtonActions();
	}
	
	private void initializeControls() {
		this.previousImage    = new ImageView(AUDIO_CONTROL_RES + "previous.png");
		this.rewindImage      = new ImageView(AUDIO_CONTROL_RES + "rewind.png");
		this.playImage        = new ImageView(AUDIO_CONTROL_RES + "play.png");
		this.pauseImage       = new ImageView(AUDIO_CONTROL_RES + "pause.png");
		this.stopImage        = new ImageView(AUDIO_CONTROL_RES + "stop.png");
		this.fastForwardImage = new ImageView(AUDIO_CONTROL_RES + "fastForward.png");
		this.skipImage        = new ImageView(AUDIO_CONTROL_RES + "skip.png");
		
		this.previousButton = new Button();
		this.rewindButton = new Button();
		this.playPauseButton = new Button();
		this.stopButton = new Button();
		this.fastForwardButton = new Button();
		this.skipButton = new Button();
	}
	
	private void setFitSize(ImageView iv) {
		iv.setFitWidth(BUTTON_FIT_SIZE);
		iv.setFitHeight(BUTTON_FIT_SIZE);
		iv.setPreserveRatio(true);
	}
	
	private void configureButtonControl(Button b, ImageView iv) {
		setFitSize(iv);
		b.setGraphic(iv);
		this.getChildren().add(b);
	}
	
	private void configureButtonActions() {
		this.previousButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// TODO Previous action event code
			}
		});
		
		this.rewindButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				MediaControl.this.voxPlayer.quickSeek(false);
			}
		});
		
		this.playPauseButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				if(MediaControl.this.voxPlayer.isPlaying()) {
					MediaControl.this.playPauseButton.setGraphic(playImage);
					MediaControl.this.voxPlayer.pause();
				} else {
					MediaControl.this.playPauseButton.setGraphic(pauseImage);
					MediaControl.this.voxPlayer.play();
				}
			}
		});
		
		this.stopButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				MediaControl.this.playPauseButton.setGraphic(playImage);
				MediaControl.this.voxPlayer.stop();
			}
		});
		
		this.fastForwardButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				MediaControl.this.voxPlayer.quickSeek(true);
			}
		});
		
		this.skipButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// TODO Skip action event code
			}
		});
	}
	
	private class AudioControlEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {}
	}
	
}
