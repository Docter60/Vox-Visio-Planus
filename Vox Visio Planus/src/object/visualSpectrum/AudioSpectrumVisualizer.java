/**
 * 
 */
package object.visualSpectrum;

import java.util.ArrayList;
import java.util.List;

import asset.EffectsKit;
import audio.SpectrumMediaPlayer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;

/**
 *  The AudioSpectrumVisualizer class requires SpectrumMediaPlayer to function.
 *  This class will visualize the given spectrum data in whatever way is coded in.
 * 
 * @author Docter60
 */
public abstract class AudioSpectrumVisualizer extends Group {
	protected static final double NATIVE_WIDTH = 1920.0 / 2.0;
	protected static final double NATIVE_HEIGHT = 1080.0 / 2.0;
	protected static final double UPDATE_FREQUENCY = 0.01;
	protected static final Interpolator INTERPOLATOR = Interpolator.LINEAR;
	
	private static List<AudioSpectrumVisualizer> audioSpectrumVisualizers;
	private static final Timeline updateLoop;
	
	static {
		audioSpectrumVisualizers = new ArrayList<AudioSpectrumVisualizer>();
		updateLoop = new Timeline();
		updateLoop.setCycleCount(Timeline.INDEFINITE);
		final KeyFrame kf = new KeyFrame(Duration.seconds(UPDATE_FREQUENCY), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				for(AudioSpectrumVisualizer asv : audioSpectrumVisualizers) {
					asv.update();
				}
			}
		});
		updateLoop.getKeyFrames().add(kf);
		updateLoop.play();
	}
	
	protected Scene scene;
	protected EffectsKit effectsKit;
	protected double sceneWidth;
	protected double sceneHeight;
	protected float[] spectrumData;
	
	private SceneWidthResizeListener sceneWidthResizeListener;
	private SceneHeightResizeListener sceneHeightResizeListener;
	
	public AudioSpectrumVisualizer(Scene scene, SpectrumMediaPlayer spectrumMediaPlayer) {
		super();
		this.scene = scene;
		sceneWidth = scene.getWidth();
		sceneHeight = scene.getHeight();
		spectrumData = spectrumMediaPlayer.getSpectrumData();
		effectsKit = new EffectsKit(this);
		
		sceneWidthResizeListener = new SceneWidthResizeListener();
		sceneHeightResizeListener = new SceneHeightResizeListener();
		
		scene.widthProperty().addListener(sceneWidthResizeListener);
		scene.heightProperty().addListener(sceneHeightResizeListener);
		
		audioSpectrumVisualizers.add(this);
	}
	
	protected void remove() {
		audioSpectrumVisualizers.remove(this);
		scene.widthProperty().removeListener(sceneWidthResizeListener);
		scene.heightProperty().removeListener(sceneHeightResizeListener);
	}
	
	protected abstract void update();
	
	public EffectsKit getEffectsKit() {
		return effectsKit;
	}
	
	protected void onSceneWidthResize(double newWidth) {
		sceneWidth = newWidth;
	}
	
	protected void onSceneHeightResize(double newHeight) {
		sceneHeight = newHeight;
	}
	
	protected class SceneWidthResizeListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			AudioSpectrumVisualizer.this.onSceneWidthResize(newVal.doubleValue());
		}
	}
	
	protected class SceneHeightResizeListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			AudioSpectrumVisualizer.this.onSceneHeightResize(newVal.doubleValue());
		}
	}
}
