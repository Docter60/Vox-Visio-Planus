/**
 * 
 */
package window.mod;

import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import window.WindowPane;

/**
 * @author Docter60
 *
 */
public class CloseMod {
	public static final Image CLOSE_IMAGE = new Image(CloseMod.class.getResource("close.png").toString());
	
	private static HashMap<WindowPane, CloseMod> listenerHandles = new HashMap<WindowPane, CloseMod>();
	
	private WindowPane windowPane;
	private Button closeButton;
	private Pane mainPane;
	
	private ClickListener clickListener;
	private XListener xListener;
	private XListener widthListener;
	private YListener yListener;
	private YListener heightListener;
	
	private CloseMod(WindowPane windowPane) {
		this.windowPane = windowPane;
		this.mainPane = windowPane.getMainPane();
		this.closeButton = createCloseButton(mainPane);
		
		this.clickListener = new ClickListener();
		this.xListener = new XListener();
		this.widthListener = new XListener();
		this.yListener = new YListener();
		this.heightListener = new YListener();
		
		this.closeButton.setOnAction(clickListener);
		mainPane.prefWidthProperty().addListener(widthListener);
		mainPane.prefHeightProperty().addListener(heightListener);
		mainPane.layoutXProperty().addListener(xListener);
		mainPane.layoutYProperty().addListener(yListener);
		this.windowPane.getChildren().add(closeButton);
	}
	
	public static void makeCloseButton(WindowPane windowPane) {
		listenerHandles.put(windowPane, new CloseMod(windowPane));
	}
	
	public static void removeCloseButton(WindowPane windowPane) {
		Pane borderPane = windowPane.getMainPane();
		borderPane.layoutXProperty().removeListener(listenerHandles.get(windowPane).xListener);
		borderPane.prefWidthProperty().removeListener(listenerHandles.get(windowPane).widthListener);
		borderPane.layoutYProperty().removeListener(listenerHandles.get(windowPane).yListener);
		borderPane.prefHeightProperty().removeListener(listenerHandles.get(windowPane).heightListener);
		windowPane.getChildren().remove(listenerHandles.get(windowPane).closeButton);
		listenerHandles.remove(windowPane);
	}
	
	private static Button createCloseButton(Pane mainPane) {
		Button closeButton = new Button();
		ImageView closeView = new ImageView(CLOSE_IMAGE);
		closeView.setFitWidth(15);
		closeView.setFitHeight(15);
		closeView.fitWidthProperty().bind(closeButton.prefWidthProperty());
		closeView.fitHeightProperty().bind(closeButton.prefHeightProperty());
		closeButton.setPrefWidth(16);
		closeButton.setPrefHeight(16);
		closeButton.setPadding(Insets.EMPTY);
		closeButton.setGraphic(closeView);
		closeButton.setId("closeButton");
		closeButton.setLayoutX(mainPane.getLayoutX() + mainPane.getPrefWidth() - closeView.getFitWidth() - 2);
		closeButton.setLayoutY(mainPane.getLayoutY() + 2);
		return closeButton;
	}
	
	private class XListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			closeButton.setLayoutX(mainPane.getLayoutX() + mainPane.getPrefWidth() - closeButton.getPrefWidth() - 2);
		}
	}
	
	private class YListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			closeButton.setLayoutY(mainPane.getLayoutY() + 2);
		}
	}
	
	private class ClickListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			windowPane.setVisible(false);
			windowPane.toBack();
		}
		
	}
}
