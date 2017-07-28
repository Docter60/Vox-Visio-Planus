/**
 * 
 */
package window.component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

/**
 * @author Docter60
 *
 */
public class DragBar extends HBox {
	public static final Image DRAG_IMAGE = new Image(DragBar.class.getResource("grip.png").toString());
	public static final double DRAG_IMAGE_FIT = 20.0;
	
	private BorderPane mainPane;
	
	public DragBar(BorderPane mainPane) {
		this.mainPane = mainPane;
		this.refresh();
		this.layoutXProperty().bindBidirectional(mainPane.layoutXProperty());
		this.layoutYProperty().bindBidirectional(mainPane.layoutYProperty());
	}
	
	public void refresh() {
		this.getChildren().clear();
		this.setLayoutX(mainPane.getLayoutX());
		this.setLayoutY(mainPane.getLayoutY());
		this.setPrefSize(DRAG_IMAGE_FIT, DRAG_IMAGE_FIT);
		int tileCount = (int) Math.ceil(mainPane.getPrefWidth() / DRAG_IMAGE_FIT);
		for (int i = 0; i < tileCount; i++) {
			ImageView iv = new ImageView(DRAG_IMAGE);
			iv.setFitWidth(DRAG_IMAGE_FIT);
			iv.setFitHeight(DRAG_IMAGE_FIT);
			iv.setLayoutX(mainPane.getLayoutX() + i * DRAG_IMAGE_FIT);
			iv.setLayoutY(mainPane.getLayoutY());
			this.getChildren().add(iv);
		}
		Rectangle mask = new Rectangle(mainPane.getPrefWidth(), mainPane.getPrefHeight());
		mask.setArcWidth(20);
		mask.setArcHeight(20);

		this.setMinWidth(mainPane.getPrefWidth());
		this.setClip(mask);
	}
	
}
