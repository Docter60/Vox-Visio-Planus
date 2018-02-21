/**
 * 
 */
package object.visualSpectrum;

import audio.SpectrumMediaPlayer;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * 
 * @author Docter60
 */
public class AudioBarVisualizer extends AudioSpectrumVisualizer {

	private static final double C = 1.0 / 30.0;
	
	private double barWidth;
	private double heightRatio;
	private double middleY;

	public AudioBarVisualizer(double x, double y, double width, double height, SpectrumMediaPlayer spectrumMediaPlayer, int barCount) {
		super(x, y, width, height, spectrumMediaPlayer, barCount);
		barWidth = width / barCount - 1;
		heightRatio = height * C;
		middleY = height / 2.0 + y;
	}
	
	@Override
	protected void reconstructElements() {
		elements.getChildren().clear();
		barWidth = selectionRect.getWidth() / elementCount.get() - 1;
		for (int i = 0; i < elementCount.get(); i++) {
			double barX = i * barWidth + i + selectionRect.getLayoutX();
			Rectangle r = new Rectangle();
			r.setLayoutX(barX);
			r.setLayoutY(middleY);
			r.setHeight(0.0);
			r.setWidth(barWidth);
			r.setFill(Color.WHITE);
			r.setStroke(Color.BLACK);
			elements.getChildren().add(r);
		}
	}

	@Override
	protected void update() {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Rectangle r = ((Rectangle) node);
			double newHeight = spectrumData[i] * heightRatio;
			double height = INTERPOLATOR.interpolate(r.getHeight(), newHeight, 0.07);
			r.setHeight(height);
			r.setLayoutY(middleY - height / 2.0);
		}
	}
	
	@Override
	protected void onLayoutXChange(double newLayoutX) {
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Rectangle r = ((Rectangle) node);
			r.setLayoutX(i * barWidth + i + newLayoutX);
		}
	}

	@Override
	protected void onLayoutYChange(double newLayoutY) {
		middleY = selectionRect.getHeight() / 2.0 + selectionRect.getLayoutY();
	}

	@Override
	protected void onWidthResize(double newWidth) {
		barWidth = newWidth / elements.getChildren().size() - 1;
		for (int i = 0; i < elements.getChildren().size(); i++) {
			Node node = elements.getChildren().get(i);
			Rectangle r = ((Rectangle) node);
			r.setLayoutX(i * barWidth + i + selectionRect.getLayoutX());
			r.setWidth(barWidth);
		}
	}

	@Override
	protected void onHeightResize(double newHeight) {
		middleY = selectionRect.getHeight() / 2.0 + selectionRect.getLayoutY();
		heightRatio = newHeight * C;
	}
}
