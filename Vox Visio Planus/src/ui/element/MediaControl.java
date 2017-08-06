/**
 * 
 */
package ui.element;

import audio.SpectrumMediaPlayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

/**
 * @author Docter60
 *
 */
public class MediaControl extends Group {

	public static final String AUDIO_CONTROL_RES = "file:res/texture/audioControl/";
	public static final double BUTTON_FIT_SIZE = 20.0;
	
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
	
	private Slider volumeSlider;
	
	private SpectrumMediaPlayer spectrumMediaPlayer;
	
	public MediaControl(SpectrumMediaPlayer spectrumMediaPlayer) {
		super();
		this.spectrumMediaPlayer = spectrumMediaPlayer;
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
		configureSliderControl();
		
		configureButtonActions();
		configureSliderActions();
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
		
		this.volumeSlider = new Slider();
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
	
	private void configureSliderControl() {
		this.volumeSlider.setMin(0.0);
		this.volumeSlider.setMax(1.0);
		this.volumeSlider.setValue(0.8);
		this.getChildren().add(this.volumeSlider);
	}
	
	public Slider getVolumeSlider() {
		return this.volumeSlider;
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
				MediaControl.this.spectrumMediaPlayer.quickSeek(false);
			}
		});
		
		this.playPauseButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				if(MediaControl.this.spectrumMediaPlayer.isPlaying()) {
					MediaControl.this.playPauseButton.setGraphic(playImage);
					MediaControl.this.spectrumMediaPlayer.pause();
				} else {
					MediaControl.this.playPauseButton.setGraphic(pauseImage);
					MediaControl.this.spectrumMediaPlayer.play();
				}
			}
		});
		
		this.stopButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				MediaControl.this.playPauseButton.setGraphic(playImage);
				MediaControl.this.spectrumMediaPlayer.stop();
			}
		});
		
		this.fastForwardButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				MediaControl.this.spectrumMediaPlayer.quickSeek(true);
			}
		});
		
		this.skipButton.setOnAction(new AudioControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// TODO Skip action event code
			}
		});
	}
	
	private void configureSliderActions() {
		this.volumeSlider.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> o, Object obj, Object obj2) {
				double volume = MediaControl.this.volumeSlider.getValue();
				MediaControl.this.spectrumMediaPlayer.setVolume(volume);
			}
		});
	}
	
	private class AudioControlEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {}
	}
	
}
