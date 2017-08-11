/**
 * 
 */
package object.visualSpectrum;

import asset.EffectsKit;
import asset.VisualizerMenu;
import audio.SpectrumMediaPlayer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import object.visualSpectrum.mod.DragMod;
import object.visualSpectrum.mod.ResizeMod;

/**
 * The AudioSpectrumVisualizer class requires SpectrumMediaPlayer to function.
 * This class will visualize the given spectrum data in whatever way is coded
 * in.
 * 
 * @author Docter60
 */
public abstract class AudioSpectrumVisualizer extends Group {
	protected static final double NATIVE_WIDTH = 1920.0 / 2.0;
	protected static final double NATIVE_HEIGHT = 1080.0 / 2.0;
	protected static final double UPDATE_FREQUENCY = 0.01;
	protected static final Interpolator INTERPOLATOR = Interpolator.LINEAR;

	private static Group audioSpectrumVisualizers;
	private static AudioSpectrumVisualizer selectedVisualizer;
	private static final Timeline updateLoop;

	static {
		audioSpectrumVisualizers = new Group();
		updateLoop = new Timeline();
		updateLoop.setCycleCount(Timeline.INDEFINITE);
		final KeyFrame kf = new KeyFrame(Duration.seconds(UPDATE_FREQUENCY), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				for (int i = 0; i < audioSpectrumVisualizers.getChildren().size(); i++) {
					AudioSpectrumVisualizer asv = (AudioSpectrumVisualizer) audioSpectrumVisualizers.getChildren().get(i);
					asv.update();
				}
			}
		});
		updateLoop.getKeyFrames().add(kf);
		updateLoop.play();
	}

	protected Group elements;
	protected Rectangle selectionRect;
	protected VisualizerMenu menu;
	protected EffectsKit effectsKit;
	protected float[] spectrumData;
	protected IntegerProperty elementCount;

	private LayoutXListener layoutXListener;
	private LayoutYListener layoutYListener;
	private WidthListener widthListener;
	private HeightListener heightListener;
	private ElementCountListener elementCountListener;

	public AudioSpectrumVisualizer(double x, double y, double width, double height,
			SpectrumMediaPlayer spectrumMediaPlayer, int eCount) {
		super();
		elements = new Group();
		selectionRect = new Rectangle();
		selectionRect.setLayoutX(x);
		selectionRect.setLayoutY(y);
		selectionRect.setWidth(width);
		selectionRect.setHeight(height);
		selectionRect.setFill(Color.TRANSPARENT);
		selectionRect.setStroke(Color.TRANSPARENT);
		spectrumData = spectrumMediaPlayer.getSpectrumData();
		effectsKit = new EffectsKit(elements);
		elementCount = new SimpleIntegerProperty(eCount);
		elementCount.set(eCount);

		layoutXListener = new LayoutXListener();
		layoutYListener = new LayoutYListener();
		widthListener = new WidthListener();
		heightListener = new HeightListener();
		elementCountListener = new ElementCountListener();
		
		elementCount.addListener(elementCountListener);

		selectionRect.layoutXProperty().addListener(layoutXListener);
		selectionRect.layoutYProperty().addListener(layoutYListener);
		selectionRect.widthProperty().addListener(widthListener);
		selectionRect.heightProperty().addListener(heightListener);
		selectionRect.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(selectedVisualizer != null) {
					selectedVisualizer.selectionRect.setStroke(Color.TRANSPARENT);
					ResizeMod.makeUnresizeable(selectedVisualizer.selectionRect);
					DragMod.makeUndraggable(selectedVisualizer.selectionRect);
				}
				
				if(!menu.isFullScreen()) {
					selectionRect.setStroke(Color.GRAY);
					selectedVisualizer = AudioSpectrumVisualizer.this;
					ResizeMod.makeResizable(selectedVisualizer.selectionRect);
					DragMod.makeDraggable(selectedVisualizer.selectionRect);
					toFront();
				}
				event.consume();
			}
		});
		
		menu = new VisualizerMenu(this);

		getChildren().add(elements);
		getChildren().add(selectionRect);
		audioSpectrumVisualizers.getChildren().add(this);
		toBack();
		
		reconstructElements();
	}

	protected void remove() {
		audioSpectrumVisualizers.getChildren().remove(this);
	}

	public static void setScene(Scene scene) {
		((Group) scene.getRoot()).getChildren().add(audioSpectrumVisualizers);
		audioSpectrumVisualizers.toBack();
		scene.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(selectedVisualizer != null)
					selectedVisualizer.selectionRect.setStroke(Color.TRANSPARENT);
			}
		});
	}
	
	protected abstract void reconstructElements();
	protected abstract void update();
	
	public void close() {
		remove();
		ResizeMod.makeUnresizeable(selectionRect);
		DragMod.makeUndraggable(selectionRect);
		this.getChildren().clear();
		selectedVisualizer = null;
	}
	
	public int getElementCount() {
		return elementCount.get();
	}
	
	public void setElementCount(int val) {
		elementCount.set(val);
	}
	
	public IntegerProperty elementCountProperty() {
		return elementCount;
	}

	public EffectsKit getEffectsKit() {
		return effectsKit;
	}
	
	public Rectangle getSelectionRect() {
		return selectionRect;
	}

	protected void onLayoutXChange(double newLayoutX) {
	}

	protected void onLayoutYChange(double newLayoutY) {
	}

	protected void onWidthResize(double newWidth) {
	}

	protected void onHeightResize(double newHeight) {
	}

	protected void onSceneChange(Scene scene) {
	}
	
	protected void onElementCountChange(int newElementCount) {
		reconstructElements();
		effectsKit.reapply();
	}
	
	protected class ElementCountListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			AudioSpectrumVisualizer.this.onElementCountChange((int)newVal.doubleValue());
		}
	}

	protected class LayoutXListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			AudioSpectrumVisualizer.this.onLayoutXChange(newVal.doubleValue());
		}
	}

	protected class LayoutYListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			AudioSpectrumVisualizer.this.onLayoutYChange(newVal.doubleValue());
		}
	}

	protected class WidthListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			AudioSpectrumVisualizer.this.onWidthResize(newVal.doubleValue());
		}
	}

	protected class HeightListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			AudioSpectrumVisualizer.this.onHeightResize(newVal.doubleValue());
		}
	}

	protected class SceneListener implements ChangeListener<Scene> {
		@Override
		public void changed(ObservableValue<? extends Scene> obs, Scene oldVal, Scene newVal) {
			AudioSpectrumVisualizer.this.onSceneChange(newVal);
		}
	}
}
