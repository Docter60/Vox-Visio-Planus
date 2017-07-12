/**
 * 
 */
package object.visualSpectrum;

import asset.EffectsKit;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * @author Docter60
 *
 */
public abstract class VisualSpectrum {

	protected final double HEIGHT_SCALE;
	
	protected Group elements;
	protected int elementCount;
	protected Scene sceneReference;
	protected float[] dataReference;
	protected EffectsKit ek;
	
	protected double sceneWidth;
	protected double sceneHeight;
	
	public VisualSpectrum(int elementCount, Scene sceneReference, float[] dataReference){
		this.elements = new Group();
		this.elementCount = elementCount;
		this.sceneReference = sceneReference;
		this.dataReference = dataReference;
		this.ek = new EffectsKit(elements);
		
		this.sceneWidth = sceneReference.getWidth();
		this.sceneHeight = sceneReference.getHeight();
		this.HEIGHT_SCALE = sceneReference.getHeight();
	}
	
	public abstract void resizeUpdate();
	
	public abstract void updateNodes();

	public void setElementCount(int elementCount) {
		this.elementCount = elementCount;
	}

	public void setSceneWidth(double sceneWidth) {
		this.sceneWidth = sceneWidth;
		resizeUpdate();
	}

	public void setSceneHeight(double sceneHeight) {
		this.sceneHeight = sceneHeight;
		resizeUpdate();
	}
	
	public EffectsKit getEffectsKit(){
		return this.ek;
	}

	public Group getElements() {
		return elements;
	}
	
}
