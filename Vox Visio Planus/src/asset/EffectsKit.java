package asset;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class EffectsKit {

	private Group groupReference;

	private ColorGradient colorGradient;
	
	private boolean isFillRainbow;
	private boolean isStrokeRainbow;
	
	private double bloomValue;
	private double glowValue;

	private Bloom bloom;
	// private BoxBlur boxBlur;
	private Glow glow;
	// private MotionBlur motionBlur;
	// private GaussianBlur gaussianBlur;
	// private DropShadow dropShadow;
	// private InnerShadow innerShadow;
	// private Reflection reflection;
	// private Lighting lighting; // May want a seperate class for lighting
	// private PerspectiveTransform pt; // May want a separate class for pt

	public EffectsKit(Group groupReference) {
		this.groupReference = groupReference;

		this.colorGradient = new ColorGradient();

		this.bloom = new Bloom();
		this.glow = new Glow();
		
		this.groupReference.setEffect(bloom);
		this.groupReference.setEffect(glow);
		
		isFillRainbow = false;
		isStrokeRainbow = false;
		
		bloomValue = 0.0;
		glowValue = 0.0;
	}

	public void setFillRainbow() { // Propbably should make this take in a boolean
		colorGradient.configureRainbowGradient();
		int elementCount = groupReference.getChildren().size();
		for (int i = 0; i < elementCount; i++) {
			Shape s = ((Shape) groupReference.getChildren().get(i));
			s.setFill(colorGradient.getColor(i / (float) elementCount));
		}
		isFillRainbow = true;
	}

	public void setStrokeRainbow() {
		colorGradient.configureRainbowGradient();
		int elementCount = groupReference.getChildren().size();
		for (int i = 0; i < elementCount; i++) {
			Shape s = ((Shape) groupReference.getChildren().get(i));
			s.setStroke(colorGradient.getColor(i / (float) elementCount));
		}
		isStrokeRainbow = true;
	}
	
	public void setColorStrokeAll(Color color) {
		int elementCount = groupReference.getChildren().size();
		for(int i = 0; i < elementCount; i++) {
			Node node = groupReference.getChildren().get(i);
			((Circle) node).setStroke(color);
		}
	}
	
	public void setColorFillAll(Color color) {
		int elementCount = groupReference.getChildren().size();
		for(int i = 0; i < elementCount; i++) {
			Node node = groupReference.getChildren().get(i);
			((Circle) node).setFill(color);
		}
	}

	public void setGlow(double value) {
		if (value > 1.0)
			value = 1.0;
		if (value < 0.0)
			value = 0.0;
		glow.setLevel(value);
		glowValue = value;
	}
	
	public void setBloom(double value) {
		if (value > 1.0)
			value = 1.0;
		if (value < 0.0)
			value = 0.0;
		bloom.setThreshold(value);
		bloomValue = value;
	}
	
	public void reapply() {
		if (isFillRainbow)
			setFillRainbow();
		if (isStrokeRainbow)
			setStrokeRainbow();
		if (glowValue > 0.0)
			setGlow(glowValue);
		if(bloomValue > 0.0)
			setBloom(bloomValue);
	}
}
