/**
 * 
 */
package object.visualSpectrum;

import audio.SpectrumMediaPlayer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * TODO There are many things that can be calculated in listeners instead of the update method.
 * 
 * @author Docter60
 *
 */
public class AudioLinearVisualizer extends AudioSpectrumVisualizer {

	public AudioLinearVisualizer(Scene scene, SpectrumMediaPlayer spectrumMediaPlayer, int lineNodeCount) {
		super(scene, spectrumMediaPlayer);
		double lineNodeSpread = sceneWidth / (double) lineNodeCount;
		for (int i = 0; i < lineNodeCount - 1; i++) {
			double x1 = (double) i * lineNodeSpread;
			double x2 = (double) (i + 1) * lineNodeSpread;
			Line line = new Line(x1, sceneHeight, x2, sceneHeight);
			line.setStroke(Color.GRAY);
			getChildren().add(line);
		}
		((Group) scene.getRoot()).getChildren().add(this);
		toBack();
	}

	@Override
	public void update() {
		double nativeHeightRatio = sceneHeight / NATIVE_HEIGHT;
		double lineNodeSpread = sceneWidth / (double) getChildren().size();
		for (int i = 0; i < getChildren().size(); i++) {
			Node node = getChildren().get(i);
			if (node instanceof Line) {
				Line l = ((Line) node);
				double x1 = (double) i * lineNodeSpread;
				double x2 = (double) (i + 1) * lineNodeSpread;
				double newStartHeight = sceneHeight - spectrumData[i] * 15.0 * nativeHeightRatio;
				double startHeight = INTERPOLATOR.interpolate(l.getStartY(), newStartHeight, 0.07);
				double newEndHeight = sceneHeight - spectrumData[i + 1] * 15.0 * nativeHeightRatio;
				double endHeight = INTERPOLATOR.interpolate(l.getEndY(), newEndHeight, 0.07);
				l.setStartY(startHeight);
				l.setEndY(endHeight);
				l.setStartX(x1);
				l.setEndX(x2);
			}
		}
	}
}