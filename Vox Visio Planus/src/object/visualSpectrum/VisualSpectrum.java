/**
 * 
 */
package object.visualSpectrum;

import asset.EffectsKit;
import audio.VoxMediaSpectrumListener;
import core.ResizeListener;
import core.VoxVisioPlanus;
import javafx.animation.Interpolator;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * @author Docter60
 *
 */
public abstract class VisualSpectrum implements VoxMediaSpectrumListener, ResizeListener {
	protected static final double NATIVE_WIDTH = VoxVisioPlanus.STAGE_WIDTH;
	protected static final double NATIVE_HEIGHT = VoxVisioPlanus.STAGE_HEIGHT;

	protected double sceneWidth;
	protected double sceneHeight;

	protected static Interpolator interpolator = Interpolator.LINEAR;
	
	protected Group elements;
	protected float[] dataReference;
	protected EffectsKit ek;

	public VisualSpectrum(int elementCount, Scene primaryScene, float[] dataReference) {
		this.sceneWidth = primaryScene.getWidth();
		this.sceneHeight = primaryScene.getHeight();
		this.elements = new Group();
		this.dataReference = dataReference;
		this.ek = new EffectsKit(elements);
	}

	@Override
	public void resizeUpdate(double sceneWidth, double sceneHeight) {
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
	}

	public abstract void updateNodes();

	public void setDataReference(float[] newDataReference) {
		this.dataReference = newDataReference;
	}

	public EffectsKit getEffectsKit() {
		return this.ek;
	}

	public void addToScene(Group root) {
		root.getChildren().add(elements);
	}
	
	@Override
	public void updateDataReference(float[] data) {
		this.dataReference = data;
	}

}
