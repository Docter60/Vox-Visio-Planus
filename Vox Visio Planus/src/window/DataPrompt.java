/**
 * 
 */
package window;

import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import window.mod.CloseMod;
import window.mod.ResizeMod;

/**
 *  
 * 
 * @author Docter60
 */
public class DataPrompt extends WindowPane {
	private static final double WIDTH = 300;
	private static final double HEIGHT = 100;
	
	private Node node;
	private String title;
	private IntegerProperty i;
	
	private VBox fieldVBox;
	private HBox fieldHBox;
	private Label label;
	private TextField textField;
	private HBox buttons;
	private Button ok;
	private Button cancel;
	private Label error;

	public DataPrompt(BorderPane parentPane, String title, IntegerProperty i) {
		super(0, 0, WIDTH, HEIGHT);
		this.node = parentPane;
		this.title = title;
		this.i = i;
		
		double mpX = parentPane.getLayoutX();
		double mpY = parentPane.getLayoutY();
		double mpWidth = parentPane.getPrefWidth();
		double mpHeight = parentPane.getPrefHeight();
		
		if(WIDTH < mpWidth)
			mainPane.setLayoutX(mpX + (mpWidth + WIDTH) / 2.0);
		else
			mainPane.setLayoutX(mpX - (WIDTH - mpWidth) / 2.0);
		if(HEIGHT < mpHeight)
			mainPane.setLayoutY(mpY + (mpHeight + HEIGHT) / 2.0);
		else
			mainPane.setLayoutY(mpY - (HEIGHT - mpHeight) / 2.0);
		
		ResizeMod.makeUnresizeable(this.mainPane);
		CloseMod.removeCloseButton(this);
		
		configurePromptUI();
	}
	
	public DataPrompt(Rectangle parentPane, String title, IntegerProperty i) {
		super(0, 0, WIDTH, HEIGHT);
		this.node = parentPane;
		this.title = title;
		this.i = i;
		
		double mpX = parentPane.getLayoutX();
		double mpY = parentPane.getLayoutY();
		double mpWidth = parentPane.getWidth();
		double mpHeight = parentPane.getHeight();
		
		if(WIDTH < mpWidth)
			mainPane.setLayoutX(mpX + (mpWidth + WIDTH) / 2.0);
		else
			mainPane.setLayoutX(mpX - (WIDTH - mpWidth) / 2.0);
		if(HEIGHT < mpHeight)
			mainPane.setLayoutY(mpY + (mpHeight + HEIGHT) / 2.0);
		else
			mainPane.setLayoutY(mpY - (HEIGHT - mpHeight) / 2.0);
		
		ResizeMod.makeUnresizeable(this.mainPane);
		CloseMod.removeCloseButton(this);
		
		configurePromptUI();
	}
	
	private void configurePromptUI() {
		fieldVBox = new VBox();
		fieldHBox = new HBox();
		label = new Label("New " + title.toLowerCase() + ": ");
		textField = new TextField();
		fieldHBox.setAlignment(Pos.CENTER);
		fieldHBox.getChildren().addAll(label, textField);
		error = new Label("Incorrect input.");
		error.setTextFill(Color.RED);
		fieldVBox.getChildren().addAll(fieldHBox, error);
		fieldVBox.setAlignment(Pos.CENTER);
		fieldVBox.setTranslateY(5.0);
		error.setVisible(false);
		
		buttons = new HBox();
		ok = new Button("Ok");
		cancel = new Button("Cancel");
		buttons.setAlignment(Pos.CENTER);
		buttons.setTranslateY(-10.0);
		buttons.setSpacing(10.0);
		buttons.getChildren().addAll(ok, cancel);
		
		mainPane.setCenter(fieldVBox);
		mainPane.setBottom(buttons);
		
		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String input = textField.getText();
				try {
					int in = Integer.parseInt(input);
					i.set(in);
					close();
				} catch (NumberFormatException e) {
					displayErrorMessage();
				}
			}
			
		});
		
		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				close();
			}
			
		});
		
		((Group) node.getScene().getRoot()).getChildren().add(this);
	}
	
	private void displayErrorMessage() {
		error.setVisible(true);
		error.toFront();
	}
	
}
