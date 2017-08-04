/**
 * 
 */
package window;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import window.component.DragBar;
import window.mod.CloseMod;
import window.mod.DragMod;
import window.mod.ResizeMod;
import window.mod.RestoreMod;
import window.mod.SlideMod;
import window.mod.SnapMod;

/**
 * @author Docter60
 *
 */
public class WindowPane extends Group {
	public static final double DEFAULT_SIZE = 100;

	protected BorderPane mainPane;
	protected DragBar dragBar;
	protected Label title;

	public WindowPane(double x, double y, double width, double height, String title) {
		this(x, y, width, height);
		this.setTitle(title);
	}
	
	public WindowPane(double x, double y, double width, double height) {
		super();
		this.setOnMousePressed(new ClickListener());
		this.mainPane = new BorderPane();
		this.mainPane.setId("windowPane");
		this.mainPane.setLayoutX(x);
		this.mainPane.setLayoutY(y);
		this.mainPane.setPrefSize(width, height);
		this.mainPane.prefWidthProperty().addListener(new WidthListener());
		
		Rectangle clip = new Rectangle(0, 0, mainPane.getPrefWidth(), mainPane.getPrefHeight());
		clip.setArcWidth(20);
		clip.setArcHeight(20);
		this.mainPane.setClip(clip);
		
		this.dragBar = new DragBar(mainPane);
		this.dragBar.layoutXProperty().bindBidirectional(this.mainPane.layoutXProperty());
		this.dragBar.layoutYProperty().bindBidirectional(this.mainPane.layoutYProperty());
		
		this.getChildren().add(mainPane);
		this.getChildren().add(dragBar);
		
		this.setDraggable(true);
		this.setResizeable(true);
		this.setSnappable(true);
		this.hasCloseButton(true);
	}
	
	public WindowPane(String title) {
		this(100.0, 100.0, DEFAULT_SIZE, DEFAULT_SIZE, title);
	}

	public void setTitle(String title) {
		this.title = new Label("  " + title);
		this.title.setId("windowTitle");
		this.title.setStyle("-fx-font-size: 13px;" + 
							"-fx-font-weight: bold;" + 
							"-fx-text-fill: #333333;" + 
							"-fx-effect: dropshadow(gaussian, rgba(200, 200, 200, 0.5), 0, 0, 0, 1);");
		this.mainPane.setTop(this.title);
	}
	
	public void setDraggable(boolean isDraggable) {
		if(isDraggable)
			DragMod.makeDraggable(dragBar);
		else
			DragMod.makeUndraggable(dragBar);
	}
	
	public void setResizeable(boolean isResizeable) {
		if(isResizeable)
			ResizeMod.makeResizable(mainPane);
		else
			ResizeMod.makeUnresizeable(mainPane);
	}
	
	public void setSnappable(boolean isSnappable) {
		if(isSnappable)
			SnapMod.setSnappable(this);
		else
			SnapMod.setUnsnappable(this);
	}
	
	public void setSlideable(boolean isSlideable) {
		if(isSlideable)
			SlideMod.setSlideable(this);
		else
			SlideMod.setUnslideable(this);
	}
	
	public void hasCloseButton(boolean hasCloseButton) {
		if(hasCloseButton)
			CloseMod.makeCloseButton(this);
		else
			CloseMod.removeCloseButton(this);
	}
	
	public void setRestoreShortcut(KeyCode keyCode) {
		if(keyCode != null)
			RestoreMod.createRestoreMod(this, keyCode);
		else
			RestoreMod.removeRestoreMod(this);
	}

	public BorderPane getMainPane() {
		return this.mainPane;
	}

	/*
	 * Inner classes
	 */

	protected class WidthListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			WindowPane.this.dragBar.refresh();
		}
	}

	protected class ClickListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent arg0) {
			WindowPane.this.toFront();
		}
	}
}
