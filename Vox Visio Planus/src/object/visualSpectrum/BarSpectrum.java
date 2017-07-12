/**
 * 
 */
package object.visualSpectrum;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class BarSpectrum extends VisualSpectrum{

	public BarSpectrum(int barCount, Scene sceneReference, float[] dataReference) {
		super(barCount, sceneReference, dataReference);
		
		cg.configureRainbowGradient();

		double barWidth = sceneWidth / (double) barCount;
		barWidth--;
		
		for (int i = 0; i < barCount; i++) {
			double x = (double) i * barWidth;

			Rectangle r = new Rectangle(x, sceneHeight / 2.0, barWidth, 0);
			
			Color c = cg.getColor(i / (float) barCount);
			r.setFill(c);
			
			elements.getChildren().add(r);
		}
		elements.setEffect(new Glow(1.0));
	}

	@Override
	public void updateNodes() {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			if (node instanceof Rectangle) {
				double oldHeight = ((Rectangle) node).getHeight();
				double newHeight = dataReference[i] * 30.0;
				double height = Mathg.lerp(oldHeight, newHeight * ((sceneHeight / 2.0) / HEIGHT_SCALE), 0.07);
				((Rectangle) node).setHeight(height);
				((Rectangle) node).setY((sceneHeight - height) / 2.0);
			}
		}
	}
	
	@Override
	public void resizeUpdate(){
		double barCount = elements.getChildren().size();
		double barWidth = sceneWidth / barCount;
		for(int i = 0; i < barCount; i++){
			double x = (double) i * barWidth;

			Node node = elements.getChildren().get(i);
			if(node instanceof Rectangle){
				Rectangle r = ((Rectangle) node);
				r.setX(x);
				r.setY(sceneHeight / 2.0);
				r.setWidth(barWidth - 1.0);
			}
		}
	}
}
