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
 * 
 * 
 * @author Docter60
 */
public class AudioLinearVisualizer extends AudioSpectrumVisualizer {

	private static final double C = 15.0;

	private double heightRatio;
	private double lineWidth;

	public AudioLinearVisualizer(Scene scene, SpectrumMediaPlayer spectrumMediaPlayer, int lineNodeCount) {
		super(scene, spectrumMediaPlayer);
		lineWidth = sceneWidth / lineNodeCount;
		heightRatio = sceneHeight / NATIVE_HEIGHT * C;
		for (int i = 0; i < lineNodeCount; i++) {
			double x1 = (double) i * lineWidth;
			double x2 = (double) (i + 1) * lineWidth;
			Line line = new Line(x1, sceneHeight, x2, sceneHeight);
			line.setStroke(Color.GRAY);
			getChildren().add(line);
		}
		((Group) scene.getRoot()).getChildren().add(this);
		toBack();
	}

	@Override
	public void update() {
		double endCarry = 0;
		for (int i = 1; i <= getChildren().size(); i++) {
			Node node = getChildren().get(i - 1);
			Line l = ((Line) node);
			double newStartHeight;
			double startHeight;
			double newEndHeight;
			double endHeight;
			if(i == 1) {
				newStartHeight = sceneHeight - spectrumData[i - 1] * heightRatio;
				startHeight = INTERPOLATOR.interpolate(l.getStartY(), newStartHeight, 0.07);
			} else {
				startHeight = endCarry;
			}
			newEndHeight = sceneHeight - spectrumData[i] * heightRatio;
			endHeight = INTERPOLATOR.interpolate(l.getEndY(), newEndHeight, 0.07);
			l.setStartY(startHeight);
			l.setEndY(endHeight);
			endCarry = endHeight;
		}
	}

	@Override
	protected void onSceneWidthResize(double newWidth) {
		super.onSceneWidthResize(newWidth);
		lineWidth = newWidth / getChildren().size();
		for (int i = 0; i < getChildren().size(); i++) {
			Node node = getChildren().get(i);
			Line l = ((Line) node);
			double x1 = i * lineWidth;
			double x2 = (i + 1) * lineWidth;
			l.setStartX(x1);
			l.setEndX(x2);
		}
	}

	@Override
	protected void onSceneHeightResize(double newHeight) {
		super.onSceneHeightResize(newHeight);
		heightRatio = newHeight / NATIVE_HEIGHT * C;
	}
}