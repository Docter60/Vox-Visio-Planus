/**
 * 
 */
package ui.pane;

/**
 * @author Docter60
 *
 */
public abstract class VerticalHotSpotPane extends HotSpotPane{

	private int goUp;
	
	protected VerticalHotSpotPane(String title, double x, double y, double width, double height) {
		super(title, x, y, width, height);
		this.goUp = 1;
	}

	@Override
	public void hotSpotUpdate() {
		if(this.showPane)
			this.setTranslateY(interpolator.interpolate(this.getTranslateY(), goUp * this.getHeight(), 0.07));
		else
			this.setTranslateY(interpolator.interpolate(this.getTranslateY(), 0, 0.07));
	}
	
	public void setMovesUp(boolean goesUp) {
		if(goesUp)
			this.goUp = -1;
		else
			this.goUp = 1;
	}
	
}
