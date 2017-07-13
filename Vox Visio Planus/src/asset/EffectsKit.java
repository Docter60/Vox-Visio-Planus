package asset;

import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.shape.Shape;

public class EffectsKit {

	private Group groupReference;

	private ColorGradient colorGradient;

	//private Bloom bloom;
	//private BoxBlur boxBlur;
	private Glow glow;
	//private MotionBlur motionBlur;
	//private GaussianBlur gaussianBlur;
	//private DropShadow dropShadow;
	//private InnerShadow innerShadow;
	//private Reflection reflection;
	// private Lighting lighting; // May want a seperate class for lighting
	// private PerspectiveTransform pt; // May want a separate class for pt

	public EffectsKit(Group groupReference) {
		this.groupReference = groupReference;

		this.colorGradient = new ColorGradient();

		this.glow = new Glow();
		this.groupReference.setEffect(glow);
	}

	public void setFillRainbow() {
		colorGradient.configureRainbowGradient();
		for (int i = 0; i < groupReference.getChildren().size(); i++) {
			Shape s = ((Shape) groupReference.getChildren().get(i));
			s.setFill(colorGradient.getColor((float) i / (float) groupReference.getChildren().size()));
		}
	}

	public void setStrokeRainbow() {
		colorGradient.configureRainbowGradient();
		for (int i = 0; i < groupReference.getChildren().size(); i++) {
			Shape s = ((Shape) groupReference.getChildren().get(i));
			s.setStroke(colorGradient.getColor((float) i / (float) groupReference.getChildren().size()));
		}
	}

	public void setGlow(double value) {
		if (value > 1.0)
			value = 1.0;
		if (value < 0.0)
			value = 0.0;
		glow.setLevel(value);
	}
}
