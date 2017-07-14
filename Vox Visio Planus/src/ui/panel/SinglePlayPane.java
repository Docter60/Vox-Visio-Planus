/**
 * 
 */
package ui.panel;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class SinglePlayPane extends VoxPane {

	private Group root;
	private Rectangle sceneBound;
	private boolean showPane;

	private Button openButton;

	public SinglePlayPane(Group root) {
		super("Single Play");
		this.root = root;

		this.pane.setPrefSize(80, 50);
		this.pane.setPickOnBounds(false);

		this.pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				showPane = true;
			}
		});

		this.pane.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				showPane = false;
			}
		});
		
		this.sceneBound = new Rectangle(0, 0, this.pane.getPrefWidth(), this.pane.getPrefHeight());
		this.sceneBound.setFill(Color.TRANSPARENT);
		this.sceneBound.setStroke(Color.TRANSPARENT);
		this.sceneBound.setPickOnBounds(true);
		
		this.sceneBound.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				showPane = true;
			}
		});

		this.sceneBound.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				showPane = false;
			}
		});
		
		this.showPane = false;

		this.openButton = new Button("Open");
		this.openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				openButtonPressed();
			}
		});
		this.pane.getChildren().add(openButton);
	}

	@Override
	public void updateElements() {
		super.updateElements();
		this.openButton.relocate(this.pane.getPrefWidth() / 2.0 - this.openButton.getWidth() / 2.0,
				this.pane.getHeight() - this.openButton.getHeight() - 3.0);
	}
	
	public void updatePanel(){
		double oldY = this.pane.getTranslateY();
		double newY;
		double showY = -this.pane.getPrefHeight();
		
		if(showPane)
			newY = Mathg.lerp(oldY, 0, 0.07);
		else
			newY = Mathg.lerp(oldY, showY, 0.07);
		
		this.pane.setTranslateY(newY);
		this.pane.layoutYProperty().set(newY);
	}

	private void openButtonPressed() {
		try {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_O);
			r.keyRelease(KeyEvent.VK_O);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void addToScene(){
		root.getChildren().add(sceneBound);
		root.getChildren().add(pane);
	}
	
}
