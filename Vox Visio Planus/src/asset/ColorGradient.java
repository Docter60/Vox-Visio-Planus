package asset;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import math.Mathg;

public class ColorGradient {

	private List<Color> nodes;

	public ColorGradient() {
		this.nodes = new ArrayList<Color>();
		this.addNode(Color.GRAY);
		this.addNode(Color.GRAY);
	}

	public void addNode(Color color) {
		nodes.add(color);
	}

	public void setNode(int index, Color color) {
		if (index < nodes.size())
			nodes.set(index, color);
		else
			System.err.println("Node index out of range!");
	}

	public void clearNodes() {
		nodes.clear();
	}

	public Color getColor(float lerp_t) {
		if (lerp_t > 1) {
			System.err.println("lerp_t cannot be more than 1!");
			return null;
		}

		float nodeSpread = 1f / (float) nodes.size();
		int index = (int) Math.floor((float) nodes.size() * lerp_t);
		float nodeLerp_t = (lerp_t - index * nodeSpread) * nodes.size();

		double r1 = nodes.get(index).getRed();
		double g1 = nodes.get(index).getGreen();
		double b1 = nodes.get(index).getBlue();
		double r2;
		double g2;
		double b2;

		if (index + 1 < nodes.size()) {
			r2 = nodes.get(index + 1).getRed();
			g2 = nodes.get(index + 1).getGreen();
			b2 = nodes.get(index + 1).getBlue();
		} else {
			r2 = r1;
			g2 = g1;
			b2 = b1;
		}

		double r = Mathg.lerp(r1, r2, nodeLerp_t);
		double g = Mathg.lerp(g1, g2, nodeLerp_t);
		double b = Mathg.lerp(b1, b2, nodeLerp_t);

		return new Color(r, g, b, 1.0);
	}

	public void configureRainbowGradient() {
		clearNodes();
		addNode(Color.RED);
		addNode(Color.ORANGE);
		addNode(Color.YELLOW);
		addNode(Color.GREEN);
		addNode(Color.CYAN);
		addNode(Color.BLUE);
		addNode(Color.MAGENTA);
	}
}
