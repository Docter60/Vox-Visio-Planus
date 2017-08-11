/**
 * 
 */
package asset;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import object.visualSpectrum.AudioSpectrumVisualizer;
import object.visualSpectrum.mod.DragMod;
import object.visualSpectrum.mod.ResizeMod;
import window.DataPrompt;

/**
 * 
 * 
 * @author Docter60
 */
public class VisualizerMenu extends ContextMenu {

	//private static HashMap<AudioSpectrumVisualizer, VisualizerMenu> listenerHandles = new HashMap<AudioSpectrumVisualizer, VisualizerMenu>();

	private AudioSpectrumVisualizer asv;

	private MenuItem setElementCount;
	private MenuItem setColors;
	private MenuItem delete;
	private MenuItem bringToFront;
	private MenuItem sendToBack;
	private MenuItem deselect;
	private CheckMenuItem setFullScene;

	//private DataPrompt prompt;
	
	private double oldX;
	private double oldY;
	private double oldWidth;
	private double oldHeight;
	private SceneWidthListener sceneWidthListener;
	private SceneHeightListener sceneHeightListener;

	public VisualizerMenu(AudioSpectrumVisualizer asv) {
		super();
		this.asv = asv;
		setElementCount = new MenuItem("Set Element Count");
		bringToFront = new MenuItem("Bring to Front");
		sendToBack = new MenuItem("Send to Back");
		setColors = new MenuItem("Set Colors");
		delete = new MenuItem("Delete");
		setFullScene = new CheckMenuItem("Set Fullscreen");
		deselect = new MenuItem("Deselect");

		getItems().addAll(deselect, setElementCount, setColors, setFullScene, new SeparatorMenuItem(), bringToFront, sendToBack,
				new SeparatorMenuItem(), delete);

		asv.getSelectionRect().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					show(asv.getSelectionRect(), e.getScreenX(), e.getScreenY());
				} else {
					hide();
				}
			}
		});

		recordOldData();
		
		sceneWidthListener = new SceneWidthListener();
		sceneHeightListener = new SceneHeightListener();
		
		configureMenuActions();
	}
	
	private void recordOldData() {
		oldX = asv.getSelectionRect().getLayoutX();
		oldY = asv.getSelectionRect().getLayoutY();
		oldWidth = asv.getSelectionRect().getWidth();
		oldHeight = asv.getSelectionRect().getHeight();
	}
	
	private void useOldData() {
		asv.getSelectionRect().setLayoutX(oldX);
		asv.getSelectionRect().setLayoutY(oldY);
		asv.getSelectionRect().setWidth(oldWidth);
		asv.getSelectionRect().setHeight(oldHeight);
	}

	private void configureMenuActions() {
		bringToFront.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				asv.toFront();
			}
		});

		sendToBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				asv.toBack();
			}
		});

		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				asv.close();
			}
		});

		setElementCount.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new DataPrompt(asv.getSelectionRect(), "Element Count", asv.elementCountProperty());
			}
		});

		setFullScene.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (setFullScene.isSelected()) {
					recordOldData();
					asv.getSelectionRect().setLayoutX(0);
					asv.getSelectionRect().setLayoutY(0);
					asv.getSelectionRect().setWidth(asv.getScene().getWidth());
					asv.getSelectionRect().setHeight(asv.getScene().getHeight());
					
					asv.getScene().widthProperty().addListener(sceneWidthListener);
					asv.getScene().heightProperty().addListener(sceneHeightListener);
					
					ResizeMod.makeUnresizeable(asv.getSelectionRect());
					DragMod.makeUndraggable(asv.getSelectionRect());
				} else {
					asv.getScene().widthProperty().removeListener(sceneWidthListener);
					asv.getScene().heightProperty().removeListener(sceneHeightListener);
					useOldData();
					ResizeMod.makeResizable(asv.getSelectionRect());
					DragMod.makeDraggable(asv.getSelectionRect());
				}
			}
		});
		
		deselect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				asv.getSelectionRect().setStroke(Color.TRANSPARENT);
				ResizeMod.makeUnresizeable(asv.getSelectionRect());
				DragMod.makeUndraggable(asv.getSelectionRect());
			}
		});
	}
	
	public boolean isFullScreen() {
		return setFullScene.isSelected();
	}
	
	private class SceneWidthListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			asv.getSelectionRect().setWidth(newValue.doubleValue());
		}
	}
	
	private class SceneHeightListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			asv.getSelectionRect().setHeight(newValue.doubleValue());
		}
	}
}
