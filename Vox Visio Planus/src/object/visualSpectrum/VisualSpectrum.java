/**
 * 
 */
package object.visualSpectrum;

import asset.ColorGradient;
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
	protected ColorGradient cg;
	
	protected double sceneWidth;
	protected double sceneHeight;
	
	public VisualSpectrum(int elementCount, Scene sceneReference, float[] dataReference){
		this.elements = new Group();
		this.elementCount = elementCount;
		this.sceneReference = sceneReference;
		this.dataReference = dataReference;
		this.cg = new ColorGradient();
		
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

	public ColorGradient getCg() {
		return cg;
	}

	public void setCg(ColorGradient cg) {
		this.cg = cg;
	}

	public Group getElements() {
		return elements;
	}
	
}
