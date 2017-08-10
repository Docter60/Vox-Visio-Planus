/**
 * 
 */
package object.visualSpectrum;

import audio.SpectrumMediaPlayer;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * 
 * 
 * @author Docter60
 */
public class AudioLinearVisualizer extends AudioSpectrumVisualizer {

	private static final double C = 1.0 / 50.0;

	private double heightRatio;
	private double lineWidth;
	private double linearStartHeight;

	public AudioLinearVisualizer(double x, double y, double width, double height, SpectrumMediaPlayer spectrumMediaPlayer, int lineNodeCount) {
		super(x, y, width, height, spectrumMediaPlayer, lineNodeCount);
		lineWidth = width / lineNodeCount;
		heightRatio = height * C;
		linearStartHeight = height + y;
	}
	
	@Override
	protected void reconstructElements() {
		lineWidth = selectionRect.getWidth() / elementCount.get();
		elements.getChildren().clear();
		for (int i = 0; i < elementCount.get(); i++) {
			double x1 = (double) i * lineWidth + selectionRect.getLayoutX();
			double x2 = (double) (i + 1) * lineWidth + selectionRect.getLayoutX();
			Line line = new Line(x1, linearStartHeight, x2, linearStartHeight);
			line.setStroke(Color.GRAY);
			elements.getChildren().add(line);
		}
	}

	@Override
	public void update() {
		double endCarry = 0;
		for (int i = 1; i <= elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i - 1);
			Line l = (Line) node;
			double newStartHeight;
			double startHeight;
			double newEndHeight;
			double endHeight;
			if(i == 1) {
				newStartHeight = linearStartHeight - spectrumData[i - 1] * heightRatio;
				startHeight = INTERPOLATOR.interpolate(l.getStartY(), newStartHeight, 0.07);
			} else {
				startHeight = endCarry;
			}
			newEndHeight = linearStartHeight - spectrumData[i] * heightRatio;
			endHeight = INTERPOLATOR.interpolate(l.getEndY(), newEndHeight, 0.07);
			l.setStartY(startHeight);
			l.setEndY(endHeight);
			endCarry = endHeight;
		}
	}
	
	@Override
	protected void onLayoutXChange(double newLayoutX) {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Line l = (Line) node;
			double x1 = (double) i * lineWidth + newLayoutX;
			double x2 = (double) (i + 1) * lineWidth + newLayoutX;
			l.setStartX(x1);
			l.setEndX(x2);
		}
	}

	@Override
	protected void onLayoutYChange(double newLayoutY) {
		linearStartHeight = selectionRect.getLayoutY() + selectionRect.getHeight();
	}

	@Override
	protected void onWidthResize(double newWidth) {
		lineWidth = newWidth / elements.getChildren().size();
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Line l = ((Line) node);
			double x1 = i * lineWidth + selectionRect.getLayoutX();
			double x2 = (i + 1) * lineWidth + selectionRect.getLayoutX();
			l.setStartX(x1);
			l.setEndX(x2);
		}
	}

	@Override
	protected void onHeightResize(double newHeight) {
		super.onHeightResize(newHeight);
		heightRatio = newHeight * C;
		linearStartHeight = selectionRect.getLayoutY() + selectionRect.getHeight();
	}
}