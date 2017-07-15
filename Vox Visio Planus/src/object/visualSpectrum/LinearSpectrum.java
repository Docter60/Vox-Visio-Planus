/**
 * 
 */
package object.visualSpectrum;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class LinearSpectrum extends VisualSpectrum {

	public LinearSpectrum(int lineNodeCount, Scene sceneReference, float[] dataReference) {
		super(lineNodeCount, sceneReference, dataReference);
		double lineNodeSpread = sceneWidth / (double) lineNodeCount;
		for (int i = 0; i < lineNodeCount - 1; i++) {
			double x1 = (double) i * lineNodeSpread;
			double x2 = (double) (i + 1) * lineNodeSpread;
			Line line = new Line(x1, sceneHeight, x2, sceneHeight);
			line.setStroke(Color.GRAY);
			elements.getChildren().add(line);
		}
	}

	@Override
	public void updateNodes() {
		double nativeHeightRatio = sceneHeight / NATIVE_HEIGHT;
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			if (node instanceof Line) {
				Line l = ((Line) node);
				double newStartHeight = sceneHeight - dataReference[i] * 15.0 * nativeHeightRatio;
				double startHeight = Mathg.lerp(l.getStartY(), newStartHeight, 0.07);
				double newEndHeight = sceneHeight - dataReference[i + 1] * 15.0 * nativeHeightRatio;
				double endHeight = Mathg.lerp(l.getEndY(), newEndHeight, 0.07);
				l.setStartY(startHeight);
				l.setEndY(endHeight);
			}
		}
	}

	@Override
	public void sceneResizeUpdate(double sceneWidth, double sceneHeight) {
		super.sceneResizeUpdate(sceneWidth, sceneHeight);
		double lineNodeCount = elements.getChildren().size();
		double lineNodeSpread = sceneWidth / lineNodeCount;
		for (int i = 0; i < lineNodeCount; i++) {
			double x = (double) i * lineNodeSpread;
			double x2 = (double) (i + 1) * lineNodeSpread;
			Node node = elements.getChildren().get(i);
			if (node instanceof Line) {
				Line l = ((Line) node);
				l.setStartX(x);
				l.setEndX(x2);
				l.setStartY(sceneHeight);
				l.setEndY(sceneHeight);
			}
		}
	}
}