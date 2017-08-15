/**
 * 
 */
package object.visualSpectrum;

import audio.SpectrumMediaPlayer;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * 
 * 
 * @author Docter60
 */
public class AudioCircleVisualizer extends AudioSpectrumVisualizer {

	private static final double C = 1.0 / 100.0;

	private double heightRatio;

	public AudioCircleVisualizer(double x, double y, double width, double height, SpectrumMediaPlayer spectrumMediaPlayer,
			int circleCount) {
		super(x, y, width, height, spectrumMediaPlayer, circleCount);
		heightRatio = height * C;
	}
	
	@Override
	protected void reconstructElements() {
		elements.getChildren().clear();
		double halfSceneWidth = selectionRect.getWidth() / 2.0 + selectionRect.getLayoutX();
		double halfSceneHeight = selectionRect.getHeight() / 2.0 + selectionRect.getLayoutY();
		for (int i = 0; i < elementCount.get(); i++) {
			Circle c = new Circle();
			c.setCenterX(halfSceneWidth);
			c.setCenterY(halfSceneHeight);
			c.setStroke(Color.WHITE);
			c.setStrokeType(StrokeType.CENTERED);
			c.setStrokeWidth(3.0);
			c.setFill(Color.TRANSPARENT);
			elements.getChildren().add(c);
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Circle c = ((Circle) node);
			double newRadius = spectrumData[i] * heightRatio;
			double radius = INTERPOLATOR.interpolate(c.getRadius(), newRadius, 0.035);
			c.setRadius(radius);
		}
	}

	@Override
	protected void onLayoutXChange(double newLayoutX) {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Circle c = ((Circle) node);
			c.setCenterX(newLayoutX + selectionRect.getWidth() / 2.0);
		}
	}

	@Override
	protected void onLayoutYChange(double newLayoutY) {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Circle c = ((Circle) node);
			c.setCenterY(selectionRect.getHeight() / 2.0 + newLayoutY);
		}
	}

	@Override
	protected void onWidthResize(double newWidth) {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Circle c = ((Circle) node);
			c.setCenterX(selectionRect.getLayoutX() + newWidth / 2.0);
		}
		if (newWidth < selectionRect.getHeight()) {
			heightRatio = newWidth * C;
		}
	}

	@Override
	protected void onHeightResize(double newHeight) {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Circle c = ((Circle) node);
			c.setCenterY(newHeight / 2.0 + selectionRect.getLayoutY());
		}
		if (newHeight < selectionRect.getWidth()) {
			heightRatio = newHeight * C;
		}
	}
}
