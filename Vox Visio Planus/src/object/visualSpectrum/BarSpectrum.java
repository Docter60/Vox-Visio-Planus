/**
 * 
 */
package object.visualSpectrum;

import asset.ColorGradient;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import math.Mathg;

/**
 * @author Docter60
 *
 */
public class BarSpectrum {

	private final double heightScale;
	
	private Group bars;
	private ColorGradient colorGradient;
	private double middleY;
	private double windowWidth;

	public BarSpectrum(double width, double height, int barCount) {
		this.bars = new Group();
		this.windowWidth = width;
		this.middleY = height / 2.0;
		this.heightScale = middleY;
		
		this.colorGradient = new ColorGradient();
		colorGradient.configureRainbowGradient();

		double barWidth = width / (double) barCount;
		
		for (int i = 0; i < barCount; i++) {
			double x = (double) i * barWidth;

			//TODO use constructor instead of setters
			Rectangle r = new Rectangle();
			r.setX(x);
			r.setY(middleY);
			r.setWidth(barWidth - 1.0);
			r.setHeight(0);
			
			Color c = colorGradient.getColor(i / (float) barCount);
			r.setFill(c);
			
			bars.getChildren().add(r);
		}
		bars.setEffect(new Glow(1.0));
	}

	public void setBarHeights(float[] data) {
		for (int i = 0; i < bars.getChildren().size(); i++) {
			Node node = bars.getChildren().get(i);
			if (node instanceof Rectangle) {
				double oldHeight = ((Rectangle) node).getHeight();
				double newHeight = data[i] * 30.0;
				double height = Mathg.lerp(oldHeight, newHeight * (middleY / heightScale), 0.07);
				((Rectangle) node).setHeight(height);
				((Rectangle) node).setY(middleY - height / 2.0);
			}
		}
	}
	
	public void updateResizedPositions(){
		double barCount = bars.getChildren().size();
		double barWidth = windowWidth / barCount;
		for(int i = 0; i < barCount; i++){
			double x = (double) i * barWidth;

			Node node = bars.getChildren().get(i);
			if(node instanceof Rectangle){
				Rectangle r = ((Rectangle) node);
				r.setX(x);
				r.setY(middleY);
				r.setWidth(barWidth - 1.0);
			}
		}
	}

	public Group getBars() {
		return bars;
	}

	public void setMiddleY(double middleY) {
		this.middleY = middleY;
		updateResizedPositions();
	}

	public void setWindowWidth(double windowWidth) {
		this.windowWidth = windowWidth;
		updateResizedPositions();
	}

	
}
