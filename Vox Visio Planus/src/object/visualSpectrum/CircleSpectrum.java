/**
 * 
 */
package object.visualSpectrum;

import audio.SpectrumMediaPlayer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * 
 * 
 * @author Docter60
 */
public class CircleSpectrum extends AudioSpectrumVisualizer {

	private static final double C = 10.0;
	
	private double heightRatio;
	
	public CircleSpectrum(Scene scene, SpectrumMediaPlayer spectrumMediaPlayer, int circleCount) {
		super(scene, spectrumMediaPlayer);
		heightRatio = sceneHeight / NATIVE_HEIGHT * C;
		double halfSceneWidth = sceneWidth / 2.0;
		double halfSceneHeight = sceneHeight / 2.0;
		for (int i = 0; i < circleCount; i++) {
			Circle c = new Circle();
			c.setCenterX(halfSceneWidth);
			c.setCenterY(halfSceneHeight);
			c.setStroke(Color.WHITE);
			getChildren().add(c);
		}
		((Group) scene.getRoot()).getChildren().add(this);
		toBack();
	}

	@Override
	public void update() {
		for (int i = 0; i < getChildren().size(); i++) {
			Node node = getChildren().get(i);
			Circle c = ((Circle) node);
			double newRadius = spectrumData[i] * heightRatio;
			double radius = INTERPOLATOR.interpolate(c.getRadius(), newRadius, 0.035);
			c.setRadius(radius);
		}
	}

	@Override
	protected void onSceneWidthResize(double newWidth) {
		super.onSceneWidthResize(newWidth);
		for (int i = 0; i < getChildren().size(); i++) {
			Node node = getChildren().get(i);
			Circle c = ((Circle) node);
			c.setCenterX(newWidth / 2.0);
		}
	}

	@Override
	protected void onSceneHeightResize(double newHeight) {
		super.onSceneHeightResize(newHeight);
		for (int i = 0; i < getChildren().size(); i++) {
			Node node = getChildren().get(i);
			Circle c = ((Circle) node);
			c.setCenterY(newHeight / 2.0);
		}
		heightRatio = newHeight / NATIVE_HEIGHT * C;
	}
}
