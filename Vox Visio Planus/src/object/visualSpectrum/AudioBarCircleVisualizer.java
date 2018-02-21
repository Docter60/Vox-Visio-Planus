/**
 * 
 */
package object.visualSpectrum;

import audio.SpectrumMediaPlayer;

/**
 *  
 * 
 * @author Docter60
 */
public class AudioBarCircleVisualizer extends AudioSpectrumVisualizer {

//	private double barWidth;
//	private double radius;
//	private double speed;
	
	public AudioBarCircleVisualizer(double x, double y, double width, double height, SpectrumMediaPlayer spectrumMediaPlayer, int barCount, double radius, double speed) {
		super(x, y, width, height, spectrumMediaPlayer, barCount);
//		this.radius = radius;
//		this.speed = speed;
	}
	
	@Override
	protected void reconstructElements() {
		elements.getChildren().clear();
		
	}
	
	@Override
	protected void update() {}
	
	@Override
	protected void onLayoutXChange(double newLayoutX) {}
	
	@Override
	protected void onLayoutYChange(double newLayoutY) {}
	
	@Override
	protected void onWidthResize(double newWidth) {}
	
	@Override
	protected void onHeightResize(double newHeight) {}
	
}
