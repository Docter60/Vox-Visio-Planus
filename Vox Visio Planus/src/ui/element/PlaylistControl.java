package ui.element;

import audio.SpectrumMediaPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;


/**
 * TODO Create Playlist UI
 * 
 * @author Docter60
 */
public class PlaylistControl extends Group {

	public static final String PLAYLIST_CONTROL_RES = "file:res/texture/playlistControl/";
	public static final double BUTTON_FIT_WIDTH = 70.0;
	public static final double BUTTON_FIT_HEIGHT = 20.0;
	
	private Button openButton;
	private Button closeButton;
	private Button addButton;
	private Button removeButton;
	private Button addFolderButton;
	private Button swapButton;
	private ToggleButton shuffleToggle;
	
	//private SpectrumMediaPlayer spectrumMediaPlayer;
	
	public PlaylistControl(SpectrumMediaPlayer spectrumMediaPlayer) {
		super();
		//this.spectrumMediaPlayer = spectrumMediaPlayer;
		initializeControls();
		configureControls();
	}
	
	private void initializeControls() {
		this.openButton = new Button("Open");
		this.closeButton = new Button("Close");
		this.addButton = new Button("Add");
		this.removeButton = new Button("Remove");
		this.addFolderButton = new Button("Add multiple");
		this.swapButton = new Button("Swap");
		this.shuffleToggle = new ToggleButton("Shuffle");
	}
	
	private void configureControls() {
		configureButtonControl(this.openButton, BUTTON_FIT_WIDTH);
		configureButtonControl(this.closeButton, BUTTON_FIT_WIDTH);
		configureButtonControl(this.addButton, BUTTON_FIT_WIDTH);
		configureButtonControl(this.removeButton, BUTTON_FIT_WIDTH);
		configureButtonControl(this.addFolderButton, BUTTON_FIT_WIDTH);
		configureButtonControl(this.swapButton, BUTTON_FIT_WIDTH);
		
		this.getChildren().add(shuffleToggle);
		
		configureButtonActions();
		configureToggleActions();
	}
	
	private void configureButtonControl(Button b, double width) {
		b.setPrefHeight(BUTTON_FIT_HEIGHT);
		b.setPrefWidth(width);
		this.getChildren().add(b);
	}
	
	private void configureButtonActions() {
		this.openButton.setOnAction(new PlaylistControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// Handle Opening
			}
		});
		
		this.closeButton.setOnAction(new PlaylistControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// Handle Closing
			}
		});
		
		this.addButton.setOnAction(new PlaylistControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// Handle Adding
			}
		});
		
		this.removeButton.setOnAction(new PlaylistControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// Handle Removing
			}
		});
		
		this.addFolderButton.setOnAction(new PlaylistControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// Handle Adding entire folder
			}
		});
		
		this.swapButton.setOnAction(new PlaylistControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// Handle Swapping
			}
		});
	}
	
	private void configureToggleActions() {
		this.shuffleToggle.setOnAction(new PlaylistControlEventHandler() {
			@Override
			public void handle(ActionEvent e) {
				// Handle Shuffling
			}
		});
	}
	
	public void initializeElements(double width, double height) {
		double startXPos = width - 2 * BUTTON_FIT_WIDTH - 10;
		this.openButton.relocate(10, 10);
		this.closeButton.relocate(width - BUTTON_FIT_WIDTH - 10, 10);
		this.addButton.relocate(startXPos, 40);
		this.removeButton.relocate(startXPos + BUTTON_FIT_WIDTH, 70);
		this.addFolderButton.relocate(startXPos + BUTTON_FIT_WIDTH, 40);
		this.swapButton.relocate(startXPos, 70);
		this.shuffleToggle.relocate(startXPos + BUTTON_FIT_WIDTH + 10, height - BUTTON_FIT_HEIGHT - 20);
	}
	
	private class PlaylistControlEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {}
	}
	
}
