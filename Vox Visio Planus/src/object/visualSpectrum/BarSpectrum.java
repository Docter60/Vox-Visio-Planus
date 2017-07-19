/**
 * 
 */
package object.visualSpectrum;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Docter60
 *
 */
public class BarSpectrum extends VisualSpectrum {

	public BarSpectrum(int barCount, Scene scene, float[] dataReference) {
		super(barCount, scene, dataReference);
		double barWidth = sceneWidth / (double) barCount;
		double halfSceneHeight = sceneHeight / 2.0;
		for (int i = 0; i < barCount; i++) {
			double x = (double) i * barWidth;
			Rectangle r = new Rectangle(x, halfSceneHeight, barWidth - 1, 0);
			r.setFill(Color.GRAY);
			elements.getChildren().add(r);
		}
	}

	@Override
	public void updateNodes() {
		double nativeHeightRatio = (sceneHeight / 2.0) / NATIVE_HEIGHT;
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			if (node instanceof Rectangle) {
				Rectangle r = ((Rectangle) node);
				double oldHeight = r.getHeight();
				double newHeight = dataReference[i] * 30.0 * nativeHeightRatio;
				double height = interpolator.interpolate(oldHeight, newHeight, 0.07);
				r.setHeight(height);
				r.setY((sceneHeight - height) / 2.0);
			}
		}
	}

	@Override
	public void resizeUpdate(double sceneWidth, double sceneHeight) {
		super.resizeUpdate(sceneWidth, sceneHeight);
		double nativeHeightRatio = sceneHeight / 2.0 / NATIVE_HEIGHT;
		double barCount = elements.getChildren().size();
		double barWidth = sceneWidth / barCount;
		double halfSceneHeight = sceneHeight / 2.0;
		for (int i = 0; i < barCount; i++) {
			double x = (double) i * barWidth;
			double height = dataReference[i] * 30.0 * nativeHeightRatio;
			Node node = elements.getChildren().get(i);
			if (node instanceof Rectangle) {
				Rectangle r = ((Rectangle) node);
				r.setWidth(barWidth - 1.0);
				r.setHeight(height);
				r.setX(x);
				r.setY(halfSceneHeight - r.getHeight() / 2.0);
			}
		}
	}
}