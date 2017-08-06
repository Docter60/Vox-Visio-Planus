/**
 * 
 */
package object.visualSpectrum;

import asset.EffectsKit;
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
	
	public AudioBarVisualizer(Scene scene, SpectrumMediaPlayer spectrumMediaPlayer, int barCount) {
		super(scene, spectrumMediaPlayer);
		elements = new Group();
		effectsKit = new EffectsKit(elements);
		double barWidth = scene.getWidth() / (double) barCount;
		double halfSceneHeight = scene.getHeight() / 2.0;
		for (int i = 0; i < barCount; i++) {
			double x = (double) i * barWidth;
			Rectangle r = new Rectangle(x, halfSceneHeight, barWidth - 1, 0);
			r.setFill(Color.GREY);
			r.setStroke(Color.BLACK);
			elements.getChildren().add(r);
		}
		((Group) scene.getRoot()).getChildren().add(elements);
	}
	
	public EffectsKit getEffectsKit() {
		return effectsKit;
	}

	@Override
	protected void update() {
		double nativeHeightRatio = sceneHeight / NATIVE_HEIGHT;
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			if (node instanceof Rectangle) {
				Rectangle r = ((Rectangle) node);
				double oldHeight = r.getHeight();
				double newHeight = spectrumData[i] * 15.0 * nativeHeightRatio;
				double height = INTERPOLATOR.interpolate(oldHeight, newHeight, 0.07);
				r.setWidth(sceneWidth / elements.getChildren().size() - 1);
				r.setHeight(height);
				r.setX(i * sceneWidth / elements.getChildren().size());
				r.setY((sceneHeight - height) / 2.0);
			}
		}
	}
}
