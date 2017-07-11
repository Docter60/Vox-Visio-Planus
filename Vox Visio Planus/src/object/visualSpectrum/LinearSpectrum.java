/**
 * 
 */
package object.visualSpectrum;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class LinearSpectrum {
	
	private Group lines;
	private double middleY;
	private double screenHeight;
	
	public LinearSpectrum(double width, double height, int lineNodeCount){
		this.lines = new Group();
		
		double lineNodeSpread = width / (double) lineNodeCount;
		this.screenHeight = height;
		this.middleY = height / 2.0;
		
		for(int i = 0; i < lineNodeCount - 1; i++){
			double x1 = (double) i * lineNodeSpread;
			double x2 = (double) (i + 1) * lineNodeSpread;
			
			Line line = new Line(x1, 0, x2, 0);
			line.setStroke(Color.AQUA);
			
			lines.getChildren().add(line);
		}
		lines.setEffect(new Glow(1.0));
	}
	
	public void setLineNodeHeights(float[] data){
		for(int i = 0; i < lines.getChildren().size(); i++){
			Node node = lines.getChildren().get(i);
			if(node instanceof Line){
				double oldStartHeight = ((Line) node).getStartY();
				double newStartHeight = screenHeight - data[i] * 30.0;
				double startHeight = Mathg.lerp(oldStartHeight, newStartHeight, 0.07);
				
				double oldEndHeight = ((Line) node).getEndY();
				double newEndHeight = screenHeight - data[i+1] * 30.0;
				double endHeight = Mathg.lerp(oldEndHeight, newEndHeight, 0.07);
				
				((Line) node).setStartY(startHeight);
				((Line) node).setEndY(endHeight);
			}
		}
	}
	
	public Group getLines(){
		return lines;
	}
	
}
