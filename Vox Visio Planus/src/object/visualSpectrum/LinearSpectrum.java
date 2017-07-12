/**
 * 
 */
package object.visualSpectrum;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class LinearSpectrum extends VisualSpectrum{
	
	public LinearSpectrum(int lineNodeCount, Scene sceneReference, float[] dataReference){
		super(lineNodeCount, sceneReference, dataReference);
		
		cg.configureRainbowGradient();
		
		double lineNodeSpread = sceneWidth / (double) lineNodeCount;
		
		for(int i = 0; i < lineNodeCount - 1; i++){
			double x1 = (double) i * lineNodeSpread;
			double x2 = (double) (i + 1) * lineNodeSpread;
			
			Line line = new Line(x1, sceneHeight, x2, sceneHeight);
			
			Color c = cg.getColor(i / (float) lineNodeCount);
			line.setStroke(c);
			
			elements.getChildren().add(line);
		}
		elements.setEffect(new Glow(1.0));
	}
	
	@Override
	public void updateNodes(){
		for(int i = 0; i < elements.getChildren().size(); i++){
			Node node = elements.getChildren().get(i);
			if(node instanceof Line){
				double oldStartHeight = ((Line) node).getStartY();
				double newStartHeight = sceneHeight - dataReference[i] * 15.0 * (sceneHeight / HEIGHT_SCALE);
				double startHeight = Mathg.lerp(oldStartHeight, newStartHeight, 0.07);
				
				double oldEndHeight = ((Line) node).getEndY();
				double newEndHeight = sceneHeight - dataReference[i+1] * 15.0 * (sceneHeight / HEIGHT_SCALE);
				double endHeight = Mathg.lerp(oldEndHeight, newEndHeight, 0.07);
				
				((Line) node).setStartY(startHeight);
				((Line) node).setEndY(endHeight);
			}
		}
	}
	
	@Override
	public void resizeUpdate(){
		double lineNodeCount = elements.getChildren().size();
		double lineNodeSpread = sceneWidth / lineNodeCount;
		for(int i = 0; i < lineNodeCount; i++){
			double x = (double) i * lineNodeSpread;
			double x2 = (double) (i+1) * lineNodeSpread;
			
			Node node = elements.getChildren().get(i);
			if(node instanceof Line){
				Line line = ((Line) node);
				line.setStartX(x);
				line.setEndX(x2);
				line.setStartY(sceneHeight);
				line.setEndY(sceneHeight);
			}
		}
	}
	
}
