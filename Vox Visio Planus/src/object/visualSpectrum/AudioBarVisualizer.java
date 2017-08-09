/**
 * 
 */
package object.visualSpectrum;

import audio.SpectrumMediaPlayer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * 
 * @author Docter60
 */
public class AudioBarVisualizer extends AudioSpectrumVisualizer {

	private static final double C = 15.0;
	
	private double barWidth;
	private double heightRatio;
	private double middleY;

	public AudioBarVisualizer(Scene scene, SpectrumMediaPlayer spectrumMediaPlayer, int barCount) {
		super(scene, spectrumMediaPlayer);
		barWidth = scene.getWidth() / barCount - 1;
		heightRatio = sceneHeight / NATIVE_HEIGHT * C;
		middleY = sceneHeight / 2.0;
		for (int i = 0; i < barCount; i++) {
			double x = i * barWidth + i;
			Rectangle r = new Rectangle(x, middleY, barWidth, 0);
			r.setWidth(barWidth);
			r.setFill(Color.GREY);
			r.setStroke(Color.BLACK);
			getChildren().add(r);
		}
		((Group) scene.getRoot()).getChildren().add(this);
		toBack();
	}

	@Override
	protected void update() {
		for (int i = 0; i < getChildren().size(); i++) {
			Node node = getChildren().get(i);
			Rectangle r = ((Rectangle) node);
			double newHeight = spectrumData[i] * heightRatio;
			double height = INTERPOLATOR.interpolate(r.getHeight(), newHeight, 0.07);
			r.setHeight(height);
			r.setY(middleY - height / 2.0);
		}
	}

	@Override
	protected void onSceneWidthResize(double newWidth) {
		super.onSceneWidthResize(newWidth);
		barWidth = newWidth / getChildren().size() - 1;
		for (int i = 0; i < getChildren().size(); i++) {
			Node node = getChildren().get(i);
			Rectangle r = ((Rectangle) node);
			r.setX(i * barWidth + i);
			r.setWidth(barWidth);
		}
	}

	@Override
	protected void onSceneHeightResize(double newHeight) {
		super.onSceneHeightResize(newHeight);
		heightRatio = newHeight / NATIVE_HEIGHT * C;
		middleY = newHeight / 2.0;
	}
}
