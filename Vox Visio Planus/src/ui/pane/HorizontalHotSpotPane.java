/**
 * 
 */
package ui.pane;

/**
 * @author Docter60
 *
 */
public abstract class HorizontalHotSpotPane extends HotSpotPane{

	private int goLeft;
	
	protected HorizontalHotSpotPane(String title, double x, double y, double width, double height) {
		super(title, x, y, width, height);
		this.goLeft = 1;
	}
	
	@Override
	public void hotSpotUpdate() {
		if(this.showPane)
			this.setTranslateX(interpolator.interpolate(this.getTranslateX(), goLeft * this.getWidth(), 0.07));
		else
			this.setTranslateX(interpolator.interpolate(this.getTranslateX(), 0, 0.07));
	}
	
	public void setMoveLeft(boolean goesLeft) {
		if(goesLeft)
			this.goLeft = -1;
		else
			this.goLeft = 1;
	}

}
