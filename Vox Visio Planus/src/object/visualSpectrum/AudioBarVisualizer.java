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
 *  TODO There are many things that can be calculated in listeners instead of the update method.
 * 
 * @author Docter60
 */
public class AudioBarVisualizer extends AudioSpectrumVisualizer {
	
	public AudioBarVisualizer(Scene scene, SpectrumMediaPlayer spectrumMediaPlayer, int barCount) {
		super(scene, spectrumMediaPlayer);
		double barWidth = scene.getWidth() / (double) barCount;
		double halfSceneHeight = scene.getHeight() / 2.0;
		for (int i = 0; i < barCount; i++) {
			double x = (double) i * barWidth;
			Rectangle r = new Rectangle(x, halfSceneHeight, barWidth - 1, 0);
			r.setFill(Color.GREY);
			r.setStroke(Color.BLACK);
			getChildren().add(r);
		}
		((Group) scene.getRoot()).getChildren().add(this);
		toBack();
	}

	@Override
	protected void update() {
		double nativeHeightRatio = sceneHeight / NATIVE_HEIGHT;
		for (int i = 0; i < getChildren().size(); i++) {
			Node node = getChildren().get(i);
			if (node instanceof Rectangle) {
				Rectangle r = ((Rectangle) node);
				double oldHeight = r.getHeight();
				double newHeight = spectrumData[i] * 15.0 * nativeHeightRatio;
				double height = INTERPOLATOR.interpolate(oldHeight, newHeight, 0.07);
				r.setWidth(sceneWidth / getChildren().size() - 1);
				r.setHeight(height);
				r.setX(i * sceneWidth / getChildren().size());
				r.setY((sceneHeight - height) / 2.0);
			}
		}
	}
}
